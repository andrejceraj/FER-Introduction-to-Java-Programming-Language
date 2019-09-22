package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors;

import java.awt.GridLayout;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.FilledCircle;

/**
 * Filled circle editing dialog
 * 
 * @author Andrej Ceraj
 *
 */
public class FilledCircleEditor extends CircleEditor {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -5946184149058736563L;
	/**
	 * Filled circle being edited
	 */
	private FilledCircle filledCircle;
	/**
	 * Filled circle background color chooser
	 */
	private JColorArea bgColorSelector;

	/**
	 * Constructor
	 * 
	 * @param filledCircle filled circle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);
		setLayout(new GridLayout(4, 2));
		this.filledCircle = filledCircle;
		bgColorSelector = new JColorArea(filledCircle.getBgColor());
		add(new JLabel("Background color: "));
		add(bgColorSelector);
	}

	@Override
	public void checkEditing() throws Exception {
		super.checkEditing();
	}

	@Override
	public void acceptEditing() {
		super.acceptEditing();
		filledCircle.setBgColor(bgColorSelector.getCurrentColor());
	}

}
