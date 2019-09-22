package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command lists all charsets supported for this Java platform.
 * 
 * @author Andrej Ceraj
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "charsets";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] words;
		try {
			words = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException exception) {
			env.writeln("Illegal argument");
			return ShellStatus.CONTINUE;
		}
		if (words.length != 0) {
			env.writeln("Command 'charsets' expects no arguments");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		for (Map.Entry<String, Charset> entry : charsets.entrySet()) {
			env.writeln(entry.getValue().toString());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Expects no arguments");
		description.add("Lists names of supported charsets for this Java platform.");
		return Collections.unmodifiableList(description);
	}

}
