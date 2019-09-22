package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;

/**
 * Representation of a shape that is drawn in {@link JVDraw} application.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class GeometricalObject {
	/**
	 * List of listeners
	 */
	protected List<GeometricalObjectListener> listeners = new ArrayList<GeometricalObjectListener>();

	/**
	 * Method that accepts given visitor and calls
	 * {@code GeometricalObjectVisitor#visit(this)}.
	 * 
	 * @param v Geometrical object visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates concrete editor for this object
	 * 
	 * @return concrete {@link GeometricalObjectEditor}.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Adds {@link GeometricalObjectListener} to list of listeners.
	 * 
	 * @param l listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Removes {@link GeometricalObjectListener} from list of listeners.
	 * 
	 * @param l listener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners
	 */
	protected void notifyListeners() {
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

}
