package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLFrameSetScanner;
import com.kizna.html.tags.HTMLFrameSetTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLFrameSetTagTest extends TestCase {

	/**
	 * Constructor for HTMLFrameSetTagTest.
	 * @param arg0
	 */
	public HTMLFrameSetTagTest(String arg0) {
		super(arg0);
	}
	public void testToHTML() {
		String testHTML = new String(
		"<frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\n"+ 
  			"<frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\">\n"+
	  		"<frame name=\"mainFrame\" src=\"http://www.kizna.com/web_e/\" scrolling=\"AUTO\">\n"+
		"</frameset>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[20];

		parser.addScanner(new HTMLFrameSetScanner(""));
		
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 nodes identified",1,i);	
		assertTrue("Node 0 should be End Tag",node[0] instanceof HTMLFrameSetTag);
		HTMLFrameSetTag frameSetTag = (HTMLFrameSetTag)node[0];
		assertEquals("HTML Contents",
		"<frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\r\n"+ 
  			"<frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\">\r\n"+
	  		"<frame name=\"mainFrame\" src=\"http://www.kizna.com/web_e/\" scrolling=\"AUTO\">\r\n"+
		"</FRAMESET>",
		frameSetTag.toHTML());
		
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLFrameSetTagTest.class);
	}
}

