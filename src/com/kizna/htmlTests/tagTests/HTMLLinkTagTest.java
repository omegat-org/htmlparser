package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.util.Enumeration;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.scanners.*;

import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/17/2001 3:59:52 PM)
 * @author: Administrator
 */
public class HTMLLinkTagTest extends TestCase
{
/**
 * HTMLStringNodeTest constructor comment.
 * @param name java.lang.String
 */
public HTMLLinkTagTest(String name) {
	super(name);
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:36 AM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() 
{
	TestSuite suite = new TestSuite(HTMLLinkTagTest.class);
	return suite;
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
 * vLink=#551a8b&gt;
 * The above line is incorrectly parsed in that, the BODY tag is not identified.
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testLinkNodeBug()  
{
	String testHTML = new String("<A HREF=\"../test.html\">abcd</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
	assertEquals("The image locn","http://www.google.com/test.html",linkNode.getLink());
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
 * vLink=#551a8b&gt;
 * The above line is incorrectly parsed in that, the BODY tag is not identified.
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testLinkNodeBug2() 
{
	String testHTML = new String("<A HREF=\"../../test.html\">abcd</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
	assertEquals("The image locn","http://www.google.com/test.html",linkNode.getLink());
}
/**
 * The bug being reproduced is this : <BR>
 * When a url ends with a slash, and the link begins with a slash,the parser puts two slashes
 * This bug was submitted by Roget Kjensrud
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testLinkNodeBug3() 
{
	String testHTML = new String("<A HREF=\"/mylink.html\">abcd</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
	assertEquals("Link incorrect","http://www.cj.com/mylink.html",linkNode.getLink());
}
/**
 * The bug being reproduced is this : <BR>
 * Simple url without index.html, doesent get appended to link
 * This bug was submitted by Roget Kjensrud
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testLinkNodeBug4() 
{
	String testHTML = new String("<A HREF=\"/mylink.html\">abcd</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
	assertEquals("Link incorrect!!","http://www.cj.com/mylink.html",linkNode.getLink());
}
/**
 * Insert the method's description here.
 * Creation date: (8/2/2001 2:38:26 AM)
 */
public void testLinkNodeBug5() 
{
	String testHTML = new String("<a href=http://note.kimo.com.tw/>���O</a>&nbsp; <a \n"+
	"href=http://photo.kimo.com.tw/>��ï</a>&nbsp; <a\n"+
	"href=http://address.kimo.com.tw/>�q�T��</a>&nbsp;&nbsp;");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
			
	assertEquals("There should be 6 nodes identified",new Integer(6),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[2];
	assertEquals("Link incorrect!!","http://photo.kimo.com.tw/",linkNode.getLink());
	assertEquals("Link beginning",new Integer(48),new Integer(linkNode.elementBegin()));
	assertEquals("Link ending",new Integer(38),new Integer(linkNode.elementEnd()));

		HTMLLinkTag linkNode2 = (HTMLLinkTag)node[4];
	assertEquals("Link incorrect!!","http://address.kimo.com.tw/",linkNode2.getLink());
	assertEquals("Link beginning",new Integer(46),new Integer(linkNode2.elementBegin()));
	assertEquals("Link ending",new Integer(42),new Integer(linkNode2.elementEnd()));
}
/**
 * This bug occurs when there is a null pointer exception thrown while scanning a tag using HTMLLinkScanner.
 * Creation date: (7/1/2001 2:42:13 PM)
 */
public void testLinkNodeBugNullPointerException() 
{
	String testHTML = new String("<FORM action=http://search.yahoo.com/bin/search name=f><MAP name=m><AREA\n"+ 
		"coords=0,0,52,52 href=\"http://www.yahoo.com/r/c1\" shape=RECT><AREA"+
		"coords=53,0,121,52 href=\"http://www.yahoo.com/r/p1\" shape=RECT><AREA"+
		"coords=122,0,191,52 href=\"http://www.yahoo.com/r/m1\" shape=RECT><AREA"+
		"coords=441,0,510,52 href=\"http://www.yahoo.com/r/wn\" shape=RECT>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 6 nodes identified",new Integer(6),new Integer(i));
}
/**
 * This bug occurs when there is a null pointer exception thrown while scanning a tag using HTMLLinkScanner.
 * Creation date: (7/1/2001 2:42:13 PM)
 */
public void testLinkNodeMailtoBug() 
{
	String testHTML = new String("<A HREF='mailto:somik@yahoo.com'>hello</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
	assertEquals("Link incorrect","somik@yahoo.com",linkNode.getLink());
	assertEquals("Link Type",new Boolean(true),new Boolean(linkNode.isMailLink()));
}
/**
 * This bug occurs when there is a null pointer exception thrown while scanning a tag using HTMLLinkScanner.
 * Creation date: (7/1/2001 2:42:13 PM)
 */
public void testLinkNodeSingleQuoteBug() 
{
	String testHTML = new String("<A HREF='abcd.html'>hello</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
	assertEquals("Link incorrect","http://www.cj.com/abcd.html",linkNode.getLink());	
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
 * vLink=#551a8b&gt;
 * The above line is incorrectly parsed in that, the BODY tag is not identified.
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testLinkTag() 
{
	String testHTML = new String("<A HREF=\"test.html\">abcd</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag LinkTag = (HTMLLinkTag)node[0];
	assertEquals("The image locn","http://www.google.com/test/test.html",LinkTag.getLink());
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
 * vLink=#551a8b&gt;
 * The above line is incorrectly parsed in that, the BODY tag is not identified.
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testLinkTagBug() 
{
	String testHTML = new String("<A HREF=\"../test.html\">abcd</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag LinkTag = (HTMLLinkTag)node[0];
	assertEquals("The image locn","http://www.google.com/test.html",LinkTag.getLink());
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;A HREF=&gt;Something&lt;A&gt;<BR>
 * vLink=#551a8b&gt;
 * The above line is incorrectly parsed in that, the BODY tag is not identified.
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testNullTagBug() 
{
	String testHTML = new String("<A HREF=>Something</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLLinkTag
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
	assertEquals("The link location","",linkTag.getLink());
	assertEquals("The link text","Something",linkTag.getLinkText());
}
public void testToPlainTextString() {
	String testHTML = new String("<A HREF='mailto:somik@yahoo.com'>hello</A>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLLinkScanner("-l"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
	HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
	assertEquals("Link Plain Text","hello",linkTag.toPlainTextString());	
}
	public void testToRawString() {
		String testHTML = new String("<A HREF='mailto:somik@yahoo.com'>hello</A>\n"+
			"<LI><font color=\"FF0000\" size=-1><b>Tech Samachar:</b></font> <a \n"+
			"href=\"http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/tech\n"+
			"nical.html\"> Journalism 3.0</a> by Rajesh Jain");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 node identified",new Integer(9),new Integer(i));
		assertTrue("First Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link Raw Text","<A HREF='mailto:somik@yahoo.com'>hello</A>",linkTag.toRawString());
		assertTrue("Eighth Node should be a HTMLLinkTag",node[7] instanceof HTMLLinkTag);
		linkTag = (HTMLLinkTag)node[7];
		assertEquals("Link Raw Text","<a \n"+
			"href=\"http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/tech\n"+
			"nical.html\"> Journalism 3.0</A>",linkTag.toRawString());
	}
}
