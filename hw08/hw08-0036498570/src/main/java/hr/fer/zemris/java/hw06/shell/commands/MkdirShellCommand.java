package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
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
 * Command creates a directory to a path given by the command argument.
 * 
 * @author Andrej Ceraj
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "mkdir";
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects one argument - directory name");
		description.add("Creates a directory if it does not already exist.");
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
			env.writeln("Invalid number of arguments for command 'mkdir'");
			return ShellStatus.CONTINUE;
		}
		Path path = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		if (Files.exists(path)) {
			env.writeln("Directory or file already exists");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.createDirectories(path);
			env.writeln("Directory created");
		} catch (IOException e) {
			env.writeln("Unable to create directory.");
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
