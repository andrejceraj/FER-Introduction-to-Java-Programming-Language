package hr.fer.zemris.java.gui.calc.model.components;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Representation of unary operation button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcUnaryOperationButton extends JButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 8159466545205051675L;
	/**
	 * Reversed operation flag
	 */
	public static boolean isReversed = false;

	/**
	 * Text appearing on button
	 */
	private String buttonText;
	/**
	 * Text appearing on button when reversed flag is true
	 */
	private String buttonTextReversed;

	/**
	 * Creates an instance of {@link CalcUnaryOperationButton} with the given
	 * attributes
	 * 
	 * @param buttonText         button text
	 * @param buttonTextReversed button text when reversed
	 * @param operator           button operation
	 * @param operatorReversed   button operation when reversed
	 * @param calc               calculator
	 */
	public CalcUnaryOperationButton(String buttonText, String buttonTextReversed, DoubleUnaryOperator operator,
			DoubleUnaryOperator operatorReversed, CalcModel calc) {
		super(buttonText);
		this.buttonText = buttonText;
		this.buttonTextReversed = buttonTextReversed;
		this.addActionListener(a -> {
			double result;
			if (!isReversed)
				result = operator.applyAsDouble(calc.getValue());
			else
				result = operatorReversed.applyAsDouble(calc.getValue());
			calc.setValue(result);
		});
	}

	/**
	 * Updates button text based on reversed flag
	 */
	public void update() {
		setText(isReversed ? buttonTextReversed : buttonText);
	}

}
