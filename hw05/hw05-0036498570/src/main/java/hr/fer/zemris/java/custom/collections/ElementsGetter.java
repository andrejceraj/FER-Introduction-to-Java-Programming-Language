package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * Classes implementing this interface should have a memory of last returned
 * object of a collection. Method {@link hasNextElement} checks if there are
 * still elements in the collection that has not been returned yet. Method
 * {@link getNextElement} returns the next element in the collection if there is
 * any.
 * 
 * 
 * @author Andrej Ceraj
 *
 */

public interface ElementsGetter<T> {
	/**
	 * Checks if the collection has next element.
	 * 
	 * @throws ConcurrentModificationException Exception is thrown if the collection
	 *                                         has been modified since the
	 *                                         construction of an instance of
	 *                                         {@code ElementsGetter}.
	 * @return True if collection still has next element; false otherwise
	 */
	boolean hasNextElement();

	/**
	 * Gets next element in the collection.
	 * 
	 * * @throws ConcurrentModificationException Exception is thrown if the
	 * collection has been modified since the construction of an instance of
	 * {@code ElementsGetter}.
	 * 
	 * @throws NoSuchElementException Exception is thrown if there is no next
	 *                                element.
	 * @return Next element
	 */
	T getNextElement();

	/**
	 * Checks if the the collection has been modified.
	 * 
	 * @return True if the collection has been modified; false otherwise
	 */
	boolean isModified();

	/**
	 * Calls {@code processor.process} on each of the remaining elements of the
	 * collection
	 * 
	 * @param processor
	 */
	void forEachRemaining(Processor<? super T> processor);

}
