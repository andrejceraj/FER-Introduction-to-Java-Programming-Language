package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArgumentParserTest {

	@Test
	void test() {
		String arguments = "String1 \"String 2\"";
		String[] args = ArgumentParser.parseArguments(arguments);
		assertEquals("String1", args[0]);
		assertEquals("String 2", args[1]);
	}
	
	@Test
	void testInvalid() {
		String arguments = "\"this\\is some\\argument";
		assertThrows(IllegalArgumentException.class,() -> ArgumentParser.parseArguments(arguments));
	}

}
