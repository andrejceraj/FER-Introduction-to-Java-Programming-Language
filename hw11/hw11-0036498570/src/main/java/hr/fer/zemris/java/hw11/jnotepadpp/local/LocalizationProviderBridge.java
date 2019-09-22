package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Localization provider bridge used to connect and disconnect listener with
 * provider
 * 
 * @author Andrej Ceraj
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Flag showing if the listener is connected to the provider
	 */
	private boolean connected;
	/**
	 * Localization provider
	 */
	private ILocalizationProvider provider;
	/**
	 * Listener for provider
	 */
	private ILocalizationListener listener;
	/**
	 * Currently selected language
	 */
	private String language;

	/**
	 * Creates an instance of {@link LocalizationProviderBridge}
	 * 
	 * @param provider Localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		language = provider.getCurrentLanguage();

		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	/**
	 * Disconnects listener from the provider
	 */
	public void disconnect() {
		provider.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * Connects listener to the provider
	 */
	public void connect() {
		if (connected == true) {
			return;
		}
		if (provider.getCurrentLanguage() != language) {
			fire();
		}
		provider.addLocalizationListener(listener);
		connected = true;
	}

	@Override
	public String getString(String string) {
		return provider.getString(string);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getString("locale");
	}

}
