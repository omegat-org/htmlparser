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

package org.htmlparser.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Tag;

public class ParserUtils
{
    public static String removeChars(String s, char occur) {
        StringBuffer newString = new StringBuffer();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (ch != occur)
                newString.append(ch);
        }
        return newString.toString();
    }

    public static String removeEscapeCharacters(String inputString) {
        inputString = ParserUtils.removeChars(inputString, '\r');
        inputString = ParserUtils.removeChars(inputString, '\n');
        inputString = ParserUtils.removeChars(inputString, '\t');
        return inputString;
    }

    public static String removeTrailingBlanks(String text) {
        char ch = ' ';
        while (ch == ' ') {
            ch = text.charAt(text.length() - 1);
            if (ch == ' ')
                text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    /**
     * Search given node and pick up any objects of given type.
     * @param node The node to search.
     * @param type The class to search for.
     * @return A node array with the matching nodes.
     */
    public static Node[] findTypeInNode(Node node, Class type)
    {
        NodeFilter filter;
        NodeList ret;
        
        ret = new NodeList ();
        filter = new NodeClassFilter (type);
        node.collectInto (ret, filter);

        return (ret.toNodeArray ());
    }

}