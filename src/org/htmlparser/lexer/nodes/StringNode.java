// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Derrick Oswald
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

package org.htmlparser.lexer.nodes;

import org.htmlparser.AbstractNode;
import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Normal text in the HTML document is represented by this class.
 */
public class StringNode extends AbstractNode
{
    public static final String STRING_FILTER = "-string";

    /**
     * Constructor takes in the text string, beginning and ending posns.
     * @param page The page this string is on.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public StringNode (Page page, int start, int end)
    {
        super (page, start, end);
    }

    /**
     * Returns the text of the string line.
     */
    public String getText ()
    {
        return (toHtml ());
    }

    /**
     * Sets the string contents of the node.
     * @param text The new text for the node.
     */
    public void setText (String text)
    {
        mPage = new Page (text);
        nodeBegin = 0;
        nodeEnd = text.length ();
        // TODO: this really needs work
        try
        {
            Cursor cursor = new Cursor (mPage, nodeBegin);
            for (int i = nodeBegin; i < nodeEnd; i++)
                mPage.getCharacter (cursor);
        }
        catch (ParserException pe)
        {
        }
    }

    public String toPlainTextString ()
    {
        return (toHtml ());
    }

    public String toHtml ()
    {
        return (mPage.getText (getStartPosition (), getEndPosition ()));
    }

    /**
     * Express this string node as a printable string
     * This is suitable for display in a debugger or output to a printout.
     * Control characters are replaced by their equivalent escape
     * sequence and contents is truncated to 80 characters.
     * @return A string representation of the string node.
     */
    public String toString ()
    {
        int startpos;
        int endpos;
        Cursor start;
        Cursor end;
        char c;
        StringBuffer ret;

        startpos = getStartPosition ();
        endpos = getEndPosition ();
        ret = new StringBuffer (endpos - startpos + 20);
        start = new Cursor (getPage (), startpos);
        end = new Cursor (getPage (), endpos);
        ret.append ("Txt (");
        ret.append (start);
        ret.append (",");
        ret.append (end);
        ret.append ("): ");
        while (start.getPosition () < endpos)
        {
            try
            {
                c = mPage.getCharacter (start);
                switch (c)
                {
                    case '\t':
                        ret.append ("\\t");
                        break;
                    case '\n':
                        ret.append ("\\n");
                        break;
                    case '\r':
                        ret.append ("\\r");
                        break;
                    default:
                        ret.append (c);
                }
            }
            catch (ParserException pe)
            {
                // not really expected, but we'return only doing toString, so ignore
            }
            if (77 <= ret.length ())
            {
                ret.append ("...");
                break;
            }
        }

        return (ret.toString ());
    }

    public void accept (Object visitor)
    {
    }
}
