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
import org.htmlparser.lexer.nodes.StringNode;

/**
 * This class accepts all string nodes containing the given string.
 */
public class StringFilter implements NodeFilter
{
    /**
     * The string to search for.
     */
    protected String mPattern;

    /**
     * Case sensitive toggle.
     */
    protected boolean mCaseSensitive;

    /**
     * Creates a new instance of StringFilter that accepts string nodes containing a certain string.
     * The comparison is case insensitive.
     * @param pattern The pattern to search for.
     */
    public StringFilter (String pattern)
    {
        this (pattern, false);
    }

    /**
     * Creates a new instance of StringFilter that accepts string nodes containing a certain string.
     * @param pattern The pattern to search for.
     * @param case_sensitive If <code>true</code>, comparisons are performed
     * respecting case.
     */
    public StringFilter (String pattern, boolean case_sensitive)
    {
        mCaseSensitive = case_sensitive;
        if (mCaseSensitive)
            mPattern = pattern;
        else
            mPattern = pattern.toUpperCase ();
    }

    /**
     * Accept string nodes that contain the string.
     * @param node The node to check.
     */
    public boolean accept (Node node)
    {
        String string;
        boolean ret;
        
        ret = false;
        if (node instanceof StringNode)
        {
            string = ((StringNode)node).getText ();
            if (!mCaseSensitive)
                string = string.toUpperCase ();
            ret = -1 != string.indexOf (mPattern);
        }

        return (ret);
    }
}
