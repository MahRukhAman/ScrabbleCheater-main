package scrabble;

import scrabble.data.SimpleWordList;
import scrabble.data.WordList;

import java.util.Scanner;
import java.util.Set;

public class ScrabbleCheater {

    public static void main(String[] args) {
        // Example usage with SimpleWordList
        WordList wordList = new SimpleWordList();
        wordList.initFromFile("wordlist.txt"); // Replace with your actual file path

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your tile rack: ");
        String tileRack = scanner.nextLine();

        Set<String> validWords = wordList.validWordsUsingAllTiles(tileRack);
        System.out.println("Valid words: " + validWords);
    }
}
