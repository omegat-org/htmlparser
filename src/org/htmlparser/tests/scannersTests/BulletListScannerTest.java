// HTMLParser Library v1_4_20030907 - A java-based parser for HTML
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
import org.htmlparser.StringNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author Somik Raha
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BulletListScannerTest extends ParserTestCase {

    public BulletListScannerTest(String name) {
        super(name);
    }

    public void testScan() throws ParserException {
        createParser(
            "<ul TYPE=DISC>" +
                "<ul TYPE=\"DISC\"><li>Energy supply\n"+
                    " (Campbell)  <A HREF=\"/hansard/37th3rd/h20307p.htm#1646\">1646</A>\n"+
                    " (MacPhail)  <A HREF=\"/hansard/37th3rd/h20307p.htm#1646\">1646</A>\n"+
                "</ul><A NAME=\"calpinecorp\"></A><B>Calpine Corp.</B>\n"+
                "<ul TYPE=\"DISC\"><li>Power plant projects\n"+
                    " (Neufeld)  <A HREF=\"/hansard/37th3rd/h20314p.htm#1985\">1985</A>\n"+
                "</ul>" +
            "</ul>"
        );
        parser.registerScanners();
        parseAndAssertNodeCount(1);

        NodeList nestedBulletLists =
            ((CompositeTag)node[0]).searchFor(
                BulletList.class
            );
        assertEquals(
            "bullets in first list",
            2,
            nestedBulletLists.size()
        );
        BulletList firstList =
            (BulletList)nestedBulletLists.elementAt(0);
        Bullet firstBullet =
            (Bullet)firstList.childAt(0);
        Node firstNodeInFirstBullet =
            firstBullet.childAt(0);
        assertType(
            "first child in bullet",
            StringNode.class,
            firstNodeInFirstBullet
        );
        assertStringEquals(
            "expected text",
            "Energy supply\r\n" +
            " (Campbell)  ",
            firstNodeInFirstBullet.toPlainTextString()
        );
    }

    public void testMissingendtag ()
        throws ParserException
    {
        createParser ("<li>item 1<li>item 2");
        parser.registerScanners ();
        parseAndAssertNodeCount (2);
        assertStringEquals ("item 1 not correct", "item 1", ((Bullet)node[0]).childAt (0).toHtml ());
        assertStringEquals ("item 2 not correct", "item 2", ((Bullet)node[1]).childAt (0).toHtml ());
    }
}
