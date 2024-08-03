package scrabble;

import scrabble.data.OwnHashWordList;
import scrabble.data.WordList;

import java.util.Scanner;
import java.util.Set;

public class ScrabbleCheater {

    public static void main(String[] args) {
        // Initialize the word list (choose between SimpleWordList or OwnHashWordList)
        WordList wordList = new OwnHashWordList(); // Change to SimpleWordList if needed

        // Use the correct file path
        String filePath = "wordlists/sowpods.txt";
        System.out.println("Reading word list from: " + filePath);
        wordList.initFromFile(filePath);

        // Print hash table statistics if using OwnHashWordList
        if (wordList instanceof OwnHashWordList) {
            ((OwnHashWordList) wordList).printStatistics();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your tile rack: ");
        String tileRack = scanner.nextLine();

        System.out.println("Choose an option:");
        System.out.println("1. Find valid words using all tiles");
        System.out.println("2. Find all valid words that can be formed");
        int choice = scanner.nextInt();

        if (choice == 1) {
            Set<String> validWords = wordList.validWordsUsingAllTiles(tileRack);
            System.out.println("Valid words using all tiles: " + validWords);
        } else if (choice == 2) {
            Set<String> validWords = wordList.allValidWords(tileRack);
            System.out.println("All valid words: " + validWords);
        } else {
            System.out.println("Invalid choice");
        }
    }
}
