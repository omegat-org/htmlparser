// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2003 Derrick Oswald
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

import java.util.Vector;

import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;

/**
 * This interface defines the methods needed to create new nodes.
 * The factory is used when lexing to generate the nodes passed
 * back to the caller.
 */
public interface NodeFactory
{
    /**
     * Create a new string node.
     * @param page The page the node is on.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public Text createStringNode (Page page, int start, int end)
        throws
            ParserException;

    /**
     * Create a new remark node.
     * @param page The page the node is on.
     * @param start The beginning position of the remark.
     * @param end The ending positiong of the remark.
     */
    public Remark createRemarkNode (Page page, int start, int end)
        throws
            ParserException;

    /**
     * Create a new tag node.
     * Note that the attributes vector contains at least one element,
     * which is the tag name (standalone attribute) at position zero.
     * This can be used to decide which type of node to create, or
     * gate other processing that may be appropriate.
     * @param page The page the node is on.
     * @param start The beginning position of the tag.
     * @param end The ending positiong of the tag.
     * @param attributes The attributes contained in this tag.
     */
    public Tag createTagNode (Page page, int start, int end, Vector attributes)
        throws
            ParserException;
}
