// HTMLParser Library v1_2_20021208 - A java-based parser for HTML
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

package org.htmlparser.tests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.scanners.HTMLBaseHREFScanner;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.scanners.HTMLTitleScanner;
import org.htmlparser.tags.HTMLBaseHREFTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLBaseHREFScannerTest extends HTMLParserTestCase {

	private HTMLBaseHREFScanner scanner;

	public HTMLBaseHREFScannerTest(String arg0) {
		super(arg0);
	}
	
	protected void setUp() {
		scanner=new HTMLBaseHREFScanner();
	}

	public void testRemoveLastSlash() {
		String url1 = "http://www.yahoo.com/";
		String url2 = "http://www.google.com";
		String modifiedUrl1 = scanner.removeLastSlash(url1);
		String modifiedUrl2 = scanner.removeLastSlash(url2);
		assertEquals("Url1","http://www.yahoo.com",modifiedUrl1);
		assertEquals("Url2","http://www.google.com",modifiedUrl2);
	}
	
	public void testEvaluate() {
		String testData1 = "BASE HREF=\"http://www.abc.com/\"";
		assertTrue("Data 1 Should have evaluated true",scanner.evaluate(testData1,null));
		String testData2 = "Base href=\"http://www.abc.com/\"";
		assertTrue("Data 2 Should have evaluated true",scanner.evaluate(testData2,null));		
	}
	
	public void testScan() throws HTMLParserException{
		createParser("<html><head><TITLE>test page</TITLE><BASE HREF=\"http://www.abc.com/\"><a href=\"home.cfm\">Home</a>...</html>","http://www.google.com/test/index.html");
		HTMLLinkScanner linkScanner = new HTMLLinkScanner("-l");
		parser.addScanner(linkScanner);
		parser.addScanner(new HTMLTitleScanner("-t"));
		parser.addScanner(linkScanner.createBaseHREFScanner("-b"));
		parseAndAssertNodeCount(7);
		//Base href tag should be the 4th tag
		assertTrue(node[3] instanceof HTMLBaseHREFTag);
		HTMLBaseHREFTag baseRefTag = (HTMLBaseHREFTag)node[3];
		assertEquals("Base HREF Url","http://www.abc.com",baseRefTag.getBaseUrl());	
	}
	
}
