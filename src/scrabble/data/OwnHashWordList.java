package scrabble.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class OwnHashWordList implements WordList {
    private LinkedList<String>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public OwnHashWordList() {
        // Initialize table with a prime number of buckets
        table = new LinkedList[101]; // You can choose a better prime number
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    private int hashFunction(String word) {
        return Math.abs(word.hashCode()) % table.length;
    }

    @Override
    public boolean add(String word) {
        int index = hashFunction(word);
        if (!table[index].contains(word)) {
            table[index].add(word);
            size++;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<String> words) {
        return false;
    }

    @Override
    public boolean contains(String word) {
        int index = hashFunction(word);
        return table[index].contains(word);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public WordList initFromFile(String fileName) {
        return null;
    }

    @Override
    public Set<String> allValidWords(String tileRack) {
        Set<String> validWords = new HashSet<>();
        for (LinkedList<String> list : table) {
            for (String word : list) {
                if (canFormWord(tileRack, word)) {
                    validWords.add(word);
                }
            }
        }
        return validWords;
    }

    @Override
    public Set<String> validWordsUsingAllTiles(String tileRack) {
        Set<String> validWords = new HashSet<>();
        for (LinkedList<String> list : table) {
            for (String word : list) {
                if (canFormWord(tileRack, word)) {
                    validWords.add(word);
                }
            }
        }
        return validWords;
    }

    private boolean canFormWord(String tileRack, String word) {
        char[] tiles = tileRack.toCharArray();
        for (char c : word.toCharArray()) {
            int index = new String(tiles).indexOf(c);
            if (index == -1) {
                return false;
            }
            tiles[index] = ' ';
        }
        return true;
    }

    public int getLongestChainLength() {
        int maxLength = 0;
        for (LinkedList<String> list : table) {
            if (list.size() > maxLength) {
                maxLength = list.size();
            }
        }
        return maxLength;
    }

    public int getNumberOfCollisions() {
        int collisions = 0;
        for (LinkedList<String> list : table) {
            if (list.size() > 1) {
                collisions += list.size() - 1;
            }
        }
        return collisions;
    }
}
