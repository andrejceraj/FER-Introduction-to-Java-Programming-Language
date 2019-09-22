package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * Component that is used as small rectangle which when pressed changes color.
 * 
 * @author Andrej Ceraj
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -5719035803128387835L;
	/**
	 * Default component width
	 */
	private static final int WIDTH = 15;
	/**
	 * Default component height
	 */
	private static final int HEIGHT = 15;

	/**
	 * Selected color
	 */
	private Color selectedColor;
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> colorChangeListeners = new ArrayList<ColorChangeListener>();

	/**
	 * Constructor
	 * 
	 * @param color color to be currently selected
	 */
	public JColorArea(Color color) {
		selectedColor = color;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showSelectColorDialog();
			}
		});

		addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				repaint();
			}
		});
	}

	/**
	 * Displays color chooser dialog and sets currently selected color to the chosen
	 * value
	 */
	private void showSelectColorDialog() {
		setSelectedColor(JColorChooser.showDialog(this, "Select color", selectedColor));
	}

	/**
	 * Sets currently selected color to the given color
	 * 
	 * @param color color
	 */
	public void setSelectedColor(Color color) {
		if (color == null) {
			return;
		}

		Color oldColor = selectedColor;
		selectedColor = color;
		notifyColorChangeListeners(oldColor, selectedColor);

	}

	/**
	 * Notifies all listeners
	 * 
	 * @param oldColor previously selected color
	 * @param newColor currently selected color
	 */
	private void notifyColorChangeListeners(Color oldColor, Color newColor) {
		colorChangeListeners.forEach(l -> l.newColorSelected(this, oldColor, newColor));
	}

	@Override
	public Dimension preferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.remove(l);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

}
