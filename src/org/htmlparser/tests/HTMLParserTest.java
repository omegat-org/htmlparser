// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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

package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

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
public class HTMLParserTest extends HTMLParserTestCase {

	public HTMLParserTest(String name) {
		super(name);
	}
	public void testElements() throws HTMLParserException {
		createParser("<SomeHTML>");
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
	
	public void testNullUrl() {
		HTMLParser parser;
		try {
			parser = new HTMLParser("http://someoneexisting.com");
			assertTrue("Should have thrown an exception!",false);
		}
		catch (HTMLParserException e) {
			
		}
	}
	
	public void testURLWithSpaces() throws HTMLParserException{
		HTMLParser parser;
		//System.out.println("************ Testing URL ************");
		String url = "http://htmlparser.sourceforge.net/test/This is a Test Page.html";
		
		parser = new HTMLParser(url);
		HTMLNode node [] = new HTMLNode[30];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i] = e.nextHTMLNode();
		//	node[i].print();
			i++;
			
		}
		assertEquals("Expected nodes",12,i);
		//System.out.println("************ Finished Testing URL ************");
	}
}
