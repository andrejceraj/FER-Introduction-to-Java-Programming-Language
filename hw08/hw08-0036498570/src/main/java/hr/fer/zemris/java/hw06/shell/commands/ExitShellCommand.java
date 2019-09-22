package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command exits {@link MyShell}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ExitShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "exit";
	/**
	 * Command description
	 */
	private static List<String> description = new ArrayList<String>();
	{
		description.add("Expects no arguments");
		description.add("Exits MyShell.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command 'exit' expects no arguments");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
