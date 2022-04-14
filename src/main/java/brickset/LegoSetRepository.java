package brickset;

import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {
    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /** 1. Check if LEGO sets contains a certain tag
     * @param tag a LEGO set tag
     * @return a boolean value
     */
    public boolean checkIfThereExistsTagMicroscale(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null)
                .anyMatch(LegoSet -> LegoSet.getTags().contains(tag));
    }

    /** 2. Count the number of members whose Tag starts with the given letter
     * @param tag the start letter
     * @return the number of bricksets with the given specified start letter.
     */
    public long countTheOccurrencesOfTheSpecifiedTagWithStratLetter(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null)
                .flatMap(legoSet -> legoSet.getTags().stream())
                .filter(s->s.startsWith(tag))
                .count();
    }

    /** 3. Find the largest piece in stock
     * @return the max pieces of LEGO sets
     */
    public int findTheMaxPieces() {
        return getAll().stream()
                .map(LegoSet::getPieces)
                .reduce(0, Integer::max);
    }

    /**
     * 4. Count the number of LegoSets that pieces >1000
     * @return a boolen map for example {false=471, true=16} which means there's 16 LegoSets whose pieces>1000
     */
    public Map<Boolean,Long> findTheNumOfPiece() {
        return getAll().stream()
                .collect(Collectors.partitioningBy((LegoSet->LegoSet.getPieces() > 1000), counting()));
    }

    /**
     * 5. Count the number of each Theme (by grouping, not include null)
     * @return the name and number of themes
     */
    public Map<String, Long> countTheNumberOfEachTheme(){
        return getAll().stream()
                .filter(legoSet -> legoSet.getTheme() != null)
                .collect(Collectors.groupingBy(LegoSet::getTheme, counting()));
    }


    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        // Method 1
        System.out.println(repository.checkIfThereExistsTagMicroscale("Microscale"));
        System.out.println();
        // Method 2
        System.out.println(repository.countTheOccurrencesOfTheSpecifiedTagWithStratLetter("A"));
        System.out.println();
        // Method 3
        System.out.println(repository.findTheMaxPieces());
        System.out.println();
        //Method 4
        System.out.println(repository.findTheNumOfPiece());
        System.out.println();
        //Method 5
        System.out.println(repository.countTheNumberOfEachTheme());
    }
}