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
public void testDirtyHTMLEvaluate() {
	HTMLLinkScanner scanner = new HTMLLinkScanner();
	boolean retVal = scanner.evaluate("A HREF",scanner);
	assertEquals("Link found, when previous link scanner was open",true,retVal);
	assertEquals("Dirty",true,scanner.isPreviousLinkScannerOpen());
}
public void testErroneousLinkBug() {
	String testHTML = new String("<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us<a></p>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader);
	parser.addScanner(new HTMLLinkScanner("-l"));
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
public void testErroneousLinkBugFromYahoo() {
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
	assertTrue("Second node should be a HTMLEndTag",node[1] instanceof HTMLEndTag);
	assertTrue("Fifth node should be HTMLLiknTag",node[4] instanceof HTMLLinkTag);
	HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
	assertEquals("Link","http://www.yahoo.com/s/8741",linkTag.getLink());
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
}
