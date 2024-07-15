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
        Permutation rackPermutation = new Permutation(tileRack);

        for (Map.Entry<String, Set<String>> entry : wordMap.entrySet()) {
            Permutation wordPermutation = new Permutation(entry.getKey());
            if (rackPermutation.equals(wordPermutation)) {
                validWords.addAll(entry.getValue());
            }
        }

        return validWords;
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
        return false;
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
