// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2005 Derrick Oswald
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

package org.htmlparser.parserapplications.filterbuilder.wrappers;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.parserapplications.filterbuilder.SubFilterList;

/**
 * Wrapper for NotFilters.
 */
public class NotFilterWrapper
	extends
		Filter
{
    /**
     * The drop target container.
     */
    protected SubFilterList mContainer;
    
    /**
     * The underlying filter.
     */
    protected NotFilter mFilter;

    /**
     * Create a wrapper over a new NotFilter.
     */ 
    public NotFilterWrapper ()
    {
        mFilter = new NotFilter ();

        // add the subfilter container
        mContainer = new SubFilterList (this, "Predicate", 1);
        add (mContainer);
    }

    //
    // Filter overrides and concrete implementations
    //
    
    public String getDescription ()
    {
        return ("Not");
    }

    public String getIconSpec ()
    {
        return ("images/NotFilter.gif");
    }

    public NodeFilter getNodeFilter ()
    {
        NodeFilter predicate;
        NotFilter ret;
        
        ret = new NotFilter ();

        predicate = mFilter.getPredicate ();
        if (null != predicate)
            ret.setPredicate (((Filter)predicate).getNodeFilter ());
            
        return (ret);
    }

    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        mFilter = (NotFilter)filter;
    }

    public NodeFilter[] getSubNodeFilters ()
    {
        NodeFilter predicate;
        NodeFilter[] ret;

        predicate = mFilter.getPredicate ();
        if (null != predicate)
            ret = new NodeFilter[] { predicate };
        else
            ret = new NodeFilter[0];

        return (ret);
    }

    public void setSubNodeFilters (NodeFilter[] filters)
    {
        if (0 != filters.length)
            mFilter.setPredicate (filters[0]);
        else
            mFilter.setPredicate (null);
    }

    public String toJavaCode (StringBuffer out, int[] context)
    {
        String name;
        String ret;

        if (null != mFilter.getPredicate ())
            name = ((Filter)mFilter.getPredicate ()).toJavaCode (out, context);
        else
            name = null;
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("NotFilter ");
        out.append (ret);
        out.append (" = new NotFilter ();");
        newline (out);
        if (null != name)
        {
	        spaces (out, context[0]);
	        out.append (ret);
	        out.append (".setPredicate (");
	        out.append (name);
	        out.append (");");
	        newline (out);
        }
        
        return (ret);
    }

    //
    // NodeFilter interface
    //

    public boolean accept (Node node)
    {
        return (mFilter.accept (node));
    }
}
