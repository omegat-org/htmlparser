// HTMLParser Library v1_2 - A java-based parser for HTML
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

import java.io.BufferedReader;
import java.util.Hashtable;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.tests.tagTests.HTMLTagTest;
import org.htmlparser.HTMLStringNode;
import java.util.Enumeration;
import org.htmlparser.*;
import org.htmlparser.tags.*;
import java.io.StringReader;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.scanners.HTMLImageScanner;
import junit.framework.TestSuite;

public class HTMLLinkScannerTest extends HTMLParserTestCase
{
	public HTMLLinkScannerTest(String name) {
		super(name);
	}

	public void testAccessKey() throws HTMLParserException {
		createParser("<a href=\"http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph\" accessKey=1>Click Here</A>");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("The node should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link URL of link tag","http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph",linkTag.getLink());
		assertEquals("Link Text of link tag","Click Here",linkTag.getLinkText());
		assertEquals("Access key","1",linkTag.getAccessKey());	
	}

	public void testErroneousLinkBug() throws HTMLParserException {
		createParser("<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us<a></p>");
		parser.registerScanners();
		parseAndAssertNodeCount(5);
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
	public void testErroneousLinkBugFromYahoo2() throws HTMLParserException {
		createParser("<a href=s/8741><img src=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" height=16 width=16 border=0></img></td><td nowrap> &nbsp;\n"+
		"<a href=s/7509><b>Yahoo! Movies</b></a>","http://www.yahoo.com");
		parser.registerScanners();
		parseAndAssertNodeCount(5);
		// The first node should be a HTMLTag 
		assertTrue("First node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		// The second node should be a HTMLStringNode
		assertTrue("Fifth node should be a HTMLLinkTag",node[4] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link","http://www.yahoo.com/s/8741",linkTag.getLink());
		// Verify the link data
		assertEquals("Link Text","",linkTag.getLinkText());
		// Verify the reconstruction html
		assertStringEquals("Raw String","<A HREF=\"s/8741\"><IMG BORDER=\"0\" WIDTH=\"16\" SRC=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" HEIGHT=\"16\"></img></A>",linkTag.toHTML());
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
	public void testErroneousLinkBugFromYahoo() throws HTMLParserException {
		createParser("<a href=s/8741><img src=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" height=16 width=16 border=0></img>This is a test\n"+
		"<a href=s/7509><b>Yahoo! Movies</b></a>","http://www.yahoo.com");

		parser.registerScanners();
		parseAndAssertNodeCount(2);
		// The first node should be a HTMLTag 
		assertTrue("First node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		// The second node should be a HTMLStringNode
		assertTrue("Second node should be a HTMLLinkTag",node[1] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link","http://www.yahoo.com/s/8741",linkTag.getLink());
		// Verify the link data
		assertEquals("Link Text","This is a test\r\n",linkTag.getLinkText());
		// Verify the reconstruction html
		assertStringEquals("Raw String","<A HREF=\"s/8741\"><IMG BORDER=\"0\" WIDTH=\"16\" SRC=\"http://us.i1.yimg.com/us.yimg.com/i/i16/mov_popc.gif\" HEIGHT=\"16\"></img>This is a test\r\n</A>",linkTag.toHTML());
	}
	
	public void testEvaluate() 
	{
		HTMLLinkScanner scanner = new HTMLLinkScanner("-l");
		boolean retVal = scanner.evaluate("   a href ",null);
		assertEquals("Evaluation of the Link tag",new Boolean(true),new Boolean(retVal));
	}

	/**
	 * This is the reproduction of a bug which causes a null pointer exception
	 */
	public void testExtractLinkInvertedCommasBug() throws HTMLParserException
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
	public void testLinkSpacesBug() throws HTMLParserException{
		createParser("<a href=\"http://www.kizna.com/servlets/SomeServlet?name=Sam Joseph\">Click Here</A>");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
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
	public void testMultipleLineBug() throws HTMLParserException {
		createParser("<LI><font color=\"FF0000\" size=-1><b>Tech Samachar:</b></font> <a \n"+
		"href=\"http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/tech\n"+
		"nical.html\"> Journalism 3.0</a> by Rajesh Jain");
		parser.setLineSeparator("\r\n");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(8);
		assertTrue("Seventh node should be a link tag",node[6] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[6];
		String exp = new String("http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/technical.html");
		//assertEquals("Length of link tag",exp.length(), linkTag.getLink().length());
		assertStringEquals("Link URL of link tag",exp,linkTag.getLink());
		assertEquals("Link Text of link tag"," Journalism 3.0",linkTag.getLinkText());
		assertTrue("Eight node should be a string node",node[7] instanceof HTMLStringNode);
		HTMLStringNode stringNode = (HTMLStringNode)node[7];
		assertEquals("String node contents"," by Rajesh Jain",stringNode.getText());
	}

	public void testRelativeLinkScan() throws HTMLParserException {
		createParser("<A HREF=\"mytest.html\"> Hello World</A>","http://www.yahoo.com");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/mytest.html",linkTag.getLink());
	}

	public void testRelativeLinkScan2() throws HTMLParserException {
		createParser("<A HREF=\"abc/def/mytest.html\"> Hello World</A>","http://www.yahoo.com");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mytest.html",linkTag.getLink());
	}

	public void testRelativeLinkScan3() throws HTMLParserException {
		createParser("<A HREF=\"../abc/def/mytest.html\"> Hello World</A>","http://www.yahoo.com/ghi");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mytest.html",linkTag.getLink());
	}

	/**
	 * Test scan with data which is of diff nodes type
	 */
	public void testScan() throws HTMLParserException
	{
		createParser("<A HREF=\"mytest.html\"> <IMG SRC=\"abcd.jpg\">Hello World</A>","http://www.yahoo.com");
		// Register the image scanner
		HTMLLinkScanner linkScanner = new HTMLLinkScanner("-l");
		parser.addScanner(linkScanner);
		parser.addScanner(linkScanner.createImageScanner("-i"));	
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a link node",node[0] instanceof HTMLLinkTag);
	
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		// Get the link data and cross-check
		HTMLNode [] dataNode= new HTMLNode[10];
		int i = 0;
		for (Enumeration e = linkTag.linkData();e.hasMoreElements();)
		{
			dataNode[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("Number of data nodes",new Integer(2),new Integer(i));
		assertTrue("First data node should be an Image Node",dataNode[0] instanceof HTMLImageTag);
		assertTrue("Second data node shouls be a String Node",dataNode[1] instanceof HTMLStringNode);
	
		// Check the contents of each data node
		HTMLImageTag imageTag = (HTMLImageTag)dataNode[0];
		assertEquals("Image URL","http://www.yahoo.com/abcd.jpg",imageTag.getImageURL());
		HTMLStringNode stringNode = (HTMLStringNode)dataNode[1];
		assertEquals("String Contents","Hello World",stringNode.getText());
	}

	public void testReplaceFaultyTagWithEndTag() throws HTMLParserException {
		String currentLine = "<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us<a></p>";
		HTMLTag tag = new HTMLTag(85,87,"a",currentLine);
		HTMLLinkScanner linkScanner = new HTMLLinkScanner();
		String newLine = linkScanner.replaceFaultyTagWithEndTag(tag,currentLine);
		assertEquals("Expected replacement","<p>Site Comments?<br><a href=\"mailto:sam@neurogrid.com?subject=Site Comments\">Mail Us</A></p>",newLine);
	}

	public void testInsertEndTagBeforeTag() throws HTMLParserException {
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
	public void testFreshMeatBug() throws HTMLParserException {
		createParser("<a>Revision</a>","http://www.yahoo.com");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(3);
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
	public void testBrokenLink() throws HTMLParserException {
		createParser("<a href=\"faq.html\"><br>\n"+
        "<img src=\"images/46revues.gif\" width=\"100\" height=\"46\" border=\"0\" alt=\"Rejoignez revues.org!\" align=\"middle\"","http://www.yahoo.com");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
				
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertNotNull(linkTag.toString());
	}	

	public void testLinkDataContents() throws HTMLParserException {
		createParser("<a href=\"http://transfer.go.com/cgi/atransfer.pl?goto=http://www.signs.movies.com&name=114332&srvc=nws&context=283&guid=4AD5723D-C802-4310-A388-0B24E1A79689\" target=\"_new\"><img src=\"http://ad.abcnews.com/ad/sponsors/buena_vista_pictures/bvpi-ban0003.gif\" width=468 height=60 border=\"0\" alt=\"See Signs in Theaters 8-2 - Starring Mel Gibson\" align=><font face=\"verdana,arial,helvetica\" SIZE=\"1\"><b></b></font></a>","http://transfer.go.com");
		// Register the image scanner
		HTMLLinkScanner linkScanner = new HTMLLinkScanner("-l"); 
		parser.addScanner(linkScanner);
		parser.addScanner(linkScanner.createImageScanner("-i"));
				
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link URL","http://transfer.go.com/cgi/atransfer.pl?goto=http://www.signs.movies.com&name=114332&srvc=nws&context=283&guid=4AD5723D-C802-4310-A388-0B24E1A79689",linkTag.getLink());
		assertEquals("Link Text","",linkTag.getLinkText());
		HTMLNode [] containedNodes = new HTMLNode[10];
		int i=0;
		for (Enumeration e = linkTag.linkData();e.hasMoreElements();) {
			containedNodes[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 5 contained nodes in the link tag",5,i);
		assertTrue("First contained node should be an image tag",containedNodes[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)containedNodes[0];
		assertEquals("Image Location","http://ad.abcnews.com/ad/sponsors/buena_vista_pictures/bvpi-ban0003.gif",imageTag.getImageURL());
		assertEquals("Image Height","60",imageTag.getParameter("HEIGHT"));
		assertEquals("Image Width","468",imageTag.getParameter("WIDTH"));
		assertEquals("Image Border","0",imageTag.getParameter("BORDER"));
		assertEquals("Image Alt","See Signs in Theaters 8-2 - Starring Mel Gibson",imageTag.getParameter("ALT"));
		assertTrue("Second contained node should be HTMLTag",containedNodes[1] instanceof HTMLTag);
		HTMLTag tag1 = (HTMLTag)containedNodes[1];
		assertEquals("Tag Contents","font face=\"verdana,arial,helvetica\" SIZE=\"1\"",tag1.getText());
		assertTrue("Third contained node should be HTMLTag",containedNodes[2] instanceof HTMLTag);
		HTMLTag tag2 = (HTMLTag)containedNodes[2];
		assertEquals("Tag Contents","b",tag2.getText());
		assertTrue("Fourth contained node should be HTMLEndTag",containedNodes[3] instanceof HTMLEndTag);		
		HTMLEndTag endTag1 = (HTMLEndTag)containedNodes[3];
		assertEquals("Fourth Tag contents","b",endTag1.getText());
		assertTrue("Fifth contained node should be HTMLEndTag",containedNodes[4] instanceof HTMLEndTag);		
		HTMLEndTag endTag2 = (HTMLEndTag)containedNodes[4];
		assertEquals("Fifth Tag contents","font",endTag2.getText());
		
	}

	public void testBaseRefLink() throws HTMLParserException {
		createParser("<html>\n"+
		"<head>\n"+
		"<TITLE>test page</TITLE>\n"+
		"<BASE HREF=\"http://www.abc.com/\">\n"+
		"<a href=\"home.cfm\">Home</a>\n"+
		"...\n"+
		"</html>","http://transfer.go.com");
		// Register the image scanner
		parser.registerScanners();			
		parseAndAssertNodeCount(7);
		assertTrue("Node 4 should be a link tag",node[4] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[4];
		assertEquals("Resolved Link","http://www.abc.com/home.cfm",linkTag.getLink());
		assertEquals("Resolved Link Text","Home",linkTag.getLinkText());
	}
	
	/**
	 * This is a reproduction of bug 617228, reported by
	 * Stephen J. Harrington. When faced with a link like :
	 * &lt;A 
	 * HREF="/cgi-bin/view_search?query_text=postdate&gt;20020701&txt_clr=White&bg_clr=Red&url=http://loc 
	 * al 
	 * host/Testing/Report 
	 * 1.html"&gt;20020702 Report 1&lt;/A&gt;
	 * 
	 * parser is unable to handle the link correctly due to the greater than 
	 * symbol being confused to be the end of the tag.
	 */
	public void testQueryLink() throws HTMLParserException {
		createParser("<A \n"+
		"HREF=\"/cgi-bin/view_search?query_text=postdate>20020701&txt_clr=White&bg_clr=Red&url=http://localhost/Testing/Report1.html\">20020702 Report 1</A>","http://transfer.go.com");
		// Register the image scanner
		parser.registerScanners();			
		parseAndAssertNodeCount(1);
		assertTrue("Node 1 should be a link tag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertStringEquals("Resolved Link","http://transfer.go.com/cgi-bin/view_search?query_text=postdate>20020701&txt_clr=White&bg_clr=Red&url=http://localhost/Testing/Report1.html",linkTag.getLink());
		assertEquals("Resolved Link Text","20020702 Report 1",linkTag.getLinkText());
			
	}
	
	public void testNotMailtoLink() throws HTMLParserException {
		createParser("<A HREF=\"mailto.html\">not@for.real</A>","http://www.cj.com/");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];

		assertEquals("Link Plain Text", "not@for.real", linkTag.toPlainTextString());
		assertTrue("Link is not a mail link", !linkTag.isMailLink());
	}

	public void testMailtoLink() throws HTMLParserException {
		createParser("<A HREF=\"mailto:this@is.real\">this@is.real</A>","http://www.cj.com/");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];
		assertEquals("Link Plain Text", "this@is.real", linkTag.toPlainTextString());
		assertTrue("Link is a mail link", linkTag.isMailLink());
	}

	public void testJavascriptLink() throws HTMLParserException {
		createParser("<A HREF=\"javascript:alert('hello');\">say hello</A>","http://www.cj.com/");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];

		assertEquals("Link Plain Text", "say hello", linkTag.toPlainTextString());
		assertTrue("Link is a Javascript command", linkTag.isJavascriptLink());
	}

	public void testNotJavascriptLink() throws HTMLParserException {
		createParser("<A HREF=\"javascript_not.html\">say hello</A>","http://www.cj.com/");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];

		assertEquals("Link Plain Text", "say hello", linkTag.toPlainTextString());
		assertTrue("Link is not a Javascript command", !linkTag.isJavascriptLink());
	}

	public void testFTPLink() throws HTMLParserException {
		createParser("<A HREF=\"ftp://some.where.it\">my ftp</A>","http://www.cj.com/");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];

		assertEquals("Link Plain Text", "my ftp", linkTag.toPlainTextString());
		assertTrue("Link is a FTP site", linkTag.isFTPLink());
	}

	public void testNotFTPLink() throws HTMLParserException {
		createParser("<A HREF=\"ftp.html\">my ftp</A>","http://www.cj.com/");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];

		assertEquals("Link Plain Text", "my ftp", linkTag.toPlainTextString());
		assertTrue("Link is not a FTP site", !linkTag.isFTPLink());
	}
	
	public void testRelativeLinkNotHTMLBug() throws HTMLParserException {
		createParser("<A HREF=\"newpage.html\">New Page</A>","http://www.mysite.com/books/some.asp");
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];
		assertEquals("Link","http://www.mysite.com/books/newpage.html",linkTag.getLink());
	}

	public void testBadImageInLinkBug() throws HTMLParserException {
		createParser("<a href=\"registration.asp?EventID=1272\"><img border=\"0\" src=\"\\images\\register.gif\"</a>","http://www.fedpage.com/Event.asp?EventID=1272");
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag", node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag) node[0];
		// Get the image tag from the link

		HTMLNode insideNodes [] = new HTMLNode[10];
		int j =0 ;
		for (Enumeration e = linkTag.linkData();e.hasMoreElements();) {
			insideNodes[j++]= (HTMLNode)e.nextElement();
		}
		assertEquals("Number of contained internal nodes",1,j);
		assertTrue(insideNodes[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)insideNodes[0];
		assertEquals("Image Tag Location","http://www.fedpage.com/images\\register.gif",imageTag.getImageURL());
	}
}
