// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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

import org.htmlparser.Parser;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class StyleTagTest extends ParserTestCase {

    public StyleTagTest(String name) {
        super(name);
    }

    public void testToHTML() throws ParserException {
        String html = "<style>a.h{background-color:#ffee99}</style>";
        createParser(html);
        parser.registerScanners();
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof StyleTag);
        StyleTag styleTag = (StyleTag)node[0];
        assertEquals("Raw String",html,styleTag.toHtml());
    }

    /**
     * Reproducing a bug reported by Dhaval Udani relating to
     * style tag attributes being missed
     */
    public void testToHtmlAttributes() throws ParserException {
        String style = "<STYLE type=\"text/css\">\n"+
        "<!--"+
        "{something....something}"+
        "-->"+
        "</STYLE>";
        createParser(style);

        Parser.setLineSeparator("\r\n");
        parser.registerScanners();
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof StyleTag);
        StyleTag styleTag = (StyleTag)node[0];
        assertStringEquals("toHtml",style,styleTag.toHtml());
    }
}
