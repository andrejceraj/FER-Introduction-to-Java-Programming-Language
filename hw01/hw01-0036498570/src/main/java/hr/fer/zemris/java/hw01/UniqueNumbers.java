package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program gets values from the user and adds them to binary tree if the tree
 * does not already contains the values. The process is continued until the user
 * types in 'kraj'
 * 
 * @author Andrej Ceraj
 *
 */
public class UniqueNumbers {

	/**
	 * Method which is called when the program starts
	 * 
	 * @param args parameters are not used
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String input;
		TreeNode head = null;
		while (true) {
			System.out.print("Unesite broj > ");
			input = sc.next();
			if (input.equals("kraj")) {
				break;
			}
			try {
				Integer value = Integer.parseInt(input);
				if (containsValue(head, value) == true) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					head = addNode(head, value);
					System.out.println("Dodano.");
				}
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}
		}
		sc.close();
		System.out.print("Ispis od najmanjeg:");
		printAscending(head);
		System.out.print("\nIspis od najvećeg:");
		printDescending(head);

	}

	/**
	 * Binary tree structure
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	public static class TreeNode {
		/**
		 * TreeNodes on a lower hierarchy level. Left TreeNode has lower value, right
		 * TreeNode has greater value
		 */
		TreeNode left = null, right = null;
		/**
		 * Value of a TreeNode
		 */
		int value;

		/**
		 * Creates a new TreeNode and sets its value to a given integer.
		 * 
		 * @param value new value of created TreeNode
		 */
		public TreeNode(int value) {
			this.value = value;
		}
	}

	/**
	 * Adds a node with the given value in a binary tree if the tree does not
	 * already contains the value.
	 * 
	 * @param head  binary tree root node
	 * @param value value to add in the binary tree
	 * @return binary tree root node
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode(value);
			return head;
		}
		if (head.value == value) {
			return head;
		} else if (head.value > value) {
			head.left = addNode(head.left, value);
		} else {
			head.right = addNode(head.right, value);
		}
		return head;
	}

	/**
	 * Calculates binary tree size
	 * 
	 * @param head binary tree root node
	 * @return tree size
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		return treeSize(head.left) + treeSize(head.right) + 1;
	}

	/**
	 * Checks if a binary tree contains a value
	 * 
	 * @param head  binary tree root node
	 * @param value value which will be checked if it is in the binary tree
	 * @return true if the value is contained in the binary tree, false if it is not
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		if (head.value == value) {
			return true;
		}
		return value < head.value ? containsValue(head.left, value) : containsValue(head.right, value);
	}

	/**
	 * Prints values of a binary tree in ascending order
	 * 
	 * @param head binary tree root node
	 */
	public static void printAscending(TreeNode head) {
		if (head == null) {
			return;
		}
		printAscending(head.left);
		System.out.print(" " + head.value);
		printAscending(head.right);
	}

	/**
	 * Prints values of a binary tree in descending order
	 * 
	 * @param head binary tree root node
	 */
	public static void printDescending(TreeNode head) {
		if (head == null) {
			return;
		}
		printDescending(head.right);
		System.out.print(" " + head.value);
		printDescending(head.left);
	}
}
