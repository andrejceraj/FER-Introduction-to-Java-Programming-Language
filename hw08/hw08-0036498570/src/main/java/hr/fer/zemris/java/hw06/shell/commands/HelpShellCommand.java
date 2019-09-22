package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.ArgumentUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * If the command is called with no arguments it lists all {@link MyShell}
 * commands. If it is called with one argument that is any command name, it
 * prints that command's description.
 * 
 * @author Andrej Ceraj
 *
 */
public class HelpShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "help";
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects zero or one argument - command name (optional)");
		description.add("If the argument is given, it prints description of the command.");
		description.add("Otherwise it lists all available commands and their descriptions.");
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
		if (words.length != 0 && words.length != 1) {
			env.writeln("Invalid number of arguments for command 'help'");
			return ShellStatus.CONTINUE;
		}
		SortedMap<String, ShellCommand> commands = env.commands();
		if (words.length == 1) {
			if (commands.containsKey(words[0])) {
				env.writeln(commands.get(words[0]).getCommandName());
				printDescription(env, commands.get(words[0]).getCommandDescription());
			} else {
				env.writeln("Command not recognized");
				return ShellStatus.CONTINUE;
			}
		} else {
			for (Entry<String, ShellCommand> entry : commands.entrySet()) {
				env.writeln(entry.getValue().getCommandName());
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

	/**
	 * Prints given description using the given environment.
	 * 
	 * @param env         environment
	 * @param description description
	 */
	private void printDescription(Environment env, List<String> description) {
		for (String line : description) {
			env.writeln(line);
		}
	}

}
