package com.kizna.htmlTests;

import com.kizna.html.scanners.HTMLLinkScanner;
import com.kizna.html.tags.HTMLLinkTag;

import java.util.Enumeration;
import com.kizna.html.HTMLReader;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLRemarkNode;
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
public void testRemarkNodeBug() 
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
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 8 nodes identified",new Integer(8),new Integer(i));
	// The first node should be a HTMLRemarkNode
	assertTrue("First node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
	HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
	assertEquals("Text of the remarkNode #1"," saved from url=(0022)http://internet.e-mail ",remarkNode.getText());	
	// The sixth node should be a HTMLRemarkNode 
	assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
	remarkNode = (HTMLRemarkNode)node[5];
	assertEquals("Text of the remarkNode #6","   Whats gonna happen now ?",remarkNode.getText());
}
/**
 * Insert the method's description here.
 * Creation date: (5/6/2002 11:29:51 PM)
 */
public void testToPlainTextString() {
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
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 8 nodes identified",new Integer(8),new Integer(i));
	// The first node should be a HTMLRemarkNode
	assertTrue("First node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
	HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
	assertEquals("Plain Text of the remarkNode #1"," saved from url=(0022)http://internet.e-mail ",remarkNode.toPlainTextString());	
	// The sixth node should be a HTMLRemarkNode 
	assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
	remarkNode = (HTMLRemarkNode)node[5];
	assertEquals("Plain Text of the remarkNode #6","   Whats gonna happen now ?",remarkNode.getText());	
	
}
	public void testToRawString() {
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
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 8 nodes identified",new Integer(8),new Integer(i));
		// The first node should be a HTMLRemarkNode
		assertTrue("First node should be a HTMLRemarkNode",node[0] instanceof HTMLRemarkNode);
		HTMLRemarkNode remarkNode = (HTMLRemarkNode)node[0];
		assertEquals("Raw String of the remarkNode #1","<!--\n saved from url=(0022)http://internet.e-mail \n-->",remarkNode.toRawString());	
		// The sixth node should be a HTMLRemarkNode 
		assertTrue("Sixth node should be a HTMLRemarkNode",node[5] instanceof HTMLRemarkNode);
		remarkNode = (HTMLRemarkNode)node[5];
		assertEquals("Raw String of the remarkNode #6","<!--\n   Whats gonna happen now ?\n-->",remarkNode.toRawString());			
	}
}
