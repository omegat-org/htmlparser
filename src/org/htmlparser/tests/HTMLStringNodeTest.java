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


package org.htmlparser.tests;
import java.io.BufferedReader;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;


import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;
import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/17/2001 3:59:52 PM)
 * @author: Administrator
 */
public class HTMLStringNodeTest extends TestCase {
	/**
	 * HTMLStringNodeTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLStringNodeTest(String name) {
		super(name);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:22:36 AM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLStringNodeTest.class);
		return suite;
	}
	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;HTML&gt;&lt;HEAD&gt;&lt;TITLE&gt;Google&lt;/TITLE&gt; <BR>
	 * The above line is incorrectly parsed in that, the text Google is missed.
	 * The presence of this bug is typically when some tag is identified before the string node is. (usually seen
	 * with the end tag). The bug lies in HTMLReader.readElement().
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testStringNodeBug1() throws HTMLParserException {
		String testHTML = new String("<HTML><HEAD><TITLE>Google</TITLE>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 5 nodes identified",new Integer(5),new Integer(i));
		// The fourth node should be a HTMLStringNode-  with the text - Google
		assertTrue("Fourth node should be a HTMLStringNode",node[3] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[3];
		assertEquals("Text of the StringNode","Google",stringNode.getText());
	}
	/**
	 * Bug reported by Kaarle Kaila of Nokia<br>
	 * For the following HTML :
	 * view these documents, you must have &lt;A href='http://www.adobe.com'&gt;Adobe <br>
	 * Acrobat Reader&lt;/A&gt; installed on your computer.<br>
	 * The first string before the link is not identified, and the space after the link is also not identified
	 * Creation date: (8/2/2001 2:07:32 AM)
	 */
	public void testStringNodeBug2() throws HTMLParserException {
		// Register the link scanner
		
		String testHTML = new String("view these documents, you must have <A href='http://www.adobe.com'>Adobe \n"+
			"Acrobat Reader</A> installed on your computer.");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\r\n");
		parser.addScanner(new HTMLLinkScanner("-l"));
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 3 nodes identified",new Integer(3),new Integer(i));
		// The first node should be a HTMLStringNode-  with the text - view these documents, you must have 
		assertTrue("First node should be a HTMLStringNode",node[0] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[0];
		assertEquals("Text of the StringNode","view these documents, you must have ",stringNode.getText());
		assertTrue("Second node should be a link node",node[1] instanceof HTMLLinkTag);
		HTMLLinkTag linkNode = (HTMLLinkTag)node[1];
		assertEquals("Link is","http://www.adobe.com",linkNode.getLink());
		assertEquals("Link text is","Adobe \r\nAcrobat Reader",linkNode.getLinkText());
	
		assertTrue("Third node should be a string node",node[2] instanceof HTMLStringNode);
		HTMLStringNode stringNode2 = (HTMLStringNode)node[2];
		assertEquals("Contents of third node"," installed on your computer.",stringNode2.getText());
	}
	/**
	 * Bug reported by Roger Sollberger<br>
	 * For the following HTML :
	 * &lt;a href="http://asgard.ch"&gt;[&lt; ASGARD &gt;&lt;/a&gt;&lt;br&gt;
	 * The string node is not correctly identified
	 */
	
	public void testTagCharsInStringNode() throws HTMLParserException {
		String testHTML = new String("<a href=\"http://asgard.ch\">[> ASGARD <]</a>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.addScanner(new HTMLLinkScanner("-l"));
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 nodes identified",new Integer(1),new Integer(i));
		assertTrue("Node identified must be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];
		assertEquals("[> ASGARD <]",linkTag.getLinkText());
		assertEquals("http://asgard.ch",linkTag.getLink());
	}
	
	/**
	 * Insert the method's description here.
	 * Creation date: (5/6/2002 11:25:26 PM)
	 */
	public void testToPlainTextString() throws HTMLParserException {
		String testHTML = new String("<HTML><HEAD><TITLE>This is the Title</TITLE></HEAD><BODY>Hello World, this is the HTML Parser</BODY></HTML>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 10 nodes identified",new Integer(10),new Integer(i));
		assertTrue("Fourth Node identified must be a string node",node[3] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[3];
		assertEquals("First String Node","This is the Title",stringNode.toPlainTextString());
		assertTrue("Eighth Node identified must be a string node",node[7] instanceof HTMLStringNode);
		stringNode = (HTMLStringNode)node[7];
		assertEquals("Second string node","Hello World, this is the HTML Parser",stringNode.toPlainTextString());
	}
	
	/**
	 * Insert the method's description here.
	 * Creation date: (5/6/2002 11:25:26 PM)
	 */
	public void testToHTML() throws HTMLParserException {
		String testHTML = new String("<HTML><HEAD><TITLE>This is the Title</TITLE></HEAD><BODY>Hello World, this is the HTML Parser</BODY></HTML>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 10 nodes identified",new Integer(10),new Integer(i));
		assertTrue("Fourth Node identified must be a string node",node[3] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[3];
		assertEquals("First String Node","This is the Title",stringNode.toHTML());
		assertTrue("Eighth Node identified must be a string node",node[7] instanceof HTMLStringNode);
		stringNode = (HTMLStringNode)node[7];
		assertEquals("Second string node","Hello World, this is the HTML Parser",stringNode.toHTML());
	}
	public void testEmptyLines() throws HTMLParserException {
		String testHTML = new String(
		"David Nirenberg (Center for Advanced Study in the Behavorial Sciences, Stanford).<br>\n"+
		"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      \n"+
		"<br>"
		);
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 4 nodes identified",new Integer(4),new Integer(i));
		assertTrue("Third Node identified must be a string node",node[2] instanceof HTMLStringNode);
	}
	/**
	 * This is a bug reported by John Zook (586222), where the first few chars
	 * before a remark is being missed, if its on the same line.
	 */
	public void testStringBeingMissedBug() throws HTMLParserException {
		String testHTML = new String(
		"Before Comment <!-- Comment --> After Comment"
		);
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 3 nodes identified",new Integer(3),new Integer(i));
		assertTrue("First node should be HTMLStringNode",node[0] instanceof HTMLStringNode);
		assertTrue("Second node should be HTMLRemarkNode",node[1] instanceof HTMLRemarkNode);
		assertTrue("Third node should be HTMLStringNode",node[2] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[0];
		assertEquals("First String node contents","Before Comment ",stringNode.getText());
		HTMLStringNode stringNode2 = (HTMLStringNode)node[2];
		assertEquals("Second String node contents"," After Comment",stringNode2.getText());
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[1];
		assertEquals("Remark Node contents"," Comment ",remarkNode.getText());
			
	}
	/**
	 * Based on a bug report submitted by Cedric Rosa, if the last line contains a single character,
	 * HTMLStringNode does not return the string node correctly.
	 */
	public void testLastLineWithOneChar() throws HTMLParserException {
		String testHTML = new String("a");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 nodes identified",new Integer(1),new Integer(i));
		assertTrue("First node should be HTMLStringNode",node[0] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[0];
		assertEquals("First String node contents","a",stringNode.getText());
	}
}