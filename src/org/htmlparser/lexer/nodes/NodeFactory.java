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
     * Note that the attributes vector contains at least one element,
     * which is the tag name (standalone attribute) at position zero.
     * This can be used to decide which type of node to create, or
     * gate other processing that may be appropriate.
     * @param lexer The lexer parsing this tag.
     * @param start The beginning position of the tag.
     * @param end The ending positiong of the tag.
     * @param attributes The attributes contained in this tag.
     */
    public Node createTagNode (Lexer lexer, int start, int end, Vector attributes)
        throws
            ParserException;

    /**
     * Scan a new tag node.
     * Provides composite tags the opportunity to collect their children by
     * scanning forward using the same lexer that created the composite tag.
     * On isolating a tag, processing in the lexer is:
     * <pre><code>
     * Node node = getNodeFactory ().createTagNode (this, begin, end, attributes);
     * node = getNodeFactory ().scanTagNode (this, node);
     * </code></pre>
     * This two step process, allows a node factory to only handle node
     * creation if it wishes, and delegate the recursion and scanning of child
     * nodes to the original factory.
     * Without giving too much implementation details, the low level lexer node
     * factory simply returns the same tag, while the higher level parser node
     * factory checks for a scanner registered for the node type and if there
     * is one, calls the scanner to create the specific type of node, which
     * advances the lexer past the children of the node.
     * @param lexer The lexer that parsed this tag.
     * @param tag The tag (just) created by createTagNode. Although this is
     * of type Node, it can safely be cast to the type returned by
     * {@link #createTagNode createTagNode}.
     * @return Either the same node or a new node containing children.
     * In any case the lexer should be positioned to proceed with the isolation
     * of the next unknown node.
     */
    public Node scanTagNode (Lexer lexer, Node tag)
        throws
            ParserException;
}
