package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Demonstration of solving the 3x3 Sliding Puzzle using Breadth-First Search
 * (BFS) algorithm.
 * 
 * @author Andrej Ceraj
 *
 */
public class SlagalicaDemo {
	/**
	 * Number of indexes in a sliding puzzle.
	 */
	private static final int SIZE = 9;

	/**
	 * Method starts when the program is run.
	 * 
	 * @param args puzzle content
	 */
	public static void main(String[] args) {
		if (!isArgValid(args)) {
			return;
		}
		Slagalica slagalica = null;
		try {
			slagalica = getSlagalicaFromString(args[0]);
		} catch (Exception numberFormatException) {
			System.out.println("Argument should only contain digits in range [0,8].");
			return;
		}

		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		printRješenje(rješenje);
	}

	/**
	 * Checks the program arguments. There should only be one argument with length
	 * equals to 9.
	 * 
	 * @param args program argument
	 * @return true if the argument is valid; false otherwise.
	 */
	private static boolean isArgValid(String[] args) {
		if (args.length != 1) {
			System.out.println("Only one argument should be passed");
			return false;
		}
		if (args[0].length() != SIZE) {
			System.out.println("Argument should contain 9 digits");
			return false;
		}
		return true;
	}

	/**
	 * Creates and returns new {@code Slagalica} from the given string.
	 * 
	 * @param arg string
	 * @return new Slagalica
	 * @throws NumberFormatException if the string contains non-digit characters.
	 */
	private static Slagalica getSlagalicaFromString(String arg) throws NumberFormatException {
		char[] charArray = arg.toCharArray();
		int[] polje = new int[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			polje[i] = Integer.parseInt(String.valueOf(charArray[i]));
		}
		return new Slagalica(new KonfiguracijaSlagalice(polje));
	}

	/**
	 * Method prints the solution for the 3x3 sliding puzzle and displays its the
	 * step by step progress using the {@code SlagalicaViewer}.
	 * 
	 * @param rješenje solution {@link Node}
	 */
	private static void printRješenje(Node<KonfiguracijaSlagalice> rješenje) {
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
	}
}