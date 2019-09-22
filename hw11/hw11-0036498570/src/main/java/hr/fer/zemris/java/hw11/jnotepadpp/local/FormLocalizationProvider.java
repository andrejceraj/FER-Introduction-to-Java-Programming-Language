package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class used to manage connecting and disconnection of provider based on frame
 * activity
 * 
 * @author Andrej Ceraj
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Creates an instance of {@link FormLocalizationProvider}
	 * 
	 * @param provider localization provider
	 * @param frame    frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				disconnect();
			}
		});
	}
}
