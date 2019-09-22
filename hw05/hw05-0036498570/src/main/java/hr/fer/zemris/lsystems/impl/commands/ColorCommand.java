package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Changes the color in the current {@code TurtleState}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ColorCommand implements Command {
	/**
	 * New color
	 */
	Color color;

	/**
	 * Creates an instance of {@code ColorCommand} with new color.
	 * 
	 * @param color New color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getTurtleState().setColor(color);

	}
}
