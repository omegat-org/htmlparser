// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Joshua Kerievsky
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

package org.htmlparser.nodeDecorators;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public abstract class AbstractNodeDecorator implements Node {
    protected Node delegate;

    protected AbstractNodeDecorator(Node delegate) {
        this.delegate = delegate;
    }

    public void accept(Object visitor) {
        delegate.accept(visitor);
    }

    public void collectInto(NodeList list, NodeFilter filter) {
        delegate.collectInto(list, filter);
    }

    public int elementBegin() {
        return delegate.elementBegin();
    }

    public int elementEnd() {
        return delegate.elementEnd();
    }

    /**
     * Gets the starting position of the node.
     * @return The start position.
     */
    public int getStartPosition ()
    {
        return (delegate.getStartPosition ());
    }

    /**
     * Sets the starting position of the node.
     * @param position The new start position.
     */
    public void setStartPosition (int position)
    {
        delegate.setStartPosition (position);
    }

    /**
     * Gets the ending position of the node.
     * @return The end position.
     */
    public int getEndPosition ()
    {
        return (delegate.getEndPosition ());
    }

    /**
     * Sets the ending position of the node.
     * @param position The new end position.
     */
    public void setEndPosition (int position)
    {
        delegate.setEndPosition (position);
    }
    
    public boolean equals(Object arg0) {
        return delegate.equals(arg0);
    }

    public Node getParent() {
        return delegate.getParent();
    }

    public String getText() {
        return delegate.getText();
    }

    public void setParent(Node node) {
        delegate.setParent(node);
    }

    /**
     * Get the children of this node.
     * @return The list of children contained by this node, if it's been set, <code>null</code> otherwise.
     */
    public NodeList getChildren ()
    {
        return (delegate.getChildren ());
    }

    /**
     * Set the children of this node.
     * @param children The new list of children this node contains.
     */
    public void setChildren (NodeList children)
    {
        delegate.setChildren (children);
    }

    public void setText(String text) {
        delegate.setText(text);
    }

    public String toHtml() {
        return delegate.toHtml();
    }

    public String toPlainTextString() {
        return delegate.toPlainTextString();
    }

    public String toString() {
        return delegate.toString();
    }

    public void doSemanticAction () throws ParserException {
        delegate.doSemanticAction ();
    }
}
