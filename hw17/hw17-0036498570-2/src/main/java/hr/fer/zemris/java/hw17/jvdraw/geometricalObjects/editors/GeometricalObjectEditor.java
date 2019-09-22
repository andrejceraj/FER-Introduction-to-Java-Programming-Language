package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors;

import java.awt.Point;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;

/**
 * Dialog that pops up when user wants to edit {@link GeometricalObject}.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 1733544105341070464L;

	/**
	 * Checks if user has input valid values
	 * 
	 * @throws Exception if user did not input valid values
	 */
	public abstract void checkEditing() throws Exception;

	/**
	 * Updates user's values to {@link GeometricalObject}
	 */
	public abstract void acceptEditing();

	/**
	 * Converts point into string
	 * 
	 * @param point point
	 * @return string from point
	 */
	protected String pointToString(Point point) {
		return "(" + point.x + "," + point.y + ")";
	}

	/**
	 * Converts string into point
	 * 
	 * @param string string
	 * @return point from string
	 */
	protected Point stringToPoint(String string) {
		string = string.trim().substring(1, string.length() - 1);
		String[] values = string.split(",");
		return new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
	}
}
