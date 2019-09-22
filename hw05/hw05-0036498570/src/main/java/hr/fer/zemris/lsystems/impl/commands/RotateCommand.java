package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Rotates the turtle in the current {@code TurtleState}.
 * 
 * @author Andrej Ceraj
 *
 */
public class RotateCommand implements Command {
	/**
	 * Angle by which the turtle should be rotated.
	 */
	private double angle;

	/**
	 * 
	 * Creates an instance of {@code RotateCommand} with the given angle.
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getTurtleState().setOrientation(ctx.getTurtleState().getOrientation().rotated(Math.toRadians(angle)));
	}
}
