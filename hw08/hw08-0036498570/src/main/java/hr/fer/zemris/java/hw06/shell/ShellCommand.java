package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Representation of {@link MyShell} command.
 * 
 * @author Andrej Ceraj
 *
 */
public interface ShellCommand {
	/**
	 * Executes the command using {@link Environment} and command arguments.
	 * 
	 * @param env       Environment
	 * @param arguments Command arguments
	 * @return {@code MyShell} status after the execution of the command
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * @return command name
	 */
	String getCommandName();

	/**
	 * @return command description
	 */
	List<String> getCommandDescription();
}
