package hr.fer.zemris.java.hw17.commands;

import hr.fer.zemris.java.hw17.Environment;
import hr.fer.zemris.java.hw17.Status;

/**
 * Exits the search shell
 * 
 * @author Andrej Ceraj
 *
 */
public class ExitCommand implements Command {

	@Override
	public Status execute(Environment env, String arguments) {
		if(arguments != null) {
			env.write("Command 'exit' takes no arguments.");
			return Status.CONTINUE;
		}
		env.writeln("Goodbye.");
		return Status.TERMINATE;
	}

}
