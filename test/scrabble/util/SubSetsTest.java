package scrabble.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubSetsTest {

	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "a", new String[] {"a"} },
				{ "ab", new String[] { "ab", "a", "b" } },
				{ "java", new String[] { "java", "jav", "jaa", "jv", "ja", "av", "aa", "j", "va", "v", "a", "ava", "jva" } },
				{ "abcd", new String[] { "abcd", "abc", "abd", "ab", "acd", "ac", "ad", "a", "bcd", "bc", "bd", "b", "cd", "c", "d" } }
		});
	}

	@ParameterizedTest
	@MethodSource("data")
	public void testComputeSubSets(String str, String[] list) {
		Set<String> expected = new HashSet<>(Arrays.asList(list));
		Set<String> actual = SubSets.getSubSets(str);
		actual.remove(""); // Remove empty string from the actual results if not expected
		System.out.println("Input: " + str);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
		assertEquals(expected, actual);
	}
}
