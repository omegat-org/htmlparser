package com.kizna.htmlTests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLFrameScanner;
import com.kizna.html.tags.HTMLFrameSetTag;
import com.kizna.html.tags.HTMLFrameTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLFrameSetScannerTest extends TestCase {
	public HTMLFrameSetScannerTest(String name) {
		super(name);
	}
	public void testEvaluate() {
		String line1="frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\"";
		String line2="FRAMESET rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\"";
		String line3="Frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\"";		
		HTMLFrameScanner frameScanner = new HTMLFrameScanner("");
		assertTrue("Line 1",frameScanner.evaluate(line1,null));
		assertTrue("Line 2",frameScanner.evaluate(line2,null));
		assertTrue("Line 3",frameScanner.evaluate(line3,null));		
	}
	public void testScan() {
		String testHTML = new String(
		"<frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\n"+ 
  			"<frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\">\n"+
	  		"<frame name=\"mainFrame\" src=\"http://www.kizna.com/web_e/\" scrolling=\"AUTO\">\n"+
		"</frameset>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[20];

		parser.addScanner(new HTMLFrameScanner(""));
		
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 nodes identified",1,i);	
		assertTrue("Node 0 should be End Tag",node[0] instanceof HTMLFrameSetTag);
		HTMLFrameTag frameTag = (HTMLFrameTag)node[0];
		//assertEquals("FrameSet Rows","115,*",frameTag.getRows());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLFrameSetScannerTest.class);
	}
}
