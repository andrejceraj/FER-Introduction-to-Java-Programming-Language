package hr.fer.zemris.java.custom.scripting.stack;

/**
 * Representation of an abstract class and includes methods that custom
 * collections should implement when extending it
 * 
 * @author Andrej Ceraj
 *
 */
public class Collection {

	/**
	 * Default constructor for Collection class
	 * 
	 */
	protected Collection() {
	}

	/**
	 * Methods checks if the collection is empty.
	 * 
	 * @return Returns true if collection is empty, false if it is not
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Method should return size of the collection.
	 * 
	 * @return Returns 0 as this is a representation of an abstract class.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the object into the collection. Should be implemented in classes
	 * extending Collection.
	 * 
	 * @param value Object that should be added to the collection.
	 */
	public void add(Object value) {
	}

	/**
	 * Checks if the collection contains a given object. Should be implemented in
	 * classes extending Collection.
	 * 
	 * @param value Object that is checked if it is contained in the collection.
	 * @return Returns false as this is a representation of an abstract class
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes a given object from the collection if is contained in it. Should be
	 * implemented in classes extending Collection.
	 * 
	 * @param value Object being removed from the collection
	 * @return Returns false as this is a representation of an abstract class
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Creates and returns an array of objects from the collection. Should be
	 * implemented in classes extending Collection.
	 * 
	 * @throws Throws @{@link UnsupportedOperationException} as the method is not
	 *                implemented yet.
	 * @return Should return array of objects.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method should call process on a given processor for each element in the
	 * collection. Should be implemented in classes extending Collection.
	 * 
	 * @param processor Processor that should process each object in the collection.
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Method adds all objects from a given collection to this collection.
	 * 
	 * @param other Collection from which objects are copied in this collection.
	 */
	public void addAll(Collection other) {
		/**
		 * Processor which adds object into a collection when process method is called.
		 * 
		 * @author Andrej Ceraj
		 *
		 */
		class LocalProcessor extends Processor {
			/**
			 * Process adds given object to the collection.
			 * 
			 * @param value Object to add to the collection.
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		LocalProcessor localProcessor = new LocalProcessor();
		other.forEach(localProcessor);
	}

	/**
	 * Clears all the objects from the collection. Should be implemented in classes
	 * extending Collection.
	 * 
	 */
	public void clear() {
	}

}
