// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.util;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.NodeClassFilter;

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