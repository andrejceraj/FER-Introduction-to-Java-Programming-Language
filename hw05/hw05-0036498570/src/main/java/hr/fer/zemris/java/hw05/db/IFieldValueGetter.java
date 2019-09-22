package hr.fer.zemris.java.hw05.db;

/**
 * Gets value of an appropriate field of {@link StudentRecord}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface IFieldValueGetter {
	/**
	 * Gets value of one field of the given {@code StudentRecord}. 
	 * 
	 * @param studentRecord
	 * @return field value
	 */
	String get(StudentRecord studentRecord);
}
