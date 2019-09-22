package hr.fer.zemris.java.hw07.observer1;

/**
 * Observer for class {@link IntegerStorage}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method does an action when the {@code IntegerStorage} values is changed.
	 * 
	 * @param istorage Reference to IntegerStorage
	 */
	public void valueChanged(IntegerStorage istorage);
}
