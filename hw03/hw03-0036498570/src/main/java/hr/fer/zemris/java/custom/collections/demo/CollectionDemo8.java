package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Example demonstration of problem 1 in third homework of Basics of Java
 * Programming Language course.F
 * 
 * @author Marko Čupić
 *
 */
public class CollectionDemo8 {
	public static void main(String[] args) {
		class EvenIntegerTester implements Tester {
			public boolean test(Object obj) {
				if (!(obj instanceof Integer))
					return false;
				Integer i = (Integer) obj;
				return i % 2 == 0;
			}
		}

		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
}
