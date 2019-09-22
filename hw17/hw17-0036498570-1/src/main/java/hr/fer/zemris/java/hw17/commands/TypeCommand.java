package hr.fer.zemris.java.hw17.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw17.Environment;
import hr.fer.zemris.java.hw17.Status;

/**
 * Outputs file content from one of the search results selected with the command
 * argument
 * 
 * @author Andrej Ceraj
 *
 */
public class TypeCommand implements Command {

	@Override
	public Status execute(Environment env, String arguments) {
		if (env.getResults() == null) {
			env.writeln("Command 'type' cannot be called when there is no results yet.");
			return Status.CONTINUE;
		}
		int index;
		try {
			index = Integer.parseInt(arguments);
		} catch (Exception e) {
			env.writeln("Invalid argument.");
			return Status.CONTINUE;
		}
		try {

			String filePath = env.getResults()[index].getFilePath();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(Files.newInputStream(Paths.get(filePath))));

			String line;
			while ((line = reader.readLine()) != null) {
				env.writeln(line);
			}
		} catch (Exception e) {
			env.writeln("Error ocurred while reading file");
		}
		return Status.CONTINUE;
	}

}
