package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Abstract implementation of an {@link AbstractAction} used in providing
 * localization.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -5842089802379741337L;

	/**
	 * Creates an instance of {@link LocalizableAction}
	 * 
	 * @param key      bundle key string
	 * @param provider localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		if (provider == null) {
			return;
		}
		putValue(key, provider.getString(key));

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(key, provider.getString(key));
			}
		});
	}
}
