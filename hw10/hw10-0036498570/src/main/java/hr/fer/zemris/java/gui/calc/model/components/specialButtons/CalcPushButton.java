package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of "push" calculator button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcPushButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 5895448589847940536L;
	/**
	 * Calculator stack
	 */
	private Stack<Double> stack;

	/**
	 * Creates an instance of {@link CalcPushButton}
	 * 
	 * @param stack calculator stack
	 * @param calc  calculator
	 */
	public CalcPushButton(Stack<Double> stack, CalcModel calc) {
		super("push");
		this.stack = stack;
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			stack.push(calc.getValue());
		});

	}

}
