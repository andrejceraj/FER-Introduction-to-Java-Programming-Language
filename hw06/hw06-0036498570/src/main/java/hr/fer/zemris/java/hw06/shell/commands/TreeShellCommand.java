package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command prints tree with root of the directory given by the command argument.
 * 
 * @author Andrej Ceraj
 *
 */
public class TreeShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] words;
		try {
			words = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException exception) {
			env.writeln("Illegal argument");
			return ShellStatus.CONTINUE;
		}
		if (words.length != 1) {
			env.writeln("Invalid number of arguments for command 'tree'");
			return ShellStatus.CONTINUE;
		}
		Path path = Paths.get(words[0]);
		if (!Files.isDirectory(path)) {
			env.writeln(path.getFileName() + " is not directory");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(path, new TreeFileVisitor(env));
		} catch (IOException exception) {
			env.writeln("Error in walking file tree.");
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
		description.add("Expects one argument - directory name");
		description.add("Prints tree with directory name as root.");
		return Collections.unmodifiableList(description);
	}

	/**
	 * Class used for printing directory tree.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private static class TreeFileVisitor implements FileVisitor<Path> {
		/**
		 * recursion depth
		 */
		private int depth = 0;
		/**
		 * {@link Environment} used to print the current file or directory.
		 */
		private Environment env;

		/**
		 * Creates an instance of {@code TreeFileVisitor} with the given
		 * {@code Environment}.
		 * 
		 * @param env Environment
		 */
		public TreeFileVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln("  ".repeat(depth) + dir.getFileName());
			depth++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln("  ".repeat(depth) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			throw new IOException();
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			depth--;
			return FileVisitResult.CONTINUE;
		}

	}

}
