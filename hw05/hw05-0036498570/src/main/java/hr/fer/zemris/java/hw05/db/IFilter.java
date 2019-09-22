package hr.fer.zemris.java.hw05.db;

/**
 * Method that filter classes should implement.
 * 
 * @author Andrej Ceraj
 *
 */
public interface IFilter {
	/**
	 * Checks if {@code StudentRecord} passes the filter.
	 * 
	 * @param studentRecord
	 * @return True if {@code StudentRecord} passes the filter; false otherwise.
	 */
	boolean accepts(StudentRecord studentRecord);
}
