package hr.fer.zemris.java.custom.collections;

/**
 * Representation of stack object
 * 
 * @author Andrej Ceraj
 */
public class ObjectStack {
	/**
	 * Collection used to store objects in stack
	 */
	private ArrayIndexedCollection arrayIndexedCollection;

	/**
	 * Creates an empty {@link ObjectStack}.
	 */
	public ObjectStack() {
		arrayIndexedCollection = new ArrayIndexedCollection();
	}

	/**
	 * @return Returns local {@link ArrayIndexedCollection}
	 */
	private ArrayIndexedCollection getArrayIndexedCollection() {
		return arrayIndexedCollection;
	}

	/**
	 * Checks if the {@link ObjectStack} is empty.
	 * 
	 * @return True if the {@link ObjectStack} is empty, false otherwise
	 */
	public boolean isEmpty() {
		return getArrayIndexedCollection().isEmpty();
	}

	/**
	 * @return Number of objects in the {@link ObjectStack}
	 */
	public int size() {
		return getArrayIndexedCollection().size();
	}

	/**
	 * Adds the given object to top of the {@link ObjectStack}
	 * 
	 * @throws NullPointerException Exception is thrown if the object is null.
	 * @param value Object to be inserted to top of the {@link ObjectStack}
	 */
	public void push(Object value) {
		getArrayIndexedCollection().add(value);
	}

	/**
	 * Gets the top object of the {@link ObjectStack}. The object is then removed
	 * from the {@link ObjectStack}.
	 * 
	 * @throws EmptyStackException Exception is thrown if the stack is empty
	 * @return Object from the top of the {@link ObjectStack}
	 */
	public Object pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		Object object = getArrayIndexedCollection().get(size() - 1);
		getArrayIndexedCollection().remove(size() - 1);
		return object;
	}

	/**
	 * Gets the top object of the {@link ObjectStack}
	 * 
	 * @throws EmptyStackException Exception is thrown if the stack is empty
	 * @return Object from the top of the {@link ObjectStack}
	 */
	public Object peak() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return getArrayIndexedCollection().get(size() - 1);
	}

	/**
	 * Clears all the objects from the {@link ObjectStack}.
	 */
	public void clear() {
		getArrayIndexedCollection().clear();
	}
}
