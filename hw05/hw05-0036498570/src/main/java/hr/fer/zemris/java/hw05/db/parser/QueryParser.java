package hr.fer.zemris.java.hw05.db.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter;
import hr.fer.zemris.java.hw05.db.lexer.SimpleLexer;
import hr.fer.zemris.java.hw05.db.lexer.SimpleToken;
import hr.fer.zemris.java.hw05.db.lexer.SimpleTokenType;

/**
 * QueryParser is used to convert given text (query) into list of
 * {@link ConditionalExpression}s.
 * 
 * @author Andrej Ceraj
 *
 */
public class QueryParser {
	private static final String JMBAG = "jmbag";
	private static final String LAST_NAME = "lastName";
	private static final String FIRST_NAME = "firstName";

	/**
	 * List of expression gotten from parsing the text (query).
	 */
	List<ConditionalExpression> query;
	/**
	 * Lexer used to tokenize the text (query)
	 */
	SimpleLexer lexer;
	/**
	 * Current token.
	 */
	SimpleToken token;

	/**
	 * Creates an instance of {@code QueryParser} and parses the given text (query).
	 * 
	 * @param data Query
	 */
	public QueryParser(String data) {
		query = new ArrayList<ConditionalExpression>();
		lexer = new SimpleLexer(data);
		token = lexer.nextToken();
		parse();

	}

	/**
	 * @return true if the text is direct query, which means that it compares if
	 *         {@code JMBAG} is equal to user input; false otherwise.
	 */
	public boolean isDirectQuery() {
		if (query.size() == 1 && query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG)
				&& query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)) {
			return true;
		}
		return false;
	}

	/**
	 * @return queried {@code JMBAG}.
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Querry is not direct");
		}
		return query.get(0).getStringLiteral();
	}

	/**
	 * @return List of {@code ConditionalExpression} that are parsed from the given
	 *         text (query).
	 */
	public List<ConditionalExpression> getQuery() {
		return query;
	}

	/**
	 * Method parses the text (query) into expressions.
	 * 
	 * @throws QueryParserException Exception is thrown if the text (query) is
	 *                              invalid.
	 */
	private void parse() {
		while (token.getType() != SimpleTokenType.EOF) {
			if (token.getType() == SimpleTokenType.NAME && token.getValue().toUpperCase().equals("AND")) {
				token = lexer.nextToken();
				continue;
			} else if (token.getType() == SimpleTokenType.NAME) {
				parseExpression();
			} else {
				throw new QueryParserException("Invalid expression");
			}
		}
	}

	/**
	 * Parses part of the text into one expression and adds that expression to the
	 * list of expressions.
	 */
	private void parseExpression() {
		IFieldValueGetter fieldGetter = getField();
		token = lexer.nextToken();
		IComparisonOperator comparisonOperator = getOperator();
		token = lexer.nextToken();
		String stringLiteral = getStringLiteral();
		token = lexer.nextToken();

		query.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
	}

	/**
	 * @throws QueryParserException Exception is thrown if the token value is
	 *                              invalid.
	 * @return returns appropriate {@link IFieldValueGetter} based on the token
	 *         value.
	 */
	private IFieldValueGetter getField() {
		if (token.getValue().equals(JMBAG)) {
			return FieldValueGetters.JMBAG;
		} else if (token.getValue().equals(LAST_NAME)) {
			return FieldValueGetters.LAST_NAME;
		} else if (token.getValue().equals(FIRST_NAME)) {
			return FieldValueGetters.FIRST_NAME;
		} else {
			throw new QueryParserException("Invalid variable name: " + token.getValue());
		}
	}

	/**
	 * @throws QueryParserException Exception is thrown if the operator token value
	 *                              is invalid or if the token type is not
	 *                              {@code OPERATOR}.
	 * @return appropriate {@link IComparisonOperator} based on the operator token
	 *         value.
	 */
	private IComparisonOperator getOperator() {
		if (token.getType() == SimpleTokenType.OPERATOR) {
			switch (token.getValue()) {
			case "<":
				return ComparisonOperators.LESS;
			case "<=":
				return ComparisonOperators.LESS_OR_EQUALS;
			case ">":
				return ComparisonOperators.GREATER;
			case ">=":
				return ComparisonOperators.GREATER_OR_EQUALS;
			case "=":
				return ComparisonOperators.EQUALS;
			case "!=":
				return ComparisonOperators.NOT_EQUALS;
			default:
				throw new QueryParserException("Invalid operator: " + token.getValue());
			}
		} else if (token.getType() == SimpleTokenType.NAME && token.getValue().toUpperCase().equals("LIKE")) {
			return ComparisonOperators.LIKE;
		} else {
			throw new QueryParserException("Expected operator instead of:" + token.getValue());
		}
	}

	/**
	 * @throws QueryParserException Exception is thrown if the token type is not
	 *                              {@code STRING_LITERAL}.
	 * @return string literal
	 */
	private String getStringLiteral() {
		if (token.getType() == SimpleTokenType.STRING_LITERAL) {
			return token.getValue();
		} else {
			throw new QueryParserException("Expected string literal instead of: " + token.getValue());
		}
	}

}
