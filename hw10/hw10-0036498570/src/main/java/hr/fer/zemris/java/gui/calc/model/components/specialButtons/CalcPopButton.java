package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of "pop" calculator button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcPopButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 7406950501625818120L;
	/**
	 * Calculator stack
	 */
	private Stack<Double> stack;

	/**
	 * Creates an instance of {@link CalcPopButton}
	 * 
	 * @param stack calculator stack
	 * @param calc  calculator
	 */
	public CalcPopButton(Stack<Double> stack, CalcModel calc) {
		super("pop");
		this.stack = stack;
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			calc.setValue(stack.pop());
		});

	}

}
