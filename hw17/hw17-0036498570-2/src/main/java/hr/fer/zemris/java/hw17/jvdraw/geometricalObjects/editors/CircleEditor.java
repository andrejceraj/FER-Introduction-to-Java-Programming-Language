package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle;

/**
 * Circle editing dialog
 * 
 * @author Andrej Ceraj
 *
 */
public class CircleEditor extends GeometricalObjectEditor {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 5631765534530341463L;
	/**
	 * Circle being edited
	 */
	private Circle circle;
	/**
	 * Text area for circle center point
	 */
	private JTextField centerPointField;
	/**
	 * Text area for circle radius
	 */
	private JTextField radiusField;
	/**
	 * Circle foreground color chooser
	 */
	private JColorArea fgColorSelector;

	/**
	 * Constructor
	 * 
	 * @param circle circle
	 */
	public CircleEditor(Circle circle) {
		super();
		setLayout(new GridLayout(3, 2));
		this.circle = circle;
		centerPointField = new JTextField(pointToString(circle.getCenter()));
		radiusField = new JTextField(Integer.toString(circle.getRadius()));
		fgColorSelector = new JColorArea(circle.getFgColor());

		add(new JLabel("Center point: "));
		add(centerPointField);
		add(new JLabel("Radius: "));
		add(radiusField);
		add(new JLabel("Foreground color: "));
		add(fgColorSelector);
	}

	@Override
	public void checkEditing() throws Exception {
		Point center = stringToPoint(centerPointField.getText());
		int radius = Integer.parseInt(radiusField.getText());
		if (center.x < 0 || center.y < 0 || radius < 0) {
			throw new Exception("Input data is invalid");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(stringToPoint(centerPointField.getText()));
		circle.setRadius(Integer.parseInt(radiusField.getText()));
		circle.setFgColor(fgColorSelector.getCurrentColor());
	}

}
