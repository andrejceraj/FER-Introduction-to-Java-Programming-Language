package hr.fer.zemris.java.custom.collections;

/**
 * Interface includes methods that custom collections should implement.
 * 
 * @author Andrej Ceraj
 *
 */
public interface Collection {

	/**
	 * Methods checks if the collection is empty.
	 * 
	 * @return Returns true if collection is empty, false if it is not
	 */
	public default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Method should return size of the collection.
	 * 
	 * @return Returns 0 as this is a representation of an abstract class.
	 */
	public int size();

	/**
	 * Adds the object into the collection.
	 * 
	 * @param value Object that should be added to the collection.
	 */
	public void add(Object value);

	/**
	 * Checks if the collection contains a given object.
	 * 
	 * @param value Object that is checked if it is contained in the collection.
	 * @return Returns false as this is a representation of an abstract class
	 */
	public boolean contains(Object value);

	/**
	 * Removes a given object from the collection if is contained in it.
	 * 
	 * @param value Object being removed from the collection
	 * @return Returns false as this is a representation of an abstract class
	 */
	public boolean remove(Object value);

	/**
	 * Creates and returns an array of objects from the collection.
	 * 
	 * @return Returns array of objects.
	 */
	public Object[] toArray();

	/**
	 * Method calls process on a given processor for each element in the collection.
	 * 
	 * @param processor Processor that should process each object in the collection.
	 */
	public default void forEach(Processor processor) {
		ElementsGetter elementsGetter = createElementsGetter();
		elementsGetter.forEachRemaining(processor);
	}

	/**
	 * Method adds all objects from a given collection to this collection.
	 * 
	 * @param other Collection from which objects are copied in this collection.
	 */
	public default void addAll(Collection other) {
		/**
		 * Processor which adds object into a collection when process method is called.
		 * 
		 * @author Andrej Ceraj
		 *
		 */
		class LocalProcessor implements Processor {
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
	 * Clears all the objects from the collection.
	 * 
	 */
	public void clear();

	/**
	 * Creates an instance of a representation of {@link ElementsGetter}
	 * 
	 * @return New {@code ElementsGetter}
	 */
	public abstract ElementsGetter createElementsGetter();

	/**
	 * Adds all elements from given collection, which satisfies tester's criteria,
	 * to this collection.
	 * 
	 * @param collection Other collection
	 * @param tester     Class with certain criteria
	 */
	public default void addAllSatisfying(Collection collection, Tester tester) {
		ElementsGetter elementsGetter = collection.createElementsGetter();
		elementsGetter.forEachRemaining((Object value) -> {
			if (tester.test(value)) {
				add(value);
			}
		});
	}

}
