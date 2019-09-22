package hr.fer.zemris.java.gui.calc.model.components.specialButtons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.components.CalcSpecialButton;

/**
 * Representation of "clr" calculator button
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcClrButton extends JButton implements CalcSpecialButton {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -3568067204276598207L;

	/**
	 * Creates an instance of {@link CalcClrButton}
	 * 
	 * @param calc calculator
	 */
	public CalcClrButton(CalcModel calc) {
		super("clr");
		addAction(calc);
	}

	@Override
	public void addAction(CalcModel calc) {
		this.addActionListener(a -> {
			calc.clear();
		});
	}

}
