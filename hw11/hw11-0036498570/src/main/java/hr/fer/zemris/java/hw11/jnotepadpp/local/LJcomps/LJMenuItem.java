package hr.fer.zemris.java.hw11.jnotepadpp.local.LJcomps;

import javax.swing.Action;
import javax.swing.JMenuItem;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * {@link JMenuItem} modified for localization
 * 
 * @author Andrej Ceraj
 *
 */
public class LJMenuItem extends JMenuItem {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 1927041241101010606L;

	/**
	 * Creates an instance of {@link LJMenuItem}
	 * 
	 * @param action   button action
	 * @param provider localization provider
	 * @param key      name key
	 */
	public LJMenuItem(Action action, ILocalizationProvider provider, String key) {
		super(action);

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});

	}
}
