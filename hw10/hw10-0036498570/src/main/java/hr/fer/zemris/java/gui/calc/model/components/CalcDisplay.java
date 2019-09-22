package hr.fer.zemris.java.gui.calc.model.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Representation of calculator display
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcDisplay extends JLabel implements CalcValueListener {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 8782454292120908970L;

	/**
	 * Creates an instance of {@link CalcDisplay}.
	 * 
	 * @param size display dimensions
	 */
	public CalcDisplay(Dimension size) {
		setOpaque(true);
		setBorder(BorderFactory.createEtchedBorder());
		setBackground(Color.YELLOW);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(new Font(getFont().toString(), getFont().getStyle(), (int) size.getHeight() / 10));
		setText("0");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.gui.calc.model.CalcValueListener#valueChanged(hr.fer.
	 * zemris.java.gui.calc.model.CalcModel)
	 */
	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
	}

}
