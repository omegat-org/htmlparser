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

import org.htmlparser.Node;

import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserUtils;

/**
 * A select tag within a form.
 */
public class SelectTag extends CompositeTag
{
    public SelectTag ()
    {
        setTagName ("SELECT");
    }

    public OptionTag [] getOptionTags ()
    {
        NodeList list;
        OptionTag[] ret;
        
        list = getChildren ().searchFor (OptionTag.class, true);
        ret = new OptionTag[list.size()];
        list.copyToNodeArray (ret);

        return (ret);
    }

    public String toString()
    {
        StringBuffer lString;
        NodeList children;
        Node node;

        lString = new StringBuffer(ParserUtils.toString(this));
        children = getChildren ();
        for(int i=0;i<children.size(); i++)
        {
            node = children.elementAt(i);
            if (node instanceof OptionTag)
            {
                OptionTag optionTag = (OptionTag)node;
                lString.append(optionTag.toString()).append("\n");
            }
        }

        return lString.toString();
    }
}
