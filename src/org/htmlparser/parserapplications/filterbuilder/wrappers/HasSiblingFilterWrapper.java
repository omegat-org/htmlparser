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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasSiblingFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.parserapplications.filterbuilder.SubFilterList;

/**
 * Wrapper for HasSiblingFilters.
 */
public class HasSiblingFilterWrapper
	extends
		Filter
    implements
        ActionListener
{
    /**
     * The underlying filter.
     */
    protected HasSiblingFilter mFilter;

    /**
     * The drop target container.
     */
    protected SubFilterList mContainer;
    
    /**
     * Create a wrapper over a new HasParentFilter.
     */ 
    public HasSiblingFilterWrapper ()
    {
        mFilter = new HasSiblingFilter ();

        // add the subfilter container
        mContainer = new SubFilterList (this, "Sibling Filter", 1);
        add (mContainer);
    }

    //
    // Filter overrides and concrete implementations
    //
    
    public String getDescription ()
    {
        return ("Has Sibling");
    }

    public String getIconSpec ()
    {
        return ("images/HasSiblingFilter.gif");
    }

    public NodeFilter getNodeFilter ()
    {
        NodeFilter filter;
        HasSiblingFilter ret;
        
        ret = new HasSiblingFilter ();

        filter = mFilter.getSiblingFilter ();
        if (null != filter)
            ret.setSiblingFilter (((Filter)filter).getNodeFilter ());
            
        return (ret);
    }

    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        mFilter = (HasSiblingFilter)filter;
    }

    public NodeFilter[] getSubNodeFilters ()
    {
        NodeFilter filter;
        NodeFilter[] ret;

        filter = mFilter.getSiblingFilter ();
        if (null != filter)
            ret = new NodeFilter[] { filter };
        else
            ret = new NodeFilter[0];

        return (ret);
    }

    public void setSubNodeFilters (NodeFilter[] filters)
    {
        if (0 != filters.length)
            mFilter.setSiblingFilter (filters[0]);
        else
            mFilter.setSiblingFilter (null);
    }

    public String toJavaCode (StringBuffer out, int[] context)
    {
        String name;
        String ret;

        if (null != mFilter.getSiblingFilter ())
            name = ((Filter)mFilter.getSiblingFilter ()).toJavaCode (out, context);
        else
            name = null;
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("HasSiblingFilter ");
        out.append (ret);
        out.append (" = new HasSiblingFilter ();");
        newline (out);
        if (null != name)
        {
	        spaces (out, context[0]);
	        out.append (ret);
	        out.append (".setSiblingFilter (");
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

    //
    // ActionListener interface
    //

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed (ActionEvent event)
    {
    }
}
