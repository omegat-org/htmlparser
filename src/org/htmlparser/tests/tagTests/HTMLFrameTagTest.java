// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import org.htmlparser.scanners.HTMLFrameScanner;
import org.htmlparser.tags.HTMLFrameTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLFrameTagTest extends HTMLParserTestCase {

	public HTMLFrameTagTest(String name) {
		super(name);
	}

	public void testToHTML() throws HTMLParserException {
		createParser(
		"<frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\n"+ 
  			"<frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\">\n"+
	  		"<frame name=\"mainFrame\" src=\"http://www.kizna.com/web_e/\" scrolling=\"AUTO\">\n"+
		"</frameset>");
		parser.addScanner(new HTMLFrameScanner(""));
		
		parseAndAssertNodeCount(4);
		assertTrue("Node 1 should be Frame Tag",node[1] instanceof HTMLFrameTag);
		assertTrue("Node 2 should be Frame Tag",node[2] instanceof HTMLFrameTag);

		HTMLFrameTag frameTag1 = (HTMLFrameTag)node[1];
		HTMLFrameTag frameTag2 = (HTMLFrameTag)node[2];		
		
		assertStringEquals("Frame 1 toHTML()","<FRAME SCROLLING=\"NO\" FRAMEBORDER=\"NO\" SRC=\"demo_bc_top.html\" NAME=\"topFrame\" NORESIZE=\"\">",frameTag1.toHTML());
		assertStringEquals("Frame 2 toHTML()","<FRAME SCROLLING=\"AUTO\" SRC=\"http://www.kizna.com/web_e/\" NAME=\"mainFrame\">",frameTag2.toHTML());		
	}
}

