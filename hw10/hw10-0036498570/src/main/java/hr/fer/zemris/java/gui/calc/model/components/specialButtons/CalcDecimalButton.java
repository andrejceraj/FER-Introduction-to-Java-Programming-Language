package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of "." calculator button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcDecimalButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -5691463894777057284L;

	/**
	 * Creates an instance of {@link CalcDecimalButton}
	 * 
	 * @param calc calculator
	 */
	public CalcDecimalButton(CalcModel calc) {
		super(".");
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			try {
				calc.insertDecimalPoint();
			} catch (CalculatorInputException e) {

			}
		});
	}

}
