// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
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

import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class EndTagTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.EndTagTest", "EndTagTest");
    }

    public EndTagTest(String name) {
        super(name);
    }

    public void testToHTML() throws ParserException {
        createParser("<HTML></HTML>");
        // Register the image scanner
        parser.registerScanners();
        parseAndAssertNodeCount(2);
        // The node should be an HTMLLinkTag
        assertTrue("Node should be a Tag",node[1] instanceof Tag);
        Tag endTag = (Tag)node[1];
        assertTrue("Node should be an end Tag",endTag.isEndTag ());
        assertEquals("Raw String","</HTML>",endTag.toHtml());
    }

    public void testEndTagFind() throws ParserException {
        String testHtml =
            "<SCRIPT>document.write(d+\".com\")</SCRIPT><BR>";
        createParser(testHtml);
        int pos = testHtml.indexOf("</SCRIPT>");
        parseAndAssertNodeCount(4);
        assertTrue("Node should be a Tag",node[2] instanceof Tag);
        Tag endTag = (Tag)node[2];
        assertTrue("Node should be an end Tag",endTag.isEndTag ());
        assertEquals("endtag element begin",pos,endTag.getStartPosition ());
        assertEquals("endtag element end",pos+9,endTag.getEndPosition ());
    }
}
