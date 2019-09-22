package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * Interface for objects that are used to provide color for any other object.
 * 
 * @author Andrej Ceraj
 *
 */
public interface IColorProvider {
	/**
	 * @return currently selected color
	 */
	public Color getCurrentColor();

	/**
	 * Attaches {@link ColorChangeListener} to provider's list of listeners
	 * 
	 * @param l listener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Detaches {@link ColorChangeListener} from provider's list of listeners
	 * 
	 * @param l listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
