package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;
import hr.fer.zemris.java.custom.collections.Tester;

public class CollectionsTest {
	public static void main(String[] args) {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<String>();
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<String>();
		
		class LengthTester<T> implements Tester<T>{
			@Override
			public boolean test(T object) {
				try {
					if(((String) object).length() <= 5){
						return true;
					}
					return false;
				}catch (Exception e) {
					return false;
				}
			}
		}
		class PrintProcessor<T> implements Processor<T> {
			@Override
			public void process(T value) {
				System.out.println(value.toString());
				
			}
		}

		array.add("string0");
		array.add("str1");
		array.add("string2");
		array.add("str3");
		array.add("str4");
		array.add("string5");
		array.add("string6");
		array.add("str7");
		
		list.addAllSatisfying(array, new LengthTester<>());
		
		array.forEach(new PrintProcessor<>());
		System.out.println("----------------------------");
		list.forEach(new PrintProcessor<>());
	}
}
