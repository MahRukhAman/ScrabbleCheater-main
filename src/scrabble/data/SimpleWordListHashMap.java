package scrabble.data;

import scrabble.util.Permutation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SimpleWordListHashMap implements WordList {

    private Map<String, Set<String>> wordMap;

    public SimpleWordListHashMap() {
        this.wordMap = new HashMap<>();
    }

    @Override
    public Set<String> validWordsUsingAllTiles(String tileRackPart) {
        Set<String> validWords = new HashSet<>();
        Permutation rackPermutation = new Permutation(tileRackPart);

        for (Map.Entry<String, Set<String>> entry : wordMap.entrySet()) {
            Permutation wordPermutation = new Permutation(entry.getKey());
            if (rackPermutation.equals(wordPermutation)) {
                validWords.addAll(entry.getValue());
            }
        }

        return validWords;
    }

    @Override
    public Set<String> allValidWords(String tileRack) {
        Set<String> validWords = new HashSet<>();
        Set<String> subsets = generateSubsets(tileRack);

        System.out.println("Tile rack: " + tileRack);
        System.out.println("Generated subsets: " + subsets);

        for (String subset : subsets) {
            String normalizedSubset = new Permutation(subset).getNormalized();
            if (wordMap.containsKey(normalizedSubset)) {
                validWords.addAll(wordMap.get(normalizedSubset));
            }
        }

        System.out.println("Valid words: " + validWords);
        return validWords;
    }

    private Set<String> generateSubsets(String tileRack) {
        Set<String> subsets = new HashSet<>();
        generateSubsets("", tileRack, subsets);
        return subsets;
    }

    private void generateSubsets(String prefix, String remaining, Set<String> result) {
        if (!remaining.isEmpty()) {
            generateSubsets(prefix + remaining.charAt(0), remaining.substring(1), result);
            generateSubsets(prefix, remaining.substring(1), result);
        }
        if (!prefix.isEmpty()) {
            result.add(prefix);
        }
    }

    @Override
    public boolean add(String word) {
        String normalized = new Permutation(word).getNormalized();
        return wordMap.computeIfAbsent(normalized, k -> new HashSet<>()).add(word);
    }

    @Override
    public boolean addAll(Collection<String> words) {
        boolean modified = false;
        for (String word : words) {
            modified |= add(word);
        }
        return modified;
    }

    @Override
    public boolean contains(String word) {
        String normalized = new Permutation(word).getNormalized();
        return wordMap.containsKey(normalized) && wordMap.get(normalized).contains(word);
    }

    @Override
    public int size() {
        return wordMap.size();
    }

    @Override
    public WordList initFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
