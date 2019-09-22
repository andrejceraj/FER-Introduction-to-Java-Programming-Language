package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Collection that uses an array to store objects. Null objects are not allowed
 * to be stored into the collection, although null objects in the array
 * represents empty indexes.
 * 
 * @author Andrej Ceraj
 *
 */
public class ArrayIndexedCollection implements List {
	/**
	 * Default capacity of an {@link ArrayIndexedCollection}
	 */
	private static final int DEFAULT_ARRAY_CAPACITY = 16;
	/**
	 * Initial array indexed collection size.
	 */
	private static final int INIT_COLLECTION_SIZE = 0;
	/**
	 * Number of elements in an array.
	 */
	private int size = INIT_COLLECTION_SIZE;
	/**
	 * Array of objects in the collection.
	 */
	private Object[] elements;

	private long modificationCount = 0;

	/**
	 * Creates array indexed collection with default capacity.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_ARRAY_CAPACITY);
	}

	/**
	 * Creates array indexed collection with capacity of a given value.
	 * 
	 * @throws IllegalArgumentException exception is thrown if value of initial
	 *                                  capacity is less than 1.
	 * @param initialCapacity Capacity of the {@link ArrayIndexedCollection}.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
	}

	/**
	 * Creates {@link ArrayIndexedCollection} with elements of the given collection.
	 * If the given collection size is greater than {@link DEFAULT_ARRAY_CAPACITY},
	 * {@link ArrayIndexedCollection} capacity is set to the given collection size.
	 * Otherwise it is set to {@link DEFAULT_ARRAY_CAPACITY}
	 * 
	 * @throws NullPointerException exception is thrown if the given collection is
	 *                              null.
	 * @param collection Collection from which elements are copied to
	 *                   {@link ArrayIndexedCollection}.
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, DEFAULT_ARRAY_CAPACITY);
	}

	/**
	 * Creates {@link ArrayIndexedCollection} with elements of the given collection.
	 * {@link ArrayIndexedCollection} capacity is set to the greater value of the
	 * given collection size and the given initial capacity.
	 * 
	 * @throws NullPointerException exception is thrown if the given collection is
	 *                              null.
	 * @param collection      Collection from which elements are copied to
	 *                        {@link ArrayIndexedCollection}.
	 * @param initialCapacity Capacity of {@link ArrayIndexedCollection} if its
	 *                        value is greater than the given collection size.
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		super();
		if (collection == null) {
			throw new NullPointerException();
		}
		elements = new Object[collection.size() > initialCapacity ? collection.size() : initialCapacity];
		addAll(collection);
	}

	/**
	 * @return Collection capacity.
	 */
	public int getCapacity() {
		return getElements().length;
	}

	/**
	 * @return Array of {@link ArrayIndexedCollection} elements.
	 */
	public Object[] getElements() {
		return elements;
	}

	/**
	 * @param size New size of the {@link ArrayIndexedCollection}
	 */
	private void setSize(int size) {
		this.size = size;
	}

	/**
	 * @param elements New array of elements of the {@link ArrayIndexedCollection}
	 */
	private void setElements(Object[] elements) {
		this.elements = elements;
		modificationCount++;
	}

