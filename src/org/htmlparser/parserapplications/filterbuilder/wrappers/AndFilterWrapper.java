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
import org.htmlparser.filters.AndFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.parserapplications.filterbuilder.SubFilterList;

/**
 * Wrapper for AndFilters.
 */
public class AndFilterWrapper
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
    protected AndFilter mFilter;

    /**
     * Create a wrapper over a new AndFilter.
     */ 
    public AndFilterWrapper ()
    {
        mFilter = new AndFilter ();

        // add the subfilter container
        mContainer = new SubFilterList (this, "Predicates", 0);
        add (mContainer);
    }

    //
    // Filter overrides and concrete implementations
    //

    public String getDescription ()
    {
        return ("And");
    }

    public String getIconSpec ()
    {
        return ("images/AndFilter.gif");
    }

    public NodeFilter getNodeFilter ()
    {
        NodeFilter[] predicates;
        NodeFilter[] temp;
        AndFilter ret;
        
        ret = new AndFilter ();

        predicates = mFilter.getPredicates ();
        temp = new NodeFilter[predicates.length];
        for (int i = 0; i < predicates.length; i++)
            temp[i] = ((Filter)predicates[i]).getNodeFilter ();
        ret.setPredicates (temp);
            
        return (ret);
    }

    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        mFilter = (AndFilter)filter;
    }

    public NodeFilter[] getSubNodeFilters ()
    {
        return (mFilter.getPredicates ());
    }

    public void setSubNodeFilters (NodeFilter[] filters)
    {
        mFilter.setPredicates (filters);
    }

    public String toJavaCode (StringBuffer out, int[] context)
    {
        String array;
        NodeFilter[] predicates;
        String[] names;
        String ret;
        
        predicates = mFilter.getPredicates ();
        array = null; // stoopid Java compiler
        if (0 != predicates.length)
        {
            names = new String[predicates.length];
            for (int i = 0; i < predicates.length; i++)
            {
                names[i] = ((Filter)predicates[i]).toJavaCode (out, context);
            }
            array = "array" + context[2]++;
	        spaces (out, context[0]);
	        out.append ("NodeFilter[] ");
	        out.append (array);
	        out.append (" = new NodeFilter[");
	        out.append (predicates.length);
	        out.append ("];");
	        newline (out);
	        for (int i = 0; i < predicates.length; i++)
	        {
		        spaces (out, context[0]);
		        out.append (array);
		        out.append ("[");
		        out.append (i);
		        out.append ("] = ");
		        out.append (names[i]);
		        out.append (";");
		        newline (out);
	        }
        }
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("AndFilter ");
        out.append (ret);
        out.append (" = new AndFilter ();");
        newline (out);
        if (0 != predicates.length)
        {
	        spaces (out, context[0]);
	        out.append (ret);
	        out.append (".setPredicates (");
	        out.append (array);
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