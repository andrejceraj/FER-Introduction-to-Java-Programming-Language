package hr.fer.zemris.java.hw17;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.hw17.commands.Command;

/**
 * Search shell environment
 * 
 * @author Andrej Ceraj
 *
 */
public class Environment {
	/**
	 * Top 10 search results
	 */
	private SearchResult[] results;
	/**
	 * Output writer
	 */
	private BufferedWriter writer;
	/**
	 * Search shell status
	 */
	private Status status = Status.CONTINUE;
	/**
	 * Commands map
	 */
	private Map<String, Command> commands;
	/**
	 * Stop words
	 */
	private Set<String> stopWords;
	/**
	 * Vocabulary
	 */
	private Set<String> vocabulary;
	/**
	 * TF vectors for files
	 */
	private Map<String, Map<String, Integer>> vectorsMap;
	/**
	 * IDF vector
	 */
	private Map<String, Double> idfMap;

	/**
	 * Constructor
	 * 
	 * @param writer     writer
	 * @param commands   commands map
	 * @param stopWords  stop words
	 * @param vocabulary vocabulary
	 * @param vectorsMap TF vectors
	 * @param idfMap     IDF vector
	 */
	public Environment(BufferedWriter writer, Map<String, Command> commands, Set<String> stopWords,
			Set<String> vocabulary, Map<String, Map<String, Integer>> vectorsMap, Map<String, Double> idfMap) {
		this.writer = writer;
		this.commands = commands;
		this.stopWords = stopWords;
		this.vocabulary = vocabulary;
		this.vectorsMap = vectorsMap;
		this.idfMap = idfMap;
	}

	/**
	 * Outputs the given string
	 * 
	 * @param string string
	 */
	public void write(String string) {
		try {
			writer.write(string);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Outputs the given string with added next line symbol
	 * 
	 * @param string string
	 */
	public void writeln(String string) {
		try {
			writer.write(string + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return search shell status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Displays top results (max 10)
	 */
	public void showResults() {
		if (results == null) {
			writeln("No results");
			return;
		}
		int count = 0;
		for (SearchResult result : results) {
			if (result == null) {
				break;
			}
			writeln("[" + count + "] " + result.toString());
			count++;
		}
	}

	/**
	 * Executes command with its arguments
	 * 
	 * @param commandName command
	 * @param arguments   arguments
	 */
	public void executeCommand(String commandName, String arguments) {
		if (commandName == null || commandName.isBlank()) {
			return;
		}
		if (!commands.containsKey(commandName)) {
			writeln("Invalid command!");
			return;
		}
		Command command = commands.get(commandName);
		status = command.execute(this, arguments);
	}

	/**
	 * @return results
	 */
	public SearchResult[] getResults() {
		return results;
	}

	/**
	 * @return commands map
	 */
	public Map<String, Command> getCommands() {
		return commands;
	}

	/**
	 * @return stop words
	 */
	public Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * @return vocabulary
	 */
	public Set<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * @return TF vectors
	 */
	public Map<String, Map<String, Integer>> getVectorsMap() {
		return vectorsMap;
	}

	/**
	 * @return IDF vector
	 */
	public Map<String, Double> getIdfMap() {
		return idfMap;
	}

	/**
	 * Sets search results to the given value
	 * 
	 * @param finalResults search results
	 */
	public void setResults(SearchResult[] finalResults) {
		results = finalResults;
	}
}
