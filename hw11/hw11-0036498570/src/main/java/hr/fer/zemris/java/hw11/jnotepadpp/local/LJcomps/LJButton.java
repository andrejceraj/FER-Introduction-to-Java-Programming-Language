package hr.fer.zemris.java.hw11.jnotepadpp.local.LJcomps;

import javax.swing.Action;
import javax.swing.JButton;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * {@link JButton} modified for localization
 * 
 * @author Andrej Ceraj
 *
 */
public class LJButton extends JButton {

	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 6917226450417846538L;

	/**
	 * Creates an instance of {@link LJButton}
	 * 
	 * @param action   button action
	 * @param provider localization provider
	 * @param key      name key
	 */
	public LJButton(Action action, ILocalizationProvider provider, String key) {
		super(action);

		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});

	}

}
