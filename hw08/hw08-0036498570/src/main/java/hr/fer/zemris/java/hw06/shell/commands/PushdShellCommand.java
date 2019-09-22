package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.ArgumentUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command pushes the current directory path to the shell paths stack and
 * changes shell current directory to the given value.
 * 
 * @author Andrej Ceraj
 *
 */
public class PushdShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "pushd";
	/**
	 * Shared data map keyword for stack
	 */
	private static final String STACK = "stack";
	/**
	 * Command description
	 */
	List<String> description = new ArrayList<String>();
	{
		description.add("Expects one argument - directory name or absolute path");
		description.add("Pushes current directory path to the shell paths stack and changes");
		description.add("current directory to the given argument");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] words;
		try {
			words = ArgumentUtil.parseArguments(arguments);
		} catch (IllegalArgumentException exception) {
			env.writeln("Illegal argument");
			return ShellStatus.CONTINUE;
		}
		if (words.length != 1) {
			env.writeln("Invalid number of arguments for command 'pushd'");
			return ShellStatus.CONTINUE;
		}

		Path resolvedPath = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		try {
			resolvedPath = resolvedPath.toRealPath();
		} catch (IOException e) {
			env.writeln("Path: " + resolvedPath + " does not exist");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK);
		if (stack == null) {
			stack = new Stack<Path>();
		}
		stack.push(env.getCurrentDirectory());
		env.setSharedData(STACK, stack);
		env.setCurrentDirectory(resolvedPath);
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
