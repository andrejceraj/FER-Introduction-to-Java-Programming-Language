package hr.fer.zemris.java.hw11.jnotepadpp.local.LJcomps;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * {@link JMenu} modified for localization
 * 
 * @author Andrej Ceraj
 *
 */
public class LJMenu extends JMenu {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 1500137144060979311L;

	/**
	 * Creates an instance of {@link LJMenu}
	 * 
	 * @param provider localization provider
	 * @param key      name key
	 */
	public LJMenu(ILocalizationProvider provider, String key) {
		super();
		setText(provider.getString(key));

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});

	}

}
