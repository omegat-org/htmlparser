// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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
public class HTMLFrameScannerTest extends TestCase {
	public HTMLFrameScannerTest(String name) {
		super(name);
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