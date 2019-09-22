package hr.fer.zemris.java.hw17.commands;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw17.Environment;
import hr.fer.zemris.java.hw17.SearchResult;
import hr.fer.zemris.java.hw17.Status;

/**
 * Queries the command arguments and returns best search results (max 10) from
 * the articles. For finding the best search results, TF and IDF vectors are
 * used.
 * 
 * @author Andrej Ceraj
 *
 */
public class QueryCommand implements Command {

	@Override
	public Status execute(Environment env, String arguments) {
		if (arguments == null) {
			env.writeln("Command 'query' expects arguments");
		}
		String[] words = arguments.split("\\s+");
		Map<String, Integer> searchVector = getSearchVector(words, env);
		if (searchVector.isEmpty()) {
			env.setResults(null);
			printWords(searchVector, env);
			env.showResults();
			return Status.CONTINUE;
		}

		Map<String, Double> searchResults = calculateSearchResults(searchVector, env);
		SearchResult[] finalResults = getTop10Results(searchResults);
		env.setResults(finalResults);
		printWords(searchVector, env);
		env.writeln("Top 10 results:");
		env.showResults();
		return Status.CONTINUE;
	}

	/**
	 * Outputs words considered in query
	 * 
	 * @param searchVector user search vector
	 * @param env          environment
	 */
	private void printWords(Map<String, Integer> searchVector, Environment env) {
		StringBuilder sb = new StringBuilder();
		sb.append("Query is: [");
		boolean first = true;
		for (String word : searchVector.keySet()) {
			if (first) {
				sb.append(word);
				first = false;
			} else {
				sb.append(", ").append(word);
			}
		}
		sb.append(']');
		env.writeln(sb.toString());
	}

	/**
	 * Gets top results from the search results map
	 * 
	 * @param searchResults search results map
	 * @return array of best search results
	 */
	private SearchResult[] getTop10Results(Map<String, Double> searchResults) {
		SearchResult[] finalResults = new SearchResult[10];
		int i = 0;
		for (Entry<String, Double> resultEntry : searchResults.entrySet()) {
			if (Math.abs(resultEntry.getValue()) < 1e-8 || i >= 10) {
				break;
			}
			SearchResult r = new SearchResult(resultEntry.getValue(), resultEntry.getKey());
			finalResults[i] = r;
			i++;
		}
		return finalResults;
	}

	/**
	 * Calculates search results
	 * 
	 * @param searchVector user's query vector
	 * @param env          environment
	 * @return search results
	 */
	private Map<String, Double> calculateSearchResults(Map<String, Integer> searchVector, Environment env) {
		Map<String, Double> searchResults = new HashMap<String, Double>();
		for (Entry<String, Map<String, Integer>> fileVector : env.getVectorsMap().entrySet()) {
			double scalarProduct = calculateScalarProduct(fileVector.getValue(), searchVector, env);
			double normProduct = calculateNormProduct(fileVector.getValue(), searchVector, env);

			searchResults.put(fileVector.getKey(), scalarProduct / normProduct);
		}
		Map<String, Double> sortedResults = searchResults.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
		return sortedResults;
	}

	/**
	 * Constructs user's search vector from its query
	 * 
	 * @param words query words
	 * @param env   environment
	 * @return search vector
	 */
	private Map<String, Integer> getSearchVector(String[] words, Environment env) {
		Map<String, Integer> vector = new LinkedHashMap<String, Integer>();
		for (String word : words) {
			if (!env.getVocabulary().contains(word)) {
				continue;
			}

			if (vector.containsKey(word)) {
				vector.put(word, vector.get(word) + 1);
			} else {
				vector.put(word, 1);
			}
		}
		return vector;
	}

	/**
	 * Calculates product of norms of 2 vectors.
	 * 
	 * @param fileVector   vector1
	 * @param searchVector vector2
	 * @param env          environment
	 * @return product of norms of vector1 and vector2
	 */
	private double calculateNormProduct(Map<String, Integer> fileVector, Map<String, Integer> searchVector,
			Environment env) {
		double fileVectorNormSquared = 0;
		for (Entry<String, Integer> entry : fileVector.entrySet()) {
			fileVectorNormSquared += Math.pow(entry.getValue() * env.getIdfMap().get(entry.getKey()), 2);
		}
		double fileVectorNorm = Math.sqrt(fileVectorNormSquared);

		double searchVectorNormSquared = 0;
		for (Entry<String, Integer> entry : searchVector.entrySet()) {
			searchVectorNormSquared += Math.pow(entry.getValue() * env.getIdfMap().get(entry.getKey()), 2);
		}
		double searchVectorNorm = Math.sqrt(searchVectorNormSquared);

		return fileVectorNorm * searchVectorNorm;
	}

	/**
	 * Calculate scalar product of 2 vectors
	 * 
	 * @param fileVector   vector1
	 * @param searchVector vector2
	 * @param env          environment
	 * @return scalar product of vector1 and vector2
	 */
	private double calculateScalarProduct(Map<String, Integer> fileVector, Map<String, Integer> searchVector,
			Environment env) {
		double product = 0;
		for (Entry<String, Integer> searchEntry : searchVector.entrySet()) {
			Integer n = fileVector.containsKey(searchEntry.getKey()) ? fileVector.get(searchEntry.getKey()) : 0;
			product += searchEntry.getValue() * n * Math.pow(env.getIdfMap().get(searchEntry.getKey()), 2);
		}
		return product;
	}

}
