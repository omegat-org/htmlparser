package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLStyleTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLStyleTagTest extends TestCase {

	/**
	 * Constructor for HTMLStyleTagTest.
	 * @param arg0
	 */
	public HTMLStyleTagTest(String name) {
		super(name);
	}
	public void testToRawString() {
		String testHTML = new String("<style>a.h{background-color:#ffee99}</style>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yle.fi/");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		parser.registerScanners();
	 	for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLNode)e.nextElement();
		}
	 	assertEquals("Number of nodes expected",1,i);
		assertTrue(node[0] instanceof HTMLStyleTag);
		HTMLStyleTag styleTag = (HTMLStyleTag)node[0];
		assertEquals("Raw String","<STYLE>a.h{background-color:#ffee99}</STYLE>",styleTag.toHTML());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLStyleTagTest.class);
	}
}
