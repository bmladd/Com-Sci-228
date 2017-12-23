package edu.iastate.cs228.hw4;
/**
 * @author Brandon Ladd
 *
 *         Junit test cases for the methods built in EntryTree
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EntryTreeTest {
	public EntryTree test;
	public Object[] Arbitrary = new Object[5]; // sample string 01
	public Object Def = "meaningless";

	public Object[] Reaction = new Object[5]; // sample string 1
	public Object Definition = "response to an action";

	public Object[] pie = new Object[12]; // sample string 2
	public Object type = "cherry";

	public Object[] League = new Object[23]; // sample string 3
	public Object Game = "Ranked is fucked";

	public Object[] Dog = new Object[17]; // sample string 4
	public Object breed = "Shepard or Colly";

	public Object[] error = new Object[3]; // purposeful error
	public Object errorDef = "testing";

	public Object[] error2 = new Object[0]; // null for testing purposes
	public Object errorDef2 = null;
	
	@Before
	public void initiate() {
		test = new EntryTree<>();

		Arbitrary = toCharacterArray("will");

		Reaction = toCharacterArray("punch");

		pie = toCharacterArray("will to live");
		
		League = toCharacterArray("will to live, not found");
		
		Dog = toCharacterArray("Labrador");
	}

	private Character[] toCharacterArray(String s) {
		Character[] temp = new Character[s.length()];
		for (int i = 0; i < s.length(); i++) {
			temp[i] = s.charAt(i);
		}
		return temp;
	}


	@Test
	public void addTest() throws Exception {

		boolean result = false;
		try {
			test.add(error, errorDef);
			fail("Expected an NullPointerException to be thrown");
		} catch (NullPointerException e) {
			result = true;
		}
		assertEquals(result, true);

		assertEquals(test.add(error2, errorDef2), false);

		assertEquals(test.add(Arbitrary, Def), true);

		// test.showTree();

		Def = "revized";

		assertEquals(test.add(Arbitrary, Def), true);

		// test.showTree();

		assertEquals(test.add(pie, type), true);

		// test.showTree();

		assertEquals(test.add(Reaction, Definition), true);

		// test.showTree();
	}

	@Test
	public void searchTest() throws Exception {
		test.add(Reaction, Definition);
		assertEquals(test.search(Reaction), Definition);

		assertEquals(test.search(pie), null);

		boolean result = false;
		try {
			test.search(error);
			fail("Expected an NullPointerException to be thrown");
		} catch (NullPointerException e) {
			result = true;
		}
		assertEquals(result, true);

		assertEquals(test.search(error2), null);

	}

	@Test
	public void prefixTest() throws Exception {
		test.add(pie, pie);
		Object[] ret = test.prefix(League);
		String res = "";
		for (int i = 0; i < ret.length; i++) {
			res += ret[i];
		}
		assertEquals(res, ("will to live"));

		ret = test.prefix(Arbitrary);
		res = "";
		for (int i = 0; i < ret.length; i++) {
			res += ret[i];
		}
		assertEquals(res, ("will"));
	}

	@Test
	public void removeTest() throws Exception {
		// editorialDef == "an article"
		// editorial == "editorial"
		test.add(pie, type);
		assertEquals(test.remove(pie), type);

		boolean result = false;
		try {
			test.remove(error);
			fail("Expected an NullPointerException to be thrown");
		} catch (NullPointerException e) {
			result = true;
		}
		assertEquals(result, true);

		assertEquals(test.search(error2), null);

		assertEquals(test.remove(Dog), null);

	}

	@Test
	public void showTreeTest() throws Exception {
		test.add(Arbitrary, Def);
		test.add(Reaction, Definition);
		test.add(League, Game);
		test.showTree();
	}

}
