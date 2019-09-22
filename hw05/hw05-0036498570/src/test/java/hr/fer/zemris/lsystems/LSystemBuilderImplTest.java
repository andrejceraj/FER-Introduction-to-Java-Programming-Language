package hr.fer.zemris.lsystems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

class LSystemBuilderImplTest {

	@Test
	void testGenerate() {
		LSystemBuilderImpl lsystemBuilder = new LSystemBuilderImpl();
		lsystemBuilder.setAxiom("F");
		lsystemBuilder.registerProduction('F', "F+F--F+F");
		
		assertEquals("F", lsystemBuilder.build().generate(0));
		assertEquals("F+F--F+F", lsystemBuilder.build().generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lsystemBuilder.build().generate(2));
	}
	
	@Test
	void testGenerate2() {
		LSystemBuilderImpl lsystemBuilder = new LSystemBuilderImpl();
		lsystemBuilder.setAxiom("F");
		lsystemBuilder.registerProduction('F', "F[+F]F[-F]F");
		
		assertEquals("F[+F]F[-F]F", lsystemBuilder.build().generate(1));
		assertEquals("F[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F", lsystemBuilder.build().generate(2));
	}
	
	@Test
	void testGenerate3() {
		LSystemBuilderImpl lSystemBuilder = new LSystemBuilderImpl();
		lSystemBuilder.setAxiom("A");
		lSystemBuilder.registerProduction('A', "ABA");
		lSystemBuilder.registerProduction('B', "BBB");
		

		assertEquals("ABA", lSystemBuilder.build().generate(1));
		assertEquals("ABABBBABA", lSystemBuilder.build().generate(2));
		assertEquals("ABABBBABABBBBBBBBBABABBBABA", lSystemBuilder.build().generate(3));
	}
}
