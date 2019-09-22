package hr.fer.zemris.java.gui.layouts;

/**
 * Calculator layout exception. It is thrown if not possible operation is to be
 * done upon {@link CalcLayout}
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcLayoutException extends RuntimeException {
	/**
	 * Defaultly generated ID
	 */
	private static final long serialVersionUID = -3503721957041493250L;

	/**
	 * Creates an instance of {@link CalcLayoutException}
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Creates an instance of {@link CalcLayoutException} with the given message
	 *
	 * @param message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}
