// HTMLParser Library v1_2_20020721 - A java-based parser for HTML
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
		HTMLTitleScanner titleScanner = new HTMLTitleScanner("-t");
		parser.addScanner(titleScanner);
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
		assertEquals("Title Scanner",titleScanner,titleTag.getThisScanner());
	}
	public void testIncompleteTitle() {
		String testHTML = new String(
		"<TITLE>SISTEMA TERRA, VOL. VI , No. 1-3, December 1997</TITLE\n"+
		"</HEAD>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		HTMLTitleScanner titleScanner = new HTMLTitleScanner("-t");
		parser.addScanner(titleScanner);
	 	for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLNode)e.nextElement();
		}
	 	assertEquals("Number of nodes expected",2,i);		
	 	assertTrue("First Node is a title tag",node[0] instanceof HTMLTitleTag);
	
	}
}
