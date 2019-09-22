package hr.fer.zemris.java.hw13.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class used in process of voting (Glasanje).
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class GlasanjeUtils {
	/**
	 * Reads lines from the given file and returns list containing the lines.
	 * 
	 * @param fileName full path file name
	 * @return list of lines
	 * @throws IOException if it is unable to read from the given file
	 */
	public static List<String> readFileIntoList(String fileName) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(fileName))));
		String line;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		return lines;
	}

	/**
	 * Creates a file containing band ids and votes for that band. Band ids are
	 * found in the {@code fileNameBands} file and all votes values are set to 0.
	 * 
	 * @param fileNameResults file with results
	 * @param fileNameBands   file with bands
	 * @throws IOException if it is unable to read from bands file or write into
	 *                     file with results.
	 */
	public static void createResultsFile(String fileNameResults, String fileNameBands) throws IOException {
		List<String> bands = readFileIntoList(fileNameBands);
		StringBuffer sb = new StringBuffer();
		for (String line : bands) {
			String[] words = line.split("\\t");
			sb.append(words[0]).append('\t').append(0).append('\n');
		}
		OutputStream os = Files.newOutputStream(Paths.get(fileNameResults));
		os.write(sb.toString().getBytes());
		os.flush();
	}

}
