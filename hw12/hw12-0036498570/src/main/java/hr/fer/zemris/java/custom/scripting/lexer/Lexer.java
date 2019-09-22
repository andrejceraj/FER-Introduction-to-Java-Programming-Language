package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Arrays;
import java.util.Objects;

/**
 * Lexer is used to process given text. It turns parts of the text to tokens.
 * Lexer can work in two {@link LexerState}s and processes the text based on the
 * current state.
 * 
 * @author Andrej Ceraj
 *
 */
public class Lexer {
	private static final int INITIAL_INDEX = 0;
	private static final char[] BLANKS = { '\r', '\n', '\t', ' ' };
	private static final char[] OPERATORS = { '+', '-', '*', '/', '^' };
	/**
	 * Entry text
	 */
	private char[] data;
	/**
	 * Current part of the text converted to token
	 */
	private Token token;
	/**
	 * Index of first unprocessed text symbol
	 */
	private int currentIndex;
	/**
	 * State Lexer is in
	 */
	private LexerState lexerState;

	/**
	 * Creates an instance of {@code Lexer}.
	 * 
	 * @throws NullPointerException Exception is thrown if the given text is null.
	 * @param text Text to be processed
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		data = text.toCharArray();
		token = null;
		currentIndex = INITIAL_INDEX;
		lexerState = LexerState.TEXT;
	}

	/**
	 * 
	 * Sets {@code Lexer} to new state
	 * 
	 * @throws NullPointerException Exception is thrown if the given state is null.
	 * @param state New state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.lexerState = state;
	}

	/**
	 * Returns current {@link Token}. Multiple call on this method returns the same
	 * token until the {@code nextToken()} is called.
	 * 
	 * @return Current token
	 */
	public Token getToken() {
		return this.token;
	}

