package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testByteToHex() {
		byte[] bytes = {1, -82, 34};
		assertEquals("01ae22", Util.byteToHex(bytes));
	}
	@Test
	void testHexToByte() {
		byte[] bytes = {1, -82, 34};
		assertEquals(bytes[0], Util.hexToByte("01ae22")[0]);
		assertEquals(bytes[1], Util.hexToByte("01ae22")[1]);
		assertEquals(bytes[2], Util.hexToByte("01ae22")[2]);
		
		assertEquals(bytes[0], Util.hexToByte("01AE22")[0]);
		assertEquals(bytes[1], Util.hexToByte("01AE22")[1]);
		assertEquals(bytes[2], Util.hexToByte("01AE22")[2]);
		
		assertEquals(bytes[0], Util.hexToByte("01aE22")[0]);
		assertEquals(bytes[1], Util.hexToByte("01aE22")[1]);
		assertEquals(bytes[2], Util.hexToByte("01aE22")[2]);
	}
	
	@Test
	void testEmptyByteToHex() {
		byte[] bytes = new byte[0];
		assertEquals("", Util.byteToHex(bytes));
	}
	
	@Test
	void testEmptyHexToByte() {
		assertEquals(0, Util.hexToByte("").length);
	}
	
	@Test
	void testOneByteToHex() {
		byte b1 = 1;
		byte b2 = -82;
		byte b3 = 34;

		assertEquals("01", Util.byteToHex(b1));
		assertEquals("ae", Util.byteToHex(b2));
		assertEquals("22", Util.byteToHex(b3));
	}
	
	@Test
	void testInvalidHexLength() {
		String hex = "1e2";
		assertThrows(IllegalArgumentException.class, () -> Util.hexToByte(hex));
	}
	
	@Test
	void testInvalidHexCharacter() {
		String hex = "2w";
		assertThrows(IllegalArgumentException.class, () -> Util.hexToByte(hex));
	}

}
