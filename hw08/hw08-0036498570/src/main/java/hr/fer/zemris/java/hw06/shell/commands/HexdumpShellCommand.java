package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * Command prints hex dump of a file given by the command argument.
 * 
 * @author Andrej Ceraj
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "hexdump";
	/**
	 * Default buffer size equals to bytes written per line in hexdump.
	 */
	private static final int BUFFER_SIZE = 16;
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects one argument - file name");
		description.add("Command converts file content into hexdump and prints it into the console.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] words;
		try {
			words = ArgumentUtil.parseArguments(arguments);
		} catch (IllegalArgumentException exception) {
			env.writeln("Illegal argument");
			return ShellStatus.CONTINUE;
		}
		if (words.length != 1) {
			env.writeln("Invalid number of arguments for command 'hexdump'");
			return ShellStatus.CONTINUE;
		}
		Path path = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		if (Files.isDirectory(path)) {
			env.writeln("File is a directory");
			return ShellStatus.CONTINUE;
		}
		try (InputStream bufferedInputStream = Files.newInputStream(path)) {
			int readBytes;
			byte[] buffer = new byte[BUFFER_SIZE];
			int counter = 0;
			while ((readBytes = bufferedInputStream.read(buffer)) != -1) {
				env.writeln(hexdumpLine(buffer, readBytes, counter));
				counter++;
			}
		} catch (IOException e) {
			env.writeln("Unable to read file");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

	/**
	 * Method formats read bytes stored in buffer into hexdump line.
	 * 
	 * @param buffer
	 * @param readBytes
	 * @param counterD
	 * @return hexdump line
	 */
	private String hexdumpLine(byte[] buffer, int readBytes, int counter) {
		StringBuilder builder = new StringBuilder();
		String hexIndex = Integer.toHexString(counter * BUFFER_SIZE);
		for (int i = 8; i > hexIndex.length(); i--) {
			builder.append("0");
		}
		builder.append(hexIndex.toUpperCase()).append(":");
		for (int i = 0; i < buffer.length; i++) {
			if (i == 8) {
				builder.append("|");
			} else {
				builder.append(" ");
			}
			if (i < readBytes) {
				builder.append(Util.byteToHex(buffer[i]).toUpperCase());
			} else {
				builder.append("  ");
			}

		}
		builder.append(" |");
		char[] letters = new char[readBytes];
		for (int i = 0; i < readBytes; i++) {
			if (buffer[i] >= 32 && buffer[i] <= 127) {
				letters[i] = (char) buffer[i];
			} else {
				letters[i] = '.';
			}
		}
		builder.append(letters);
		return builder.toString();
	}

}
