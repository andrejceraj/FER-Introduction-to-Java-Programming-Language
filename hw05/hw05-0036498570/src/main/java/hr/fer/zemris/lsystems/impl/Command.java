package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * {@code Command} stores an action that can be executed. Action involves
 * {@link Context} and {@code Painter}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface Command {
	/**
	 * Performs the action stored in this command on {@code Context} and {@code Painter}.
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	void execute(Context ctx, Painter painter);
}
