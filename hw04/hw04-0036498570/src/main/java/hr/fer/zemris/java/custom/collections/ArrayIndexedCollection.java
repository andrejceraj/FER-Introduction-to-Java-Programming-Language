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
public class ArrayIndexedCollection<T> implements List<T> {
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
	private T[] elements;

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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = (T[]) new Object[initialCapacity];
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
	public ArrayIndexedCollection(Collection<T> collection) {
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		super();
		if (collection == null) {
			throw new NullPointerException();
		}
		elements = (T[]) new Object[collection.size() > initialCapacity ? collection.size() : initialCapacity];
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
	public T[] getElements() {
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
	private void setElements(T[] elements) {
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
	public void add(T value) {
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

	public T get(int index) {
		if (index < 0 || index >= getCapacity()) {
			throw new IndexOutOfBoundsException();
		}
		return this.getElements()[index];
	}

	public void insert(T value, int index) {
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
	@SuppressWarnings("unchecked")
	private T[] expandArrayLength(int newCapacity) {
		if (newCapacity < getCapacity()) {
			throw new IllegalArgumentException();
		}
		T[] newArray = (T[]) new Object[newCapacity];
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
		result = prime * result + Objects.hash(modificationCount, size);
		return result;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ArrayIndexedCollection))
			return false;
		ArrayIndexedCollection other = (ArrayIndexedCollection) obj;
		return Arrays.deepEquals(elements, other.elements) && modificationCount == other.modificationCount
				&& size == other.size;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayElementsGetter<T>(this, modificationCount);
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
	private static class ArrayElementsGetter<T> implements ElementsGetter<T> {
		/**
		 * Reference to collection {@code EllementsGetter} is handling.
		 */
		private ArrayIndexedCollection<T> arrayIndexedCollection;
		/**
		 * Current index of position in the collection.
		 */
		private int index = 0;
		/**
		 * Variable used to be compared with {@code modificationCount} and if they are
		 * not equal, then there has been some modifications in a collection.
		 */
		private long savedModificationCount;

		/**
		 * Creates an instance of {@code ArrayElementsGetter}.
		 * 
		 * @param array {@code ArrayIndexedCollection}
		 */
		public ArrayElementsGetter(ArrayIndexedCollection<T> array, long modificationCount) {
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
		public T getNextElement() {
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
		public void forEachRemaining(Processor<? super T> processor) {
			for (int i = index; i < arrayIndexedCollection.size(); i++) {
				processor.process(arrayIndexedCollection.get(i));
			}
		}
	}

}