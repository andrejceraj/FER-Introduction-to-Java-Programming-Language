package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {

	public static TreeNode instanceBinaryTree1() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		return head;
	}
	
	public static TreeNode instanceBinaryTree2() {
		TreeNode head = null;
		head = addNode(head, 20);
		head = addNode(head, 15);
		head = addNode(head, 10);
		head = addNode(head, 15);
		head = addNode(head, 5);
		head = addNode(head, 1);
		head = addNode(head, 6);
		head = addNode(head, 21);
		return head;
	}

	@Test
	void addingNodesToTree() {
		TreeNode head1 = instanceBinaryTree1(), head2 = instanceBinaryTree2();
		assertEquals(42, head1.value);
		assertEquals(21, head1.left.value);
		assertEquals(35, head1.left.right.value);
		assertEquals(76, head1.right.value);
		
		assertEquals(20, head2.value);
		assertEquals(15, head2.left.value);
		assertEquals(10, head2.left.left.value);
		assertEquals(5, head2.left.left.left.value);
		assertEquals(1, head2.left.left.left.left.value);
		assertEquals(6, head2.left.left.left.right.value);
		assertEquals(21, head2.right.value);
	}
	
	@Test
	void calculateTreeSize() {
		TreeNode head1 = instanceBinaryTree1(), head2 = instanceBinaryTree2();
		assertEquals(4, treeSize(head1));
		assertEquals(7, treeSize(head2));
		assertEquals(0,  treeSize(null));
	}
	
	@Test
	void doesTreeContainsValue() {
		TreeNode head = instanceBinaryTree2();
		assertTrue(containsValue(head, 20));
		assertTrue(containsValue(head, 10));
		assertTrue(containsValue(head, 1));
		assertTrue(containsValue(head, 6));
		assertFalse(containsValue(head, 25));
		assertFalse(containsValue(head, 4));
		assertFalse(containsValue(null, 0));
	}
}
