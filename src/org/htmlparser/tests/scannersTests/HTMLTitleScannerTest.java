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

package org.htmlparser.tests.scannersTests;
import java.io.*;
import java.util.Enumeration;

import javax.swing.border.TitledBorder;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.HTMLTitleTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.*;

public class HTMLTitleScannerTest extends HTMLParserTestCase {

	public HTMLTitleScannerTest(String name) {
		super(name);
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
	
	public void testScan() throws HTMLParserException {
		createParser("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
		HTMLTitleScanner titleScanner = new HTMLTitleScanner("-t");
		parser.addScanner(titleScanner);
		parser.addScanner(new HTMLStyleScanner("-s"));
		parser.addScanner(new HTMLMetaTagScanner("-m"));
	 	parseAndAssertNodeCount(7);
	 	assertTrue(node[2] instanceof HTMLTitleTag);
		// check the title node
		HTMLTitleTag titleTag = (HTMLTitleTag) node[2];
		assertEquals("Title","Yahoo!",titleTag.getTitle());
		assertEquals("Title Scanner",titleScanner,titleTag.getThisScanner());
	}
	
	/**
	 * Testcase to reproduce a bug reported by Cedric Rosa,
	 * on not ending the title tag correctly, we would get 
	 * null pointer exceptions..
	 */
	public void testIncompleteTitle() throws HTMLParserException {
		createParser(
		"<TITLE>SISTEMA TERRA, VOL. VI , No. 1-3, December 1997</TITLE\n"+
		"</HEAD>");
		HTMLTitleScanner titleScanner = new HTMLTitleScanner("-t");
		parser.addScanner(titleScanner);
	 	parseAndAssertNodeCount(2);
	 	assertTrue("First Node is a title tag",node[0] instanceof HTMLTitleTag);
	 	HTMLTitleTag titleTag = (HTMLTitleTag)node[0];
	 	assertEquals("Title","SISTEMA TERRA, VOL. VI , No. 1-3, December 1997",titleTag.getTitle());
	
	}
	
	/**
	 * If there are duplicates of the title tag, the parser crashes.
	 * This bug was reported by Claude Duguay
	 */
	public void testDoubleTitleTag() throws HTMLParserException{
		createParser(
		"<html><head><TITLE>\n"+
		"<html><head><TITLE>\n"+
		"Double tags can hang the code\n"+
		"</TITLE></head><body>\n"+
		"<body><html>");
		HTMLTitleScanner titleScanner = new HTMLTitleScanner("-t");
		parser.addScanner(titleScanner);
	 	parseAndAssertNodeCount(7);
	 	assertTrue("Third tag should be a title tag",node[2] instanceof HTMLTitleTag);
		HTMLTitleTag titleTag = (HTMLTitleTag)node[2];
	 	assertEquals("Title","Double tags can hang the code\r\n",titleTag.getTitle());
	 	
	}
	
	/**
	 * Testcase based on Claude Duguay's report. This proves
	 * that the parser throws exceptions when faced with malformed html
	 */
	public void testNoEndTitleTag() throws HTMLParserException {
		createParser(
		"<TITLE>KRP VALIDATION<PROCESS/TITLE>");
		try {
			HTMLTitleScanner titleScanner = new HTMLTitleScanner("-t");
			parser.addScanner(titleScanner);
		 	parseAndAssertNodeCount(1);
			assertTrue("Should have thrown an HTMLParserException",false);
		}
		catch (HTMLParserException e) {

		}
	 	
	}	
}
