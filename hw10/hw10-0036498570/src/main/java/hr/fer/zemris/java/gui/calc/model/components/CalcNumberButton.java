package hr.fer.zemris.java.gui.calc.model.components;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Representation of number button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcNumberButton extends JButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 2591383496332374112L;

	/**
	 * Creates an instance of {@link CalcNumberButton}
	 * 
	 * @param buttonDigit number
	 * @param calc        calculator
	 */
	public CalcNumberButton(int buttonDigit, CalcModel calc) {
		super(Integer.toString(buttonDigit));

		this.addActionListener(a -> {
			try {
				calc.insertDigit(buttonDigit);
			} catch (CalculatorInputException e) {
				calc.clear();
				calc.insertDigit(buttonDigit);
			}
		});
	}

}
