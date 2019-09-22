package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class for providing localization
 * 
 * @author Andrej Ceraj
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Bundle source
	 */
	private static final String BUNDLE_SOURCE = "hr.fer.zemris.java.hw11.jnotepadpp.local.translation";
	/**
	 * Currently selected language
	 */
	private String language;
	/**
	 * Loaded bundle
	 */
	private ResourceBundle bundle;
	/**
	 * Singleton instance of {@link LocalizationProvider}
	 */
	private static final LocalizationProvider LOCALIZATION_PROVIDER = new LocalizationProvider();

	/**
	 * Creates an instance of {@link LocalizationProvider}
	 */
	private LocalizationProvider() {
		super();
		language = "en";
		bundle = getBundle();
	}

	/**
	 * @return localization provider
	 */
	public static LocalizationProvider getInstance() {
		return LOCALIZATION_PROVIDER;
	}

	/**
	 * Sets current language to the given language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		if (this.language.equals(language)) {
			return;
		}
		this.language = language;
		bundle = getBundle();
		fire();
	}

	@Override
	public String getString(String string) {
		return bundle.getString(string);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

	/**
	 * Gets the right bundle based on the source and the language
	 * 
	 * @return bundle
	 */
	private ResourceBundle getBundle() {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_SOURCE, Locale.forLanguageTag(language));
		return bundle;
	}
}
