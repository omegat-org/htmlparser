// HTMLParser Library v1_3_20030413 - A java-based parser for HTML
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
import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.Parser;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

public class TagScannerTest extends ParserTestCase
{

	public TagScannerTest(String name) {
		super(name);
	}

	public void testAbsorbLeadingBlanks()
	{
		String test = "   This is a test";
		String result = TagScanner.absorbLeadingBlanks(test);
		assertEquals("Absorb test","This is a test",result);
	}

	public void testExtractXMLData() throws ParserException {
		createParser(
			"<MESSAGE>\n"+
			"Abhi\n"+
			"Sri\n"+
			"</MESSAGE>"); 
		Parser.setLineSeparator("\r\n");
		NodeIterator e = parser.elements(); 
	
		Node node = e.nextNode();
		try {
			String result = TagScanner.extractXMLData(node,"MESSAGE",parser.getReader());
			assertEquals("Result","Abhi\r\nSri\r\n",result);
		}
		catch (ParserException ex) {
			assertTrue(e.toString(),false);
		}		
	}

	public void testExtractXMLDataSingle() throws ParserException {
		createParser(
			"<MESSAGE>Test</MESSAGE>");
		NodeIterator e = parser.elements(); 
	
		Node node = (Node)e.nextNode();
		try {
			String result = TagScanner.extractXMLData(node,"MESSAGE",parser.getReader());
			assertEquals("Result","Test",result);
		}
		catch (ParserException ex) {
			assertTrue(e.toString(),false);
		}
	}

	public void testTagExtraction()
	{
		String testHTML = "<AREA \n coords=0,0,52,52 href=\"http://www.yahoo.com/r/c1\" shape=RECT>";
		createParser(testHTML);
		Tag tag = Tag.find(parser.getReader(),testHTML,0);
		assertNotNull(tag);
	}

	/**
	 * Captures bug reported by Raghavender Srimantula
	 * Problem is in isXMLTag - when it uses equals() to 
	 * find a match
	 */
	public void testIsXMLTag() throws ParserException {
		createParser("<OPTION value=\"#\">Select a destination</OPTION>");
		Node node;
		NodeIterator e = parser.elements();
		node = (Node)e.nextNode();
		assertTrue("OPTION tag could not be identified",TagScanner.isXMLTagFound(node,"OPTION"));
	}

	public void testRemoveChars() {
		String test = "hello\nworld\n\tqsdsds";
		TagScanner scanner = new TagScanner() { 
			public Tag scan(Tag tag,String url,NodeReader reader,String currLine) { return null;}
			public boolean evaluate(String s,TagScanner previousOpenScanner) { return false; }
			public String []getID() {
				
				return null;
			}
		};
		String result = ParserUtils.removeChars(test,'\n');
		assertEquals("Removing Chars","helloworld\tqsdsds",result);
	}
	
	public void testRemoveChars2() {
		String test = "hello\r\nworld\r\n\tqsdsds";
		TagScanner scanner = new TagScanner() { 
			public Tag scan(Tag tag,String url,NodeReader reader,String currLine) { return null;}
			public boolean evaluate(String s,TagScanner previousOpenScanner) { return false; }
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
		String result=TagScanner.absorbLeadingBlanks(testData);
		assertEquals("",result);
	}

}
