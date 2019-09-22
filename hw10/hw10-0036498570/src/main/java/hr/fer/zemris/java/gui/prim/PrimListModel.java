package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model containing only prim numbers
 * 
 * @author Andrej Ceraj
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * List of prim numbers
	 */
	private List<Integer> primeNumbers;
	/**
	 * List of listeners
	 */
	private List<ListDataListener> dataListeners;

	/**
	 * Creates an instance of {@link PrimListModel}
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<Integer>();
		dataListeners = new ArrayList<ListDataListener>();
		primeNumbers.add(1);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		dataListeners.add(l);
	}

	@Override
	public Integer getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		dataListeners.remove(l);
	}

	/**
	 * Finds next prim number, adds it to prim numbers list and notifies listeners.
	 */
	public void next() {
		int lastPrime = primeNumbers.get(primeNumbers.size() - 1);
		while (true) {
			lastPrime++;
			if (isPrime(lastPrime)) {
				primeNumbers.add(lastPrime);
				break;
			}
		}
		notifyListeners();
	}

	/**
	 * Checks if the number is prim
	 * 
	 * @param lastPrime number
	 * @return true if number is prim; false otherwise
	 */
	private boolean isPrime(int lastPrime) {
		if (lastPrime == 2) {
			return true;
		}
		for (int i = 2; i * i <= lastPrime; i++) {
			if (lastPrime % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Notifies all listeners
	 */
	private void notifyListeners() {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primeNumbers.size() - 1,
				primeNumbers.size());
		dataListeners.forEach(a -> a.intervalAdded(event));
	}
}
