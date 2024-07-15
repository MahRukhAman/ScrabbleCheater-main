package scrabble.util;

import java.util.Arrays;

public class Permutation {
	private String word;
	private String normalized;

	public Permutation(String word) {
		this.word = word;
		this.normalized = normalize(word);
	}

	private String normalize(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

	public String getNormalized() {
		return normalized;
	}

	@Override
	public int hashCode() {
		return normalized.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Permutation that = (Permutation) obj;
		return normalized.equals(that.normalized);
	}

	@Override
	public String toString() {
		return "[" + normalized + "] " + word;
	}

	public String getWord() {
		return word;
	}
}
