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

package org.htmlparser.lexer.nodes;

import java.util.Vector;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.Node;
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
     * @param lexer The lexer parsing this string.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public Node createStringNode (Lexer lexer, int start, int end)
        throws
            ParserException;

    /**
     * Create a new remark node.
     * @param lexer The lexer parsing this remark.
     * @param start The beginning position of the remark.
     * @param end The ending positiong of the remark.
     */
    public Node createRemarkNode (Lexer lexer, int start, int end)
        throws
            ParserException;

    /**
     * Create a new tag node.
     * @param lexer The lexer parsing this tag.
     * @param start The beginning position of the tag.
     * @param end The ending positiong of the tag.
     * @param attributes The attributes contained in this tag.
     */
    public Node createTagNode (Lexer lexer, int start, int end, Vector attributes)
        throws
            ParserException;
}
