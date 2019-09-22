package hr.fer.zemris.java.hw06.shell;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used for converting array of bytes into strings of hex numbers and vice
 * versa.
 * 
 * @author Andrej Ceraj
 *
 */
public class Util {
	/**
	 * Map associating hex number and binary number
	 */
	private static Map<Character, String> BINARY = new HashMap<Character, String>();
	/**
	 * Map associating binary number and hex number
	 */
	private static Map<String, Character> HEX = new HashMap<String, Character>();
	static {
		BINARY.put('0', "0000");
		BINARY.put('1', "0001");
		BINARY.put('2', "0010");
		BINARY.put('3', "0011");
		BINARY.put('4', "0100");
		BINARY.put('5', "0101");
		BINARY.put('6', "0110");
		BINARY.put('7', "0111");
		BINARY.put('8', "1000");
		BINARY.put('9', "1001");
		BINARY.put('a', "1010");
		BINARY.put('b', "1011");
		BINARY.put('c', "1100");
		BINARY.put('d', "1101");
		BINARY.put('e', "1110");
		BINARY.put('f', "1111");

		HEX.put("0000", '0');
		HEX.put("0001", '1');
		HEX.put("0010", '2');
		HEX.put("0011", '3');
		HEX.put("0100", '4');
		HEX.put("0101", '5');
		HEX.put("0110", '6');
		HEX.put("0111", '7');
		HEX.put("1000", '8');
		HEX.put("1001", '9');
		HEX.put("1010", 'a');
		HEX.put("1011", 'b');
		HEX.put("1100", 'c');
		HEX.put("1101", 'd');
		HEX.put("1110", 'e');
		HEX.put("1111", 'f');
	}

	/**
	 * Converts hex number into array of bytes.
	 * 
	 * @param number string containing hex number
	 * @return array of bytes
	 */
	public static byte[] hexToByte(String number) {
		if (number.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		byte[] bytes = new byte[number.length() / 2];
		number = number.toLowerCase();
		for (int i = 0; i + 2 <= number.length(); i += 2) {
			String hex = number.substring(i, i + 2);
			bytes[i / 2] = getByte(hex);
		}
		return bytes;
	}

	/**
	 * Converts array of bytes into hex number
	 * 
	 * @param bytes array of bytes
	 * @return string containing hex number
	 */
	public static String byteToHex(byte[] bytes) {
		StringBuilder binary = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			byte currentByte = bytes[i];
			if (currentByte < 0) {
				binary.append("1");
				currentByte += Math.pow(2, 7);
			} else {
				binary.append("0");
			}
			for (int j = 6; j >= 0; j--) {
				if (currentByte >= Math.pow(2, j)) {
					binary.append("1");
					currentByte -= Math.pow(2, j);
				} else {
					binary.append("0");
				}
			}
		}
		String binaryArray = binary.toString();
		StringBuilder hex = new StringBuilder();
		for (int i = 0; i + 4 <= binaryArray.length(); i += 4) {
			hex.append(HEX.get(binary.substring(i, i + 4)));
		}
		return hex.toString();
	}

	/**
	 * Converts one byte into hex number.
	 * 
	 * @param b byte
	 * @return string containing hex number
	 */
	public static String byteToHex(byte b) {
		byte[] bytes = new byte[1];
		bytes[0] = b;
		return byteToHex(bytes);
	}

	/**
	 * Converts 2 digits hex into one byte.
	 * 
	 * @param hex string containing hex number with length 2
	 * @return converted byte
	 */
	private static byte getByte(String hex) {
		if (!BINARY.containsKey(hex.charAt(0)) || !BINARY.containsKey(hex.charAt(1))) {
			throw new IllegalArgumentException();
		}
		String binary = BINARY.get(hex.charAt(0)) + BINARY.get(hex.charAt(1));
		byte result = 0;
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				result += i == 0 ? -Math.pow(2, binary.length() - i - 1) : Math.pow(2, binary.length() - i - 1);
			}
		}
		return result;
	}
}
