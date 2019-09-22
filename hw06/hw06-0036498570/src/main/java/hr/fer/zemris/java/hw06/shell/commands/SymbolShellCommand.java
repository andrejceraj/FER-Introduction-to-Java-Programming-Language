package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command either returns the current symbol for the given attribute, or sets
 * the new symbol for the given attribute.
 * 
 * @author Andrej Ceraj
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "symbol";
	/**
	 * PROMPT attribute
	 */
	private static final String PROMPT = "PROMPT";
	/**
	 * MORELINES attribute
	 */
	private static final String MORELINES = "MORELINES";
	/**
	 * MULTILINE attribute
	 */
	private static final String MULTILINE = "MULTILINE";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] words;
		try {
			words = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException exception) {
			env.writeln("Illegal argument");
			return ShellStatus.CONTINUE;
		}
		if (words.length != 1 && words.length != 2) {
			env.writeln("Invalid number of arguments for command 'symbol'.");
			return ShellStatus.CONTINUE;
		}

		switch (words[0]) {
		case PROMPT:
			symbol(env, words, env.getPromptSymbol());
			break;
		case MORELINES:
			symbol(env, words, env.getMorelinesSymbol());
			break;
		case MULTILINE:
			symbol(env, words, env.getMultilineSymbol());
			break;

		default:
			env.writeln("Invalid argument for command 'symbol'");
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
		description.add("Expects one or two arguments");
		description.add("First argument can be: PROMPT, MORELINES or MULTILINES");
		description.add("Second argument is optional and can be only one character");
		description.add("If only one argument is given, the command returns the current symbol");
		description.add("for the given field. If there are two arguments, then the command sets");
		description.add("the first argument field to the second argument character.");
		return Collections.unmodifiableList(description);
	}

	/**
	 * If only one argument is passed to the command, then it prints current symbol
	 * for the attribute given by the command argument. If two arguments are passed
	 * to the command, it then sets the attribute given by the first command
	 * argument to symbol given by the second command argument.
	 * 
	 * @param env Environment
	 * @param words command arguments
	 * @param currentSymbol current attribute symbol
	 */
	private void symbol(Environment env, String[] words, char currentSymbol) {
		if (words.length == 1) {
			env.writeln("Symbol for " + words[0] + " is '" + currentSymbol + "'");
		} else if (words[1].length() == 1 && !Character.isLetter(words[1].charAt(0))
				&& !Character.isDigit(words[1].charAt(0))) {
			env.writeln("Symbol for " + words[0] + " changed from '" + currentSymbol + "' to '" + words[1] + "'");
			switch (words[0]) {
			case PROMPT:
				env.setPromptSymbol(words[1].charAt(0));
				break;
			case MORELINES:
				env.setMorelinesSymbol(words[1].charAt(0));
				break;
			case MULTILINE:
				env.setMultilineSymbol(words[1].charAt(0));
				break;
			}
		} else {
			env.writeln("Second argument must be only one symbol");
		}
	}

}
