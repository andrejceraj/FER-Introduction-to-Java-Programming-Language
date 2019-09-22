package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Program representing a shell.
 * 
 * @author Andrej Ceraj
 *
 */
public class MyShell {

	/**
	 * Method that starts when the program is run.
	 * 
	 * @param args Not used 
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
		fillCommandsMap(commands);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

			Environment environment = new EnvironmentImpl(commands, reader, writer);
			commands = environment.commands();
			environment.writeln("Welcome to MyShell v 1.0");
			ShellStatus status = ShellStatus.CONTINUE;

			do {
				environment.write(environment.getPromptSymbol() + " ");
				String line = environment.readLine();
				String commandName = extractCommand(line);
				String arguments = extractArguments(line, commandName);
				ShellCommand command = commands.get(commandName);
				if (command != null) {
					status = command.executeCommand(environment, arguments);
				} else {
					environment.writeln("Command not recognized.");
				}
			} while (status != ShellStatus.TERMINATE);

		} catch (IOException ioException) {
			System.out.println("Error in I/O");
			return;
		} catch (ShellIOException shellIOException) {
			System.out.println("Error in MyShell I/O.");
		} catch (Exception exception) {
			System.out.println("Something went wrong.");
		}

	}

	/**
	 * @param line
	 * @return command name extracted from the line
	 */
	private static String extractCommand(String line) {
		return line.split(" ")[0];

	}

	/**
	 * @param line
	 * @param commandName
	 * @return arguments extracted from the line containing the command
	 */
	private static String extractArguments(String line, String commandName) {
		return line.equals(commandName) ? "" : line.substring(commandName.length() + 1, line.length());

	}

	/**
	 * Method fills the map with available {@link ShellCommand}s
	 * 
	 * @param map
	 */
	private static void fillCommandsMap(SortedMap<String, ShellCommand> map) {
		map.put(CharsetsShellCommand.NAME, new CharsetsShellCommand());
		map.put(CatShellCommand.NAME, new CatShellCommand());
		map.put(LsShellCommand.NAME, new LsShellCommand());
		map.put(TreeShellCommand.NAME, new TreeShellCommand());
		map.put(CopyShellCommand.NAME, new CopyShellCommand());
		map.put(MkdirShellCommand.NAME, new MkdirShellCommand());
		map.put(HexdumpShellCommand.NAME, new HexdumpShellCommand());
		map.put(SymbolShellCommand.NAME, new SymbolShellCommand());
		map.put(ExitShellCommand.NAME, new ExitShellCommand());
		map.put(HelpShellCommand.NAME, new HelpShellCommand());
	}
}
