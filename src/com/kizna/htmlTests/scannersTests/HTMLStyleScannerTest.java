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
import java.util.Hashtable;
import com.kizna.html.tags.HTMLStyleTag;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

import com.kizna.html.*;
import java.io.StringReader;
import com.kizna.html.scanners.HTMLStyleScanner;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/18/2001 2:20:43 AM)
 * @author: Administrator
 */
public class HTMLStyleScannerTest extends junit.framework.TestCase 
{
	/**
	 * HTMLAppletScannerTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLStyleScannerTest(String name) {
		super(name);
	}
	public static void main(String[] args) {
		new junit.awtui.TestRunner().start(new String[] {"com.kizna.htmlTests.scannersTests.HTMLStyleScannerTest"});
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:22:36 AM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLStyleScannerTest.class);
		return suite;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:23:14 AM)
	 */
	public void testEvaluate() 
	{
		HTMLStyleScanner scanner = new HTMLStyleScanner("-s");
		boolean retVal = scanner.evaluate("   style ",null);
		assertEquals("Evaluation of STYLE tag",new Boolean(true),new Boolean(retVal));
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/8/2002 8:57:36 PM)
	 */
	public void testScan() {
		
		String testHTML = new String("<STYLE TYPE=\"text/css\"><!--\n\n"+
		"</STYLE>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yle.fi/");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		parser.addScanner(new HTMLStyleScanner("-s"));
		try {
		 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
				node[i++] = e.nextHTMLNode();

			}
			assertTrue("Should've thrown exception",false);
		}
		catch (HTMLParserException e) {

		}	
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 1:04:23 PM)
	 */
	public void testScanBug() throws HTMLParserException {
		String testHTML = new String("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
	//	parser.addScanner(new HTMLStyleScanner("-s"));
		parser.registerScanners();
	 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
	 	assertEquals("Number of nodes expected",7,i);
	 	assertTrue("Second last node should be a style tag",node[5] instanceof HTMLStyleTag);
	 	HTMLStyleTag styleTag = (HTMLStyleTag)node[5];
		assertEquals("Style Code","a.h{background-color:#ffee99}",styleTag.getStyleCode());
	}
	/**
	 * This is a bug reported by Kaarle Kaaila. 
	 */
	public void testScanBug2() throws HTMLParserException {
		String testHTML = new String("<STYLE TYPE=\"text/css\"><!--\n\n"+
		"input{font-family: arial, helvetica, sans-serif; font-size:11px;}\n\n"+
		"i {font-family: times; font-size:10pt; font-weight:normal;}\n\n"+
		".ruuhka {font-family: arial, helvetica, sans-serif; font-size:11px;}\n\n"+
		".paalinkit {font-family: arial, helvetica, sans-serif; font-size:12px;}\n\n"+
		".shortselect{font-family: arial, helvetica, sans-serif; font-size:12px; width:130;}\n\n"+
		".cityselect{font-family: arial, helvetica, sans-serif; font-size:11px; width:100;}\n\n"+
		".longselect{font-family: arial, helvetica, sans-serif; font-size:12px;}\n\n"+
		"---></STYLE>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yle.fi/");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		parser.addScanner(new HTMLStyleScanner("-s"));
	 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
	 	assertEquals("Number of nodes expected",1,i);
		assertTrue(node[0] instanceof HTMLStyleTag);
			
	}
	/**
	 * This is a bug reported by Dr. Wes Munsil, with the parser crashing on Google
	 */
	public void testScanBug3() throws HTMLParserException {
		String testHTML = new String("<html><head><META HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=ISO-8859-1\"><title>Google</title><style><!--\n"+
		"body,td,a,p,.h{font-family:arial,sans-serif;} .h{font-size: 20px;} .h{color:} .q{text-decoration:none; color:#0000cc;}\n"+
		"//--></style>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yle.fi/");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		parser.registerScanners();
	 	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
	 	assertEquals("Number of nodes expected",5,i);
		assertTrue(node[4] instanceof HTMLStyleTag);
		HTMLStyleTag styleTag = (HTMLStyleTag)node[4];
		String expectedCode = "<!--\r\n\r\n"+"body,td,a,p,.h{font-family:arial,sans-serif;} .h{font-size: 20px;} .h{color:} .q{text-decoration:none; color:#0000cc;}\r\n"+
		"//\r\n-->";
		assertEquals("Expected Style Code",expectedCode,styleTag.getStyleCode());
	}
}
