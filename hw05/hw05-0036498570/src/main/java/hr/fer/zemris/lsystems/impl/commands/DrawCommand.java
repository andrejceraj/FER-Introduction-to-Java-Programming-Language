package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Moves the turtle while drawing on the screen for given number of steps.
 * 
 * @author Andrej Ceraj
 *
 */
public class DrawCommand implements Command {
	/**
	 * Steps turtle should make while drawing.
	 */
	private double step;

	/**
	 * Creates an instance of {@code DrawCommand} with given number of turtle steps.
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		Command skip = new SkipCommand(step);
		Vector2D startingPosition = new Vector2D(ctx.getTurtleState().getPosition().getX(),
				ctx.getTurtleState().getPosition().getY());
		skip.execute(ctx, painter);

		painter.drawLine(startingPosition.getX(), startingPosition.getY(), ctx.getTurtleState().getPosition().getX(),
				ctx.getTurtleState().getPosition().getY(), ctx.getTurtleState().getColor(), 1f);
	}

}
