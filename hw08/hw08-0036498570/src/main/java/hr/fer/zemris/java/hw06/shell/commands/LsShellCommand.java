package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command lists details of all files and directories in a directory given as
 * the command argument.
 * 
 * @author Andrej Ceraj
 *
 */
public class LsShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "ls";
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects one argument - directory name");
		description.add("Lists details of all files and directories in the given directory.");
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
			env.writeln("Invalid number of arguments for command 'ls'");
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		if (!Files.isDirectory(path)) {
			env.writeln(path.getFileName() + " is not directory.");
			return ShellStatus.CONTINUE;
		}
		File root = path.toFile();
		File[] allFiles = root.listFiles();
		for (File file : allFiles) {
			env.writeln(getFileDetails(file));
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
	 * Gets files's details
	 * 
	 * @param file
	 * @return file details
	 */
	private String getFileDetails(File file) {
		Path path = file.toPath();
		StringBuilder builder = new StringBuilder();

		builder.append(Files.isDirectory(path) ? "d" : "-");
		builder.append(Files.isReadable(path) ? "r" : "-");
		builder.append(Files.isWritable(path) ? "w" : "-");
		builder.append(Files.isExecutable(path) ? "x" : "-");
		builder.append(" ");

		try {
			long fileSize = Files.size(path);
			String alignedSize = String.format("%10s", Long.toString(fileSize));
			builder.append(alignedSize).append(" ");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
					LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes;
			attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			builder.append(formattedDateTime).append(" ");

			builder.append(path.getFileName());
		} catch (Exception e) {
			return "Unable to load file information for: " + path.getFileName();
		}
		return builder.toString();
	}

}
