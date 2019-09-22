package hr.fer.zemris.java.custom.collections;

/**
 * Model of an object capable of performing some operation on the passed object
 * 
 * @author Andrej Ceraj
 *
 */
public interface Processor<T> {
	/**
	 * Operation that should be performed on the passed object by object that
	 * extends Processor class.
	 * 
	 * @param value An object to perform action on.
	 */
	abstract void process(T value);
}
