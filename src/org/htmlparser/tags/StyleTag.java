// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
//
// Postal Address :
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley,
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.tags;

/**
 * A StyleTag represents a &lt;style&gt; tag.
 */
public class StyleTag extends CompositeTag {
    
    public StyleTag ()
    {
        setTagName ("STYLE");
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
