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

package org.htmlparser.filters;

import java.util.Locale;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.lexer.nodes.TagNode;

/**
 * This class accepts all tags matching the tag name.
 */
public class TagNameFilter
    implements
        NodeFilter
{
    /**
     * The tag name to match.
     */
    protected String mName;

    /**
     * Creates a new instance of TagNameFilter that accepts tags with the given name.
     * @param name The tag name to match.
     */
    public TagNameFilter (String name)
    {
        mName = name.toUpperCase (Locale.ENGLISH);
    }

    /**
     * Accept nodes that are tags and have a matching tag name.
     * This discards non-tag nodes and end tags.
     * The end tags are available on the enclosing non-end tag.
     * @param node The node to check.
     */
    public boolean accept (Node node)
    {
        return ((node instanceof TagNode) &&
                !((TagNode)node).isEndTag () &&
                ((TagNode)node).getTagName ().equals (mName));
    }
}
