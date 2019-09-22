package hr.fer.zemris.java.gui.calc.model.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Representation of special button
 * 
 * @author Andrej Ceraj
 *
 */
public interface CalcSpecialButton {
	/**
	 * Adds action to button when pressed
	 * 
	 * @param calc calculator
	 */
	void addAction(CalcModel calc);
}
