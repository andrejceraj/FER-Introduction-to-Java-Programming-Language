package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Example demonstration of problem 1 in third homework of Basics of Java
 * Programming Language course.F
 * 
 * @author Marko Čupić
 *
 */
public class CollectionDemo4 {
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedanelement: " + getter1.getNextElement());
		System.out.println("Jedanelement: " + getter1.getNextElement());
		System.out.println("Jedanelement: " + getter2.getNextElement());
		System.out.println("Jedanelement: " + getter1.getNextElement());
		System.out.println("Jedanelement: " + getter2.getNextElement());
	}
}
