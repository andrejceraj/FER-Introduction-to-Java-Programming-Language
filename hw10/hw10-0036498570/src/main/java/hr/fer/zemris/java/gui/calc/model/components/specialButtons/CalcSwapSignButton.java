package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of +/- (swap sign) button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcSwapSignButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 2897190679741405803L;

	/**
	 * Creates an instance of {@link CalcSwapSignButton}
	 * 
	 * @param calc calculator
	 */
	public CalcSwapSignButton(CalcModel calc) {
		super("+/-");
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			try {
				calc.swapSign();
			} catch (Exception e) {
			}
		});

	}

}
