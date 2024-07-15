package scrabble.util;

import java.util.HashSet;
import java.util.Set;

public class SubSets {

	/**
	 * Generates all subsets of a given string.
	 *
	 * @param str The input string
	 * @return Set of all subsets of the input string
	 */
	public static Set<String> getSubSets(String str) {
		Set<String> subsets = new HashSet<>();
		generateSubsets(str, 0, new StringBuilder(), subsets);
		return subsets;
	}

	/**
	 * Helper method to recursively generate subsets.
	 *
	 * @param str     The input string
	 * @param index   Current index in the input string
	 * @param current Current subset being constructed
	 * @param subsets Set to store generated subsets
	 */
	private static void generateSubsets(String str, int index, StringBuilder current, Set<String> subsets) {
		if (index == str.length()) {
			subsets.add(current.toString());
			return;
		}

		// Include the current character
		current.append(str.charAt(index));
		generateSubsets(str, index + 1, current, subsets);

		// Exclude the current character
		current.deleteCharAt(current.length() - 1);
		generateSubsets(str, index + 1, current, subsets);
	}
}
