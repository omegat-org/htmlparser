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

package org.htmlparser;

import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

/**
 * Normal text in the html document is identified and represented by this class.
 */
public class StringNode
    extends
        org.htmlparser.lexer.nodes.StringNode
{
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
     * String visiting code.
     * @param visitor The <code>NodeVisitor</code> object to invoke 
     * <code>visitStringNode()</code> on.
     */
    public void accept (Object visitor)
    {
        ((NodeVisitor)visitor).visitStringNode (this);
    }
}
