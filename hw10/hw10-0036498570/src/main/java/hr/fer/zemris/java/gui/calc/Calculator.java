package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.components.CalcBinaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.components.CalcDisplay;
import hr.fer.zemris.java.gui.calc.model.components.CalcInvCheckBox;
import hr.fer.zemris.java.gui.calc.model.components.CalcNumberButton;
import hr.fer.zemris.java.gui.calc.model.components.CalcPowerButton;
import hr.fer.zemris.java.gui.calc.model.components.CalcUnaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.components.specialButtons.*;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Basic calculator application
 * 
 * @author Andrej Ceraj
 *
 */
public class Calculator {
	/**
	 * Calculator start dimensions
	 */
	private static final Dimension SIZE = new Dimension(800, 400);

	/**
	 * Method starts when the program is run
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BasicCalculator();
			frame.setSize(SIZE);
			frame.setVisible(true);
		});
	}

	/**
	 * Representation of basic calculator
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private static class BasicCalculator extends JFrame {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 8720472478175286023L;

		/**
		 * Creates an instance of {@link BasicCalculator}
		 */
		public BasicCalculator() {
			setTitle("Calculator");
			setLocation(20, 20);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			initGUI();
		}

		/**
		 * Initializes {@link BasicCalculator} GUI
		 */
		private void initGUI() {
			CalcModel calc = new CalcModelImpl();
			Stack<Double> stack = new Stack<Double>();
			Container p = getContentPane();
			p.setLayout(new CalcLayout(4));

			JLabel display = new CalcDisplay(SIZE);
			calc.addCalcValueListener((CalcValueListener) display);
			p.add(display, new RCPosition(1, 1));

			List<CalcUnaryOperationButton> unaryOperationButtons = new ArrayList<CalcUnaryOperationButton>();
			addNumberButtons(p, calc);
			addUnaryOperators(p, calc, unaryOperationButtons);
			addBinaryOperators(p, calc);
			addSpecialButtons(p, calc, stack);

			CalcPowerButton power = new CalcPowerButton("x^n", "x^(1/n)", (x, n) -> Math.pow(x, n),
					(x, n) -> Math.pow(x, 1 / n), calc);
			p.add(power, new RCPosition(5, 1));

			CalcInvCheckBox inv = new CalcInvCheckBox(unaryOperationButtons, power, calc);
			p.add(inv, new RCPosition(5, 7));
			p.setVisible(true);
		}

		/**
		 * Adds number buttons to {@link BasicCalculator}
		 * 
		 * @param p    Content pane
		 * @param calc calculator
		 */
		private void addNumberButtons(Container p, CalcModel calc) {
			for (int i = 1; i <= 9; i++) {
				JButton button = new CalcNumberButton(i, calc);
				button.setFont(button.getFont().deriveFont(30f));
				int row = 4 - (i - 1) / 3;
				int column = 3 + (i - 1) % 3;
				p.add(button, new RCPosition(row, column));
			}
			p.add(new CalcNumberButton(0, calc), new RCPosition(5, 3));
		}

		/**
		 * Adds unary operators to {@link BasicCalculator}
		 * 
		 * @param p       Content pane
		 * @param calc    calculator
		 * @param buttons empty list to be filled with buttons
		 */
		private void addUnaryOperators(Container p, CalcModel calc, List<CalcUnaryOperationButton> buttons) {
			CalcUnaryOperationButton inverse = new CalcUnaryOperationButton("1/x", "1/x", x -> 1 / x, x -> 1 / x, calc);
			p.add(inverse, new RCPosition(2, 1));

			CalcUnaryOperationButton log = new CalcUnaryOperationButton("log", "10^", x -> Math.log10(x),
					x -> Math.pow(10, x), calc);
			p.add(log, new RCPosition(3, 1));

			CalcUnaryOperationButton ln = new CalcUnaryOperationButton("ln", "e^", x -> Math.log(x),
					x -> Math.pow(Math.E, x), calc);
			p.add(ln, new RCPosition(4, 1));

			CalcUnaryOperationButton sin = new CalcUnaryOperationButton("sin", "arcsin", x -> Math.sin(x),
					x -> Math.asin(x), calc);
			p.add(sin, new RCPosition(2, 2));

			CalcUnaryOperationButton cos = new CalcUnaryOperationButton("cos", "arccos", x -> Math.cos(x),
					x -> Math.acos(x), calc);
			p.add(cos, new RCPosition(3, 2));

			CalcUnaryOperationButton tan = new CalcUnaryOperationButton("tan", "arctan", x -> Math.tan(x),
					x -> Math.atan(x), calc);
			p.add(tan, new RCPosition(4, 2));

			CalcUnaryOperationButton ctg = new CalcUnaryOperationButton("ctg", "arcctg", x -> 1 / Math.tan(x),
					x -> 1 / Math.atan(x), calc);
			p.add(ctg, new RCPosition(5, 2));

			buttons.add(inverse);
			buttons.add(log);
			buttons.add(ln);
			buttons.add(sin);
			buttons.add(cos);
			buttons.add(tan);
			buttons.add(ctg);
		}

		/**
		 * Adds binary operators to {@link BasicCalculator}
		 * 
		 * @param p    content pane
		 * @param calc calculator
		 */
		private void addBinaryOperators(Container p, CalcModel calc) {
			CalcBinaryOperationButton add = new CalcBinaryOperationButton("+", (x, y) -> x + y, calc);
			p.add(add, new RCPosition(5, 6));

			CalcBinaryOperationButton subtract = new CalcBinaryOperationButton("-", (x, y) -> x - y, calc);
			p.add(subtract, new RCPosition(4, 6));

			CalcBinaryOperationButton multiply = new CalcBinaryOperationButton("*", (x, y) -> x * y, calc);
			p.add(multiply, new RCPosition(3, 6));

			CalcBinaryOperationButton divide = new CalcBinaryOperationButton("/", (x, y) -> x / y, calc);
			p.add(divide, new RCPosition(2, 6));

		}

		/**
		 * Adds special buttons to {@link BasicCalculator}
		 * 
		 * @param p     content pane
		 * @param calc  calculator
		 * @param stack calculator stack
		 */
		private void addSpecialButtons(Container p, CalcModel calc, Stack<Double> stack) {
			p.add(new CalcEqualButton(calc), new RCPosition(1, 6));
			p.add(new CalcClrButton(calc), new RCPosition(1, 7));
			p.add(new CalcResetButton(calc), new RCPosition(2, 7));
			p.add(new CalcSwapSignButton(calc), new RCPosition(5, 4));
			p.add(new CalcDecimalButton(calc), new RCPosition(5, 5));
			p.add(new CalcPushButton(stack, calc), new RCPosition(3, 7));
			p.add(new CalcPopButton(stack, calc), new RCPosition(4, 7));
		}
	}

}
