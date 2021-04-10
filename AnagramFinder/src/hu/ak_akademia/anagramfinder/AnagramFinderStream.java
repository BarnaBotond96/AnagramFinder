package hu.ak_akademia.anagramfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import hu.ak_akademia.anagramfinder.inputhandler.InputHandler;
import hu.ak_akademia.anagramfinder.validator.NumberValidator;
import hu.ak_akademia.anagramfinder.validator.Validator;

public class AnagramFinderStream {

	public static void main(String[] args) throws IOException {
		new AnagramFinderStream().run();
	}

	private void run() throws IOException {
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
			final int finalSize = sizeOfGroups;
			System.out.println("A legalább " + sizeOfGroups + " elemű anagramma csoportok a következők:\n");

			Files.lines(Path.of("resources/Szólista.txt"))
				.filter(word -> word.length() >= 1)
				.collect(Collectors.toMap(word -> word.chars()
						.sorted()
						.mapToObj(code -> Character.toString(code))
						.collect(Collectors.joining()), word -> List.of(word), (list1, list2) -> {
							List<String> result = new ArrayList<>();
								result.addAll(list1);
								result.addAll(list2);
								return result;
							}))
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue().size() >= finalSize)
				.forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));
		}
	}
}