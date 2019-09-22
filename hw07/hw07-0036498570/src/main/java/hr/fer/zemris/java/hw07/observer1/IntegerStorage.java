package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores an integer value. It can contain observers and whenever the
 * integer value changes, all the observers do their action.
 * 
 * @author Andrej Ceraj
 *
 */
public class IntegerStorage {
	/**
	 * Integer value
	 */
	private int value;
	/**
	 * Observers
	 */
	private List<IntegerStorageObserver> observers;
	/**
	 * Copy of observers
	 */
	private List<IntegerStorageObserver> observersCopy;

	/**
	 * Creates an instance of {@code IntegerStorage}.
	 * 
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<IntegerStorageObserver>();
		observersCopy = new ArrayList<IntegerStorageObserver>();
	}

	/**
	 * Adds the given observer to the observers list.
	 * 
	 * @param observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		observersCopy.add(observer);
	}

	/**
	 * Adds the given observer to the observers to be removed list.
	 * 
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observersCopy.remove(observer);
	}

	/**
	 * Clears all observers lists.
	 */
	public void clearObservers() {
		observersCopy.clear();
	}

	/**
	 * @return stored value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Method sets the value to the given value, removes all observers contained in
	 * observers to be removed list from the observers list and then calls the
	 * action for each remaining observer in observers list.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			observers = new ArrayList<IntegerStorageObserver>(observersCopy);
			// Notify all registered observers
			if (observers != null) {

				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
