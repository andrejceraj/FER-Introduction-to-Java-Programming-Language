package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An object that has a certain {@link TokenType}.
 * 
 * @author Andrej Ceraj
 *
 */
public class Token {
	private TokenType tokenType;
	Object value;

	/**
	 * Creates an instance of a token with given type and value.
	 * 
	 * @param type  Token type
	 * @param value Value stored in token
	 */
	public Token(TokenType type, Object value) {
		this.tokenType = type;
		this.value = value;
	}

	/**
	 * @return Value of the token
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * @return Token type
	 */
	public TokenType getType() {
		return this.tokenType;
	}

}