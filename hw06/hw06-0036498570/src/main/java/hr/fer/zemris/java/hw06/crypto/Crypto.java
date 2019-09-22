package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used encrypting, decrypting and comparing message digest of selected
 * file. Option and file path are given through the command line and based on
 * the option, the user is required to enter option attributes. Once the
 * attributes are entered the result message is shown to the user.
 * 
 * 
 * @author Andrej Ceraj
 *
 */
public class Crypto {

	/**
	 * Option for checking message digest of a file.
	 */
	private static final String CHECKSHA = "checksha";
	/**
	 * Option for encrypting a file.
	 */
	private static final String ENCRYPT = "encrypt";
	/**
	 * Option for decrypting a file.
	 */
	private static final String DECRYPT = "decrypt";
	/**
	 * Default buffer size
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Starts when the program is run.
	 * 
	 * @param args arguments passed in the command line
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No arguments entered");
			return;
		}
		switch (args[0]) {
		case CHECKSHA:
			checkSHA(args);
			break;
		case ENCRYPT:
			crypt(args);
			break;
		case DECRYPT:
			crypt(args);
			break;
		default:
			System.out.println("Invalid operation: " + args[0]);
			return;
		}
	}

	/**
	 * Method expects the user to enter expected 256-sha digest for the selected
	 * file. It then calculates the actual message digest of the selected file and
	 * prints the result message to the user weather the expected and actual message
	 * digests match or not.
	 * 
	 * @param args arguments passed in the command line
	 */
	private static void checkSHA(String[] args) {
		if (args.length != 2) {
			System.out.println("Invalid number of arguments for operation 'checksha'");
			System.exit(1);
		}
		String fileName = args[1];
		try (Scanner scanner = new Scanner(System.in);
				InputStream bufferedInputStream = Files.newInputStream(Paths.get(fileName))) {

			System.out.print("Please provide expected sha-256 digest for " + fileName + ":\n> ");
			String enteredDigest = scanner.next();

			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] buffer = new byte[BUFFER_SIZE];
			int readBytes;
			while ((readBytes = bufferedInputStream.read(buffer)) != -1) {
				digest.update(buffer, 0, readBytes);
			}
			String trueDigest = Util.byteToHex(digest.digest());
			if (enteredDigest.equals(trueDigest)) {
				System.out.println("Digesting completed. Digest of " + fileName + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + fileName
						+ " does not match the expected digest.\nDigest was: " + trueDigest);
			}

		} catch (IOException ioException) {
			System.out.println("Error occured while reading the file");
			System.exit(1);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			System.out.println("Error occured while instancing MessageDigest");
			System.exit(1);
		}

	}

	/**
	 * Method expects user to enter crypting password and initialization vector as
	 * hex-encoded texts. Then based on an option selected it encrypts or decrypts a
	 * selected file into a new one. After the process the result message is
	 * printed.
	 * 
	 * @param args arguments passed in the command line.
	 */
	private static void crypt(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of arguments for operation 'encrypt'");
			System.exit(1);
		}
		String cryption = args[0];
		String fileName = args[1];
		String outputFileName = args[2];
		try (Scanner scanner = new Scanner(System.in);
				InputStream bufferedInputStream = Files.newInputStream(Paths.get(fileName));
				OutputStream bufferedOutputStream = Files.newOutputStream(Paths.get(outputFileName))) {
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			String keyText = scanner.next();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			String ivText = scanner.next();

			SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(cryption.equals(ENCRYPT) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			byte[] inputBuffer = new byte[BUFFER_SIZE];
			int inputBytes;
			byte[] outputBuffer = new byte[BUFFER_SIZE];

			while ((inputBytes = bufferedInputStream.read(inputBuffer)) != -1) {
				outputBuffer = inputBytes != BUFFER_SIZE ? cipher.doFinal(inputBuffer, 0, inputBytes)
						: cipher.update(inputBuffer, 0, inputBytes);
				bufferedOutputStream.write(outputBuffer, 0, outputBuffer.length);
			}
			switch (cryption) {
			case ENCRYPT:
				System.out.print("Encryption completed. ");
				break;
			case DECRYPT:
				System.out.print("Decryption completed. ");
				break;
			}
			System.out.println("Generated file " + outputFileName + " based on file " + fileName + ".");

		} catch (IOException ioException) {
			System.out.println("Error occured while reading the file");
			System.exit(1);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
				| InvalidKeyException cipherException) {
			System.out.println("Error occured while instancing Cipher");
			System.exit(1);
		} catch (IllegalBlockSizeException | BadPaddingException cryptingException) {
			System.out.println("Error occured encrypting or decrypting the data");
			System.exit(1);
		}
	}
}
