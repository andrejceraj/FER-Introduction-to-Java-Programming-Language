package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Scales length of one turtle step in the current {@code TurtleState} by the
 * given factor.
 * 
 * @author Andrej Ceraj
 *
 */
public class ScaleCommand implements Command {
	/**
	 * Scaling factor
	 */
	private double factor;

	/**
	 * Creates an instance of {@code ScaleCommand} with scaling factor.
	 * 
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getTurtleState().setShift(ctx.getTurtleState().getShift() * factor);
	}
}
