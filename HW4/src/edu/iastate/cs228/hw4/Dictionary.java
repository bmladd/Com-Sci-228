package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * @author Brandon Ladd
 * 
 *         An application class
 */
public class Dictionary {
	public static void main(String[] args) throws FileNotFoundException {
		EntryTree Dic = new EntryTree<>();
		File clearInput = null;
		try { // attempts to locate the file given by the arguments, 
			File input = new File(args[0]);
			clearInput = input;
		} catch (ArrayIndexOutOfBoundsException e) { // if the file is not found, it cathes an array index out of bounds error that should be thrown 
			System.out.println("Array Index does not exist or is not specified"); // prints out a statement telling you what went wrong
			throw new ArrayIndexOutOfBoundsException();
		}
		Scanner s = new Scanner(clearInput);
		while (s.hasNext()) {
			String next = s.next();

			{
				// If add:
				if (next.equals("add")) {
					char[] keyarray = s.next().toCharArray();
					Object value = s.next();
					Object[] keyarr = toObjArray(keyarray);
					String pri = ToString(keyarr);
					System.out.println("Command: add " + pri + " " + value);
					System.out.println("Result From Add; " + Dic.add(keyarr, value));
					System.out.println("");
				}

				// If prefix:
				if (next.equals("prefix")) {
					char[] keyarray = s.next().toCharArray();
					Object[] keyarr = toObjArray(keyarray);
					String pri = ToString(keyarr);
					String post = ToString(Dic.prefix(keyarr));
					System.out.println("Command: prefix " + pri);
					System.out.println("Result from prefix; " + post);
					System.out.println("");
				}

				// If search:
				if (next.equals("search")) {
					char[] keyarray = s.next().toCharArray();
					Object[] keyarr = toObjArray(keyarray);
					String pri = ToString(keyarr);
					System.out.println("Command: search " + pri);
					System.out.println("Result from search; " + Dic.search(keyarr));
					System.out.println("");
				}

				// If remove:
				if (next.equals("remove")) {
					char[] keyarray = s.next().toCharArray();
					Object[] keyarr = toObjArray(keyarray);
					String pri = ToString(keyarr);
					System.out.println("Command: remove " + pri);
					System.out.println("Result from remove; " + Dic.remove(keyarr));
					System.out.println("");
				}

				// If showTree:
				if (next.equals("showTree")) {
					System.out.println("Command: showTree");
					System.out.println("");
					System.out.println("Results from showTree; ");
					Dic.showTree();
					System.out.println("");
				}
			}
		}
		s.close();
	}

	/**
	 * Helper method for Main
	 * 
	 * @param s
	 * @return
	 */
	public static Object[] toObjArray(char[] s) {
		Object[] ret = new Object[s.length];
		for (int i = 0; i < s.length; i++)
			ret[i] = s[i];
		return ret;
	}

	/**
	 * Helper method for the Main method.
	 *
	 * @param s
	 *            The char array to convert.
	 */
	public static String ToString(Object[] s) {
		String ret = "";
		for (int i = 0; i < s.length; i++)
			ret += s[i];
		return ret;
	}
}
