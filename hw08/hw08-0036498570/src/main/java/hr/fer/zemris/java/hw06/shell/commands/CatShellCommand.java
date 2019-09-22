package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
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
 * Command prints out file content given by the first command argument,
 * interpreting it using either default charset or charset given by the second
 * command argument.
 * 
 * @author Andrej Ceraj
 *
 */
public class CatShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "cat";
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects one or two arguments - file name and charset (optional)");
		description.add("If the second argument is not provided, a default platfom charset");
		description.add("is used to interpret chars from bytes.");
		description.add("Command opens the given file and writes its content to console.");
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
		if (words.length != 1 && words.length != 2) {
			env.writeln("Invalid number of arguments for command 'cat'");
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		if (words.length == 2 && !Charset.availableCharsets().containsKey(words[1])) {
			env.writeln("Nonexisting charset");
			return ShellStatus.CONTINUE;
		}
		Charset charset = words.length == 2 ? Charset.forName(words[1]) : Charset.defaultCharset();

		try {
			BufferedReader reader = Files.newBufferedReader(path, charset);
			String line;
			while ((line = reader.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Unable to read file");
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
