package com.kizna.htmlTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLParserTest extends TestCase {

	/**
	 * Constructor for HTMLParserTest.
	 * @param arg0
	 */
	public HTMLParserTest(String name) {
		super(name);
	}
	public void testElements() throws HTMLParserException {
		String testHTML = new String("<SomeHTML>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",1,i);
		// Now try getting the elements again
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified (second call to parser.elements())",1,i);
	}
	/**
	 * This testcase needs you to be online.
	 */
	public void testElementsFromWeb() throws HTMLParserException {
		HTMLParser parser;
		try {
			parser = new HTMLParser("http://www.google.com");
		}
		catch (Exception e ){
			throw new HTMLParserException("You must be offline! This test needs you to be connected to the internet.",e);
		}
		HTMLNode [] node = new HTMLNode[200];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		int cnt = i;
		// Now try getting the elements again
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be "+cnt+" nodes identified (second call to parser.elements())",cnt,i);
	}	
	public static TestSuite suite() {
		return new TestSuite(HTMLParserTest.class);
	}
}
