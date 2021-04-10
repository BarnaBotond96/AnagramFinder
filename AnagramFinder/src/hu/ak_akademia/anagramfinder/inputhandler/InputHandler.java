package hu.ak_akademia.anagramfinder.inputhandler;

import java.util.Scanner;

public class InputHandler {

	private Scanner scanner;

	public InputHandler(Scanner scanner) {
		this.scanner = scanner;
	}

	public int readInt(String message) {
		System.out.print(message);
		int number = scanner.nextInt();
		scanner.nextLine();
		return number;
	}
}