package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Representation of simple map. The {@link Dictionary} contains {@link Pair}s
 * of {@code Key}s and {@code Value}s. {@code Pair}s are stored to first free
 * index of an array. One {@code key} can be associated with only one
 * {@code value}, but one {@code value} can be stored to multiple {@code key}s.
 * 
 * @author Andrej Ceraj
 *
 * @param <K> Key
 * @param <V> Value
 */
public class Dictionary<K, V> {
	/**
	 * Storage of {@link Pair}s
	 */
	private ArrayIndexedCollection<Pair<K, V>> array;

	/**
	 * Creates a new {@code Dictionary}
	 */
	public Dictionary() {
		array = new ArrayIndexedCollection<Pair<K, V>>();
	}

	/**
	 * Checks if the {@code Dictionary} is empty
	 * 
	 * @return True if it is empty; false otherwise
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * @return Size of the {@code Dictionary};
	 */
	public int size() {
		return array.size();
	}

	/**
	 * Deletes all {@code Pair}s from the Dictionary.
	 */
	public void clear() {
		array.clear();
	}

	/**
	 * If a {@code Pair} with the given key already exists, its value is set to the
	 * given value. If not, the new {@code Pair} is created and added to the
	 * {@code Dictionary}.
	 * 
	 * @throws NullPointerException Exception is thrown if the given key is null.
	 * @param key   Key
	 * @param value Value
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		Pair<K, V> pair = getPair(key);
		if (pair != null) {
			pair.setValue(value);
			return;
		}
		array.add(new Pair<K, V>(key, value));
	}

	/**
	 * Returns value of a {@code Pair} containing the given key. Returns null if
	 * there is no {@code Pair} containing the given key.
	 * 
	 * @param key Key
	 * @return Value of a {@code Pair} with the given key.
	 */
	public V get(K key) {
		Pair<K, V> pair = getPair(key);
		if (pair == null) {
			return null;
		}
		return pair.getValue();
	}

	/**
	 * Returns {@code Pair} that contains the given key. Returns null if there is no
	 * {@code Pair} containing the given key.
	 * 
	 * @param key Key
	 * @return {@code Pair} containing the given key.
	 */
	private Pair<K, V> getPair(K key) {
		if (key == null) {
			return null;
		}
		ElementsGetter<Pair<K, V>> getter = array.createElementsGetter();
		while (getter.hasNextElement()) {
			Pair<K, V> pair = getter.getNextElement();
			if (pair.getKey().equals(key)) {
				return pair;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(array);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Dictionary))
			return false;
		Dictionary other = (Dictionary) obj;
		return Objects.equals(array, other.array);
	}

	/**
	 * Representation of {@code key}-{@code value} pair.
	 * 
	 * @author Andrej Ceraj
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	private static class Pair<K, V> {
		/**
		 * Unique value by which a {@code Pair} can be identified
		 */
		private K key;
		/**
		 * Value addressed to a {@code Pair}
		 */
		private V value;

		/**
		 * Creates a new {@code Pair}
		 * 
		 * @throws NullPointerException Exception is thrown if the given key is null.
		 * @param key   Key
		 * @param value Value
		 */
		public Pair(K key, V value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
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
		 * Sets a {@code value} of this {@code Pair}
		 * 
		 * @param value New value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Pair))
				return false;
			Pair other = (Pair) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}
	}
}
