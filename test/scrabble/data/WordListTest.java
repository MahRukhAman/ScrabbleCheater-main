package scrabble.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordListTest {

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"just two", "ba", new String[]{"ab", "ba"},
                        new String[]{"ab", "ba"},
                        new String[]{"ab", "ba"}},
                {"just two with three characters", "abc",
                        new String[]{"abc", "cba"},
                        new String[]{"abc", "cba"},
                        new String[]{"abc", "cba"}},
                {
                        "the correct number from the word list",
                        "abc",
                        new String[]{"abc", "cba"},
                        new String[]{"ab", "bc", "abc", "cba"},
                        new String[]{"ab", "bc", "abc", "cba"}},
                {"the correct number from the word list II", "abc",
                        new String[]{"cab", "cba"},
                        new String[]{"cab", "cba"},
                        new String[]{"cab", "cba"}},
                {
                        "no permutations, but shorter suggestions",
                        "abcd",
                        new String[]{},
                        new String[]{"cab", "cba"},
                        new String[]{"cab", "cba"}},
                {"only return suggestions that are in wordlist", "abc",
                        new String[]{"abc"}, new String[]{"abc", "cb"},
                        new String[]{"abc", "cb"}}});
    }

    WordList wl;

    @BeforeEach
    public void createWordList() {
        wl = new OwnHashWordList();
    }

    @ParameterizedTest
    @MethodSource("data")
    public void sizeShouldGiveTotalNumberOfStoredWords(String message, String tileRack, String[] permutations,
                                                       String[] validSuggestions, String[] words) {
        wl.addAll(Arrays.asList(words));
        System.out.println("Added words: " + Arrays.toString(words));
        System.out.println("Word list size: " + wl.size());
        assertEquals(words.length, wl.size(), message);
    }

    @ParameterizedTest
    @MethodSource("data")
    public void shouldReturnCorrectAmountOfPermutations(String message, String tileRack, String[] permutations,
                                                        String[] validSuggestions, String[] wordsInWordList) {
        wl.addAll(Arrays.asList(wordsInWordList));
        int permutationCount = permutations.length;
        assertEquals(permutationCount, wl.validWordsUsingAllTiles(tileRack).size(), message);
    }

    @ParameterizedTest
    @MethodSource("data")
    public void shouldReturnCorrectNumberOfSuggestions(String message, String tileRack, String[] permutations,
                                                       String[] validSuggestions, String[] wordsInWordList) {
        wl.addAll(Arrays.asList(wordsInWordList));
        int wordSuggestionCount = validSuggestions.length;
        Set<String> actualSuggestions = wl.allValidWords(tileRack);
        System.out.println("Running test: " + message);
        System.out.println("Expected suggestions: " + Arrays.toString(validSuggestions));
        System.out.println("Actual suggestions: " + actualSuggestions);
        assertEquals(wordSuggestionCount, actualSuggestions.size(), message);
    }
}
