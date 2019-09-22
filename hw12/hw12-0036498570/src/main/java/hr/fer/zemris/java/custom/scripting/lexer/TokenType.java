package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Types that {@link Lexer} can adress to {@link Token}. Possible types:
 * {@code EOF, TEXT, OPEN_TAG, CLOSE_TAG, INTEGER, DOUBLE, STRING, NAME, FUNCTION, OPERATOR}.
 * 
 * @author Andrej Ceraj
 *
 */
public enum TokenType {
	EOF, TEXT, OPEN_TAG, CLOSE_TAG, INTEGER, DOUBLE, STRING, NAME, FUNCTION, OPERATOR;
}
