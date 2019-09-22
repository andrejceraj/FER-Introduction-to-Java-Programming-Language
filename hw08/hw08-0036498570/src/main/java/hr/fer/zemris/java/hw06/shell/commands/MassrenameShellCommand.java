package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.ArgumentUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.Massrename.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.Massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.Massrename.NameBuilderParser;

/**
 * Command used for filtering, grouping, previewing massive renaming or massive
 * renaming (and/or moving) files.
 * 
 * @author Andrej Ceraj
 *
 */
public class MassrenameShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "massrename";
	/**
	 * Subcommand for filtering files
	 */
	private static final String FILTER = "filter";
	/**
	 * Subcommand for grouping files
	 */
	private static final String GROUPS = "groups";
	/**
	 * Subcommand for previewing massive renaming of files
	 */
	private static final String SHOW = "show";
	/**
	 * Subcommand for massive renaming or moving files
	 */
	private static final String EXECUTE = "execute";
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects four or five arguments - source directory, destination directory,");
		description.add("subcommand, filtering pattern and if necessary renaming pattern");
		description.add("Subcommand 'filter' - lists all files from source directory whose name");
		description.add("matches the filtering pattern.");
		description.add("Subcommand 'groups' - lists all files from source directory whose name");
		description.add("matches the filtering pattern and for each file name, it lists all pattern.");
		description.add("groups.");
		description.add("Subcommand 'show' - lists all files from source directory whose name");
		description.add("matches the filtering pattern and for each file it shows what would be it's");
		description.add("new name based on the renaming pattern");
		description.add("Subcommand 'execute' - lists all files from source directory whose name");
		description.add("matches the filtering pattern, renames all files based on the renamin pattern,");
		description.add("moves the files into the destination directory and shows the new name for each");
		description.add("of the files");
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
		if (words.length != 4 && words.length != 5) {
			env.writeln("Invalid number of arguments for command 'massrename'");
			return ShellStatus.CONTINUE;
		}
		Path source = env.getCurrentDirectory().resolve(Paths.get(words[0]));
		Path destination = env.getCurrentDirectory().resolve(Paths.get(words[1]));
		String subCommand = words[2];
		String mask = words[3];

		if (((subCommand == FILTER || subCommand == GROUPS) && words.length != 4)
				|| ((subCommand == SHOW || subCommand == EXECUTE) && words.length != 5)) {
			env.writeln("Invalid number of arguments for command 'massrename' with subcommand '" + subCommand + "'.");
			return ShellStatus.CONTINUE;
		}

		switch (subCommand) {
		case FILTER:
			try {
				filter(source, mask).forEach(f -> env.writeln(f.toString()));
			} catch (IOException e) {
				env.writeln("Unable to filter");
			}
			break;
		case GROUPS:
			try {
				filter(source, mask).forEach(f -> {
					env.write(f.toString());
					for (int i = 0; i < f.numberOfGroups(); i++) {
						env.write(" " + i + ": " + f.group(i));
					}
					env.writeln("");
				});
			} catch (IOException e) {
				env.writeln("Unable to group");
			}
			break;
		case SHOW:
			try {
				execute(env, source, destination, mask, words[4], false);
			} catch (Exception e) {
				env.writeln(e.getMessage());
			}
			break;
		case EXECUTE:
			try {
				execute(env, source, destination, mask, words[4], true);
			} catch (Exception e) {
				env.writeln(e.getMessage());
			}
		default:
			break;
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
	 * Method filters all files in the given directory by the given pattern
	 * 
	 * @param dir     directory
	 * @param pattern pattern
	 * @return list of filtered results
	 */
	private List<FilterResult> filter(Path dir, String pattern) throws IOException {
		File directory = dir.toFile();
		File[] files = directory.listFiles();
		List<FilterResult> list = new ArrayList<FilterResult>();
		if (files == null) {
			return list;
		}
		for (File file : files) {
			if (Files.isDirectory(file.toPath())) {
				continue;
			}

			Pattern pat = Pattern.compile(pattern);
			Matcher matcher = pat.matcher(file.getName());

			if (matcher.find()) {
				String[] groups = new String[matcher.groupCount() + 1];
				groups[0] = file.getName();
				for (int i = 1; i < groups.length; i++) {
					groups[i] = matcher.group(i);
				}
				list.add(new FilterResult(groups));
			}

		}
		return list;
	}

	/**
	 * Method shows how the files with name matching the given pattern would be/are
	 * renamed (or moved).
	 * 
	 * @param env         shell environment
	 * @param source      source directory
	 * @param destination destination directory
	 * @param pattern     filtering pattern
	 * @param expression  renaming pattern
	 * @param move        should the method also rename (and move) the filtered
	 *                    files
	 */
	private void execute(Environment env, Path source, Path destination, String pattern, String expression,
			boolean move) {
		List<FilterResult> results = null;
		try {
			results = filter(source, pattern);
		} catch (IOException e) {
			env.writeln("Unable to filter");
		}
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		for (FilterResult result : results) {
			StringBuilder sb = new StringBuilder();
			builder.execute(result, sb);
			String oldName = result.toString();
			String newName = sb.toString();
			env.writeln(oldName + " => " + newName);
			if (move) {
				try {
					Files.move(Paths.get(source.toString(), oldName), Paths.get(destination.toString(), newName));
				} catch (IOException e) {
					env.writeln("Unable to move file: " + oldName);
				}
			}
		}

	}

}
