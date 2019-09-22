package hr.fer.zemris.java.hw17.jvdraw.models;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

/**
 * List showing basic information about {@link GeometricalObject} from the
 * referenced {@link DrawingModel}.
 * 
 * @author Andrej Ceraj
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -3562722360863228711L;
	/**
	 * Drawing model which objects should this list show
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructor
	 * 
	 * @param drawingModel drawing model
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);
	}

}
