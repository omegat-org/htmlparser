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

package org.htmlparser;

import org.htmlparser.Node;

/**
 * This interface represents a piece of the content of the HTML document.
 */
public interface Text
    extends
        Node
{
    /**
     * Accesses the textual contents of the node.
     * Returns the text of the node.
     */
    public String getText ();

    /**
     * Sets the contents of the node.
     * @param text The new text for the node.
     */
    public void setText (String text);

    //
    // Node interface
    //

//    public void accept (org.htmlparser.visitors.NodeVisitor visitor)
//    {
//    }
//    
//    public void collectInto (org.htmlparser.util.NodeList collectionList, NodeFilter filter)
//    {
//    }
//    
//    public void doSemanticAction () throws org.htmlparser.util.ParserException
//    {
//    }
//    
//    public org.htmlparser.util.NodeList getChildren ()
//    {
//    }
//    
//    public int getEndPosition ()
//    {
//    }
//    
//    public Node getParent ()
//    {
//    }
//    
//    public int getStartPosition ()
//    {
//    }
//    
//    public String getText ()
//    {
//    }
//    
//    public void setChildren (org.htmlparser.util.NodeList children)
//    {
//    }
//    
//    public void setEndPosition (int position)
//    {
//    }
//    
//    public void setParent (Node node)
//    {
//    }
//    
//    public void setStartPosition (int position)
//    {
//    }
//    
//    public void setText (String text)
//    {
//    }
//    
//    public String toHtml ()
//    {
//    }
//    
//    public String toPlainTextString ()
//    {
//    }
}
