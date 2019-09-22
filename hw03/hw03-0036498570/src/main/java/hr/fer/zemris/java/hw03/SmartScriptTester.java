package hr.fer.zemris.java.hw03;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program gets path to a document containing text to parse. The program parses
 * the text and then recreates the text to a new {@code String}. It then parses
 * the recreated {@code String} and prints the both results.
 * <p>
 * If the both results are the same then the {@link SmartScriptParser} works
 * properly.
 * </p>
 * 
 * @author Andrej Ceraj
 *
 */
public class SmartScriptTester {
	public static void main(String[] args) {
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (Exception e1) {
			System.out.println("Failed to load file");
			System.exit(-1);
		}
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			System.out.println(createOriginalDocumentBody(parser.getDocumentNode()));
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);

		System.out.println("First tree: \n\n" + originalDocumentBody);
		System.out.println("\nSecond tree: \n\n" + originalDocumentBody2);
	}
	
	/**
	 * The method creates a {@code String} out of a {@code DocumentNode}.
	 * 
	 * @param document Parser tree
	 * @return Recreated text
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		return document.toString();
	}
}
