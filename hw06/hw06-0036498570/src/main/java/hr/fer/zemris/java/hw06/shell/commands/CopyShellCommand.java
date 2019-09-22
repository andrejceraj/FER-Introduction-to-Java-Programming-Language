package hr.fer.zemris.java.hw06.shell.commands;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command copies source file given by the first command argument to a
 * destination file given by the second command argument. If the destination is
 * a directory the command copies the source file into the directory with the
 * original name. If the destination file already exists, the user is asked if
 * it wants to overwrite the existing destination file.
 * 
 * @author Andrej Ceraj
 *
 */
public class CopyShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "copy";
	/**
	 * Default buffer size
	 */
	private static final int BUFFER_SIZE = 4096;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] words;
		try {
			words = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException exception) {
			env.writeln("Illegal argument");
			return ShellStatus.CONTINUE;
		}
		if (words.length != 2) {
			env.writeln("Invalid number of arguments for command 'copy'");
			return ShellStatus.CONTINUE;
		}

		Path source = Paths.get(words[0]);
		Path destination = Paths.get(words[1]);
		if (Files.isDirectory(source)) {
			env.writeln("Source file cannot be directory");
			return ShellStatus.CONTINUE;
		}
		if (Files.isDirectory(destination)) {
			destination = Paths.get(words[1], source.getFileName().toString());
		}
		if (Files.exists(destination)) {
			env.writeln("Destination file already exists. Would you like to overwrite it? ");
			String answer;
			do {
				env.write("Please type in 'yes' or 'no' ");
				answer = env.readLine();
			} while (!answer.equals("yes") && !answer.equals("no"));
			if (answer.equals("no")) {
				return ShellStatus.CONTINUE;
			}
		}
		try (InputStream bufferedInputStream = Files.newInputStream(source);
				OutputStream bufferedOutputStream = Files.newOutputStream(destination)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int readBytes;

			while ((readBytes = bufferedInputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, readBytes);
			}
			env.writeln("Filed copied successfully");
		} catch (Exception e) {
			env.writeln("Unable to copy");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Expects two arguments - source file name and destination file name");
		description.add("If destination file exists, user is asked if it allows to overwrite it.");
		description.add("Source file may not be directory. If the destination file is directory,");
		description.add("the command copies the source file into that directory using the");
		description.add("original file name.");
		return Collections.unmodifiableList(description);
	}

}
