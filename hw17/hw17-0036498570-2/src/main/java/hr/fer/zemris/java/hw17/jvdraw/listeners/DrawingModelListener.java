package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

/**
 * Interface for listeners that should observe modifications in
 * {@link DrawingModel}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface DrawingModelListener {
	/**
	 * Method that should be called when objects are added to {@link DrawingModel}.
	 * 
	 * @param source drawing model
	 * @param index0 index of first added object
	 * @param index1 index of last added object
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method that should be called when objects are removed from
	 * {@link DrawingModel}.
	 * 
	 * @param source drawing model
	 * @param index0 index of first removed object
	 * @param index1 index of last removed object
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method that should be called when objects are changed in
	 * {@link DrawingModel}.
	 * 
	 * @param source drawing model
	 * @param index0 index of first changed object
	 * @param index1 index of last changed object
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
