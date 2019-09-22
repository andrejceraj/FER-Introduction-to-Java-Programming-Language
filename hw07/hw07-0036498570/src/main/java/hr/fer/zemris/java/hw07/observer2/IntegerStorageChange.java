package hr.fer.zemris.java.hw07.observer2;

/**
 * Representation of {@link IntegerStorage} detailed change state.
 * 
 * @author Andrej Ceraj
 *
 */
public class IntegerStorageChange {
	/**
	 * Reference to {@code IntegerStorage}.
	 */
	private IntegerStorage storage;

	/**
	 * Creates an instance of {@code IntegerStorageChange}.
	 * 
	 * @param storage
	 */
	public IntegerStorageChange(IntegerStorage storage) {
		this.storage = storage;
	}

	/**
	 * @return integer storage
	 */
	public IntegerStorage getIntegerStorage() {
		return storage;
	}

	/**
	 * @return previous integer storage value
	 */
	public int getPreviousValue() {
		return storage.getPreviousValue();
	}

	/**
	 * @return current (new) integer storage value
	 */
	public int getNewValue() {
		return storage.getValue();
	}

	/**
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		storage.removeObserver(observer);
	}
}
