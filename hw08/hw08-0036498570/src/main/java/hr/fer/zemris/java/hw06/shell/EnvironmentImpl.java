package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
	 * directory shell is currently positioned in
	 */
	private Path currentDirectory;

	/**
	 * Map with shared data
	 */
	private Map<String, Object> sharedData;

	/**
	 * Creates an instance of {@code EnvironmentImpl}.
	 * 
	 * @param map    commands
	 * @param reader buffered reader
	 * @param writer buffered writer
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> map, BufferedReader reader, BufferedWriter writer) {
		commandsMap = Collections.unmodifiableSortedMap(map);
		this.reader = reader;
		this.writer = writer;
		sharedData = new HashMap<String, Object>();
		currentDirectory = Paths.get("").toAbsolutePath();
	}

	@Override
	public String readLine() throws ShellIOException {
		String line;
		try {
			line = reader.readLine();
		} catch (IOException exception) {
			throw new ShellIOException("Unable to read line.");
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
		write(text + "\n");

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

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if (Files.exists(path)) {
			this.currentDirectory = path.toAbsolutePath();
		} else {
			throw new IllegalArgumentException("Path does not exist");
		}
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);

	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}

}
