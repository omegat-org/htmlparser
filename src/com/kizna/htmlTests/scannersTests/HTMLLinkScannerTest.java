// HTMLParser Library v1_2_20020623 - A java-based parser for HTML
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
import com.kizna.html.tags.HTMLLinkTag;
import com.kizna.html.HTMLStringNode;
import java.util.Enumeration;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import java.io.StringReader;
import com.kizna.html.scanners.HTMLLinkScanner;
import com.kizna.html.scanners.HTMLImageScanner;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/18/2001 2:20:43 AM)
 * @author: Administrator
 */
public class HTMLLinkScannerTest extends junit.framework.TestCase 
{
	/**
	 * HTMLAppletScannerTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLLinkScannerTest(String name) {
		super(name);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:22:36 AM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLLinkScannerTest.class);
		return suite;
	}
	public void testAccessKey() {
		String testHTML = new String("<a href=\"http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph\" accessKey=1>Click Here</A>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader);
		parser.addScanner(new HTMLLinkScanner("-l"));
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 node identified",1,i);
		assertTrue("The node should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link URL of link tag","http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph",linkTag.getLink());
		assertEquals("Link Text of link tag","Click Here",linkTag.getLinkText());
		assertEquals("Access key","1",linkTag.getAccessKey());	
	}
	public void testErroneousLinkBug() {
		String testHTML = new String("<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us<a></p>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader);
		parser.registerScanners();
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 5 nodes identified",5,i);
		// The first node should be a HTMLTag 
		assertTrue("First node should be a HTMLTag",node[0] instanceof HTMLTag);
		// The second node should be a HTMLStringNode
		assertTrue("Second node should be a HTMLStringNode",node[1] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[1];
		assertEquals("Text of the StringNode","Site Comments?",stringNode.getText());
		assertTrue("Third node should be a tag",node[2] instanceof HTMLTag);
	
	}
	/**
	 * Test case based on a report by Raghavender Srimantula, of the parser giving out of memory exceptions. Found to occur
	 * on the following piece of html
	 * <pre>
	 * <a href=s/8741><img src="http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif" height=16 width=16 border=0></img></td><td nowrap> &nbsp;
	 * <a href=s/7509>
	 * </pre>
	 */
	public void testErroneousLinkBugFromYahoo2() {
		String testHTML = new String("<a href=s/8741><img src=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" height=16 width=16 border=0></img></td><td nowrap> &nbsp;\n"+
		"<a href=s/7509><b>Yahoo! Movies</b></a>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		parser.registerScanners();
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 5 nodes identified",5,i);
		// The first node should be a HTMLTag 
		assertTrue("First node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		// The second node should be a HTMLStringNode
		assertTrue("Fifth node should be a HTMLLinkTag",node[4] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link","http://www.yahoo.com/s/8741",linkTag.getLink());
		// Verify the link data
		assertEquals("Link Text","",linkTag.getLinkText());
		// Verify the reconstruction html
		assertEquals("Raw String","<a href=s/8741><img src=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" height=16 width=16 border=0></A>",linkTag.toHTML());
		// Verify the tags in between
		assertTrue("Second node should be an end tag",node[1] instanceof HTMLEndTag);
		assertTrue("Third node should be an HTMLTag",node[2] instanceof HTMLTag);		
		assertTrue("Fourth node should be a string node",node[3] instanceof HTMLStringNode);
		
		// Verify their contents
		HTMLEndTag endTag = (HTMLEndTag)node[1];
		assertEquals("Second node","td",endTag.getText());
		HTMLTag tag = (HTMLTag)node[2];
		assertEquals("Third node","td nowrap",tag.getText());
		HTMLStringNode stringNode = (HTMLStringNode)node[3];
		assertEquals("Fourth node"," &nbsp;\r\n",stringNode.getText());	
	}
	
	/**
	 * Test case based on a report by Raghavender Srimantula, of the parser giving out of memory exceptions. Found to occur
	 * on the following piece of html
	 * <pre>
	 * <a href=s/8741><img src="http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif" height=16 width=16 border=0></img>This is test
	 * <a href=s/7509>
	 * </pre>
	 */
	public void testErroneousLinkBugFromYahoo() {
		String testHTML = new String("<a href=s/8741><img src=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" height=16 width=16 border=0></img>This is a test\n"+
		"<a href=s/7509><b>Yahoo! Movies</b></a>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		parser.registerScanners();
		HTMLNode [] node = new HTMLNode[10];
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 2 nodes identified",2,i);
		// The first node should be a HTMLTag 
		assertTrue("First node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		// The second node should be a HTMLStringNode
		assertTrue("Second node should be a HTMLLinkTag",node[1] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link","http://www.yahoo.com/s/8741",linkTag.getLink());
		// Verify the link data
		assertEquals("Link Text","This is a test\r\n",linkTag.getLinkText());
		// Verify the reconstruction html
		assertEquals("Raw String","<a href=s/8741><img src=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" height=16 width=16 border=0>This is a test\r\n</A>",linkTag.toHTML());
	}
	
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:23:14 AM)
	 */
	public void testEvaluate() 
	{
		HTMLLinkScanner scanner = new HTMLLinkScanner("-l");
		boolean retVal = scanner.evaluate("   a href ",null);
		assertEquals("Evaluation of the Link tag",new Boolean(true),new Boolean(retVal));
	}
	/**
	 * This is the reproduction of a bug which causes a null pointer exception
	 * Creation date: (6/18/2001 2:26:41 AM)
	 */
	public void testExtractLinkInvertedCommasBug()
	{
		String tagContents = "a href=r/anorth/top.html";
		HTMLTag tag = new HTMLTag(0,0,tagContents,"");
		String url = "c:\\cvs\\html\\binaries\\yahoo.htm";
		HTMLLinkScanner scanner = new HTMLLinkScanner("-l");
		assertEquals("Extracted Link","r/anorth/top.html",scanner.extractLink(tag,url));
	}
	/**
	 * Bug pointed out by Sam Joseph (sam@neurogrid.net)
	 * Links with spaces in them will get their spaces absorbed
	 */
	
	public void testLinkSpacesBug() {
		String testHTML = new String("<a href=\"http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph\">Click Here</A>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader);
		parser.addScanner(new HTMLLinkScanner("-l"));
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 node identified",1,i);
		assertTrue("The node should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link URL of link tag","http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph",linkTag.getLink());
		assertEquals("Link Text of link tag","Click Here",linkTag.getLinkText());
	}
	/**
	 * Bug reported by Raj Sharma,5-Apr-2002, upon parsing
	 * http://www.samachar.com, the entire page could not be picked up.
	 * The problem was occurring after parsing a particular link
	 * after which the parsing would not proceed. This link was spread over three lines.
	 * The bug has been reproduced and fixed.
	 */
	public void testMultipleLineBug() {
		String testHTML = new String("<LI><font color=\"FF0000\" size=-1><b>Tech Samachar:</b></font> <a \n"+
		"href=\"http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/tech\n"+
		"nical.html\"> Journalism 3.0</a> by Rajesh Jain");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader);
		parser.addScanner(new HTMLLinkScanner("-l"));
		HTMLNode [] node = new HTMLNode[20];
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 8 nodes identified",8,i);
		assertTrue("Seventh node should be a link tag",node[6] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[6];
		assertEquals("Link URL of link tag","http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/technical.html",linkTag.getLink());
		assertEquals("Link Text of link tag"," Journalism 3.0",linkTag.getLinkText());
		assertTrue("Eight node should be a string node",node[7] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[7];
		assertEquals("String node contents"," by Rajesh Jain",stringNode.getText());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testRelativeLinkScan() {
		String testHTML = "<A HREF=\"mytest.html\"> Hello World</A>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLTag)e.nextElement();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/mytest.html",linkTag.getLink());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testRelativeLinkScan2() {
		String testHTML = "<A HREF=\"abc/def/mytest.html\"> Hello World</A>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLTag)e.nextElement();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mytest.html",linkTag.getLink());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testRelativeLinkScan3() {
		String testHTML = "<A HREF=\"../abc/def/mytest.html\"> Hello World</A>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLTag)e.nextElement();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mytest.html",linkTag.getLink());
	}
	/**
	 * Test scan with data which is of diff nodes type
	 * Creation date: (7/1/2001 3:53:39 PM)
	 */
	public void testScan() 
	{
		String testHTML = "<A HREF=\"mytest.html\"> <IMG SRC=\"abcd.jpg\">Hello World</A>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		parser.addScanner(new HTMLImageScanner("-i"));	
			
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
		assertTrue("Node should be a link node",node[0] instanceof HTMLLinkTag);
	
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		// Get the link data and cross-check
		HTMLNode [] dataNode= new HTMLNode[10];
		i = 0;
		for (Enumeration e = linkTag.linkData();e.hasMoreElements();)
		{
			dataNode[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("Number of data nodes",new Integer(2),new Integer(i));
		assertTrue("First data node should be an Image Node",dataNode[0] instanceof HTMLImageTag);
		assertTrue("Second data node shouls be a String Node",dataNode[1] instanceof HTMLStringNode);
	
		// Check the contents of each data node
		HTMLImageTag imageTag = (HTMLImageTag)dataNode[0];
		assertEquals("Image URL","http://www.yahoo.com/abcd.jpg",imageTag.getImageLocation());
		HTMLStringNode stringNode = (HTMLStringNode)dataNode[1];
		assertEquals("String Contents","Hello World",stringNode.getText());
	}
	public void testReplaceFaultyTagWithEndTag() {
		String currentLine = "<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us<a></p>";
		HTMLTag tag = new HTMLTag(85,87,"a",currentLine);
		HTMLLinkScanner linkScanner = new HTMLLinkScanner();
		String newLine = linkScanner.replaceFaultyTagWithEndTag(tag,currentLine);
		assertEquals("Expected replacement","<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us</A></p>",newLine);
	}
	public void testInsertEndTagBeforeTag() {
		String currentLine = "<a href=s/7509><b>Yahoo! Movies</b></a>";
		HTMLTag tag = new HTMLTag(0,14,"a href=s/7509",currentLine);
		HTMLLinkScanner linkScanner = new HTMLLinkScanner();
		String newLine = linkScanner.insertEndTagBeforeNode(tag,currentLine);
		assertEquals("Expected insertion","</A><a href=s/7509><b>Yahoo! Movies</b></a>",newLine);
	}
	/**
	 * A bug in the freshmeat page - really bad html 
	 * tag - &lt;A&gt;Revision&lt;\a&gt;
	 * Reported by Mazlan Mat
	 */
	public void testFreshMeatBug() {
		String testHTML = "<a>Revision</a>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 3 nodes identified",new Integer(3),new Integer(i));
		assertTrue("Node 0 should be a tag",node[0] instanceof HTMLTag);
		HTMLTag tag = (HTMLTag)node[0];
		assertEquals("Tag Contents","a",tag.getText());
		assertTrue("Node 1 should be a string node",node[1] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[1];
		assertEquals("StringNode Contents","Revision",stringNode.getText());
		assertTrue("Node 2 should be a string node",node[2] instanceof HTMLEndTag);
		HTMLEndTag endTag = (HTMLEndTag)node[2];
		assertEquals("End Tag Contents","a",endTag.getText());
	}
	/** 
	 * Test suggested by Cedric Rosa
	 * A really bad link tag sends parser into infinite loop
	 */
	public void testBrokenLink() {
		String testHTML = "<a href=\"faq.html\"><br>\n"+
        "<img src=\"images/46revues.gif\" width=\"100\" height=\"46\" border=\"0\" alt=\"Rejoignez revues.org!\" align=\"middle\"";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
				
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 nodes identified",new Integer(1),new Integer(i));
		assertTrue("Node 0 should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertNotNull(linkTag.toString());
	}	
}
