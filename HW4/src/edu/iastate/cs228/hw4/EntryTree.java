package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * @author Brandon Ladd
 *
 *         An entry tree class Add Javadoc comments to each method
 */
public class EntryTree<K, V> {
	/**
	 * dummy root node made public for grading
	 */
	protected Node root;

	/**
	 * prefixlen is the largest index such that the keys in the subarray keyarr
	 * from index 0 to index prefixlen - 1 are, respectively, with the nodes on
	 * the path from the root to a node. prefixlen is computed by a private
	 * method shared by both search() and prefix() to avoid duplication of code.
	 */
	protected int prefixlen;

	protected class Node implements EntryNode<K, V> {
		protected Node child; // link to the first child node
		protected Node parent; // link to the parent node
		protected Node prev; // link to the previous sibling
		protected Node next; // link to the next sibling
		protected K key; // the key for this node
		protected V value; // the value at this node

		public Node(K aKey, V aValue) {
			key = aKey;
			value = aValue;
			child = null;
			parent = null;
			prev = null;
			next = null;
		}

		/*
		 * @see edu.iastate.cs228.hw4.EntryNode#parent()
		 */
		@Override
		public EntryNode<K, V> parent() {
			return this.parent;
		}

		/*
		 * @see edu.iastate.cs228.hw4.EntryNode#child()
		 */
		@Override
		public EntryNode<K, V> child() {
			return this.child;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.iastate.cs228.hw4.EntryNode#next()
		 */
		@Override
		public EntryNode<K, V> next() {

			return this.next;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.iastate.cs228.hw4.EntryNode#prev()
		 */
		@Override
		public EntryNode<K, V> prev() {

			return this.prev;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.iastate.cs228.hw4.EntryNode#key()
		 */
		@Override
		public K key() {
			return this.key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.iastate.cs228.hw4.EntryNode#value()
		 */
		@Override
		public V value() {
			return this.value;
		}
	}

	/*
	 * creates a dummy node tree that can be filled.
	 */
	public EntryTree() {
		root = new Node(null, null);
	}

	/**
	 * Returns the value of the entry with a specified key sequence, or null if
	 * this tree contains no entry with the key sequence.
	 * 
	 * @param keyarr
	 * @return
	 */
	public V search(K[] keyarr) {
		Node cur = root;

		if (keyarr == null || keyarr.length == 0) {
			return null;
		}
		for (int i = 0; i < keyarr.length; i++) {
			if (keyarr[i] == null) { // if anything in the Array given is null,
										// throw NullPointerException
				throw new NullPointerException();
			}
		}
		for (int i = 0; i < keyarr.length; i++) {
			if (cur.child != null) { // check to see if the child is null
				cur = cur.child;
				while (cur.key != keyarr[i]) {
					if (cur.next != null) { // check if the next is null
						cur = cur.next;
					} else if (cur.next == null) { // if it is null return null
						return null;
					}
				}
			}
		}

		return cur.value;
	}

	/**
	 * The method returns an array of type K[] with the longest prefix of the
	 * key sequence specified in keyarr such that the keys in the prefix label
	 * the nodes on the path from the root to a node. The length of the returned
	 * array is the length of the longest prefix.
	 * 
	 * @param keyarr
	 * @return
	 */
	public K[] prefix(K[] keyarr) {
		if (keyarr == null || keyarr.length == 0) {
			return null;
		}
		for (int i = 0; i < keyarr.length; i++) {
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}

		}
		Node cur = root;
		for (int i = 0; i < keyarr.length; i++) {
			if (cur.child != null) {
				cur = cur.child;
				while (cur.key != keyarr[i] && cur.next != null) {
					cur = cur.next;
				}

			}
			if (cur.key != keyarr[i]) {
				K[] prefix = (K[]) new Object[i + 1];
				for (int j = 0; j < keyarr.length; j++) {
					prefix[j] = keyarr[i];
					return Arrays.copyOf(keyarr, i);
				}
			}
			if (cur.child == null && keyarr[i] != cur.key) {
				return Arrays.copyOf(keyarr, i);
			}
		}

		return keyarr; // TODO
		// Hint: An array of the same type as keyarr can be created with
		// Arrays.copyOf().

	}

	/**
	 * The method locates the node P corresponding to the longest prefix of the
	 * key sequence specified in keyarr such that the keys in the prefix label
	 * the nodes on the path from the root to the node. If the length of the
	 * prefix is equal to the length of keyarr, then the method places aValue at
	 * the node P and returns true. Otherwise, the method creates a new path of
	 * nodes (starting at a node S) labelled by the corresponding suffix for the
	 * prefix, connects the prefix path and suffix path together by making the
	 * node S a child of the node P, and returns true.
	 * 
	 * @param keyarr
	 * @param aValue
	 * @return
	 */
	public boolean add(K[] keyarr, V aValue) {
		if (keyarr == null || keyarr.length == 0 || aValue == null) {
			return false;
		}
		for (int i = 0; i < keyarr.length; i++) {
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}
		}
		Node cur = root;
		for (int i = 0; i < keyarr.length; i++) {
			if (cur.child == null && i == keyarr.length - 1) {
				Node a = new Node(keyarr[i], aValue);
				cur.child = a;
				cur = cur.child;
			} else if (cur.child == null) {
				Node a = new Node(keyarr[i], aValue);
				cur.child = a;
				cur = cur.child;
			} else if (cur.child != null) {
				cur = cur.child;
				while (keyarr[i] != cur.key && cur.next != null) {
					cur = cur.next;
					if (cur.next == null && keyarr[i] != cur.key) {
						Node a = new Node(keyarr[i], null);
						cur.next = a;
						a.prev = cur;
						a.parent = cur.parent;
						cur = cur.next;
					}
				}
				if (keyarr[i] != cur.key && cur.next == null) {
					Node a = new Node(keyarr[i], null);
					cur.next = a;
					a.prev = cur;
					a.parent = cur.parent;
					cur = cur.next;
				}
				if (i + 1 == keyarr.length && cur.value != aValue) {
					cur.value = aValue;
				}
			}
		}
		return true;
	}

	/**
	 * Removes the entry for a key sequence from this tree and returns its value
	 * if it is present. Otherwise, it makes no change to the tree and returns
	 * null.
	 * 
	 * @param keyarr
	 * @return
	 */
	public V remove(K[] keyarr) {
		if (keyarr == null || keyarr.length == 0) {
			return null;
		}
		for (int i = 0; i < keyarr.length; i++) {
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}
		}
		Node cur = root;
		for (int i = 0; i < keyarr.length; i++) {
			if (cur.child != null) { // check to see if the child is null
				cur = cur.child;
				while (cur == null && cur.key != keyarr[i]) { // check if next is null and still not found the key
					cur = cur.next;
				}
				if (cur.key != keyarr[i] && cur.next == null) {
					return null;
				}
			}
		}
		V temp = cur.value;
		cur.value = null;
		while ((cur.next == null && cur.child != null && cur.value == null)) {
			cur = cur.parent;
			cur.child = null;
		}
		if (cur.value == null && cur.child != null && cur.prev != null) {
			cur.prev.next = null;
		}
		if (cur.value == null && cur.child != null && cur.prev == null) {
			cur.parent.child = cur.next;
			cur.next.prev = null;//dangerous
		}

		return temp;
	}

	/**
	 * The method prints the tree on the console in the output format shown in
	 * an example output file.
	 */
	public void showTree() {
		Node cur = root;
		String spaces = "";
		showTreeV(cur, spaces);
	}

	private void showTreeV(Node cur, String Toni) {
		if (cur.next == null && cur.child == null) {
			System.out.println(Toni + cur.key + "->" + cur.value);
		} else if (cur.next != null && cur.child != null) {
			System.out.println(Toni + cur.key + "->" + cur.value);
			String plus = Toni + "  ";
			showTreeV(cur.child, plus);
			showTreeV(cur.next, plus);
		} else if (cur.next != null && cur.child == null) {
			System.out.println(Toni + cur.key + "->" + cur.value);
			showTreeV(cur.next, Toni);
		} else if (cur.next == null && cur.child != null) {
			System.out.println(Toni + cur.key + "->" + cur.value);
			if (cur == root) {
				String next = Toni + "     ";
				showTreeV(cur.child, next);
			} else {
				String after = Toni + "  ";
				showTreeV(cur.child, after);
			}
		}
	}

}
