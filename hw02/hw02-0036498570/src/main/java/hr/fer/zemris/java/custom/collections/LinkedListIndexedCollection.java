package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Collection that uses doubly linked list to store objects. Null objects are
 * not allowed to be stored into the collection.
 * 
 * @author Andrej Ceraj
 *
 */
public class LinkedListIndexedCollection extends Collection {
	/**
	 * Initial array indexed collection size.
	 */
	private static final int INIT_COLLECTION_SIZE = 0;
	/**
	 * Size of the {@link LinkedListIndexedCollection}
	 */
	private int size;
	/**
	 * First node of {@link LinkedListIndexedCollection}
	 */
	private ListNode first;
	/**
	 * Last node of {@link LinkedListIndexedCollection}
	 */
	private ListNode last;

	/**
	 * Creates empty {@link LinkedListIndexedCollection}
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = INIT_COLLECTION_SIZE;
	}

	/**
	 * Creates {@link LinkedListIndexedCollection} with elements of the given
	 * collection.
	 * 
	 * @param collection Collection from which elements are stored in the
	 *                   {@link LinkedListIndexedCollection}
	 */
	public LinkedListIndexedCollection(Collection collection) {
		addAll(collection);
	}

	/**
	 * @return First node of the {@link LinkedListIndexedCollection}
	 */
	public ListNode getFirst() {
		return first;
	}

	/**
	 * @param newFirstNode New first node of the {@link LinkedListIndexedCollection}
	 */
	private void setFirst(ListNode newFirstNode) {
		first = newFirstNode;
	}

	/**
	 * @return Last node of the {@link LinkedListIndexedCollection}
	 */
	public ListNode getLast() {
		return last;
	}

	/**
	 * @param newLastNode New last node of the {@link LinkedListIndexedCollection}
	 */
	private void setLast(ListNode newLastNode) {
		last = newLastNode;
	}

	/**
	 * @return Size of {@link LinkedListIndexedCollection}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * @param size New size of the {@link LinkedListIndexedCollection}
	 */
	private void setSize(int size) {
		this.size = size;
	}

	/**
	 * Adds the given object to {@link LinkedListIndexedCollection}. The object may
	 * not be null.
	 * 
	 * @throws NullPointerException Exception is thrown if the given object is null.
	 * @param value Object to add to {@link LinkedListIndexedCollection}
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		ListNode newNode = new ListNode(value);
		if (size() == 0) {
			setFirst(newNode);
			setLast(newNode);
			getFirst().setNext(newNode);
			getLast().setprevious(newNode);
		} else {
			getLast().setNext(newNode);
			newNode.setprevious(getLast());
			setLast(newNode);
		}
		setSize(size() + 1);
	}

	/**
	 * Gets an object stored to the given index in
	 * {@link LinkedListIndexedCollection}.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                  invalid range.
	 * @param index Index of the object to get.
	 * @return Object stored to a given index.
	 */
	public Object get(int index) throws IndexOutOfBoundsException {
		return getNode(index).getValue();
	}

