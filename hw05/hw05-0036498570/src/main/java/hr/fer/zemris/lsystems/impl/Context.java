package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class used to store {@link TurtleState}s by putting them on stack. First
 * state on the stack is the current {@code TurtleState}.
 * 
 * @author Andrej Ceraj
 *
 */
public class Context {
	/**
	 * Representation of stack to store {@code TurtleState}s.
	 */
	public ObjectStack<TurtleState> stack;

	/**
	 * Creates an instance of {@code Context} with empty stack.
	 */
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}

	/** 
	 * @return current {@code TurtleState}
	 */
	public TurtleState getTurtleState() {
		return stack.peak();
	}

	/**
	 * Pushes current {@code TurtleState} on stack.
	 * 
	 * @param turtleState
	 */
	public void pushState(TurtleState turtleState) {
		stack.push(turtleState);
	}

	/**
	 * Pops (deletes) one {@code TurtleState} from the stack.
	 */
	public void popState() {
		stack.pop();
	}

}
