// HTMLParser Library v1_2_20020804 - A java-based parser for HTML
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

package com.kizna.htmlTests;

import com.kizna.html.scanners.HTMLLinkScanner;
import com.kizna.html.tags.HTMLLinkTag;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;


import com.kizna.html.HTMLReader;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLRemarkNode;
import com.kizna.html.HTMLStringNode;

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
	new junit.awtui.TestRunner().start(new String[] {"com.kizna.htmlTests.HTMLRemarkNodeTest"});
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
	HTMLParser parser = new HTMLParser(reader);
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
	assertEquals("Text of the remarkNode #6","\n   Whats gonna happen now ?\n",remarkNode.getText());
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
	HTMLParser parser = new HTMLParser(reader);
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
	assertEquals("Plain Text of the remarkNode #6","\n   Whats gonna happen now ?\n",remarkNode.getText());	
	
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
		HTMLParser parser = new HTMLParser(reader);
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
		assertEquals("Raw String of the remarkNode #1","<!--\n saved from url=(0022)http://internet.e-mail \n-->",remarkNode.toHTML());	
		// The sixth node should be a HTMLRemarkNode 
		assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
		remarkNode = (HTMLRemarkNode)node[5];
		assertEquals("Raw String of the remarkNode #6","<!--\n\n   Whats gonna happen now ?\n\n-->",remarkNode.toHTML());			
	}
	public void testNonRemarkNode() throws HTMLParserException {
		String testHTML = new String("&nbsp;<![endif]>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader);
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
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 nodes identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertEquals("Expected contents","\n",remarkNode.getText());
		
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
		HTMLParser parser = new HTMLParser(reader);
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
}
