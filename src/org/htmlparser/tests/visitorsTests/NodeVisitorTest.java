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

package org.htmlparser.tests.visitorsTests;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.StringNode;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.visitors.NodeVisitor;

public class NodeVisitorTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.visitorsTests.NodeVisitorTest", "NodeVisitorTest");
    }

    public NodeVisitorTest(String name) {
        super(name);
    }

    public void testVisitTag() throws Exception {
        ParameterVisitor visitor = new ParameterVisitor();
        createParser(
            "<input>" +
                "<param name='key1'>value1</param>"+
                "<param name='key2'>value2</param>"+
            "</input>"
        );
        parser.visitAllNodesWith(visitor);
        assertEquals("value of key1","value1",visitor.getValue("key1"));
        assertEquals("value of key2","value2",visitor.getValue("key2"));
    }

    class ParameterVisitor extends NodeVisitor {
        Map paramsMap = new HashMap();
        String lastKeyVisited;

        public String getValue(String key) {
            return (String)paramsMap.get(key);
        }

        public void visitStringNode(StringNode stringNode) {
            paramsMap.put(lastKeyVisited,stringNode.getText());
        }

        public void visitTag(Tag tag) {
            if (tag.getTagName().equals("PARAM")) {
                lastKeyVisited = tag.getAttribute("NAME");
            }
        }
    }
}
