package scrabble.data;

import scrabble.util.Permutation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleWordList implements WordList {

	private Set<String> wordSet;

	public SimpleWordList() {
		this.wordSet = new HashSet<>();
	}

	@Override
	public Set<String> validWordsUsingAllTiles(String tileRackPart) {
		Set<String> validWords = new HashSet<>();
		Permutation rackPermutation = new Permutation(tileRackPart);

		for (String word : wordSet) {
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
		Permutation rackPermutation = new Permutation(tileRack);

		for (String word : wordSet) {
			Permutation wordPermutation = new Permutation(word);
			if (rackPermutation.equals(wordPermutation)) {
				validWords.add(word);
			}
		}

		return validWords;
	}

	@Override
	public boolean add(String word) {
		return wordSet.add(word);
	}

	@Override
	public boolean addAll(Collection<String> words) {
		return wordSet.addAll(words);
	}

	@Override
	public boolean contains(String word) {
		return wordSet.contains(word);
	}

	@Override
	public int size() {
		return wordSet.size();
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
