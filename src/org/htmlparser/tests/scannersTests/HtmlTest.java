// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.scanners.HtmlScanner;
import org.htmlparser.scanners.TitleScanner;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;

public class HtmlTest extends ParserTestCase {

    public HtmlTest(String name) {
        super(name);
    }

    public void testScan() throws Exception {
        createParser(
            "<html>" +
            "   <head>" +
            "       <title>Some Title</title>" +
            "   </head>" +
            "   <body>" +
            "       Some data" +
            "   </body>" +
            "</html>");
        parser.addScanner(new TitleScanner(""));
        parser.addScanner(new HtmlScanner());
        parseAndAssertNodeCount(1);
        assertType("html tag",Html.class,node[0]);
        Html html = (Html)node[0];
        NodeList nodeList = new NodeList();
        html.collectInto(nodeList, TitleTag.class);
        assertEquals("nodelist size",1,nodeList.size());
        Node node = nodeList.elementAt(0);
        assertType("expected title tag",TitleTag.class,node);
        TitleTag titleTag = (TitleTag)node;
        assertStringEquals("title","Some Title",titleTag.getTitle());
    }
}
