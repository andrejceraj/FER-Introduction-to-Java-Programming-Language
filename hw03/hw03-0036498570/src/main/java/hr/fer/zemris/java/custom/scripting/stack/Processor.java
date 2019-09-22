package hr.fer.zemris.java.custom.scripting.stack;

/**
 * Model of an object capable of performing some operation on the passed object
 * 
 * @author Andrej Ceraj
 *
 */
public class Processor {
	/**
	 * Operation that should be performed on the passed object by object that extends Processor class.
	 * 
	 * @param value An object to perform action on.
	 */
	public void process(Object value) {}
}
