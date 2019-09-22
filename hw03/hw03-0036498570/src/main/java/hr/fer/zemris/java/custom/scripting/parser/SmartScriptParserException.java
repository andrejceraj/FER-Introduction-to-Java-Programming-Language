package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that should be thrown when an error occurs while parsing the text.
 * 
 * @author Andrej Ceraj
 *
 */
public class SmartScriptParserException extends RuntimeException {
	private static final long serialVersionUID = 981395893165535867L;

	/**
	 * Creates an instance of {@code SmartScriptParserException}.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Creates an instance of {@code SmartScriptParserException} with the given
	 * message.
	 * 
	 * @param message Exception message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
