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
import org.htmlparser.Text;

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
     * The locale to use converting to uppercase in the case insensitive searches.
     */
    protected Locale mLocale;

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
        this (pattern, case_sensitive, null);
    }
    
    /**
     * Creates a new instance of StringFilter that accepts string nodes containing a certain string.
     * @param pattern The pattern to search for.
     * @param case_sensitive If <code>true</code>, comparisons are performed
     * respecting case.
     */
    public StringFilter (String pattern, boolean case_sensitive, Locale locale)
    {
        mCaseSensitive = case_sensitive;
        if (mCaseSensitive)
            mPattern = pattern;
        else
        {
            mLocale = (null == locale) ? Locale.ENGLISH : locale;
            mPattern = pattern.toUpperCase (mLocale);
        }
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
        if (node instanceof Text)
        {
            string = ((Text)node).getText ();
            if (!mCaseSensitive)
                string = string.toUpperCase (mLocale);
            ret = -1 != string.indexOf (mPattern);
        }

        return (ret);
    }
}
