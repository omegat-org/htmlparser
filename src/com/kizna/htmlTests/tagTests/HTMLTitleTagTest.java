package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import com.kizna.html.HTMLReader;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLNode;
import com.kizna.html.tags.HTMLTitleTag;
import com.kizna.html.scanners.*;
import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Enumeration;
/**
 * Insert the type's description here.
 * Creation date: (5/6/2002 11:35:09 PM)
 * @author: Administrator
 */
public class HTMLTitleTagTest extends TestCase {
/**
 * HTMLTitleTagTest constructor comment.
 * @param name java.lang.String
 */
public HTMLTitleTagTest(String name) {
	super(name);
}
	public static TestSuite suite() {
		return new TestSuite(HTMLTitleTagTest.class);
	}
	public void testToPlainTextString() {
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
		assertEquals("Title","Yahoo!",titleTag.toPlainTextString());				
	}
	public void testToHTML() {
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
		assertEquals("Raw String","<TITLE>Yahoo!</TITLE>",titleTag.toHTML());				
	}
	public void testToString() {
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
		assertEquals("Title","TITLE: Yahoo!",titleTag.toString());		
	}
}
