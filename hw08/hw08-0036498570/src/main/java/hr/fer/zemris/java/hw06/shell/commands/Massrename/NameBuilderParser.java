package hr.fer.zemris.java.hw06.shell.commands.Massrename;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser iterates through the expression and creates certain
 * {@link NameBuilder}s based on it.
 * 
 * @author Andrej Ceraj
 *
 */
public class NameBuilderParser {
	/**
	 * Renaming pattern
	 */
	private String expression;

	/**
	 * Creates an instance of {@code NameBuilderParser}.
	 * 
	 * @param expression renaming pattern
	 */
	public NameBuilderParser(String expression) {
		this.expression = expression;
	}

	/**
	 * @return {@link NameBuilder} that contains other {@code NameBuilder}s and it's
	 *         method {@code execute} calls all containing Name builder's
	 *         {@code execute} method.
	 */
	public NameBuilder getNameBuilder() {
		List<NameBuilder> nameBuilders = new ArrayList<NameBuilder>();
		parseExpression(expression.replaceAll("\\s+", " ").toCharArray(), nameBuilders);
		return NameBuilders.builder(nameBuilders);
	}

	/**
	 * Method parses the given expression.
	 * 
	 * @param expression   renaming pattern
	 * @param nameBuilders list to which created {@code NameBuilder}s should be
	 *                     added
	 */
	private void parseExpression(char[] expression, List<NameBuilder> nameBuilders) {
		for (int i = 0; i < expression.length;) {
			if (expression[i] == '$' && expression[i + 1] == '{') {
				i += 2;
				try {
					i = parseGroup(i, expression, nameBuilders);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid expression: " + this.expression);
				}
			} else {
				i = parseText(i, expression, nameBuilders);
			}
		}
	}

	/**
	 * Parses text part of the expression and creates a {@code NameBuilder} for it.
	 * 
	 * @param i            iterating index
	 * @param expression   renaming pattern
	 * @param nameBuilders list to which created {@code NameBuilder}s should be
	 *                     added
	 * @return current iterating index
	 */
	private int parseText(int i, char[] expression, List<NameBuilder> nameBuilders) {
		int start = i;
		while (i < expression.length && (expression[i] != '$' || expression[i + 1] != '{')) {
			i++;
		}
		String text = String.valueOf(expression, start, i - start);
		nameBuilders.add(NameBuilders.text(text));
		return i;
	}

	/**
	 * Parses grouping part of the expression and creates a {@code NameBuilder} for
	 * it.
	 * 
	 * @param i            iterating index
	 * @param expression   renaming pattern
	 * @param nameBuilders list to which created {@code NameBuilder}s should be
	 *                     added
	 * @return current iterating index
	 */
	private int parseGroup(int i, char[] expression, List<NameBuilder> nameBuilders) {
		int index = 0;
		char padding = 0;
		int minWidth = -1;
		boolean setIndex = false;
		boolean comma = false;
		boolean setWidth = false;
		while (true) {
			if (expression[i] == '}') {
				i++;
				break;
			} else if (setIndex == false && comma == false && setWidth == false && Character.isDigit(expression[i])) {
				setIndex = true;
				int start = i;
				while (i < expression.length && Character.isDigit(expression[i])) {
					i++;
				}
				index = Integer.parseInt(String.copyValueOf(expression, start, i - start));
			} else if (setIndex == true && comma == false && setWidth == false && expression[i] == ',') {
				comma = true;
				i++;
			} else if (setIndex == true && comma == true && setWidth == false
					&& !Character.isWhitespace(expression[i])) {
				padding = expression[i];
				setWidth = true;
				i++;
				int start = i;
				while (i < expression.length && Character.isDigit(expression[i])) {
					i++;
				}
				if (start != i) {
					minWidth = Integer.parseInt(String.copyValueOf(expression, start, i - start));
				} else {
					minWidth = Integer.parseInt(String.valueOf(padding));
					padding = ' ';
				}
			} else if (Character.isWhitespace(expression[i])) {
				i++;
			} else {
				throw new IllegalArgumentException();
			}
		}
		if (minWidth == -1) {
			nameBuilders.add(NameBuilders.group(index));
		} else {
			nameBuilders.add(NameBuilders.group(index, padding, minWidth));
		}
		return i;
	}
}
