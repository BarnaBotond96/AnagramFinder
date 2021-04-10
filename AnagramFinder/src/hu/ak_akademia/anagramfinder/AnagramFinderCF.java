package hu.ak_akademia.anagramfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import hu.ak_akademia.anagramfinder.inputhandler.InputHandler;
import hu.ak_akademia.anagramfinder.validator.NumberValidator;
import hu.ak_akademia.anagramfinder.validator.Validator;

public class AnagramFinderCF {

	public static void main(String[] args) throws FileNotFoundException {
		new AnagramFinderCF().run();
	}

	private void run() throws FileNotFoundException {
		try (Scanner scanner = new Scanner(System.in)) {
			InputHandler in = new InputHandler(scanner);
			int sizeOfGroups;
			boolean isValid;
			do {
				sizeOfGroups = in.readInt("Mekkorák legyenek legalább az Anagramma csoportok?\n");
				Validator validator = new NumberValidator(sizeOfGroups);
				isValid = validator.isValid();
				if (!isValid) {
					System.out.println("Érvénytelen méret!\n");
				}
			} while (!isValid);
			System.out.println("A legalább " + sizeOfGroups + " elemű anagramma csoportok a következők:\n");
			List<String> words = loadWords("resources/Szólista.txt");
			Map<String, List<String>> anagramMap = buildMap(words);
			printMap(anagramMap, sizeOfGroups);
		}
	}

	private static List<String> loadWords(String fileName) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(fileName))) {
			List<String> words = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String word = scanner.nextLine();
				words.add(word);
			}
			return words;
		}
	}

	private static Map<String, List<String>> buildMap(List<String> words) {
		Map<String, List<String>> anagramMap = new TreeMap<>();
		for (String word : words) {
			String keyWord = sortLetters(word);
			if (anagramMap.containsKey(keyWord)) {
				anagramMap.get(keyWord).add(word);
			} else {
				List<String> newWord = new ArrayList<>();
				newWord.add(word);
				anagramMap.put(keyWord, newWord);
			}
		}
		return anagramMap;
	}

	private static String sortLetters(String words) {
		char[] chars = words.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

	private static void printMap(Map<String, List<String>> anagramMap, int sizeOfGroups) {
		Set<Entry<String, List<String>>> entrySet = anagramMap.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			if (entry.getValue().size() >= sizeOfGroups) {
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}
		}
	}
}