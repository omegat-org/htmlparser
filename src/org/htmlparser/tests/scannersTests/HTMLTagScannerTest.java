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


package org.htmlparser.tests.scannersTests;
import java.util.Enumeration;
import java.io.*;
import org.htmlparser.scanners.HTMLTagScanner;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.htmlparser.HTMLNode;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
/**
 * Insert the type's description here.
 * Creation date: (6/18/2001 2:08:51 AM)
 * @author: Administrator
 */
public class HTMLTagScannerTest extends junit.framework.TestCase 
{
	/**
	 * HTMLTagScannerTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLTagScannerTest(String name) {
		super(name);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:22:36 AM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLTagScannerTest.class);
		return suite;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:09:20 AM)
	 */
	public void testAbsorbLeadingBlanks()
	{
		String test = "   This is a test";
		String result = HTMLTagScanner.absorbLeadingBlanks(test);
		assertEquals("Absorb test","This is a test",result);
	}
	public void testExtractXMLData() throws HTMLParserException {
		String testHTML = new String(
			"<MESSAGE>\n"+
			"Abhi\n"+
			"Sri\n"+
			"</MESSAGE>"); 
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader = new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\r\n");
		HTMLEnumeration e = parser.elements(); 
	
		HTMLNode node = e.nextHTMLNode();
		try {
			String result = HTMLTagScanner.extractXMLData(node,"MESSAGE",reader);
			assertEquals("Result","Abhi\r\nSri\r\n",result);
		}
		catch (HTMLParserException ex) {
			assertTrue(e.toString(),false);
		}		
	}
	public void testExtractXMLDataSingle() throws HTMLParserException {
		String testHTML = new String(
			"<MESSAGE>Test</MESSAGE>");
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader = new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLEnumeration e = parser.elements(); 
	
		HTMLNode node = (HTMLNode)e.nextHTMLNode();
		try {
			String result = HTMLTagScanner.extractXMLData(node,"MESSAGE",reader);
			assertEquals("Result","Test",result);
		}
		catch (HTMLParserException ex) {
			assertTrue(e.toString(),false);
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/28/2001 6:05:52 PM)
	 */
	public void testTagExtraction()
	{
		String testHTML = new String("<AREA \n coords=0,0,52,52 href=\"http://www.yahoo.com/r/c1\" shape=RECT>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLTag tag = HTMLTag.find(reader,testHTML,0);
			
		assertNotNull(tag);
	
	}
	/**
	 * Captures bug reported by Raghavender Srimantula
	 * Problem is in isXMLTag - when it uses equals() to 
	 * find a match
	 */
	public void testIsXMLTag() throws HTMLParserException {
		String testHTML = "<OPTION value=\"#\">Select a destination</OPTION>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode node;
		HTMLEnumeration e = parser.elements();
		node = (HTMLNode)e.nextHTMLNode();
		assertTrue("OPTION tag could not be identified",HTMLTagScanner.isXMLTagFound(node,"OPTION"));
	}
	public void testRemoveChars() {
		String test = "hello\nworld\n\tqsdsds";
		HTMLTagScanner scanner = new HTMLTagScanner() { 
			public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currLine) { return null;}
			public boolean evaluate(String s,HTMLTagScanner previousOpenScanner) { return false; }
			public String []getID() {
				
				return null;
			}
		};
		String result = scanner.removeChars(test,'\n');
		assertEquals("Removing Chars","helloworld\tqsdsds",result);
	}
	public void testRemoveChars2() {
		String test = "hello\r\nworld\r\n\tqsdsds";
		HTMLTagScanner scanner = new HTMLTagScanner() { 
			public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currLine) { return null;}
			public boolean evaluate(String s,HTMLTagScanner previousOpenScanner) { return false; }
			public String [] getID() {
				return null;
			}
	
		};
		String result = scanner.removeChars(test,"\r\n");
		assertEquals("Removing Chars","helloworld\tqsdsds",result);
	}
	/**
	 * Bug report by Cedric Rosa
	 * in absorbLeadingBlanks - crashes if the tag 
	 * is empty
	 */
	public void testAbsorbLeadingBlanksBlankTag() {
		String testData = new String("");
		String result=HTMLTagScanner.absorbLeadingBlanks(testData);
		assertEquals("",result);
	}

}