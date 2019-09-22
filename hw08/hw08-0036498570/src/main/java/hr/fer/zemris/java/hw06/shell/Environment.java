package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * {@link MyShell} environment that is used for communicating with user and
 * storage of {@code MyShell} attributes.
 * 
 * @author Andrej Ceraj
 *
 */
public interface Environment {
	/**
	 * Method scans user input line until it does not end with {@code MORELINES}
	 * symbol.
	 * 
	 * @return input lines added together without {@code MORELINES} symbols.
	 * @throws ShellIOException if the communication with the user is broken.
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes the given text into the shell.
	 * 
	 * @param text
	 * @throws ShellIOException if the communication with the user is broken.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes the given text into the shell adding next line character.
	 * 
	 * @param text
	 * @throws ShellIOException if the communication with the user is broken.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * @return all {@code MyShell} commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * @return current {@code MULTILINES} symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets current {@code MULTILINES} symbol to the given symbol.
	 * 
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * @return current {@code PROMPT} symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets current {@code PROMPT} symbol to the given symbol.
	 * 
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * @return current {@code MORELINES} symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets current {@code MORELINES} symbol to the given symbol.
	 * 
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * @return returns directory shell is currently positioned in.
	 */
	Path getCurrentDirectory();

	/**
	 * Sets the shell currently positioned directory to the given path
	 * 
	 * @param path
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Gets shared data associated with the given key.
	 * 
	 * @param key
	 * @return data
	 */
	Object getSharedData(String key);

	/**
	 * Sets the given value to be associated with the given key to the shared data
	 * map.
	 * 
	 * @param key
	 * @param value
	 */
	void setSharedData(String key, Object value);
}
