// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
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

package org.htmlparser.tags;

/**
 * A StyleTag represents a &lt;style&gt; tag.
 */
public class StyleTag extends CompositeTag
{
    /**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"STYLE"};

    /**
     * Create a new style tag.
     */
    public StyleTag ()
    {
    }

    /**
     * Return the set of names handled by this tag.
     * @return The names to be matched that create tags of this type.
     */
    public String[] getIds ()
    {
        return (mIds);
    }

    /**
     * Get the style data in this tag.
     * @return The HTML of the children of this tag.
     */
    public String getStyleCode()
    {
        return getChildrenHTML();
    }

    /**
     * Print the contents of the style node.
     */
    public String toString()
    {
        String guts = toHtml();
        guts = guts.substring (1, guts.length () - 2);
        StringBuffer sb = new StringBuffer();
        sb.append("Style Node : \n");
        sb.append("\n");
        sb.append("Code\n");
        sb.append("****\n");
        sb.append(guts+"\n");
        return sb.toString();
    }
}
