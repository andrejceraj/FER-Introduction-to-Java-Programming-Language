package hr.fer.zemris.java.custom.collections;

/**
 * Interface with additional methods to interface {@link Collection}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface List<T> extends Collection<T> {
	/**
	 * Gets an object stored to the given index in a collection.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                   invalid range.
	 * @param index Index of the object to get.
	 * @return Object stored to a given index.
	 */
	T get(int index);

	/**
	 * Inserts the given object to the given index in the collection and shifts all
	 * other objects starting from the index by one position to the right.
	 * 
	 * @throws NullPointerException      Exception is thrown if the given object is
	 *                                   null.
	 * @throws IndexOutOfBoundsException Exception is thrown if the given index is
	 *                                   greater than the size of the collection
	 * @param value Object to be inserted into the collection
	 * @param index Index to which the object should be inserted.
	 */
	void insert(T value, int position);

	/**
	 * Gets the index of the first representation of the given object in the
	 * collection
	 * 
	 * @param value Object of which index is searched for
	 * @return Index of the given object. -1 if the object is not contained in the
	 *         collection.
	 */
	int indexOf(Object value);

	/**
	 * Removes the object from the given index in the collection. Objects after the
	 * index of the removed object are then shifted to the left by one position.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                   invalid range.
	 * @param index Index of the object to be removed
	 * @return True if the object is successfully removed, false otherwise
	 */
	void remove(int index);
}
