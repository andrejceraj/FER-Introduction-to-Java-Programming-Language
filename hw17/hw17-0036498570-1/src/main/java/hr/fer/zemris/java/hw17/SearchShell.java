package hr.fer.zemris.java.hw17;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hr.fer.zemris.java.hw17.commands.Command;
import hr.fer.zemris.java.hw17.commands.ExitCommand;
import hr.fer.zemris.java.hw17.commands.QueryCommand;
import hr.fer.zemris.java.hw17.commands.ResultsCommand;
import hr.fer.zemris.java.hw17.commands.TypeCommand;

/**
 * Search shell through communication with the user displays paths of files that
 * best matches the user search.
 * 
 * @author Andrej Ceraj
 *
 */
public class SearchShell {
	/**
	 * Path to stopwords
	 */
	private static final String STOPWORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";

	/**
	 * Method is called when the program is run
	 * 
	 * @param args path to articles
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		Map<String, Command> commands = fillCommands();
		Set<String> stopWords = null;
		Set<String> vocabulary = null;
		Map<String, Map<String, Integer>> vectorsMap = new HashMap<String, Map<String, Integer>>();
		Map<String, Double> idfMap = new HashMap<String, Double>();
		try {
			stopWords = getStopWords(STOPWORDS_PATH);
			vocabulary = getVocabulary(stopWords, args[0]);
			fillVectorAndIdfMap(vocabulary, vectorsMap, idfMap, stopWords, args[0]);
		} catch (IOException ioe) {
			System.out.println("Initialization failed, cannot read from files...");
			return;
		} catch (NullPointerException npe) {
			System.out.println("Initialization failed, invalid path given...");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

			Environment env = new Environment(writer, commands, stopWords, vocabulary, vectorsMap, idfMap);
			env.writeln("Veličina riječnika je " + env.getVocabulary().size() + " riječi.\n");

			do {
				env.write("Enter command > ");
				String[] commandAndArguments = readLine(reader);
				env.executeCommand(commandAndArguments[0], commandAndArguments[1]);
			} while (env.getStatus() != Status.TERMINATE);

		} catch (IOException exception) {
			exception.printStackTrace();
			System.out.println("Error ocurred...");
		}
	}

	/**
	 * Fills TF vectors for each file and common IDF vector.
	 * 
	 * @param vocabulary vocabulary
	 * @param vectorsMap map containing vectors
	 * @param idfMap     IDF vector
	 * @param stopWords  stop words
	 * @param path       path to articles
	 * @throws IOException if it is unable to read from the given path
	 */
	private static void fillVectorAndIdfMap(Set<String> vocabulary, Map<String, Map<String, Integer>> vectorsMap,
			Map<String, Double> idfMap, Set<String> stopWords, String path) throws IOException {
		Map<String, Integer> filesWithWord = new HashMap<String, Integer>();
		Path pathToFiles = Paths.get(path);
		int fileCounter = 0;
		for (File file : pathToFiles.toFile().listFiles()) {
			Map<String, Integer> fileVector = new HashMap<String, Integer>();

			Path pathToFile = file.toPath();
			BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath())));
			String line;
			while ((line = reader.readLine()) != null) {
				List<String> words = getWords(line);
				for (String word : words) {
					if (!vocabulary.contains(word)) {
						continue;
					}
					if (!fileVector.containsKey(word)) {
						if (filesWithWord.containsKey(word)) {
							filesWithWord.put(word, filesWithWord.get(word) + 1);
						} else {
							filesWithWord.put(word, 1);
						}
					}

					if (fileVector.containsKey(word)) {
						fileVector.put(word, fileVector.get(word) + 1);
					} else {
						fileVector.put(word, 1);
					}

				}
			}

			vectorsMap.put(pathToFile.toAbsolutePath().toString(), fileVector);
			fileCounter++;
		}
		for (Entry<String, Integer> wordFileCount : filesWithWord.entrySet()) {
			idfMap.put(wordFileCount.getKey(), Math.log((double) fileCounter / (double) wordFileCount.getValue()));
		}
	}

	/**
	 * Gets vocabulary from articles from the given path
	 * 
	 * @param stopWords stop words
	 * @param path      path to articles
	 * @return vocabulary
	 * @throws IOException if it is unable to read from the given path
	 */
	private static Set<String> getVocabulary(Set<String> stopWords, String path) throws IOException {
		Set<String> vocabulary = new HashSet<String>();
		Path pathToFiles = Paths.get(path);
		for (File file : pathToFiles.toFile().listFiles()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath())));
			String line;
			while ((line = reader.readLine()) != null) {
				List<String> words = getWords(line);
				words.forEach(w -> {
					if (!stopWords.contains(w)) {
						vocabulary.add(w);
					}
				});
			}
		}
		return vocabulary;
	}

	/**
	 * Parses words from the given line
	 * 
	 * @param line line
	 * @return list of words
	 */
	private static List<String> getWords(String line) {
		List<String> words = new ArrayList<String>();
		char[] charArray = line.toCharArray();
		boolean wordable = false;
		int wordStart = -1;
		int wordFinish = 0;
		while (wordFinish <= charArray.length) {
			if (wordFinish == charArray.length || !Character.isAlphabetic(charArray[wordFinish])) {
				if (wordable) {
					String word = line.substring(wordStart + 1, wordFinish).toLowerCase();
					words.add(word);
					wordable = false;
				}
				wordStart = wordFinish;
			} else {
				wordable = true;
			}
			wordFinish++;
		}
		return words;
	}

	/**
	 * Loads stop words into a {@link Set}.
	 * 
	 * @param stopwordsPath path to file containing stop words
	 * @return set containing stop words
	 * @throws IOException if it is unable to read from the given path
	 */
	private static Set<String> getStopWords(String stopwordsPath) throws IOException {
		Set<String> stopWords = new HashSet<String>();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(Files.newInputStream(Paths.get(stopwordsPath))));
		String line;
		while ((line = reader.readLine()) != null) {
			stopWords.add(line.trim());
		}
		return Collections.unmodifiableSet(stopWords);
	}

	/**
	 * Reads line from the console
	 * 
	 * @param reader reader
	 * @return command and arguments as array of strings
	 * @throws IOException if it is unable to read with the given reader
	 */
	public static String[] readLine(BufferedReader reader) throws IOException {
		String readLine = reader.readLine().trim();
		char[] line = readLine.toCharArray();
		String command = null;
		String arguments = null;
		for (int i = 0; i < line.length; i++) {
			if (Character.isWhitespace(line[i])) {
				command = readLine.substring(0, i);
				arguments = readLine.substring(i + 1);
				break;
			}
			if (i == line.length - 1) {
				command = readLine;
			}
		}
		String[] toReturn = { command, arguments };
		return toReturn;
	}

	/**
	 * Fills commands map with all the commands
	 * 
	 * @return unmodifiable commands map
	 */
	private static Map<String, Command> fillCommands() {
		Map<String, Command> commands = new HashMap<String, Command>();
		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());
		commands.put("results", new ResultsCommand());
		commands.put("exit", new ExitCommand());
		return Collections.unmodifiableMap(commands);
	}

}
