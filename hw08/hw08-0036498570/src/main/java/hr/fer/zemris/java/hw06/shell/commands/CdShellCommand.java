package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command changes shell current directory.
 * 
 * @author Andrej Ceraj
 *
 */
public class CdShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "cd";
	/**
	 * Command description
	 */
	List<String> description = new ArrayList<String>();
	{
		description.add("Expects one argument - directory name or absolute path");
		description.add("Changes current directory to the given argument");
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
			env.writeln("Invalid number of arguments for command 'cd'");
			return ShellStatus.CONTINUE;
		}

		Path resolvedPath = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		try {
			resolvedPath = resolvedPath.toRealPath();
		} catch (IOException e) {
			env.writeln("Path: " + resolvedPath + " does not exist");
			return ShellStatus.CONTINUE;
		}
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
