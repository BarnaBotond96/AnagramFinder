package hu.ak_akademia.anagramfinder.validator;

public class NumberValidator implements Validator {

	private int userChoice;

	public NumberValidator(int userChoice) {
		this.userChoice = userChoice;
	}

	@Override
	public boolean isValid() {
		if (userChoice > 1) {
			return true;
		}
		return false;
	}
}