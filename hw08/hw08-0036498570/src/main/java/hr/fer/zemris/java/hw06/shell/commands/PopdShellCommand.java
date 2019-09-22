package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import java.util.Stack;;

/**
 * Command pops the path from the shell paths stack and sets the current
 * directory to the popped path.
 * 
 * @author Andrej Ceraj
 *
 */
public class PopdShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "popd";
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
		description.add("Pops the path from the shell paths stack and sets the current directory");
		description.add("to the popped path");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command 'popd' expects no arguments");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK);
		if (stack == null || stack.isEmpty()) {
			env.writeln("Stack is empty");
			return ShellStatus.CONTINUE;
		}
		Path path = stack.pop();
		if (!Files.exists(path)) {
			env.writeln("Popped directory does not exist");
			return ShellStatus.CONTINUE;
		}
		env.setCurrentDirectory(path);
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
