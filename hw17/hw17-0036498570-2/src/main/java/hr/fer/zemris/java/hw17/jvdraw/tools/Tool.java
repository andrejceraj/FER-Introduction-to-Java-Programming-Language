package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;

/**
 * Interface for drawing of {@link GeometricalObject}s
 * 
 * @author Andrej Ceraj
 *
 */
public interface Tool {
	/**
	 * Method called when mouse is pressed
	 * 
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Method called when mouse is released
	 * 
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Method called when mouse is clicked
	 * 
	 * @param e mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Method called when mouse is moved
	 * 
	 * @param e mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Method called when mouse is dragged
	 * 
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method paints {@link GeometricalObject} of {@link Graphics2D} object.
	 * 
	 * @param g2d graphics object
	 */
	public void paint(Graphics2D g2d);
}