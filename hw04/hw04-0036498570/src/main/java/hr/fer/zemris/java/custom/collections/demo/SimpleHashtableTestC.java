package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class SimpleHashtableTestC {
	public static void main(String[] args) {

		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println(examMarks.toString());

		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		System.out.println(examMarks.toString());
//		
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
//			}
//		}
//
//		while (iter.hasNext()) { 	// should throw exception - multiple calls remove() on iter without calling next()
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove();
//				iter.remove();
//			}
//		}
//
//		while (iter.hasNext()) {	//should throw exception - SimpleHashtable modified by other than iterator
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}
//
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//			iter.remove();
//		}
//		System.out.printf("Veličina: %d%n", examMarks.size());
//
//		System.out.println(examMarks.toString());
	}
}
