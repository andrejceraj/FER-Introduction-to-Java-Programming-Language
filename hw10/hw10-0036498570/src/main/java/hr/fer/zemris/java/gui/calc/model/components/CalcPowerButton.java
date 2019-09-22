package hr.fer.zemris.java.gui.calc.model.components;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * @author Andrej Ceraj
 *
 */
public class CalcPowerButton extends JButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 3533422062438935988L;
	/**
	 * Text appearing on button
	 */
	private String buttonText;
	/**
	 * Text appearing on button if reverse flag is true
	 */
	private String buttonTextReversed;

	/**
	 * Creates an instance of {@link CalcPowerButton}
	 * 
	 * @param buttonText         button text
	 * @param buttonTextReversed button text when reversed
	 * @param operator           button operation
	 * @param operatorReversed   button operation when reversed
	 * @param calc               calculator
	 */
	public CalcPowerButton(String buttonText, String buttonTextReversed, DoubleBinaryOperator operator,
			DoubleBinaryOperator operatorReversed, CalcModel calc) {
		super(buttonText);
		this.buttonText = buttonText;
		this.buttonTextReversed = buttonTextReversed;
		this.addActionListener(a -> {
			if (calc.getPendingBinaryOperation() != null && calc.isActiveOperandSet()) {
				double midResult = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(),
						calc.getValue());
				calc.setActiveOperand(midResult);
			} else {
				calc.setActiveOperand(calc.getValue());
			}
			calc.setPendingBinaryOperation(CalcUnaryOperationButton.isReversed ? operatorReversed : operator);
			calc.setValue(calc.getActiveOperand());
		});
	}

	/**
	 * Updates button text based on reversed flag
	 */
	public void update() {
		setText(CalcUnaryOperationButton.isReversed ? buttonTextReversed : buttonText);
	}
}
