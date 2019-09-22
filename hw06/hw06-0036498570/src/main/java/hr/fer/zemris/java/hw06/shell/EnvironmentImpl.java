package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.SortedMap;

/**
 * Implementation of {@link Environment}.
 * 
 * @author Andrej Ceraj
 *
 */
public class EnvironmentImpl implements Environment {
	/**
	 * Default {@code MULTILINE} symbol
	 */
	private static final char DEFAULT_MULTILINE_SYMBOL = '|';
	/**
	 * Default {@code PROMPT} symbol
	 */
	private static final char DEFAULT_PROMPT_SYMBOL = '>';
	/**
	 * Default {@code MORELINES} symbol
	 */
	private static final char DEFAULT_MORE_LINES_SYMBOL = '\\';

	/**
	 * current {@code MULTILINE} symbol
	 */
	private Character multilineSymbol = DEFAULT_MULTILINE_SYMBOL;
	/**
	 * current {@code PROMPT} symbol
	 */
	private Character promptSymbol = DEFAULT_PROMPT_SYMBOL;
	/**
	 * current {@code MORELINES} symbol
	 */
	private Character moreLinesSymbol = DEFAULT_MORE_LINES_SYMBOL;

	/**
	 * Map associating command name with an actual {@link ShellCommand}.
	 */
	private SortedMap<String, ShellCommand> commandsMap;
	/**
	 * Buffered reader used to get the user input.
	 */
	private BufferedReader reader;
	/**
	 * Buffer writer used to print output to user.
	 */
	private BufferedWriter writer;

	/**
	 * Creates an instance of {@code EnvironmentImpl}.
	 * 
	 * @param map commands
	 * @param reader buffered reader
	 * @param writer buffered writer
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> map, BufferedReader reader, BufferedWriter writer) {
		commandsMap = Collections.unmodifiableSortedMap(map);
		this.reader = reader;
		this.writer = writer;
	}

	@Override
	public String readLine() throws ShellIOException {
		String line;
		try {
			line = reader.readLine();
		} catch (IOException exception) {
			throw new ShellIOException("Unable to read line.");
		}
		if (line.endsWith(Character.toString(moreLinesSymbol))) {
			line = line.substring(0, line.length() - 1);
			write(multilineSymbol + " ");
			line += readLine();
		}
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer.write(text);
			writer.flush();
		} catch (IOException exception) {
			throw new ShellIOException("Unable to write text");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text);
		System.out.println();

	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commandsMap;
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}

}
