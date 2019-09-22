package hr.fer.zemris.java.hw05.db;

/**
 * Class contains all possible implementations of {@link IComparisonOperator}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ComparisonOperators {
	/**
	 * Representation of operator '<'.
	 */
	public static final IComparisonOperator LESS = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			return string1.compareTo(string2) < 0;
		}
	};
	/**
	 * Representation of operator '<='.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			return string1.compareTo(string2) <= 0;
		}
	};
	/**
	 * Representation of operator '>'.
	 */
	public static final IComparisonOperator GREATER = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			return string1.compareTo(string2) > 0;
		}
	};
	/**
	 * Representation of operator '>='.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			return string1.compareTo(string2) >= 0;
		}
	};
	/**
	 * Representation of operator '='.
	 */
	public static final IComparisonOperator EQUALS = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			return string1.compareTo(string2) == 0;
		}
	};
	/**
	 * Representation of operator '!='.
	 */
	public static final IComparisonOperator NOT_EQUALS = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			return string1.compareTo(string2) != 0;
		}
	};
	/**
	 * <p>
	 * Representation of operator that compares if first string is like the second
	 * string, where second string can contain one symbol *. Symbol * represents any
	 * character array (including an empty array).
	 * </p>
	 * Examples:
	 * <li>"ABCDE" like "ABCDE" -> true</li>
	 * <li>"ABCDE" like "A*" -> true</li>
	 * <li>"ABCDE" like "ABCDE*" -> true</li>
	 * <li>"ABCDE" like "Z*" -> false</li>
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String string1, String string2) {
			int indexOfStar = string2.indexOf('*');
			if (indexOfStar != string2.lastIndexOf('*')) {
				throw new IllegalArgumentException(string2 + " has 2 characters *");
			} else if (indexOfStar == -1) {
				return ComparisonOperators.EQUALS.satisfied(string1, string2);
			} else {
				String prefix = string2.substring(0, indexOfStar);
				String suffix = string2.substring(indexOfStar + 1, string2.length());
				if (string1.startsWith(prefix) && string1.endsWith(suffix)
						&& string1.length() >= string2.length() - 1) {
					return true;
				}
			}
			return false;
		}
	};
}
