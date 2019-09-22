package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Line;

/**
 * Interface for visitor design pattern of class {@link GeometricalObject}.
 * 
 * @author Andrej Ceraj
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Visitor method for {@link Line}.
	 * 
	 * @param line line
	 */
	public abstract void visit(Line line);

	/**
	 * Visitor method for {@link Circle}.
	 * 
	 * @param circle circle
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visitor method for {@link FilledCircle}.
	 * 
	 * @param filledCircle filled circle
	 */
	public abstract void visit(FilledCircle filledCircle);
}