package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of "=" calculator button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcEqualButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -6525199962327011713L;

	/**
	 * Creates an instance of {@link CalcEqualButton}
	 * 
	 * @param calc calculator
	 */
	public CalcEqualButton(CalcModel calc) {
		super("=");
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			if (calc.getPendingBinaryOperation() != null && calc.isActiveOperandSet()) {
				double result = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(),
						calc.getValue());
				calc.setValue(result);
				calc.clearActiveOperand();
			}
		});
	}

}
