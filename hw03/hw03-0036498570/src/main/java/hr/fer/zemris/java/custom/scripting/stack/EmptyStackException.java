package hr.fer.zemris.java.custom.scripting.stack;

/**
 * Exception thrown when peak or pop actions are called upon the array
 * representing a stack is empty
 * 
 * @author Andrej Ceraj
 *
 */
public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = 6596221081628327342L;

	/**
	 * Creates an instance of {@code EmptyStackException}
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Creates an instance of {@code EmptyStackException} with a given message.
	 * 
	 * @param message Exception message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
