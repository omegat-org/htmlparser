// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

/**
 * A HTMLStyleTag represents a &lt;style&gt; tag
 */
public class StyleTag extends CompositeTag {
    /**
     * The HTMLStyleTag is constructed by providing the beginning posn, ending posn
     * and the tag contents.
     * @param tagData The data for this tag.
     * @param compositeTagData The data for this composite tag.
     */
    public StyleTag(TagData tagData,CompositeTagData compositeTagData) {
        super(tagData,compositeTagData);
    }
    /**
     * Get the javascript code in this tag
     * @return java.lang.String
     */
    public java.lang.String getStyleCode() {
        return getChildrenHTML();
    }
    /**
     * Print the contents of the javascript node
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Style Node : \n");
        sb.append("\n");
        sb.append("Code\n");
        sb.append("****\n");
        sb.append(tagContents+"\n");
        return sb.toString();
    }
}
