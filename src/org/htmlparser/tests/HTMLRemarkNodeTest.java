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

import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;


import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;

import java.io.BufferedReader;
import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/17/2001 3:59:52 PM)
 * @author: Administrator
 */
public class HTMLRemarkNodeTest extends TestCase 
{
/**
 * HTMLStringNodeTest constructor comment.
 * @param name java.lang.String
 */
public HTMLRemarkNodeTest(String name) {
	super(name);
}
public static void main(String[] args) {
	new junit.awtui.TestRunner().start(new String[] {"org.htmlparser.tests.HTMLRemarkNodeTest"});
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:36 AM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() 
{
	TestSuite suite = new TestSuite(HTMLRemarkNodeTest.class);
	return suite;
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;!-- saved from url=(0022)http://internet.e-mail --&gt;
 * &lt;HTML&gt;
 * &lt;HEAD&gt;&lt;META name="title" content="Training Introduction"&gt;
 * &lt;META name="subject" content=""&gt;
 * &lt;!--
     Whats gonna happen now ?
 * --&gt;
 * &lt;TEST&gt;
 * &lt;/TEST&gt;
 * 
 * The above line is incorrectly parsed - the remark is not correctly identified.
 * This bug was reported by Serge Kruppa (2002-Feb-08). 
 */
public void testRemarkNodeBug() throws HTMLParserException
{
	String testHTML = new String(
		"<!-- saved from url=(0022)http://internet.e-mail -->\n"+
		"<HTML>\n"+
		"<HEAD><META name=\"title\" content=\"Training Introduction\">\n"+
		"<META name=\"subject\" content=\"\">\n"+
		"<!--\n"+
		"   Whats gonna happen now ?\n"+
		"-->\n"+
		"<TEST>\n"+
		"</TEST>\n");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
	parser.setLineSeparator("\r\n");
	HTMLNode [] node = new HTMLNode[20];
	int i = 0;
	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
	{
		node[i++] = e.nextHTMLNode();
	}
	assertEquals("There should be 8 nodes identified",new Integer(8),new Integer(i));
	// The first node should be a HTMLRemarkNode
	assertTrue("First node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
	HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
	assertEquals("Text of the remarkNode #1"," saved from url=(0022)http://internet.e-mail ",remarkNode.getText());	
	// The sixth node should be a HTMLRemarkNode 
	assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
	remarkNode = (HTMLRemarkNode)node[5];
	assertEquals("Text of the remarkNode #6","\r\n   Whats gonna happen now ?\r\n",remarkNode.getText());
}
/**
 * Insert the method's description here.
 * Creation date: (5/6/2002 11:29:51 PM)
 */
public void testToPlainTextString() throws HTMLParserException {
	String testHTML = new String(
		"<!-- saved from url=(0022)http://internet.e-mail -->\n"+
		"<HTML>\n"+
		"<HEAD><META name=\"title\" content=\"Training Introduction\">\n"+
		"<META name=\"subject\" content=\"\">\n"+
		"<!--\n"+
		"   Whats gonna happen now ?\n"+
		"-->\n"+
		"<TEST>\n"+
		"</TEST>\n");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
	parser.setLineSeparator("\r\n");
	HTMLNode [] node = new HTMLNode[20];
	int i = 0;
	for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
	{
		node[i++] = e.nextHTMLNode();
	}
	assertEquals("There should be 8 nodes identified",new Integer(8),new Integer(i));
	// The first node should be a HTMLRemarkNode
	assertTrue("First node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
	HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
	assertEquals("Plain Text of the remarkNode #1"," saved from url=(0022)http://internet.e-mail ",remarkNode.toPlainTextString());	
	// The sixth node should be a HTMLRemarkNode 
	assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
	remarkNode = (HTMLRemarkNode)node[5];
	assertEquals("Plain Text of the remarkNode #6","\r\n   Whats gonna happen now ?\r\n",remarkNode.getText());	
	
}
	public void testToRawString()  throws HTMLParserException {
		String testHTML = new String(
			"<!-- saved from url=(0022)http://internet.e-mail -->\n"+
			"<HTML>\n"+
			"<HEAD><META name=\"title\" content=\"Training Introduction\">\n"+
			"<META name=\"subject\" content=\"\">\n"+
			"<!--\n"+
			"   Whats gonna happen now ?\n"+
			"-->\n"+
			"<TEST>\n"+
			"</TEST>\n");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\r\n");
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 8 nodes identified",new Integer(8),new Integer(i));
		// The first node should be a HTMLRemarkNode
		assertTrue("First node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertEquals("Raw String of the remarkNode #1","<!--\r\n saved from url=(0022)http://internet.e-mail \r\n-->",remarkNode.toHTML());	
		// The sixth node should be a HTMLRemarkNode 
		assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
		remarkNode = (HTMLRemarkNode)node[5];
		assertEquals("Raw String of the remarkNode #6","<!--\r\n\r\n   Whats gonna happen now ?\r\n\r\n-->",remarkNode.toHTML());			
	}
	public void testNonRemarkNode() throws HTMLParserException {
		String testHTML = new String("&nbsp;<![endif]>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 2 nodes identified",new Integer(2),new Integer(i));
		// The first node should be a HTMLRemarkNode
		assertTrue("First node should be a string node",node[0] instanceof HTMLStringNode);
		assertTrue("Second node should be a HTMLTag",node[1] instanceof HTMLTag);
		HTMLStringNode stringNode = (HTMLStringNode)node[0];
		HTMLTag tag = (HTMLTag)node[1];
		assertEquals("Text contents","&nbsp;",stringNode.getText());
		assertEquals("Tag Contents","![endif]",tag.getText());
		
	}
	/**
	 * This is the simulation of bug report 586756, submitted
	 * by John Zook.
	 * If all the comment contains is a blank line, it breaks
	 * the state
	 */	
	public void testRemarkNodeWithBlankLine() throws HTMLParserException {
		String testHTML = new String("<!--\n"+
		"\n"+
		"-->");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\r\n");
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 nodes identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertEquals("Expected contents","\r\n",remarkNode.getText());
		
	}
	/**
	 * This is the simulation of a bug report submitted
	 * by Claude Duguay.
	 * If it is a comment with nothing in it, parser crashes
	 */	
	public void testRemarkNodeWithNothing() throws HTMLParserException {
		String testHTML = new String("<!-->");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 nodes identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertEquals("Expected contents","",remarkNode.getText());
		
	}	
	/**
	 * Reproduction of bug reported by John Zook [594301]
	 * When we have tags like :
	 * &lt;!-- &lt;A&gt; --&gt;
	 * it doesent get parsed correctly
	 */	
	public void testTagWithinRemarkNode() throws HTMLParserException {
		String testHTML = new String("<!-- \n"+
		"<A>\n"+
		"bcd -->");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\n");
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertStringEquals("Expected contents"," \n<A>\nbcd ",remarkNode.getText());
				
	}
	/**
	 * Bug reported by John Zook [594301], invalid remark nodes are accepted as remark nodes.
	 * &lt;<br>
	 * -<br>
	 * -<br>
	 * ssd --&gt;<br>
	 * This is not supposed to be a remarknode
	 */
	public void testInvalidTag() throws HTMLParserException {
		String testHTML = new String("<!\n"+
		"-\n"+
		"-\n"+
		"ssd -->");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		parser.setLineSeparator("\n");
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a HTMLTag but was "+node[0],node[0] instanceof HTMLTag);
		HTMLTag tag = (HTMLTag)node[0];
		assertStringEquals("Expected contents","!\n"+
		"-\n"+
		"-\n"+
		"ssd --",tag.getText());
		parser.setLineSeparator("\r\n");
	}
	/**
	 * Bug reported by John Zook [594301]
	 * If dashes exist in a comment, they dont get added to the comment text
	 */
	public void testDashesInComment() throws HTMLParserException{
		String testHTML = new String("<!-- -- -->");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a HTMLRemarkNode but was "+node[0],node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertEquals("Remark Node contents"," -- ",remarkNode.getText());
	}
	public void assertStringEquals(String message,String s1,String s2) {
		for (int i=0;i<s1.length();i++) {
			if (s1.charAt(i)!=s2.charAt(i)) {
				assertTrue(message+
					" \nMismatch of strings at char posn "+i+
					" \nString 1 upto mismatch = "+s1.substring(0,i)+
					" \nString 2 upto mismatch = "+s2.substring(0,i)+
					" \nString 1 mismatch character = "+s1.charAt(i)+", code = "+(int)s1.charAt(i)+
					" \nString 2 mismatch character = "+s2.charAt(i)+", code = "+(int)s2.charAt(i)+
					" \nComplete String 1 = "+s1+
					" \nComplete String 2 = "+s2,false);
			}
		}
	}    
	
	
}