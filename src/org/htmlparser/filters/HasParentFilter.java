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

package org.htmlparser.filters;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;

/**
 * This class accepts all tags that have a parent acceptable to the filter.
 */
public class HasParentFilter implements NodeFilter
{
    /**
     * The filter to apply to children.
     */
    public NodeFilter mFilter;

    /**
     * Creates a new instance of HasParentFilter that accepts tags with parent acceptable to the filter.
     * @param filter The filter to apply to the parent.
     */
    public HasParentFilter (NodeFilter filter)
    {
        mFilter = filter;
    }

    /**
     * Accept tags with parent acceptable to the filter.
     * @param node The node to check.
     */
    public boolean accept (Node node)
    {
        Node parent;
        NodeList children;
        boolean ret;

        ret = false;
        parent = node.getParent ();
        if (null != parent)
            ret = mFilter.accept (parent);

        return (ret);
    }
}
