package hr.fer.zemris.java.hw17.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * Interface for listeners that should observe {@link JColorArea} color change.
 * 
 * @author Andrej Ceraj
 *
 */
public interface ColorChangeListener {
	/**
	 * Method that should be called when selected color of observed object has changed
	 * 
	 * @param source   source
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
