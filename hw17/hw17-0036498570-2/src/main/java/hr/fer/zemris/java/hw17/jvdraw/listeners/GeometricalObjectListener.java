package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;

/**
 * Interface for listeners that should observe modifications in
 * {@link GeometricalObject}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Method that should be called when observed {@link GeometricalObject} is
	 * changed
	 * 
	 * @param o geometric object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
