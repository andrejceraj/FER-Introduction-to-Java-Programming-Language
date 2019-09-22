package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of {@link ILocalizationProvider}
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of listeners
	 */
	private List<ILocalizationListener> listeners = new ArrayList<ILocalizationListener>();

	/**
	 * Constructor
	 */
	public AbstractLocalizationProvider() {
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}

}
