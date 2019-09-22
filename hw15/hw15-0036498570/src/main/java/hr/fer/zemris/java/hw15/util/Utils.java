package hr.fer.zemris.java.hw15.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class used for encryption of password into SHA-1 hash
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class Utils {

	/**
	 * Method calculates message digest of the given password and returns it as
	 * string.
	 * 
	 * @param password password to encrypt
	 */
	public static String encryptPassword(String password) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] bytes = digest.digest(password.getBytes());
		return bytesToHex(bytes);
	}

	/**
	 * Converts array of bytes into a string containing hexadecimal digits.
	 * 
	 * @param hashInBytes array of bytes
	 * @return string containing hexadecimal digits
	 */
	private static String bytesToHex(byte[] hashInBytes) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hashInBytes.length; i++) {
			sb.append(Integer.toString((hashInBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
