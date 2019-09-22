package hr.fer.zemris.java.hw05.db;

/**
 * Representation of query expression.
 * 
 * @author Andrej Ceraj
 *
 */
public class ConditionalExpression {
	/**
	 * Field a query is comparing.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * String that query is comparing field to.
	 */
	private String stringLiteral;
	/**
	 * Comparison operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Creates an instance of {@code ConditionalExpression} that compares field with
	 * string using comparison operator.
	 * 
	 * @param fieldGetter        field
	 * @param stringLiteral      string
	 * @param comparisonOperator comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * @return field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return string 
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
