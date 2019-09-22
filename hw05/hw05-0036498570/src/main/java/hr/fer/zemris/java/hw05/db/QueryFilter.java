package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class contains list of expressions and upon calling method {@code accepts}
 * checks if the given {@link StudentRecord} satisfies all of the expressions.
 * 
 * @author Andrej Ceraj
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * Query expressions
	 */
	List<ConditionalExpression> expressions;

	/**
	 * Creates an instance of {@code QueryFilter} with the given expressions.
	 * 
	 * @param expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord studentRecord) {
		if (expressions.isEmpty()) {
			return false;
		}
		for (ConditionalExpression expression : expressions) {
			if (!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(studentRecord),
					expression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
