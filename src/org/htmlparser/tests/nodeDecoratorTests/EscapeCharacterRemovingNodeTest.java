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
//
// This test class produced by Joshua Kerievsky

package org.htmlparser.tests.nodeDecoratorTests;

import org.htmlparser.StringNodeFactory;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class EscapeCharacterRemovingNodeTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.nodeDecoratorTests.EscapeCharacterRemovingNodeTest", "EscapeCharacterRemovingNodeTest");
    }

    public EscapeCharacterRemovingNodeTest(String name) {
        super(name);
    }

    private String parseToObtainDecodedResult(String STRING_TO_DECODE)
        throws ParserException {
        StringBuffer decodedContent = new StringBuffer();

        StringNodeFactory stringNodeFactory = new StringNodeFactory();
        stringNodeFactory.setEscapeCharacterRemoval(true);
        createParser(STRING_TO_DECODE);
        parser.setStringNodeFactory(stringNodeFactory);

        NodeIterator nodes = parser.elements();

        while (nodes.hasMoreNodes())
            decodedContent.append(nodes.nextNode().toPlainTextString());

        return decodedContent.toString();
    }

    public void testTab() throws Exception {
        String ENCODED_WORKSHOP_TITLE =
            "The Testing & Refactoring Workshop\tCreated by Industrial Logic, Inc.";

        String DECODED_WORKSHOP_TITLE =
            "The Testing & Refactoring WorkshopCreated by Industrial Logic, Inc.";

        assertEquals(
            "tab in string",
            DECODED_WORKSHOP_TITLE,
            parseToObtainDecodedResult(ENCODED_WORKSHOP_TITLE));
    }

    public void testCarriageReturn() throws Exception {
        String ENCODED_WORKSHOP_TITLE =
            "The Testing & Refactoring Workshop\nCreated by Industrial Logic, Inc.\n";

        String DECODED_WORKSHOP_TITLE =
            "The Testing & Refactoring WorkshopCreated by Industrial Logic, Inc.";

        assertEquals(
            "tab in string",
            DECODED_WORKSHOP_TITLE,
            parseToObtainDecodedResult(ENCODED_WORKSHOP_TITLE));
    }

    public void testWithDecodingNodeDecorator() throws Exception {
        String ENCODED_WORKSHOP_TITLE =
            "The Testing &amp; Refactoring Workshop\nCreated by Industrial Logic, Inc.\n";

        String DECODED_WORKSHOP_TITLE =
            "The Testing & Refactoring WorkshopCreated by Industrial Logic, Inc.";

        StringBuffer decodedContent = new StringBuffer();

        StringNodeFactory stringNodeFactory = new StringNodeFactory();
        stringNodeFactory.setNodeDecoding(true);
        stringNodeFactory.setEscapeCharacterRemoval(true);

        createParser(ENCODED_WORKSHOP_TITLE);
        parser.setStringNodeFactory(stringNodeFactory);
        NodeIterator nodes = parser.elements();

        while (nodes.hasMoreNodes())
            decodedContent.append(nodes.nextNode().toPlainTextString());

        assertEquals(
            "tab in string",
            DECODED_WORKSHOP_TITLE,
            decodedContent.toString());

    }
}
