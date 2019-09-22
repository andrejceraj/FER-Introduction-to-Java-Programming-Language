package hr.fer.zemris.java.hw14.dao;

/**
 * Exception that is used in Data Access Object layer.
 * 
 * @author Andrej Ceraj
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -4274318557209710708L;

	/**
	 * Constructor
	 */
	public DAOException() {
	}

	/**
	 * Constructor with message and cause.
	 * 
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with message.
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
}