package hr.fer.zemris.java.hw17.jvdraw.models.impl;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

/**
 * Implementation of {@link DrawingModel}
 * 
 * @author Andrej Ceraj
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {
	/**
	 * List of {@link GeometricalObject}s.
	 */
	private List<GeometricalObject> objects = new ArrayList<GeometricalObject>();
	/**
	 * Flag indicating if the model is modified
	 */
	private boolean modified;
	/**
	 * List of listeners
	 */
	private List<DrawingModelListener> listeners = new ArrayList<DrawingModelListener>();

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index > getSize()) {
			return null;
		}
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		if (object == null) {
			return;
		}
		modified = true;
		object.addGeometricalObjectListener(this);
		objects.add(object);
		notifyObjectAdded(getSize() - 1, getSize() - 1);
	}

	@Override
	public void remove(GeometricalObject object) {
		if (object == null) {
			return;
		}
		modified = true;
		object.removeGeometricalObjectListener(this);
		int objectIndex = objects.indexOf(object);
		objects.remove(object);
		notifyObjectRemoved(objectIndex, objectIndex);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if (offset < 0 || offset >= getSize()) {
			return;
		}
		modified = true;
		objects.remove(object);
		objects.add(offset, object);
		notifyObjectChanged(offset - 1, offset);
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		modified = true;
		objects.forEach(o -> o.removeGeometricalObjectListener(this));
		objects.clear();
		notifyObjectRemoved(0, 0);
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners about adding objects to the model
	 * 
	 * @param index0 index of first added object
	 * @param index1 index of last added object
	 */
	private void notifyObjectAdded(int index0, int index1) {
		listeners.forEach(l -> l.objectsAdded(this, index0, index1));
	}

	/**
	 * Notifies all listeners about removing objects to the model
	 * 
	 * @param index0 index of first removed object
	 * @param index1 index of last removed object
	 */
	private void notifyObjectRemoved(int index0, int index1) {
		listeners.forEach(l -> l.objectsRemoved(this, index0, index1));
	}

	/**
	 * Notifies all listeners about changing objects to the model
	 * 
	 * @param index0 index of first changed object
	 * @param index1 index of last changed object
	 */
	private void notifyObjectChanged(int index0, int index1) {
		listeners.forEach(l -> l.objectsChanged(this, index0, index1));
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = indexOf(o);
		notifyObjectChanged(index, index);
	}

}
