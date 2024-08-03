package scrabble.data;

import scrabble.util.Permutation;
import scrabble.util.SubSets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OwnHashWordList implements WordList {

    private static final int INITIAL_TABLE_SIZE = 200000;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f; // Aggressive threshold for resizing
    private List<TreeSet<String>> hashTable;
    private int size;
    private int longestChain;

    public OwnHashWordList() {
        this.hashTable = new ArrayList<>(INITIAL_TABLE_SIZE);
        for (int i = 0; i < INITIAL_TABLE_SIZE; i++) {
            hashTable.add(new TreeSet<>());
        }
        this.size = 0;
        this.longestChain = 0;
    }

    private int getHash(String key, int tableSize) {
        int hash = fnv1aHash(key);
        return Math.abs(hash) % tableSize;
    }

    private int fnv1aHash(String key) {
        final int FNV_32_PRIME = 0x01000193;
        final int FNV_32_INIT = 0x811c9dc5;
        int hash = FNV_32_INIT;
        for (byte b : key.getBytes()) {
            hash ^= b;
            hash *= FNV_32_PRIME;
        }
        return hash;
    }

    @Override
    public Set<String> validWordsUsingAllTiles(String tileRackPart) {
        Set<String> validWords = new HashSet<>();
        Permutation rackPermutation = new Permutation(tileRackPart);
        int hash = getHash(rackPermutation.getNormalized(), hashTable.size());

        for (String word : hashTable.get(hash)) {
            Permutation wordPermutation = new Permutation(word);
            if (rackPermutation.equals(wordPermutation)) {
                validWords.add(word);
            }
        }

        return validWords;
    }

    @Override
    public Set<String> allValidWords(String tileRack) {
        Set<String> validWords = new HashSet<>();
        Set<String> subsets = SubSets.getSubSets(tileRack);

        // Debugging output for subsets
        System.out.println("Generated subsets: " + subsets);

        for (String subset : subsets) {
            validWords.addAll(getAllPermutations(subset));
        }

        // Debugging output for permutations generated from subsets
        System.out.println("Permutations generated from subsets: " + validWords);

        Set<String> finalValidWords = new HashSet<>();
        for (String word : validWords) {
            int hash = getHash(new Permutation(word).getNormalized(), hashTable.size());
            if (hashTable.get(hash).contains(word)) {
                finalValidWords.add(word);
            }
        }

        return finalValidWords;
    }

    private Set<String> getAllPermutations(String str) {
        Set<String> permutations = new HashSet<>();
        permute("", str, permutations);
        return permutations;
    }

    private void permute(String prefix, String str, Set<String> permutations) {
        int n = str.length();
        if (n == 0) permutations.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permute(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), permutations);
        }
    }

    @Override
    public boolean add(String word) {
        if ((float) size / hashTable.size() > LOAD_FACTOR_THRESHOLD) {
            resizeHashTable();
        }
        String normalized = new Permutation(word).getNormalized();
        int hash = getHash(normalized, hashTable.size());
        boolean added = hashTable.get(hash).add(word);
        if (added) {
            size++;
            int chainLength = hashTable.get(hash).size();
            longestChain = Math.max(longestChain, chainLength);
        }
        return added;
    }

    private void resizeHashTable() {
        int newSize = hashTable.size() * 2;
        List<TreeSet<String>> newHashTable = new ArrayList<>(newSize);
        for (int i = 0; i < newSize; i++) {
            newHashTable.add(new TreeSet<>());
        }

        for (TreeSet<String> bucket : hashTable) {
            for (String word : bucket) {
                String normalized = new Permutation(word).getNormalized();
                int newHash = getHash(normalized, newSize);
                newHashTable.get(newHash).add(word);
            }
        }

        hashTable = newHashTable;
        longestChain = 0;
        for (TreeSet<String> bucket : hashTable) {
            longestChain = Math.max(longestChain, bucket.size());
        }
        System.out.println("Resized hash table to " + newSize + " entries.");
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
        int hash = getHash(normalized, hashTable.size());
        return hashTable.get(hash).contains(word);
    }

    @Override
    public int size() {
        return size;
    }

    public int getLongestChain() {
        return longestChain;
    }

    public int getTotalCollisions() {
        int collisions = 0;
        for (TreeSet<String> bucket : hashTable) {
            if (bucket.size() > 1) {
                collisions += bucket.size() - 1;
            }
        }
        return collisions;
    }

    public void printStatistics() {
        System.out.println("Number of entries: " + size());
        System.out.println("Number of collisions: " + getTotalCollisions());
        System.out.println("Longest chain length: " + getLongestChain());
    }

    @Override
    public WordList initFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                add(word);
                lineNumber++;
                if (lineNumber % 1000 == 0) {
                    System.out.println(lineNumber + " words added so far...");
                }
            }
            System.out.println("Total words added: " + lineNumber);
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
        return this;
    }
}
