// HTMLParser Library v1_4_20031207 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.tags.DoctypeTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class DoctypeTagTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.DoctypeTagTest", "DoctypeTagTest");
    }

    public DoctypeTagTest(String name) {
        super(name);
    }

    public void testToHTML() throws ParserException
    {
        String testHTML = new String(
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">\n"+
        "<HTML>\n"+
        "<HEAD>\n"+
        "<TITLE>Cogs of Chicago</TITLE>\n"+
        "</HEAD>\n"+
        "<BODY>\n"+
        "...\n"+
        "</BODY>\n"+
        "</HTML>\n");
        createParser(testHTML);
        parseAndAssertNodeCount(4);
        // The first node should be an DoctypeTag
        assertTrue("First node should be a DoctypeTag",node[0] instanceof DoctypeTag);
        DoctypeTag docTypeTag = (DoctypeTag)node[0];
        assertStringEquals("toHTML()","<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">",docTypeTag.toHtml());
    }

    /**
     * See bug #833592 DOCTYPE element is not parsed correctly
     * Contributed by Trevor Watson (t007).
     */
    public void DocTypeElementTest () throws ParserException
    {
        final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";
        final String HTML = DOCTYPE + "\n<HTML>\n  <HEAD>\n    <TITLE>HTMLParserDocTypeBugTest</TITLE>\n  </HEAD>\n  <BODY>\n    HTMLParser DOCTYPE node bug test.\n  </BODY>\n</HTML>";

        createParser(HTML);

        NodeIterator e = parser.elements();
        Node node = e.nextNode();

        // First node is doctype
        assertStringEquals("Doctype element output is incorrect.", DOCTYPE, node.toHtml());
    }
}
