package hr.fer.zemris.java.hw17.commands;

import hr.fer.zemris.java.hw17.Environment;
import hr.fer.zemris.java.hw17.Status;

/**
 * Outputs the search results from the last query searched
 * 
 * @author Andrej Ceraj
 *
 */
public class ResultsCommand implements Command {

	@Override
	public Status execute(Environment env, String arguments) {
		if (arguments != null) {
			env.write("Command 'results' takes no arguments.");
			return Status.CONTINUE;
		}
		env.showResults();
		return Status.CONTINUE;
	}

}
