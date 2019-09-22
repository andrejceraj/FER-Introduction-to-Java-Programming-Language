package hr.fer.zemris.java.gui.calc.model.components;

import java.util.List;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Representation of inverse button.
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcInvCheckBox extends JCheckBox {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 6864146276324867313L;

	/**
	 * Creates an instance of {@link CalcInvCheckBox}.
	 * 
	 * @param unaryOperationButtons
	 * @param power
	 * @param calc
	 */
	public CalcInvCheckBox(List<CalcUnaryOperationButton> unaryOperationButtons, CalcPowerButton power,
			CalcModel calc) {
		super("inv");

		this.addActionListener(a -> {
			CalcUnaryOperationButton.isReversed = !CalcUnaryOperationButton.isReversed;
			unaryOperationButtons.forEach(u -> u.update());
			power.update();
		});
	}

}
