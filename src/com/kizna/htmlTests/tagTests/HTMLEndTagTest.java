package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLEndTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLEndTagTest extends TestCase {

	/**
	 * Constructor for HTMLEndTagTest.
	 * @param arg0
	 */
	public HTMLEndTagTest(String name) {
		super(name);
	}
	public void testToRawString() {
		String testHTML = new String("<HTML></HTML>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.registerScanners();			
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
				node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 2 nodes identified",new Integer(2),new Integer(i));
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLEndTag",node[1] instanceof HTMLEndTag);
		HTMLEndTag endTag = (HTMLEndTag)node[1];
		assertEquals("Raw String","</HTML>",endTag.toHTML());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLEndTagTest.class);
	}

}
