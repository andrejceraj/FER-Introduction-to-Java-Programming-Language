package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command lists all paths stored in paths stack.
 * 
 * @author Andrej Ceraj
 *
 */
public class ListdShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "listd";
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
		description.add("Lists all paths stored in paths stack");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command 'listd' expects no arguments");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK);
		if (stack != null) {
			ListIterator<Path> iterator = stack.listIterator(stack.size());
			while (iterator.hasPrevious()) {
				env.writeln(iterator.previous().toString());
			}
		}
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
