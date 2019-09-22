package hr.fer.zemris.java.hw05.db;

/**
 * Interface every {@code ComparisonOperator} should implement.
 * 
 * @author Andrej Ceraj
 *
 */
public interface IComparisonOperator {
	/**
	 * Checks if the given strings satisfies some criteria.
	 * 
	 * @param string1
	 * @param string2
	 * @return true if strings satisfies some criteria; false otherwise.
	 */
	boolean satisfied(String string1, String string2);
}
