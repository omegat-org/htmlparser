// HTMLParser Library v1_2_20020831 - A java-based parser for HTML
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
import java.io.StringReader;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLFrameScanner;
import com.kizna.html.tags.HTMLFrameTag;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

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
public class HTMLFrameScannerTest extends TestCase {
	public HTMLFrameScannerTest(String name) {
		super(name);
	}
	public void testEvaluate() {
		String line1="frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\"";
		String line2="FRAME name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\"";
		String line3="Frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\"";
		String line4="Frameset name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\"";

		HTMLFrameScanner frameScanner = new HTMLFrameScanner("");
		assertTrue("Line 1",frameScanner.evaluate(line1,null));
		assertTrue("Line 2",frameScanner.evaluate(line2,null));
		assertTrue("Line 3",frameScanner.evaluate(line3,null));		
		assertTrue("Line 4 should not be a frame",!frameScanner.evaluate(line4,null));				
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

		parser.addScanner(new HTMLFrameScanner(""));
		
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 4 nodes identified",4,i);	
		assertTrue("Node 1 should be Frame Tag",node[1] instanceof HTMLFrameTag);
		assertTrue("Node 2 should be Frame Tag",node[2] instanceof HTMLFrameTag);

		HTMLFrameTag frameTag1 = (HTMLFrameTag)node[1];
		HTMLFrameTag frameTag2 = (HTMLFrameTag)node[2];		
		assertEquals("Frame 1 Locn","http://www.google.com/test/demo_bc_top.html",frameTag1.getFrameLocation());
		assertEquals("Frame 1 Name","topFrame",frameTag1.getFrameName());
		assertEquals("Frame 2 Locn","http://www.kizna.com/web_e/",frameTag2.getFrameLocation());		
		assertEquals("Frame 2 Name","mainFrame",frameTag2.getFrameName());
		assertEquals("Frame 1 Scrolling","NO",frameTag1.getParameter("scrolling"));
		assertEquals("Frame 1 Border","NO",frameTag1.getParameter("frameborder"));		
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLFrameScannerTest.class);
	}
}