	/**
	 * @return Size of {@link ArrayIndexedCollection}.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object to {@link ArrayIndexedCollection}. The object may not
	 * be null.
	 * 
	 * @throws NullPointerException Exception is thrown if the given object is null.
	 * @param value Object to add to {@link ArrayIndexedCollection}
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (size() == getCapacity()) {
			setElements(expandArrayLength(getCapacity() * 2));
		}
		getElements()[size()] = value;
		setSize(size() + 1);
		modificationCount++;
	}

	public Object get(int index) {
		if (index < 0 || index >= getCapacity()) {
			throw new IndexOutOfBoundsException();
		}
		return this.getElements()[index];
	}

	public void insert(Object value, int index) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (index == size() || size() == getCapacity()) {
			setElements(expandArrayLength(getCapacity() + 1));
		}
		shiftRightFromIndex(index);
		getElements()[index] = value;
		setSize(size() + 1);

	}

	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size(); i++) {
			if (get(i) != null && get(i).equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if the given object is contained in {@link ArrayIndexedCollection}.
	 * 
	 * @param value Object to check if it is contained in
	 *              {@link ArrayIndexedCollection}
	 * @return True if the object is contained in {@link ArrayIndexedCollection},
	 *         false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Removes the first representation of the given object from the
	 * {@link ArrayIndexedCollection}. Objects after the index of the removed object
	 * are then shifted to the left by one position.
	 * 
	 * @param value Object to be removed from the {@link ArrayIndexedCollection}
	 * @return True if the object is successfully removed, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		boolean removed = false;
		for (int i = 0; i < size(); i++) {
			if (get(i).equals(value)) {
				removed = true;
				shiftLeftFromIndex(i);
				setSize(size() - 1);
				break;
			}
		}
		return removed;
	}

	public void remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		shiftLeftFromIndex(index);
		setSize(size() - 1);
	}

	/**
	 * Method gets the objects from the {@link ArrayIndexedCollection} and returns
	 * them as an array of objects.
	 * 
	 * @return Array of objects from {@link ArrayIndexedCollection}
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size()];
		for (int i = 0; i < size(); i++) {
			array[i] = get(i);
		}
		return array;
	}

	/**
	 * Clears all the objects from the {@link ArrayIndexedCollection}.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size(); i++) {
			getElements()[i] = null;
		}
		setSize(INIT_COLLECTION_SIZE);
		modificationCount++;
	}

	/**
	 * Method shifts all objects one position to the left from the starting index to
	 * the end of {@link ArrayIndexedCollection}.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                   invalid range.
	 * @param index Starting index
	 */
	private void shiftLeftFromIndex(int index) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = index; i < size() - 1; i++) {
			getElements()[i] = getElements()[i + 1];
		}
		getElements()[size()] = null;
		modificationCount++;
	}

	/**
	 * Method shifts all objects one position to the right from the starting index
	 * to the end of {@link ArrayIndexedCollection}.
	 * 
	 * @param index Starting index
	 */
	private void shiftRightFromIndex(int index) {
		for (int i = size(); i > index + 1; i--) {
			getElements()[i] = getElements()[i - 1];
		}
		getElements()[index] = null;
		modificationCount++;
	}

	/**
	 * Expands the {@link ArrayIndexedCollection} capacity to the given value.
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the given capacity is
	 *                                  lesser than the
	 *                                  {@link ArrayIndexedCollection} capacity.
	 * @param newCapacity New {@link ArrayIndexedCollection} capacity
	 * @return New array of elements with expanded capacity.
	 */
	private Object[] expandArrayLength(int newCapacity) {
		if (newCapacity < getCapacity()) {
			throw new IllegalArgumentException();
		}
		Object[] newArray = new Object[newCapacity];
		for (int i = 0; i < size(); i++) {
			newArray[i] = get(i);
		}
		modificationCount++;
		return newArray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + Objects.hash(size);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ArrayIndexedCollection))
			return false;
		ArrayIndexedCollection other = (ArrayIndexedCollection) obj;
		return Arrays.deepEquals(elements, other.elements) && size == other.size;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayElementsGetter(this, modificationCount);
	}

	/**
	 * Class implements {@link ElementsGetter}. It uses a reference to outer class
	 * {@code ArrayIndexedCollection} and {@code int} type {@code index} to have a
	 * memory of position from where it should check if the collection has a next
	 * element or to get the next element.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class ArrayElementsGetter implements ElementsGetter {
		/**
		 * Reference to collection {@code EllementsGetter} is handling.
		 */
		private ArrayIndexedCollection arrayIndexedCollection;
		/**
		 * Current index of position in the collection.
		 */
		private int index = 0;
		/**
		 * Variable used to be compared with {@code modificationCount} and if they are
		 * not equal, then there has been some modifications in a collection.
		 */
		long savedModificationCount;

		/**
		 * Creates an instance of {@code ArrayElementsGetter}.
		 * 
		 * @param array {@code ArrayIndexedCollection}
		 */
		public ArrayElementsGetter(ArrayIndexedCollection array, long modificationCount) {
			arrayIndexedCollection = array;
			savedModificationCount = modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if (isModified()) {
				throw new ConcurrentModificationException();
			}
			return index < arrayIndexedCollection.size();
		}

		@Override
		public Object getNextElement() {
			if (isModified()) {
				throw new ConcurrentModificationException();
			}
			if (index >= arrayIndexedCollection.size()) {
				throw new NoSuchElementException();
			}
			return arrayIndexedCollection.get(index++);
		}

		@Override
		public boolean isModified() {
			return savedModificationCount != arrayIndexedCollection.modificationCount;
		}

		@Override
		public void forEachRemaining(Processor processor) {
			for (int i = index; i < arrayIndexedCollection.size(); i++) {
				processor.process(arrayIndexedCollection.get(i));
			}
		}
	}

}