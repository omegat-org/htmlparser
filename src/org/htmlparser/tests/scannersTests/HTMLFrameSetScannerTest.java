// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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
import java.io.StringReader;


import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.scanners.HTMLFrameScanner;
import org.htmlparser.scanners.HTMLFrameSetScanner;
import org.htmlparser.tags.HTMLFrameSetTag;
import org.htmlparser.tags.HTMLFrameTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

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
	public void testScan() throws HTMLParserException {
		String testHTML = new String(
		"<frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\n"+ 
  			"<frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\">\n"+
	  		"<frame name=\"mainFrame\" src=\"http://www.kizna.com/web_e/\" scrolling=\"AUTO\">\n"+
		"</frameset>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];

		parser.addScanner(new HTMLFrameSetScanner(""));
		parser.addScanner(new HTMLFrameScanner());
		
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
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
		assertNotNull("Top Frame should not be null",topFrame);
		assertNotNull("Main Frame should not be null",mainFrame);
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

