package hr.fer.zemris.java.gui.calc.model.components;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Representation of binary operation button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcBinaryOperationButton extends JButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 8768680462474292849L;

	/**
	 * Creates an instance of {@link CalcBinaryOperationButton}.
	 * 
	 * @param buttonText button text
	 * @param operator   button operation
	 * @param calc       calculator
	 */
	public CalcBinaryOperationButton(String buttonText, DoubleBinaryOperator operator, CalcModel calc) {
		super(buttonText);

		this.addActionListener(a -> {
			if (calc.getPendingBinaryOperation() != null && calc.isActiveOperandSet()) {
				double midResult = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(),
						calc.getValue());
				calc.setActiveOperand(midResult);
			} else {
				calc.setActiveOperand(calc.getValue());
			}
			calc.setPendingBinaryOperation(operator);
			calc.setValue(calc.getActiveOperand());
		});
	}

}
