package hr.fer.zemris.java.hw17.commands;

import hr.fer.zemris.java.hw17.Environment;
import hr.fer.zemris.java.hw17.SearchShell;
import hr.fer.zemris.java.hw17.Status;

/**
 * Interface used for commands
 * 
 * @author Andrej Ceraj
 *
 */
public interface Command {
	/**
	 * Executes the command
	 * 
	 * @param env       {@link SearchShell} environment
	 * @param arguments command arguments
	 * @return search shell status
	 */
	public Status execute(Environment env, String arguments);
}
