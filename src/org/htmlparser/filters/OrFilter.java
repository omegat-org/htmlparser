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

/**
 * This class accepts all nodes matching either filter (OR operation).
 */
public class OrFilter implements NodeFilter
{
    /**
     * The left hand side.
     */
    protected NodeFilter mLeft;

    /**
     * The right hand side.
     */
    protected NodeFilter mRight;

    /**
     * Creates a new instance of OrFilter that accepts nodes acceptable to either filter.
     * @param left One filter.
     * @param right The other filter.
     */
    public OrFilter (NodeFilter left, NodeFilter right)
    {
        mLeft = left;
        mRight = right;
    }

    /**
     * Accept nodes that are acceptable to either filter.
     * @param node The node to check.
     */
    public boolean accept (Node node)
    {
        return (mLeft.accept (node) || mRight.accept (node));
    }
}
