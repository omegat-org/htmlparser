package com.kizna.htmlTests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLFrameSetScanner;
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
		HTMLFrameSetScanner frameSetScanner = new HTMLFrameSetScanner("");
		assertTrue("Line 1",frameSetScanner.evaluate(line1,null));
		assertTrue("Line 2",frameSetScanner.evaluate(line2,null));
		assertTrue("Line 3",frameSetScanner.evaluate(line3,null));		
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

		parser.addScanner(new HTMLFrameSetScanner(""));
		
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 1 nodes identified",1,i);	
		assertTrue("Node 0 should be End Tag",node[0] instanceof HTMLFrameSetTag);
		HTMLFrameSetTag frameSetTag = (HTMLFrameSetTag)node[0];
		// Find the details of the frameset itself
		assertEquals("Rows","115,*",frameSetTag.getParameter("rows"));
		assertEquals("FrameBorder","NO",frameSetTag.getParameter("FrameBorder"));
		assertEquals("FrameSpacing","0",frameSetTag.getParameter("FrameSpacing"));		
		assertEquals("Border","0",frameSetTag.getParameter("Border"));
		// Now check the frames
		HTMLFrameTag topFrame = frameSetTag.getFrame("topFrame");
		HTMLFrameTag mainFrame = frameSetTag.getFrame("mainFrame");
		assertNotNull(topFrame);
		assertNotNull(mainFrame);
		assertEquals("Top Frame Name","topFrame",topFrame.getFrameName());
		assertEquals("Top Frame Location","http://www.google.com/test/demo_bc_top.html",topFrame.getFrameLocation());
		assertEquals("Main Frame Name","mainFrame",mainFrame.getFrameName());
		assertEquals("Main Frame Location","http://www.kizna.com/web_e/",mainFrame.getFrameLocation());		
		assertEquals("Scrolling in Main Frame","AUTO",mainFrame.getParameter("Scrolling"));
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLFrameSetScannerTest.class);
	}
}

