package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Table that stores entries to indexes based on their {@code hashcode}. Every
 * field of the table is a pointer to a list of entries addressed to that index.
 * 
 * @author Andrej Ceraj
 *
 * @param <K> Key
 * @param <V> Value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Default {@code SimpleHashtable} capacity.
	 */
	private static final int DEFAULT_TABLE_CAPACITY = 16;
	/**
	 * Percentage of {@code SimpleHashtable} indexes that should be filled to double
	 * the table capacity.
	 */
	private static final double REALLOCATION_BOUNDARY = 0.75;
	/**
	 * Number of stored entries.
	 */
	private int size;
	/**
	 * Storage of entries.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of times the {@code SimpleHashtable} has been modified.
	 */
	private int modificationCount;

	/**
	 * Creates an instance of {@code SimpleHashtable} with default capacity.
	 */
	public SimpleHashtable() {
		this(DEFAULT_TABLE_CAPACITY);
	}

	/**
	 * Creates an instance of {@code SimpleHashtable} with capacity equals to first
	 * power of 2 that is greater of equal to the given capacity.
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the given capacity is
	 *                                  lesser than one.
	 * @param capacity Required capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}

		this.table = new TableEntry[calculateTableCapacity(capacity)];
		this.modificationCount = 0;
	}

	/**
	 * Calculates first power of 2 that is greater of equals to the given value as
	 * the table capacity.
	 * 
	 * @param capacity
	 * @return Table capacity
	 */

	private int calculateTableCapacity(int capacity) {
		int tableCapacity = 1;
		while (tableCapacity < capacity) {
			tableCapacity *= 2;
			if (tableCapacity < 0) {
				throw new IllegalArgumentException();
			}
		}
		return tableCapacity;
	}

	/**
	 * <p>
	 * Creates an entry with the given key and value and puts it into a
	 * {@code SimpleHashtable}. If the entry with the given key already exsts, then
	 * the value of the entry is set to the given value.
	 * </p>
	 * If the percent of filled indexes exceeds {@code REALLOCATION_BOUNDARY} of
	 * 75%, then the storage table is reallocated.
	 * </p>
	 * 
	 * @throws NullPointerException Exception is thrown if the given key is null.
	 * @param key   Key
	 * @param value Value
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		if (needReallocation()) {
			reallocate();
		}

		int index = getSlot(key, table.length);
		if (table[index] == null) {
			table[index] = new TableEntry<>(key, value);
			modificationCount++;
			size++;
			return;
		}
		TableEntry<K, V> currentEntry = table[index];
		while (true) {
			if (currentEntry.getKey().equals(key)) {
				currentEntry.setValue(value);
				return;
			} else if (currentEntry.next == null) {
				break;
			}
			currentEntry = currentEntry.next;
		}
		currentEntry.next = new TableEntry<>(key, value);
		modificationCount++;
		size++;
	}

	/**
	 * Returns the value of an entry containing the given key; null if the entry
	 * containing the key does not exist.
	 * 
	 * @param key Key
	 * @return Value of entry identified with the given key
	 */
	public V get(Object key) {
		TableEntry<K, V> entry = getEntry(key);
		if (entry != null) {
			return entry.getValue();
		}
		return null;
	}

	/**
	 * Gets the {@code TableEntry} associated with the given key.
	 * 
	 * @param key
	 * @return Table entry
	 */

	private TableEntry<K, V> getEntry(Object key) {
		if (key == null) {
			return null;
		}
		int index = getSlot(key, table.length);
		TableEntry<K, V> currentEntry = table[index];
		while (currentEntry != null) {
			if (currentEntry.getKey().equals(key)) {
				return currentEntry;
			}
			currentEntry = currentEntry.next;
		}
		return null;
	}

	/**
	 * @return Number of entries in {@code SimpleHashtable}
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the {@code SimpleHashtable} contains the given key.
	 * 
	 * @param key Key which is searched for
	 * @return True if {@code SimpleHashtable} contains the given key; false
	 *         otherwise.
	 */
	public boolean containsKey(Object key) {
		TableEntry<K, V> entry = getEntry(key);
		if (entry != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the {@code SimpleHashtable} contains the given value.
	 * 
	 * @param value Value which is searched for
	 * @return True if {@code SimpleHashtable} contains the given value; false
	 *         otherwise.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> curentEntry = table[i];
			while (curentEntry != null) {
				if (curentEntry.getValue().equals(value)) {
					return true;
				}
				curentEntry = curentEntry.next;
			}
		}
		return false;
	}

	/**
	 * Removes the entry containing the given key. Does nothing if the entry
	 * containing the given key does not exist.
	 * 
	 * @param key Key of an entry to be removed.
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		int index = getSlot(key, table.length);
		TableEntry<K, V> currentEntry = table[index];
		if (currentEntry.getKey().equals(key)) {
			table[index] = currentEntry.next;
			size--;
			modificationCount++;
			return;
		}
		while (currentEntry != null) {
			if (currentEntry.next != null && currentEntry.next.getKey().equals(key)) {
				if (currentEntry.next.next != null) {
					currentEntry.next = currentEntry.next.next;
				} else {
					currentEntry.next = null;
				}
				size--;
				modificationCount++;
				return;
			}
			currentEntry = currentEntry.next;
		}
	}

	/**
	 * @return True if the {@code SimpleHashtable} is empty; false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Removes all entries from the {@code SimpleHashtable}.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}

	/**
	 * Returns an adequate index given the key and table length.
	 * 
	 * @param key
	 * @param tableLength length of the table
	 * @return Index
	 */
	private int getSlot(Object key, int tableLength) {
		return Math.floorMod(key.hashCode(), tableLength);
	}

	/**
	 * @return True if the {@code SimpleHashtable} needs reallocation; false
	 *         otherwise.
	 */
	private boolean needReallocation() {
		return size() > REALLOCATION_BOUNDARY * table.length;
	}

	/**
	 * Method doubles the {@code table} capacity and reposition the entries in it.
	 */
	@SuppressWarnings("unchecked")
	private void reallocate() {
		TableEntry<K, V>[] newTable = new TableEntry[table.length * 2];
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> currentEntry = table[i];
			while (currentEntry != null) {
				TableEntry<K, V> nextEntry = currentEntry.next;
				addToTable(new TableEntry<K, V>(currentEntry.getKey(), currentEntry.getValue()), newTable);
				currentEntry = nextEntry;
			}
		}
		table = newTable;
	}

	/**
	 * Method adds an entry to the given table.
	 * 
	 * @param entry    Entry to add to the given table.
	 * @param newTable Table to which the entry should be added.
	 */
	private void addToTable(TableEntry<K, V> entry, TableEntry<K, V>[] newTable) {
		int index = getSlot(entry.key, newTable.length);
		if (newTable[index] == null) {
			newTable[index] = entry;
			return;
		}
		TableEntry<K, V> currentEntry = newTable[index];
		while (currentEntry.next != null) {
			currentEntry = currentEntry.next;
		}
		currentEntry.next = entry;

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		boolean first = true;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			TableEntry<K, V> currentEntry = table[i];
			while (currentEntry != null) {
				if (first) {
					builder.append(currentEntry.toString());
					first = false;
				} else {
					builder.append(", ");
					builder.append(currentEntry.toString());
				}
				currentEntry = currentEntry.next;
			}
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Representation of an entry of {@code SimpleHashtable}. Each entry contains a
	 * key-value pair and a pointer to next entry if the list is built. See
	 * {@link SimpleHashtable}.
	 * 
	 * @author Andrej Ceraj
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	public static class TableEntry<K, V> {
		/**
		 * Unique value by which a {@code Pair} can be identified.
		 */
		private K key;
		/**
		 * Value addressed to a {@code TableEntry}.
		 */
		private V value;
		/**
		 * Pointer to the next entry in a list.
		 */
		private TableEntry<K, V> next;

		/**
		 * Creates an entry with the given key and value.
		 * 
		 * @throws NullPointerException Exception is thrown if the given key is null.
		 * @param key   Entry key
		 * @param value Entry value
		 */
		public TableEntry(K key, V value) {
			this(key, value, null);
		}

		/**
		 * Creates an entry with the given key and value.
		 * 
		 * @throws NullPointerException Exception is thrown if the given key is null.
		 * @param key        Entry key
		 * @param value      Entry value
		 * @param tableEntry Next entry in a list
		 */
		public TableEntry(K key, V value, TableEntry<K, V> tableEntry) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
			this.next = tableEntry;
		}

		/**
		 * @return key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @param value Sets entry value to the given value.
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}

	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}

	/**
	 * Implementation of {@code SimpleHashtable} iterator.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Progress of iteration.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Next entry
		 */
		private TableEntry<K, V> nextEntry;
		/**
		 * Index of first {@code table} slot that has not been iterated yet.
		 */
		private int indexOfNextEntry;
		/**
		 * Number of times {@link SimpleHashtable} has been modified when the iterator
		 * has been created.
		 */
		private int modificationCountIterator;
		/**
		 * Keeps track if the {@code remove()} method has already been called before
		 * calling {@code next()} method
		 */
		private boolean removed;

		/**
		 * Creates an instance of {@code SimpleHashtable} iterator.
		 * 
		 * @param modificationCount Number of times {@code SimpleHashtable} has been
		 *                          modified.
		 */
		public IteratorImpl(int modificationCount) {
			this.currentEntry = null;
			this.indexOfNextEntry = -1;
			this.modificationCountIterator = modificationCount;
			this.removed = false;
			this.nextEntry = getNextTableEntry();
		}

		/**
		 * @throws ConcurrentModificationException Exception is thrown if the
		 *                                         {@code SimpleHashtable} has been
		 *                                         modified from the outside since the
		 *                                         creation of the iterator.
		 */
		@Override
		public boolean hasNext() {
			if (modificationCountIterator != modificationCount) {
				throw new ConcurrentModificationException();
			}
			return nextEntry != null;
		}

		/**
		 * @throws ConcurrentModificationException Exception is thrown if the
		 *                                         {@code SimpleHashtable} has been
		 *                                         modified from the outside since the
		 *                                         creation of the iterator.
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationCountIterator != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			currentEntry = nextEntry;
			nextEntry = getNextTableEntry();
			removed = false;
			return currentEntry;
		}

		/**
		 * @throws ConcurrentModificationException Exception is thrown if the
		 *                                         {@code SimpleHashtable} has been
		 *                                         modified from the outside since the
		 *                                         creation of the iterator.
		 */
		@Override
		public void remove() {
			if (modificationCountIterator != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (removed) {
				throw new IllegalStateException();
			}
			if (currentEntry == null) {
				throw new NullPointerException();
			}
			SimpleHashtable.this.remove(currentEntry.getKey());
			currentEntry = null;
			modificationCountIterator++;
			removed = true;
		}

		/**
		 * Gets next entry of {@code SimpleHashtable}. Returns null if there is none
		 * left.
		 * 
		 * @param setIndex If true, sets the {@code currentIndex} to an index of found
		 *                 entry.
		 * @return Next entry
		 */
		private SimpleHashtable.TableEntry<K, V> getNextTableEntry() {
			if (currentEntry != null && currentEntry.next != null) {
				return currentEntry.next;
			}
			for (int i = indexOfNextEntry + 1; i < table.length; i++) {
				if (table[i] != null) {
					indexOfNextEntry = i;
					return table[i];
				}
			}
			return null;
		}
	}
}
