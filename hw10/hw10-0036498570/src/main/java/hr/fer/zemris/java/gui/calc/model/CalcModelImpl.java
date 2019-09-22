package hr.fer.zemris.java.gui.calc.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of {@link CalcModel} interface
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * Comparison delta
	 */
	private static final double DELTA = 1e-15;

	/**
	 * List of observers
	 */
	private List<CalcValueListener> observers;
	/**
	 * Editable flag
	 */
	private boolean editable;
	/**
	 * Positive flag
	 */
	private boolean isPositive;
	/**
	 * Calculator display string
	 */
	private String string;
	/**
	 * Calculator currently stored value
	 */
	private double value;

	/**
	 * Calculator currently active operand
	 */
	private Double activeOperand;
	/**
	 * Calculator currently activated operation
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * Creates an instance of {@link CalcModelImpl}
	 */
	public CalcModelImpl() {
		observers = new ArrayList<CalcValueListener>();
		editable = true;
		isPositive = true;
		string = "";
		value = 0;
		activeOperand = null;
		pendingOperation = null;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		observers.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		observers.remove(l);
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		if (Math.abs(value - (int) value) < DELTA) {
			string = Integer.toString((int) value);
		} else {
			DecimalFormat format = new DecimalFormat("#.#############");
			string = format.format(value);
		}
		editable = false;
		isPositive = value > 0 ? true : false;
		notifyObservers();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		editable = true;
		string = "";
		value = 0;
		isPositive = true;
		notifyObservers();
	}

	@Override
	public void clearAll() {
		activeOperand = null;
		pendingOperation = null;
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException();
		}

		if (isPositive) {
			string = "-" + string;
		} else {
			string = string.substring(1);
		}

		isPositive = !isPositive;
		value = value * (-1);
		notifyObservers();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException();
		}
		string = string + ".";
		try {
			value = Double.parseDouble(string);
		} catch (NumberFormatException e) {
			string = string.substring(0, string.length() - 1);
			throw new CalculatorInputException();
		}
		notifyObservers();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) {
			throw new CalculatorInputException();
		}
		if (string.length() >= 308) {
			throw new CalculatorInputException();
		}
		if (string.equals("0")) {
			string = Integer.toString(digit);
		} else {
			string = string + Integer.toString(digit);
		}
		try {
			value = Double.parseDouble(string);
		} catch (NumberFormatException e) {
			string = string.substring(0, string.length() - 1);
			throw new CalculatorInputException();
		}
		notifyObservers();
	}

	@Override
	public boolean isActiveOperandSet() {
		if (activeOperand == null) {
			return false;
		}
		return true;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		notifyObservers();
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
		notifyObservers();
	}

	@Override
	public String toString() {
		if (Math.abs(value) < DELTA) {
			return (!isPositive ? "-" : "") + (Integer.toString((int) Math.abs(value)));
		}
		return string;
	}

	/**
	 * Notifies all observers
	 */
	private void notifyObservers() {
		observers.forEach(o -> o.valueChanged(this));
	}

}
