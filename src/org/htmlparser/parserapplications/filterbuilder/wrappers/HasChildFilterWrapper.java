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

import javax.swing.JCheckBox;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.parserapplications.filterbuilder.SubFilterList;

/**
 * Wrapper for HasChildFilters.
 */
public class HasChildFilterWrapper
	extends
		Filter
	implements
        ActionListener
{
    /**
     * The underlying filter.
     */
    protected HasChildFilter mFilter;

    /**
     * The check box for recursion.
     */
    protected JCheckBox mRecursive;

    /**
     * The drop target container.
     */
    protected SubFilterList mContainer;
    
    /**
     * Create a wrapper over a new HasChildFilter.
     */ 
    public HasChildFilterWrapper ()
    {
        mFilter = new HasChildFilter ();

        // add the recursive flag
        mRecursive = new JCheckBox ("Recursive");
        add (mRecursive);
        mRecursive.addActionListener (this);
        mRecursive.setSelected (mFilter.getRecursive ());

        // add the subfilter container
        mContainer = new SubFilterList (this, "Child Filter", 1);
        add (mContainer);
    }

    //
    // Filter overrides and concrete implementations
    //
    
    public String getDescription ()
    {
        return ("Has Child");
    }

    public String getIconSpec ()
    {
        return ("images/HasChildFilter.gif");
    }

    public NodeFilter getNodeFilter ()
    {
        NodeFilter filter;
        HasChildFilter ret;
        
        ret = new HasChildFilter ();

        ret.setRecursive (mFilter.getRecursive ());
        filter = mFilter.getChildFilter ();
        if (null != filter)
            ret.setChildFilter (((Filter)filter).getNodeFilter ());
            
        return (ret);
    }

    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        mFilter = (HasChildFilter)filter;
        mRecursive.setSelected (mFilter.getRecursive ());
    }

    public NodeFilter[] getSubNodeFilters ()
    {
        NodeFilter filter;
        NodeFilter[] ret;

        filter = mFilter.getChildFilter ();
        if (null != filter)
            ret = new NodeFilter[] { filter };
        else
            ret = new NodeFilter[0];

        return (ret);
    }

    public void setSubNodeFilters (NodeFilter[] filters)
    {
        if (0 != filters.length)
            mFilter.setChildFilter (filters[0]);
        else
            mFilter.setChildFilter (null);
    }

    public String toJavaCode (StringBuffer out, int[] context)
    {
        String name;
        String ret;

        if (null != mFilter.getChildFilter ())
            name = ((Filter)mFilter.getChildFilter ()).toJavaCode (out, context);
        else
            name = null;
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("HasChildFilter ");
        out.append (ret);
        out.append (" = new HasChildFilter ();");
        newline (out);
        spaces (out, context[0]);
        out.append (ret);
        out.append (".setRecursive (");
        out.append (mFilter.getRecursive () ? "true" : "false");
        out.append (");");
        newline (out);
        if (null != name)
        {
	        spaces (out, context[0]);
	        out.append (ret);
	        out.append (".setChildFilter (");
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
     * Invoked when an action occurs on the check box.
     */
    public void actionPerformed (ActionEvent event)
    {
        Object source;
        boolean recursive;

        source = event.getSource ();
        if (source == mRecursive)
        {
            recursive = mRecursive.isSelected ();
	        mFilter.setRecursive (recursive);
        }
    }
}
