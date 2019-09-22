package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;;

/**
 * Example demonstration of problem 1 in third homework of Basics of Java
 * Programming Language course.F
 * 
 * @author Marko Čupić
 *
 */
public class CollectionDemo7 {
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.forEachRemaining(System.out::println);
	}
}
