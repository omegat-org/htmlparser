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


package org.htmlparser.lexer.nodes;

import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;

/**
 * The remark tag is identified and represented by this class.
 */
public class RemarkNode extends AbstractNode
{
    public final static String REMARK_NODE_FILTER="-r";
    
    /** 
     * Constructor takes in the text string, beginning and ending posns.
     * @param page The page this string is on.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public RemarkNode (Page page, int start, int end)
    {
        super (page, start, end);
    }

    /** 
     * Returns the text contents of the comment tag.
     * todo: this only works for the usual case.
     */
    public String getText()
    {
        return (mPage.getText (elementBegin () + 4, elementEnd () - 3));
    }

    public String toPlainTextString()
    {
        return (getText());
    }
    public String toHtml() {
        return (mPage.getText (elementBegin (), elementEnd ()));
    }
    /**
     * Print the contents of the remark tag.
     */
    public String toString()
    {
        Cursor start;
        Cursor end;
        
        start = new Cursor (getPage (), elementBegin ());
        end = new Cursor (getPage (), elementEnd ());
        return ("Rem (" + start.toString () + "," + end.toString () + "): " + getText ());
    }

    public void collectInto(NodeList collectionList, String filter) {
        if (filter==REMARK_NODE_FILTER) collectionList.add(this);
    }

    public void accept(Object visitor) {
    }
}
