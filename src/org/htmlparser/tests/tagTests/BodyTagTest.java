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

import java.util.Hashtable;
import junit.framework.TestSuite;
import org.htmlparser.Node;

import org.htmlparser.scanners.BodyScanner;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class BodyTagTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.BodyTagTest", "BodyTagTest");
    }

    private BodyTag bodyTag;
    private String html = "<body>Yahoo!</body>";

    public BodyTagTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        createParser("<html><head><title>body tag test</title></head>" + html + "</html>");
        parser.registerScanners();
        parser.addScanner(new BodyScanner("-b"));
        parseAndAssertNodeCount(6);
        assertTrue(node[4] instanceof BodyTag);
        bodyTag = (BodyTag) node[4];
    }

    public void testToPlainTextString() throws ParserException {
        // check the label node
        assertEquals("Body","Yahoo!",bodyTag.toPlainTextString());
    }

    public void testToHTML() throws ParserException {
        assertStringEquals("Raw String", html, bodyTag.toHtml());
    }

    public void testToString() throws ParserException  {
        assertEquals("Body","BODY: Yahoo!",bodyTag.toString());
    }

    public void testAttributes ()
    {
        NodeIterator iterator;
        Node node;
        Hashtable attributes;

        try
        {
            createParser("<body style=\"margin-top:4px; margin-left:20px;\" title=\"body\">");
            parser.addScanner (new BodyScanner ("-b"));
            iterator = parser.elements ();
            node = null;
            while (iterator.hasMoreNodes ())
            {
                node = iterator.nextNode ();
                if (node instanceof BodyTag)
                {
                    attributes = ((BodyTag)node).getAttributes ();
                    assertTrue ("no style attribute", attributes.containsKey ("STYLE"));
                    assertTrue ("no title attribute", attributes.containsKey ("TITLE"));
                }
                else
                    fail ("not a body tag");
                assertTrue ("more than one node", !iterator.hasMoreNodes ());
            }
            assertNotNull ("no elements", node);
        }
        catch (ParserException pe)
        {
            fail ("exception thrown " + pe.getMessage ());
        }
    }

    public static TestSuite suite()
    {
        return new TestSuite(BodyTagTest.class);
    }
}
