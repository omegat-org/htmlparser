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
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.lexer.nodes.TagNode;

/**
 * This class accepts all tags that have a certain attribute, and optionally, with a certain value.
 */
public class HasAttributeFilter implements NodeFilter
{
    /**
     * The attribute to check for.
     */
    protected String mAttribute;

    /**
     * The value to check for.
     */
    protected String mValue;

    /**
     * Creates a new instance of HasAttributeFilter that accepts tags with the given attribute.
     * @param attribute The attribute to search for.
     */
    public HasAttributeFilter (String attribute)
    {
        this (attribute, null);
    }

    /**
     * Creates a new instance of HasAttributeFilter that accepts tags with the given attribute.
     * @param attribute The attribute to search for.
     * @param value The value that must be matched, or null if any value will match.
     */
    public HasAttributeFilter (String attribute, String value)
    {
        mAttribute = attribute.toUpperCase (Locale.ENGLISH);
        mValue = value;
    }

    /**
     * Accept tags with a certain attribute.
     * @param node The node to check.
     */
    public boolean accept (Node node)
    {
        TagNode tag;
        Attribute attribute;
        boolean ret;

        ret = false;
        if (node instanceof TagNode)
        {
            tag = (TagNode)node;
            attribute = tag.getAttributeEx (mAttribute);
            ret = null != attribute;
            if (ret && (null != mValue))
                ret = mValue.equals (attribute.getValue ());
        }

        return (ret);
    }
}
