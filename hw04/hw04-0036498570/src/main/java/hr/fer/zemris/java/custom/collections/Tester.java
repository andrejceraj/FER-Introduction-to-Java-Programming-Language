package hr.fer.zemris.java.custom.collections;

/**
 * Interface with method to test an object by some criteria
 * 
 * @author Andrej Ceraj
 * 
 */
public interface Tester<T> {
	/**
	 * Tests if the object is valid based on some criteria.
	 * 
	 * @param object Object to be tested
	 * @return True if it the object is valid; false otherwise
	 */
	boolean test(T object);
}
