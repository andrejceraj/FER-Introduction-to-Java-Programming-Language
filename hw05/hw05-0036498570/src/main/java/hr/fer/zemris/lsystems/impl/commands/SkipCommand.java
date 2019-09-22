package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Moves the turtle for given number of steps.
 * 
 * @author Andrej Ceraj
 *
 */
public class SkipCommand implements Command {
	/**
	 * Steps turtle should make.
	 */
	private double step;

	/**
	 * Creates an instance of {@code SkipCommand} with given number of turtle steps.
	 * 
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getTurtleState();
		Vector2D startingPosition = currentState.getPosition();
		Vector2D orientationVector = currentState.getOrientation();
		double shift = currentState.getShift();
		Vector2D finishVector = startingPosition.translated(orientationVector.scaled(shift * step));
		currentState.setPosition(finishVector);
	}

}
