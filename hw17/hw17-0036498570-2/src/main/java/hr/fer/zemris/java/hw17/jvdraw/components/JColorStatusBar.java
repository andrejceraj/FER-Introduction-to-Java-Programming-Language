package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * Color status bar showing RGB of currently selected foreground color and
 * background color.
 * 
 * @author Andrej Ceraj
 *
 */
public class JColorStatusBar extends JLabel implements ColorChangeListener {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 3291766879795667980L;
	/**
	 * Reference to foreground color chooser
	 */
	private JColorArea foreground;
	/**
	 * Reference to background color chooser
	 */
	private JColorArea background;

	/**
	 * Constructor
	 * 
	 * @param foreground color chooser for foreground color
	 * @param background color chooser for background color
	 */
	public JColorStatusBar(JColorArea foreground, JColorArea background) {
		this.foreground = foreground;
		this.background = background;
		newColorSelected(null, null, null);
		foreground.addColorChangeListener(this);
		background.addColorChangeListener(this);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		StringBuilder sb = new StringBuilder();
		Color fc = foreground.getCurrentColor();
		Color bc = background.getCurrentColor();
		sb.append("Foreground color: (").append(fc.getRed()).append(", ").append(fc.getGreen()).append(", ");
		sb.append(fc.getBlue()).append("), background color: (").append(bc.getRed()).append(", ").append(bc.getGreen());
		sb.append(", ").append(bc.getBlue()).append(").");
		setText(sb.toString());
	}

}
