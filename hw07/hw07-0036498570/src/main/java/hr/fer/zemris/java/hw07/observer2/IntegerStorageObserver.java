package hr.fer.zemris.java.hw07.observer2;

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
	 * @param change {@code IntegerStorage} detailed change state.
	 */
	public void valueChanged(IntegerStorageChange change);
}
