package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command clears stack that used to contain paths.
 * 
 * @author Andrej Ceraj
 *
 */
public class DropdShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "dropd";
	/**
	 * Shared data map keyword for stack
	 */
	private static final String STACK = "stack";
	/**
	 * Command description
	 */
	List<String> description = new ArrayList<String>();
	{
		description.add("Expects no arguments");
		description.add("Clears the paths stack");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command 'dropd' expects no arguments");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK);
		if (stack == null || stack.isEmpty()) {
			env.writeln("Stack is empty");
			return ShellStatus.CONTINUE;
		}
		stack.pop();
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
