// HTMLParser Library v1_2_20021215 - A java-based parser for HTML
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
import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLFormTag;
import org.htmlparser.tags.HTMLInputTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.tests.scannersTests.HTMLFormScannerTest;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.HTMLFormScanner;
import org.htmlparser.util.HTMLEnumeration;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLFormTagTest extends HTMLParserTestCase {

	public HTMLFormTagTest(String name) {
		super(name);
	}

	public void testSetFormLocation() throws HTMLParserException{
		createParser(HTMLFormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];

		formTag.setFormLocation("http://www.yahoo.com/yahoo/do_not_login.jsp");
 
		String expectedHTML = "<FORM METHOD=\""+HTMLFormTag.POST+"\" ACTION=\"http://www.yahoo.com/yahoo/do_not_login.jsp\" NAME=\"login_form\" ONSUBMIT=\"return CheckData()\">\r\n"+
		HTMLFormScannerTest.EXPECTED_FORM_HTML_REST_OF_FORM;
		assertStringEquals("Raw String",expectedHTML,formTag.toHTML());
	}
	
	public void testToPlainTextString() throws HTMLParserException {
		createParser(HTMLFormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		assertStringEquals("Form Tag string representation","&nbsp;User NamePassword&nbsp;&nbsp;",formTag.toPlainTextString());
	}
	
	public void testSearchFor() throws HTMLParserException {
		createParser(HTMLFormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		Vector nodeVector = formTag.searchFor("USER NAME");
		assertEquals("Should have found nodes",1,nodeVector.size());
		
		HTMLNode [] nodes = new HTMLNode[10];
		int i = 0;
		for (Enumeration e = nodeVector.elements();e.hasMoreElements();) {
			nodes[i++] = (HTMLNode)e.nextElement();	
		}
		
		assertEquals("Number of nodes founs",1,i);
		assertTrue("Should have found a string node",nodes[0] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)nodes[0];
		assertEquals("Expected contents of string node","User Name",stringNode.getText());
	}

	public void testSearchForCaseSensitive() throws HTMLParserException {
		createParser(HTMLFormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		Vector nodeVector = formTag.searchFor("USER NAME",true);
		assertEquals("Should have not found nodes",0,nodeVector.size());
	
		nodeVector = formTag.searchFor("User Name",true);
		assertNotNull("Should have not found nodes",nodeVector);
	}

	
	public void testSearchByName() throws HTMLParserException {
		createParser(HTMLFormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];

		HTMLTag tag= formTag.searchByName("passwd");
		assertNotNull("Should have found the password node",node);
		assertTrue("Should be an input tag",tag instanceof HTMLInputTag);
	}	
}
