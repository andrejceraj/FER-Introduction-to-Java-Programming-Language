package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Line;

/**
 * Line editing dialog
 * 
 * @author Andrej Ceraj
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 3260774670113315104L;
	/**
	 * Line being edited
	 */
	private Line line;
	/**
	 * Text area for line starting point
	 */
	private JTextField startingPointField;
	/**
	 * Text area for line ending point
	 */
	private JTextField endingPointField;
	/**
	 * Line color chooser
	 */
	private JColorArea colorSelector;

	/**
	 * Constructor
	 * 
	 * @param line line object
	 */
	public LineEditor(Line line) {
		super();
		setLayout(new GridLayout(3, 2));
		this.line = line;
		startingPointField = new JTextField(pointToString(line.getStartingPoint()));
		endingPointField = new JTextField(pointToString(line.getEndingPoint()));
		colorSelector = new JColorArea(line.getColor());
		add(new JLabel("Starting point: "));
		add(startingPointField);
		add(new JLabel("Ending point: "));
		add(endingPointField);
		add(new JLabel("Color: "));
		add(colorSelector);
	}

	@Override
	public void checkEditing() throws Exception {
		Point startingPoint = stringToPoint(startingPointField.getText());
		Point endingPoint = stringToPoint(startingPointField.getText());
		if (startingPoint.x < 0 || startingPoint.y < 0 || endingPoint.x < 0 || endingPoint.y < 0) {
			throw new Exception("Input data is invalid");
		}

	}

	@Override
	public void acceptEditing() {
		line.setStartingPoint(stringToPoint(startingPointField.getText()));
		line.setEndingPoint(stringToPoint(endingPointField.getText()));
		line.setColor(colorSelector.getCurrentColor());
	}

}
