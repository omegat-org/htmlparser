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

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;

/**
 * This class accepts all tags that have a child acceptable to the filter.
 */
public class HasChildFilter implements NodeFilter
{
    /**
     * The filter to apply to children.
     */
    protected NodeFilter mFilter;

    /**
     * Creates a new instance of HasChildFilter that accepts tags with children acceptable to the filter.
     * Similar to asking for the parent of a node returned by the given
     * filter, but where multiple children may be acceptable, this class
     * will only accept the parent once.
     * @param filter The filter to apply to children.
     */
    public HasChildFilter (NodeFilter filter)
    {
        mFilter = filter;
    }

    /**
     * Accept tags with children acceptable to the filter.
     * @param node The node to check.
     */
    public boolean accept (Node node)
    {
        CompositeTag tag;
        NodeList children;
        boolean ret;

        ret = false;
        if (node instanceof CompositeTag)
        {
            tag = (CompositeTag)node;
            children = tag.getChildren ();
            for (int i = 0; i < children.size (); i++)
                if (mFilter.accept (children.elementAt (i)))
                {
                    ret = true;
                    break;
                }
        }

        return (ret);
    }
}
