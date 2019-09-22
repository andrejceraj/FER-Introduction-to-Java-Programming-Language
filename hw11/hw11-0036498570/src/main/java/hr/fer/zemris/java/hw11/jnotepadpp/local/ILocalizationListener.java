package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Listener for localization providers
 * 
 * @author Andrej Ceraj
 *
 */
public interface ILocalizationListener {
	/**
	 * Method that should be called when new language is selected
	 */
	void localizationChanged();
}
