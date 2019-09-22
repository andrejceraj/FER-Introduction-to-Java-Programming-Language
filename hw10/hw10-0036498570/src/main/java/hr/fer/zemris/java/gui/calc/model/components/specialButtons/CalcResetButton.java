package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of "reset" calculator button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcResetButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 2399808259740624289L;

	/**
	 * Creates an instance of {@link CalcResetButton}
	 * 
	 * @param calc calculator
	 */
	public CalcResetButton(CalcModel calc) {
		super("reset");
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			calc.clearAll();
		});
	}

}