	/**
	 * Convert part of the text to new token and returns the new token.
	 * 
	 * @throws LexerException Exception is thrown if the {@code Lexer} is unable to
	 *                        convert text to new token.
	 * @return New token
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException();
		}
		try {
			if (currentIndex >= data.length) {
				token = new Token(TokenType.EOF, null);
			} else if (lexerState == LexerState.TEXT) {
				processText();
			} else if (lexerState == LexerState.TAG) {
				processTag();
			}
			return token;
		} catch (Exception exception) {
			throw new LexerException();
		}
	}

	/**
	 * Method processes further input text as the Lexer is in TEXT state. As much
	 * input text as possible is tokenized and stored in {@code token} with type
	 * TEXT.
	 * 
	 * @throws LexerException Exception is thrown if the escaping character is used
	 *                        improperly.
	 */
	private void processText() {
		if (nextIsOpenTag()) {
			token = new Token(TokenType.OPEN_TAG, "{$");
			currentIndex += 2;
			return;
		}
		String text = "";
		while (currentIndex < data.length) {
			if (nextIsOpenTag()) {
				break;
			}
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\') {
					text += "\\\\";
					currentIndex += 2;
				} else if (data[currentIndex + 1] == '{') {
					text += "\\{";
					currentIndex += 2;
				} else {
					throw new LexerException();
				}
			} else {
				text += data[currentIndex++];
			}

		}
		token = new Token(TokenType.TEXT, (Object) text);
	}

	/**
	 * Method runs when Lexer is set to TAG state. The method processes part of the
	 * input text and tokenizes it. {@code token} is set to newly tokenized text.
	 * 
	 * @throws LexerException Exception is thrown if processing method for the
	 *                        current character is not defined.
	 */
	private void processTag() {
		getRidOfSpaces();
		if (data[currentIndex] == '\"') {
			processString();
		} else if (data[currentIndex] == '@') {
			processFunction();
		} else if (Character.isDigit(data[currentIndex])
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))) {
			processNumber();
		} else if (data[currentIndex] == '=' || Character.isLetter(data[currentIndex])) {
			processName();
		} else if (nextCharIsContainedIn(OPERATORS)) {
			processOperator();
		} else if (nextIsCloseTag()) {
			token = new Token(TokenType.CLOSE_TAG, "$}");
			currentIndex += 2;
		} else {
			throw new LexerException("dont know how to process " + data[currentIndex]);
		}
	}

	/**
	 * Method tokenizes an operator
	 */
	private void processOperator() {
		token = new Token(TokenType.OPERATOR, data[currentIndex++]);
	}

	/**
	 * Method tokenizes a {@code String}
	 * 
	 * @throws LexerException Exception is thrown if the escaping character is used
	 *                        improperly.
	 */
	private void processString() {
		currentIndex++;
		String string = "\"";
		while (data[currentIndex] != '\"') {
			if (data[currentIndex] == '\\') {
				if (data[currentIndex + 1] == '\\') {
					string += "\\\\";
					currentIndex += 2;
				} else if (data[currentIndex + 1] == '{') {
					string += "\\{";
					currentIndex += 2;
				} else if (data[currentIndex + 1] == '\"') {
					string += "\\\"";
					currentIndex += 2;
				} else if (data[currentIndex + 1] == 't') {
					string += "\\t";
					currentIndex += 2;
				} else if (data[currentIndex + 1] == 'n') {
					string += "\\n";
					currentIndex += 2;
				} else if (data[currentIndex + 1] == 'r') {
					string += "\\r";
					currentIndex += 2;
				} else {
					throw new LexerException();
				}
			} else {
				string += data[currentIndex++];
			}
		}
		string += "\"";
		currentIndex++;
		token = new Token(TokenType.STRING, string);
	}

	/**
	 * Method tokenizes representation of a function
	 */
	private void processFunction() {
		currentIndex++;
		token = new Token(TokenType.FUNCTION, getVariableName());
	}

	/**
	 * Method tokenizes a number.
	 */
	private void processNumber() {
		String number = "";
		boolean decimal = false;
		if (data[currentIndex] == '-') {
			number += '-';
			currentIndex++;
		}
		while (Character.isDigit(data[currentIndex]) || (!decimal && data[currentIndex] == '.')) {
			if (data[currentIndex] == '.') {
				decimal = true;
			}
			number += data[currentIndex++];
		}
		if (decimal) {
			double decimalNumber = Double.parseDouble(number);
			token = new Token(TokenType.DOUBLE, decimalNumber);
		} else {
			int intNumber = Integer.parseInt(number);
			token = new Token(TokenType.INTEGER, intNumber);
		}
	}

	/**
	 * Method tokenizes expresson name or a variable.
	 */
	private void processName() {
		if (data[currentIndex] == '=') {
			token = new Token(TokenType.NAME, "=");
			currentIndex++;
		} else {
			token = new Token(TokenType.NAME, getVariableName());
		}
	}

	/**
	 * Method returns variable name when tokenizing part of the input text
	 * 
	 * @throws LexerException Exception is thrown if the name of a variable to be
	 *                        processed does not start with a letter.
	 * @return Variable name
	 */
	private String getVariableName() {
		if (!Character.isLetter(data[currentIndex])) {
			throw new LexerException();
		}
		String name = "";
		while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') {
			name += data[currentIndex++];
		}
		return name;
	}

	/**
	 * Method checks if next two characters of the processing input text is
	 * representation of an open tag "{@code{$}".
	 * 
	 * @return True if what follows is open tag; false otherwise
	 */
	private boolean nextIsOpenTag() {
		if (currentIndex >= data.length - 1) {
			return false;
		}
		return (data[currentIndex] == '{' && data[currentIndex + 1] == '$');
	}

	/**
	 * Method checks if next two characters of the processing input text is
	 * representation of a close tag "{@code$}}".
	 * 
	 * @return True if what follows is close tag; false otherwise
	 */
	private boolean nextIsCloseTag() {
		if (currentIndex >= data.length - 1) {
			return false;
		}
		return (data[currentIndex] == '$' && data[currentIndex + 1] == '}');
	}

	/**
	 * Method skips all spaces and blank characters until first non-blank character.
	 */
	private void getRidOfSpaces() {
		while (nextCharIsContainedIn(BLANKS)) {
			currentIndex++;
		}
	}

	/**
	 * Checks if the processing character is contained in a character sequence.
	 * 
	 * @param charSequenece List of characters
	 * @return True if it contained in the given character list; false otherwise
	 */
	private boolean nextCharIsContainedIn(char[] charSequenece) {
		if (currentIndex == data.length) {
			return false;
		}
		for (char character : charSequenece) {
			if (data[currentIndex] == character) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + Objects.hash(currentIndex, lexerState, token);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Lexer))
			return false;
		Lexer other = (Lexer) obj;
		return currentIndex == other.currentIndex && Arrays.equals(data, other.data) && lexerState == other.lexerState
				&& Objects.equals(token, other.token);
	}

}
