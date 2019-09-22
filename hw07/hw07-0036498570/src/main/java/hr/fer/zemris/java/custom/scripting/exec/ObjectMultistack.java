package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class representing a map of stacks. It maps a key of a type string to a
 * {@link ValueWrapper} stack.
 * 
 * @author Andrej Ceraj
 *
 */
public class ObjectMultistack {
	/**
	 * Map referencing to top element of each stack.
	 */
	private MultistackMap multistackMap;

	/**
	 * Creates an instance of {@code ObjectMultistack}.
	 */
	public ObjectMultistack() {
		this.multistackMap = new MultistackMap();
	}

	/**
	 * Pushes the {@code ValueWrapper} on top of a stack mapped with the given key
	 * name.
	 * 
	 * @param keyName
	 * @param valueWrapper
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		Objects.requireNonNull(valueWrapper);
		multistackMap.push(keyName, valueWrapper);
	}

	/**
	 * Pops the {@code ValueWrapper} from the top of the stack mapped with the given
	 * key name.
	 * 
	 * @param keyName
	 * @return popped {@code ValueWrapper}
	 */
	public ValueWrapper pop(String keyName) {
		return multistackMap.pop(keyName).getValueWrapper();
	}

	/**
	 * Returns the {@code ValueWrapper} from the top of the stack mapped with the
	 * given key name.
	 * 
	 * @param keyName
	 * @return {@code ValueWrapper} from the top of the stack
	 */
	public ValueWrapper peek(String keyName) {
		return multistackMap.peek(keyName).getValueWrapper();
	}

	/**
	 * Checks if the {@code ObjectMultistack} is empty,
	 * 
	 * @param keyName
	 * @return true if it is empty; false otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !multistackMap.containsKey(keyName);
	}

	/**
	 * Adapter for {@link ObjectMultistack} class. This class maps the string key
	 * name to its stack and handles the push, pop and peek methods. The stack can
	 * contain {@link ValueWrapper} objects.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class MultistackMap extends HashMap<String, MultistackEntry> {
		/**
		 * generated serial version ID
		 */
		private static final long serialVersionUID = -8186210105131028678L;

		/**
		 * Creates {@link MultistackEntry} object containing the given
		 * {@code ValueWrapper} and adds it into the position mapped with the given key.
		 * The created {@code MultistackEntry} object now points to the to the
		 * {@code MultistackEntry} previously stored in that position.
		 * 
		 * @param key
		 * @param valueWrapper
		 */
		private void push(String key, ValueWrapper valueWrapper) {
			if (!this.containsKey(key)) {
				this.put(key, new MultistackEntry(valueWrapper));
				return;
			}

			MultistackEntry firstInStack = this.get(key);
			MultistackEntry newEntry = new MultistackEntry(valueWrapper);
			newEntry.setPrevious(firstInStack);
			this.put(key, newEntry);
		}

		/**
		 * Method removes the {@code MultistackEntry} object from the position mapped by
		 * the given key, sets the given key to map {@code MultistackEntry} that the
		 * removed one points and then the method returns the removed object.
		 * 
		 * @param key
		 * @return removed {@code MultistackEntry}
		 */
		private MultistackEntry pop(String key) {
			if (!this.containsKey(key)) {
				throw new NoSuchElementException();
			}
			MultistackEntry firstInStack = this.get(key);
			MultistackEntry nextInStack = firstInStack.getPrevious();
			this.put(key, nextInStack);

			return firstInStack;
		}

		/**
		 * 
		 * @param key
		 * @return {@code MultistackEntry} object mapped by the given key
		 */
		private MultistackEntry peek(String key) {
			if (!this.containsKey(key)) {
				throw new NoSuchElementException();
			}
			return this.get(key);
		}

	}

	/**
	 * Entry for {@link MultistackMap}. This class stores a {@link ValueWrapper} and
	 * a pointer to previous {@code MultistackEntry} in the linked list. The linked
	 * list represents the stack.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class MultistackEntry {
		/**
		 * Previous {@code MultistackEntry} in the list (stack).
		 */
		private MultistackEntry previous;
		/**
		 * {@code ValueWrapper}.
		 */
		private ValueWrapper valueWrapper;

		/**
		 * Creates an instance of {@code MultistackEntry} storing the given
		 * {@code ValueWrapper}.
		 * 
		 * @param valueWrapper
		 */
		public MultistackEntry(ValueWrapper valueWrapper) {
			Objects.requireNonNull(valueWrapper);
			this.valueWrapper = valueWrapper;
		}

		/**
		 * Sets the pointer to previous {@code MultistackEntry} to the given entry.
		 * 
		 * @param entry
		 */
		private void setPrevious(MultistackEntry entry) {
			this.previous = entry;
		}

		/**
		 * @return previous {@code MultistackEntry}.
		 */
		private MultistackEntry getPrevious() {
			return previous;
		}

		/**
		 * @return stored {@code ValueWrapper}.
		 */
		private ValueWrapper getValueWrapper() {
			return valueWrapper;
		}
	}

}
