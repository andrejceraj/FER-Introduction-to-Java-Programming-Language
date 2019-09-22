package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Representation of localization provider
 * 
 * @author Andrej Ceraj
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds {@link ILocalizationListener} to list of listeners
	 * 
	 * @param l listener
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * 
	 * Removes {@link ILocalizationListener} from the list of listeners
	 * 
	 * @param l listener
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Gets localized string for the given string key
	 * 
	 * @param string key
	 * @return localized string
	 */
	String getString(String string);

	/**
	 * @return current language
	 */
	String getCurrentLanguage();

}
