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

package org.htmlparser.tests.tagTests;

import org.htmlparser.HTMLParser;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLLinkTagData;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLLinkTagTest extends HTMLParserTestCase
{

	public HTMLLinkTagTest(String name) {
		super(name);
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testLinkNodeBug()  throws HTMLParserException
	{
		createParser("<A HREF=\"../test.html\">abcd</A>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
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
	public void testLinkNodeBug2() throws HTMLParserException
	{
		createParser("<A HREF=\"../../test.html\">abcd</A>","http://www.google.com/test/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
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
	public void testLinkNodeBug3() throws HTMLParserException
	{
		createParser("<A HREF=\"/mylink.html\">abcd</A>","http://www.cj.com/");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
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
	public void testLinkNodeBug4() throws HTMLParserException
	{
		createParser("<A HREF=\"/mylink.html\">abcd</A>","http://www.cj.com");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
		assertEquals("Link incorrect!!","http://www.cj.com/mylink.html",linkNode.getLink());
	}
	
	public void testLinkNodeBug5() throws HTMLParserException
	{
		createParser("<a href=http://note.kimo.com.tw/>筆記</a>&nbsp; <a \n"+
		"href=http://photo.kimo.com.tw/>相簿</a>&nbsp; <a\n"+
		"href=http://address.kimo.com.tw/>通訊錄</a>&nbsp;&nbsp;","http://www.cj.com");
		HTMLParser.setLineSeparator("\r\n");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(6);
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
	public void testLinkNodeBugNullPointerException() throws HTMLParserException
	{
		createParser("<FORM action=http://search.yahoo.com/bin/search name=f><MAP name=m><AREA\n"+ 
			"coords=0,0,52,52 href=\"http://www.yahoo.com/r/c1\" shape=RECT><AREA"+
			"coords=53,0,121,52 href=\"http://www.yahoo.com/r/p1\" shape=RECT><AREA"+
			"coords=122,0,191,52 href=\"http://www.yahoo.com/r/m1\" shape=RECT><AREA"+
			"coords=441,0,510,52 href=\"http://www.yahoo.com/r/wn\" shape=RECT>","http://www.cj.com/");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
		parseAndAssertNodeCount(6);
	}

	/**
	 * This bug occurs when there is a null pointer exception thrown while scanning a tag using HTMLLinkScanner.
	 * Creation date: (7/1/2001 2:42:13 PM)
	 */
	public void testLinkNodeMailtoBug() throws HTMLParserException
	{
		createParser("<A HREF='mailto:somik@yahoo.com'>hello</A>","http://www.cj.com/");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkNode = (HTMLLinkTag)node[0];
		assertEquals("Link incorrect","somik@yahoo.com",linkNode.getLink());
		assertEquals("Link Type",new Boolean(true),new Boolean(linkNode.isMailLink()));
	}
	
	/**
	 * This bug occurs when there is a null pointer exception thrown while scanning a tag using HTMLLinkScanner.
	 * Creation date: (7/1/2001 2:42:13 PM)
	 */
	public void testLinkNodeSingleQuoteBug() throws HTMLParserException
	{
		createParser("<A HREF='abcd.html'>hello</A>","http://www.cj.com/");
	
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
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
	public void testLinkTag() throws HTMLParserException
	{
		createParser("<A HREF=\"test.html\">abcd</A>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
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
	public void testLinkTagBug() throws HTMLParserException
	{
		createParser("<A HREF=\"../test.html\">abcd</A>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
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
	public void testNullTagBug() throws HTMLParserException
	{
		createParser("<A HREF=>Something</A>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("The link location","",linkTag.getLink());
		assertEquals("The link text","Something",linkTag.getLinkText());
	}
	
	public void testToPlainTextString() throws HTMLParserException {
		createParser("<A HREF='mailto:somik@yahoo.com'>hello</A>","http://www.cj.com/");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("Link Plain Text","hello",linkTag.toPlainTextString());	
	}
	
	public void testToHTML() throws HTMLParserException {
		createParser("<A HREF='mailto:somik@yahoo.com'>hello</A>\n"+
			"<LI><font color=\"FF0000\" size=-1><b>Tech Samachar:</b></font> <a \n"+
			"href=\"http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/tech\n"+
			"nical.html\"> Journalism 3.0</a> by Rajesh Jain","http://www.cj.com/");
		HTMLParser.setLineSeparator("\r\n");
		// Register the image scanner
		parser.addScanner(new HTMLLinkScanner("-l"));
			
		parseAndAssertNodeCount(9);
		assertTrue("First Node should be a HTMLLinkTag",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertStringEquals("Link Raw Text","<A HREF=\"mailto:somik@yahoo.com\">hello</A>",linkTag.toHTML());
		assertTrue("Eighth Node should be a HTMLLinkTag",node[7] instanceof HTMLLinkTag);
		linkTag = (HTMLLinkTag)node[7];
		assertStringEquals("Link Raw Text","<A HREF=\"http://ads.samachar.com/bin/redirect/tech.txt?http://www.samachar.com/tech\r\nnical.html\"> Journalism 3.0</A>",linkTag.toHTML());
	}

	public void testTypeHttps() throws HTMLParserException{
		HTMLLinkTag linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("https://www.someurl.com","","",false,false,"")
		);
		assertTrue("This is a https link",linkTag.isHTTPSLink());
	}

	public void testTypeFtp() throws HTMLParserException{
		HTMLLinkTag linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("ftp://www.someurl.com","","",false,false,"")
		);
		assertTrue("This is an ftp link",linkTag.isFTPLink());
	}	

	public void testTypeJavaScript() throws HTMLParserException {
		HTMLLinkTag linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("javascript://www.someurl.com","","",false,true,"")
		);
		assertTrue("This is a javascript link",linkTag.isJavascriptLink());
	}

	public void testTypeHttpLink() throws HTMLParserException {
		HTMLLinkTag linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("http://www.someurl.com","","",false,false,"")
		);
		assertTrue("This is a http link : "+linkTag.getLink(),linkTag.isHTTPLink());
		linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("somePage.html","","",false,false,"")
		);
		assertTrue("This relative link is alsp a http link : "+linkTag.getLink(),linkTag.isHTTPLink());
		linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("ftp://somePage.html","","",false,false,"")
		);
		assertTrue("This is not a http link : "+linkTag.getLink(),!linkTag.isHTTPLink());
	}	

	public void testTypeHttpLikeLink() throws HTMLParserException {
		HTMLLinkTag linkTag = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("http://","","",false,false,"")
		);
		assertTrue("This is a http link",linkTag.isHTTPLikeLink());
		HTMLLinkTag linkTag2 = 
		new HTMLLinkTag(
			new HTMLTagData(0,0,"",""),
			new HTMLCompositeTagData(null,null,null),
			new HTMLLinkTagData("https://www.someurl.com","","",false,false,"")
		);
		assertTrue("This is a https link",linkTag2.isHTTPLikeLink());

	}	
	public void testGetLinkContentsAndEndTagWith() throws HTMLParserException {
		createParser("<A HREF=\"test.html\"><IMG SRC=\"pic.jpg\">abcd</A>","http://www.google.com/test/index.html");
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Expected link",node[0] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[0];
		assertEquals("contents and end of link","<IMG SRC=\"pic.jpg\">abcd</A>",linkTag.getChildContentsAndEndTagWith(null));		
	}	
}
