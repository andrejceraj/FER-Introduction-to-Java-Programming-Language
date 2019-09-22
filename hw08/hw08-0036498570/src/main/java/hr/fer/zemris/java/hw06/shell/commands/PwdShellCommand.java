package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command displays current shell directory.
 * 
 * @author Andrej Ceraj
 *
 */
public class PwdShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	public static final String NAME = "pwd";
	/**
	 * Command description
	 */
	List<String> description = new ArrayList<String>();
	{
		description.add("Expects no arguments");
		description.add("Displays current shell directory");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command 'pwd' expects no arguments");
			return ShellStatus.CONTINUE;
		}
		String apsolutePath = env.getCurrentDirectory().toString();
		env.writeln(apsolutePath);
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

}
