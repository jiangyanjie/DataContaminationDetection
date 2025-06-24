package application.prompts;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Ewan C. Burns
 * Class to prompt a console output permitting a user to select a command to 
 * execute.
 */
public class ConsolePrompt {
	/**
	 * The list of possible actions.
	 */
	private List<ConsoleOption> options;

	/**
	 * Default constructor for a ConsolePrompt.
	 */
	public ConsolePrompt() {
		this.options = new ArrayList<ConsoleOption>();
	}

	/**
	 * Method to add an option the console prompt.
	 * @param optionIn The ConsoleOption to add
	 */
	public final void addOption(final ConsoleOption optionIn) {
		this.options.add(optionIn);
	}

	/**
	 * Method to add an option the console prompt.
	 * @param promptIn The string of characters to prompt to the user
	 * @param handlerIn The function to call if the option is selected
	 */
	public final void addOption(
			final String promptIn,
			final Callable<Void> handlerIn) {
		this.options.add(new ConsoleOption(promptIn, handlerIn));
	}

	/**
	 * Method to prompt the user with the current options.
	 */
	public final void run() {
		int optionCount = this.options.size();

		if (optionCount < 1) {
			System.out.println(
					"No options for you to choose from.");
		} else {
			int input = -1;
			ConsoleOption selected = null;

			do {
				System.out.println(
						"Please select one of the following option.");
				for (int i = 0; i < optionCount; i++) {
					System.out.println(
							"\t" + this.options.get(i).getPrompt(i + 1));
				}
				try {
					input = RequestInput.requestInt("");
				} catch (InputMismatchException e) {
					e.printStackTrace();
				}
			} while (input < 0 || input > optionCount);

			if (input > 0) {
				selected = this.options.get(input - 1);
			}

			if (selected != null) {
				System.out.println("Running: " + selected.getPrompt());
				try {
					selected.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("No item selected, aborting");
			}
		}
	}
}
