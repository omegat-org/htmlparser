// HTMLParser Library v1_2_20020831 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.htmlTests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLBaseHREFScanner;
import com.kizna.html.scanners.HTMLLinkScanner;
import com.kizna.html.scanners.HTMLTitleScanner;
import com.kizna.html.tags.HTMLBaseHREFTag;
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
public class HTMLBaseHREFScannerTest extends TestCase {
	private HTMLBaseHREFScanner scanner;

	/**
	 * Constructor for HTMLBaseHREFScannerTest.
	 * @param arg0
	 */
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
		String testHTML = new String("<html><head><TITLE>test page</TITLE><BASE HREF=\"http://www.abc.com/\"><a href=\"home.cfm\">Home</a>...</html>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		HTMLLinkScanner linkScanner = new HTMLLinkScanner("-l");
		parser.addScanner(linkScanner);
		parser.addScanner(new HTMLTitleScanner("-t"));
		parser.addScanner(linkScanner.createBaseHREFScanner("-b"));
	 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
	 	assertEquals("Number of nodes expected",7,i);		
		//Base href tag should be the 4th tag
		assertTrue(node[3] instanceof HTMLBaseHREFTag);
		HTMLBaseHREFTag baseRefTag = (HTMLBaseHREFTag)node[3];
		assertEquals("Base HREF Url","http://www.abc.com",baseRefTag.getBaseUrl());	
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLBaseHREFScannerTest.class);
	}
}
