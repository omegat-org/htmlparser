package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLLinkScanner;
import com.kizna.html.tags.HTMLDoctypeTag;
import com.kizna.html.tags.HTMLLinkTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLDoctypeTagTest extends TestCase {

	/**
	 * Constructor for HTMLDoctypeTagTest.
	 * @param arg0
	 */
	public HTMLDoctypeTagTest(String name) {
		super(name);
	}
	public void testToHTML() {
		String testHTML = new String(
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">\n"+
		"<HTML>\n"+
		"<HEAD>\n"+
		"<TITLE>Cogs of Chicago</TITLE>\n"+
		"</HEAD>\n"+
		"<BODY>\n"+
		"...\n"+
		"</BODY>\n"+
		"</HTML>\n");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		parser.registerScanners();
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 9 nodes identified",new Integer(9),new Integer(i));
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLDoctypeTag",node[0] instanceof HTMLDoctypeTag);
		HTMLDoctypeTag docTypeTag = (HTMLDoctypeTag)node[0];
		assertEquals("Raw HTML","<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">",docTypeTag.toHTML());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLDoctypeTagTest.class);
	}

}
