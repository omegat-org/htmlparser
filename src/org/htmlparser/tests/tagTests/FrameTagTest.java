// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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

import org.htmlparser.scanners.FrameScanner;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class FrameTagTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.FrameTagTest", "FrameTagTest");
    }

    public FrameTagTest(String name) {
        super(name);
    }

    public void testToHTML() throws ParserException {
        String frame1 = "<frame name=\"topFrame\" noresize src=\"demo_bc_top.html\" scrolling=\"NO\" frameborder=\"NO\">";
        String frame2 = "<frame name=\"mainFrame\" src=\"http://www.kizna.com/web_e/\" scrolling=\"AUTO\">";
        createParser(
        "<frameset rows=\"115,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\n"+
            frame1 + "\n"+
            frame2 + "\n"+
        "</frameset>");
        parser.addScanner(new FrameScanner(""));

        parseAndAssertNodeCount(7);
        assertTrue("Node 3 should be Frame Tag",node[2] instanceof FrameTag);
        assertTrue("Node 5 should be Frame Tag",node[4] instanceof FrameTag);

        FrameTag frameTag1 = (FrameTag)node[2];
        FrameTag frameTag2 = (FrameTag)node[4];

        assertStringEquals("Frame 1 toHTML()",frame1,frameTag1.toHtml());
        assertStringEquals("Frame 2 toHTML()",frame2,frameTag2.toHtml());
    }
}

