package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command exits {@link MyShell}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ExitShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "exit";

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
			env.writeln("Command 'exit' expects no arguments");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Expects no arguments");
		description.add("Exits MyShell.");
		return Collections.unmodifiableList(description);
	}

}
