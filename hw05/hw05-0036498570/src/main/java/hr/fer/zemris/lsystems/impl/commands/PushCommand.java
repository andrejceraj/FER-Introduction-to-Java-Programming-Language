package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Pushes current {@code TurtleState} to the {@code Context} stack.
 * 
 * @author Andrej Ceraj
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copyState = ctx.getTurtleState().copy();
		ctx.pushState(copyState);
	}
}
