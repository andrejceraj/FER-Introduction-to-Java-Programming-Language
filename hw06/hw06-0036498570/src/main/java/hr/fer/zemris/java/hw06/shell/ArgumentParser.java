package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to get clean arguments in String array from the user input
 * arguments.
 * 
 * @author Andrej Ceraj
 *
 */
public final class ArgumentParser {
	/**
	 * Converts string containing user input arguments into clean String array
	 * arguments
	 * 
	 * 
	 * @param string
	 * @return array of arguments
	 */
	public static String[] parseArguments(String string) {
		List<String> list = new ArrayList<String>();
		char[] arguments = string.toCharArray();
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == '"') {
				String parsedString = parseString(arguments, i);
				list.add(parsedString);
				i += parsedString.length() + 2;
			} else {
				String argument = string.substring(i, string.length()).split(" ")[0];
				list.add(argument);
				i += argument.length();
			}
		}
		String[] argumentsArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			argumentsArray[i] = list.get(i);
		}
		return argumentsArray;
	}

	/**
	 * Converts string starting with '"' and possibly containing escape characters
	 * into clean argument.
	 * 
	 * @param arguments char array containing the string
	 * @param i         string starting index
	 * @return clean argument
	 * @throws IllegalArgumentException if the string is not closed with '"'.
	 */
	private static String parseString(char[] arguments, int i) throws IllegalArgumentException {
		StringBuilder builder = new StringBuilder();
		i++;
		boolean finished = false;
		while (i < arguments.length) {
			if (arguments[i] == '"') {
				finished = true;
				break;
			}
			if (arguments[i] == '\\') {
				if (i + 1 < arguments.length && arguments[i + 1] == '"') {
					builder.append('"');
					i += 2;
				} else if (i + 1 < arguments.length && arguments[i + 1] == '\\') {
					builder.append('\\');
					i += 2;
				} else {
					builder.append('\\');
					i++;
				}
			} else {
				builder.append(arguments[i]);
				i++;
			}
		}
		if (!finished) {
			throw new IllegalArgumentException();
		}
		i++;
		return builder.toString();
	}

}
