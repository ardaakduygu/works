/**
 *
 * @author Arda AKDUYGU,İlker Cihan DURMUŞ
 *
 *
 */
package spellchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class SpellChecker {

    public static void main(String[] args) {

        SpellChecker spellChecker = new SpellChecker();
        DictHashTable<String> dictHashTable = new DictHashTable<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSpell Checker Menu:");
            System.out.println("1. Load dictionary from a text file");
            System.out.println("2. Search for an entry in the dictionary");
            System.out.println("3. Insert a word into the dictionary");
            System.out.println("4. Delete a word from the dictionary");
            System.out.println("5. Spell check a text file");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter the dictionary file name: ");
                    String dictFileName = scanner.nextLine();

                    try {
                        List<String> lines = Files.readAllLines(Paths.get(dictFileName));
                        for (String line : lines) {
                            System.out.println(" Adding " + line.trim());
                            dictHashTable.addValue(line.trim().toLowerCase());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Dictionary loaded successfully.");
                    break;
                case 2:
                    System.out.print("Enter the word to search: ");
                    String searchWord = scanner.nextLine();
                    System.out.println(dictHashTable.containsValue(searchWord.toLowerCase()));
                    break;
                case 3:
                    System.out.print("Enter the word to insert: ");
                    String insertWord = scanner.nextLine();
                    if (dictHashTable.containsValue(insertWord.toLowerCase())) {
                        System.out.println("Already inserted.");
                    } else {
                        dictHashTable.addValue(insertWord.toLowerCase());
                        System.out.println("Word inserted successfully.");
                    }
                    break;
                case 4:
                    System.out.print("Enter the word to delete: ");
                    String deleteWord = scanner.nextLine();
                    if (dictHashTable.containsValue(deleteWord.toLowerCase())) {
                        dictHashTable.delete(deleteWord.toLowerCase());
                        System.out.println("Word deleted successfully.");
                    } else {

                        System.out.println("Word not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter the file name to spell check: ");
                    String fileName = scanner.nextLine();

                    try {
                        Path filePath = Paths.get(fileName);
                        List<String> lines = Files.readAllLines(filePath);

                        for (String line : lines) {
                            String[] words = line.split("\\s+");
                            for (String word : words) {
                                String cleanWord = word.replaceAll("[^a-zA-Z]", "");

                                if (!dictHashTable.containsValue(cleanWord.toLowerCase())) {
                                    System.out.println("Incorrect word: " + word);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    System.out.println("Exiting Spell Checker. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

}