	/**
	 * Inserts the given object to the given index in the
	 * {@link LinkedListIndexedCollection} and shifts all other objects starting from the
	 * index by one position to the right.
	 * 
	 * @throws NullPointerException     Exception is thrown if the given object is
	 *                                  null.
	 * @throws IndexOutOfBoundsException Exception is thrown if the given index is
	 *                                  greater than the size of the
	 *                                  {@link LinkedListIndexedCollection}.
	 * @param value Object to be inserted into {@link LinkedListIndexedCollection}
	 * @param index Index to which the object should be inserted.
	 */
	public void insert(Object value, int index) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		if (index == size()) {
			add(value);
		} else if (index == 0) {
			ListNode currentNode = getNode(index), newNode = new ListNode(value);
			newNode.setNext(currentNode);
			currentNode.setprevious(newNode);
			setFirst(newNode);
			setSize(size() + 1);
		} else {
			ListNode currentNode = getNode(index), newNode = new ListNode(value);
			newNode.setprevious(currentNode.getPrevious());
			newNode.setNext(currentNode);
			currentNode.getPrevious().setNext(newNode);
			currentNode.setprevious(newNode);
			setSize(size() + 1);
		}

	}

	/**
	 * Gets the index of the first representation of the given object in
	 * {@link LinkedListIndexedCollection}.
	 * 
	 * @param value Object of which index is searched for
	 * @return Index of the given object. -1 if the object is not contained in
	 *         {@link LinkedListIndexedCollection}.
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		ListNode front = getFirst(), back = getLast();
		int frontIndex = 0, backIndex = size() - 1;
		while (frontIndex < backIndex) {
			if (front.getValue().equals(value)) {
				return frontIndex;
			} else if (back.getValue().equals(value)) {
				return backIndex;
			}
			front = front.getNext();
			back = back.getPrevious();
			frontIndex++;
			backIndex--;
		}
		return -1;
	}

	/**
	 * Checks if the given object is contained in {@link LinkedListIndexedCollection}.
	 * 
	 * @param value Object to check if it is contained in
	 *              {@link LinkedListIndexedCollection}
	 * @return True if the object is contained in {@link LinkedListIndexedCollection},
	 *         false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Removes the first representation of the given object from the
	 * {@link LinkedListIndexedCollection}. Objects after the index of the removed object
	 * are then shifted to the left by one position.
	 * 
	 * @param value Object to be removed from the {@link LinkedListIndexedCollection}
	 * @return True if the object is successfully removed, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		if (contains(value)) {
			shiftLeftFromIndex(indexOf(value));
			setSize(size() - 1);
			return true;
		}
		return false;
	}

	/**
	 * Removes the object from the given index in the
	 * {@link LinkedListIndexedCollection}. Objects after the index of the removed object
	 * are then shifted to the left by one position.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                   invalid range.
	 * @param index Index of the object to be removed
	 * @return True if the object is successfully removed, false otherwise
	 */
	public boolean remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		shiftLeftFromIndex(index);
		setSize(size() - 1);
		return true;
	}

	/**
	 * Method gets the objects from the {@link LinkedListIndexedCollection} and returns
	 * them as an array of objects.
	 * 
	 * @return Array of objects from {@link LinkedListIndexedCollection}
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size()];
		ListNode currentNode = getFirst();
		for (int i = 0; i < size(); i++) {
			array[i] = currentNode.getValue();
			currentNode = currentNode.getNext();
		}
		return array;
	}

	/**
	 * Method calls process on a given processor for each element in the collection.
	 * 
	 * @param processor Processor that should process each object in the collection
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode currentNode = getFirst();
		for (int i = 0; i < size(); i++) {
			if (currentNode.getValue() != null) {
				processor.process(currentNode.getValue());
			}
			currentNode = currentNode.getNext();
		}
	}

	/**
	 * Clears all the objects from the {@link LinkedListIndexedCollection}.
	 */
	@Override
	public void clear() {
		setFirst(null);
		setLast(null);
		setSize(INIT_COLLECTION_SIZE);
	}

	/**
	 * Method gets the object from the given index in the
	 * {@link LinkedListIndexedCollection}.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                  invalid range.
	 * @param index Index of the object to get
	 * @return Object stored to a given index.
	 */
	public ListNode getNode(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (index > size() / 2) {
			ListNode currentNode = getLast();
			int currentIndex = size() - 1;
			while (currentIndex != index) {
				currentNode = currentNode.getPrevious();
				currentIndex--;
			}
			return currentNode;
		} else {
			ListNode currentNode = getFirst();
			int currentIndex = 0;
			while (currentIndex != index) {
				currentNode = currentNode.getNext();
				currentIndex++;
			}
			return currentNode;
		}
	}

	/**
	 * Method shifts all objects one position to the left from the starting index to
	 * the end of {@link LinkedListIndexedCollection}.
	 * 
	 * @throws IndexOutOfBoundsException Exception is thrown if the index is in
	 *                                  invalid range.
	 * @param index Starting index
	 */
	private void shiftLeftFromIndex(int index) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode currentNode = getNode(index);
		currentNode.getPrevious().setNext(currentNode.getNext());
		currentNode.getNext().setprevious(currentNode.getPrevious());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(first, last, size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LinkedListIndexedCollection))
			return false;
		LinkedListIndexedCollection other = (LinkedListIndexedCollection) obj;
		return Objects.equals(first, other.first) && Objects.equals(last, other.last) && size == other.size;
	}

	/**
	 * Representation of nodes in {@link LinkedListIndexedCollection}. Each node contains
	 * value of previous and next node in the {@link LinkedListIndexedCollection}.
	 * 
	 * @author Andrej Ceraj
	 */
	class ListNode {
		/**
		 * Previous node in {@link LinkedListIndexedCollection}
		 */
		private ListNode previous;
		/**
		 * Next node in {@link LinkedListIndexedCollection}
		 */
		private ListNode next;
		/**
		 * Object stored in the node
		 */
		private Object value;

		/**
		 * Creates an empty node
		 */
		public ListNode() {
		}

		/**
		 * Creates a node with the given object stored in it
		 * 
		 * @param value Object stored in the node
		 */
		public ListNode(Object value) {
			this.value = value;
		}

		/**
		 * @return Previous node
		 */
		public ListNode getPrevious() {
			return previous;
		}

		/**
		 * @param previous New value of previous node
		 */
		private void setprevious(ListNode previous) {
			this.previous = previous;
		}

		/**
		 * @return Next node
		 */
		public ListNode getNext() {
			return next;
		}

		/**
		 * @param next New value of next node
		 */
		private void setNext(ListNode next) {
			this.next = next;
		}

		/**
		 * @return  Object stored in the node
		 */
		public Object getValue() {
			return value;
		}
	}

}
