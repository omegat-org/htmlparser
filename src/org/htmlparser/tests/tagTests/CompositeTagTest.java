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

import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.*;
import org.htmlparser.util.*;


public class CompositeTagTest extends ParserTestCase {

    public CompositeTagTest(String name) {
        super(name);
    }

    public void testDigupStringNode() throws ParserException {
        createParser(
            "<table>" +
                "<table>" +
                    "<tr>" +
                    "<td>" +
                    "Hello World" +
                    "</td>" +
                    "</tr>" +
                "</table>" +
            "</table>"
        );
        parser.registerScanners();
        parseAndAssertNodeCount(1);
        TableTag tableTag = (TableTag)node[0];
        StringNode [] stringNode =
            tableTag.digupStringNode("Hello World");

        assertEquals("number of string nodes",1,stringNode.length);
        assertNotNull("should have found string node",stringNode);
        Node parent = stringNode[0].getParent();
        assertType("should be column",TableColumn.class,parent);
        parent = parent.getParent();
        assertType("should be row",TableRow.class,parent);
        parent = parent.getParent();
        assertType("should be table",TableTag.class,parent);
        parent = parent.getParent();
        assertType("should be table again",TableTag.class,parent);
        assertSame("should be original table",tableTag,parent);
    }

    public void testFindPositionOf() throws ParserException {
        createParser(
            "<table>" +
                "<table>" +
                    "<tr>" +
                    "<td>" +
                    "Hi There<a><b>sdsd</b>" +
                    "Hello World" +
                    "</td>" +
                    "</tr>" +
                "</table>" +
            "</table>"
        );
        parser.registerScanners();
        parseAndAssertNodeCount(1);
        TableTag tableTag = (TableTag)node[0];
        StringNode [] stringNode =
            tableTag.digupStringNode("Hello World");

        assertEquals("number of string nodes",1,stringNode.length);
        assertNotNull("should have found string node",stringNode);
        CompositeTag parent = (CompositeTag)stringNode[0].getParent();
        int pos = parent.findPositionOf(stringNode[0]);
        assertEquals("position",5,pos);
    }
}
