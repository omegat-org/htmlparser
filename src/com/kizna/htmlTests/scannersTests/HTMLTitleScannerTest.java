/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.kizna.htmlTests.scannersTests;
import java.io.*;
import java.util.Enumeration;

import javax.swing.border.TitledBorder;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.HTMLTitleTag;
import com.kizna.html.*;
/**
 * @version 	1.0
 * @author
 */
public class HTMLTitleScannerTest extends TestCase {

	/**
	 * Constructor for HTMLTitleScannerTest.
	 * @param arg0
	 */
	public HTMLTitleScannerTest(String arg0) {
		super(arg0);
	}
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLTitleScannerTest.class);
		return suite;
	}	
	public void testEvaluate() {
		HTMLTitleScanner scanner = new HTMLTitleScanner("-t");
		boolean retVal = scanner.evaluate("TITLE",null);
		assertEquals("Title evaluation - Upper Case",true,retVal);
		retVal = scanner.evaluate("title",null);
		assertEquals("Title evaluation - Lower Case",true,retVal);
		retVal = scanner.evaluate("abcd",null);
		assertEquals("Incorrect Case",false,retVal);
	}
	public void testScan() {
		String testHTML = new String("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		parser.addScanner(new HTMLTitleScanner("-t"));
		parser.addScanner(new HTMLStyleScanner("-s"));
		parser.addScanner(new HTMLMetaTagScanner("-m"));
	 	for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLNode)e.nextElement();
		}
	 	assertEquals("Number of nodes expected",7,i);		
	 	assertTrue(node[2] instanceof HTMLTitleTag);
		// check the title node
		HTMLTitleTag titleTag = (HTMLTitleTag) node[2];
		assertEquals("Title","Yahoo!",titleTag.getTitle());
		
	}
}
