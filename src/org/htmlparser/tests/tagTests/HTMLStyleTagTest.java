// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.tests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLStyleTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

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
	public void testToHTML() throws HTMLParserException {
		String testHTML = new String("<style>a.h{background-color:#ffee99}</style>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yle.fi/");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		parser.registerScanners();
	 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
	 	assertEquals("Number of nodes expected",1,i);
		assertTrue(node[0] instanceof HTMLStyleTag);
		HTMLStyleTag styleTag = (HTMLStyleTag)node[0];
		assertEquals("Raw String","<STYLE>a.h{background-color:#ffee99}</STYLE>",styleTag.toHTML());
	}
	/**
	 * Reproducing a bug reported by Dhaval Udani relating to
	 * style tag attributes being missed
	 */
	public void testToHTML_Attriubtes() throws HTMLParserException {
		String testHTML = new String("<STYLE type=\"text/css\">\n"+
		"<!--"+
		"{something....something}"+
		"-->"+
		"</STYLE>");

		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yle.fi/");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\r\n");
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		parser.registerScanners();
	 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
	 	assertEquals("Number of nodes expected",1,i);
		assertTrue(node[0] instanceof HTMLStyleTag);
		HTMLStyleTag styleTag = (HTMLStyleTag)node[0];
		HTMLTagTest.assertStringEquals("Raw String","<STYLE TYPE=\"text/css\">"+
		"<!--\r\n"+
		"{something....something}\r\n"+
		"-->"+
		"</STYLE>",styleTag.toHTML());
	}	
	public static TestSuite suite() {
		return new TestSuite(HTMLStyleTagTest.class);
	}
}