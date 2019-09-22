package hr.fer.zemris.java.hw17.jvdraw.models;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

/**
 * Interface for objects representing drawing model. Drawing model should
 * contain list of {@link GeometricalObject}s which are accessible over the
 * interface.
 * 
 * @author Andrej Ceraj
 *
 */
public interface DrawingModel {
	/**
	 * @return number of containing {@link GeometricalObject}s
	 */
	public int getSize();

	/**
	 * Gets object on the given index
	 * 
	 * @param index index
	 * @return object
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds {@link GeometricalObject} into this model
	 * 
	 * @param object object
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes {@link GeometricalObject} from this model
	 * 
	 * @param object object
	 */
	public void remove(GeometricalObject object);

	/**
	 * Changes order for the given object setting it to offset position
	 * 
	 * @param object object
	 * @param offset offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns index of the given {@link GeometricalObject}.
	 * 
	 * @param object object
	 * @return object index
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clears all objects from the model
	 */
	public void clear();

	/**
	 * Sets modification flag to false
	 */
	public void clearModifiedFlag();

	/**
	 * @return
	 */
	public boolean isModified();

	/**
	 * Attaches {@link DrawingModelListener} to this model
	 * 
	 * @param l listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Detaches {@link DrawingModelListener} from this model
	 * 
	 * @param l listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
