// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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
// 
// This class was contributed by 
// Derrick Oswald
//

package org.htmlparser.lexer.nodes;

import org.htmlparser.lexer.Page;

/**
 * Extend org.htmlparser.AbstractNode temporarily to add the Page.
 * <em>This will be folded into org.htmlparser.AbstractNode eventually.</em>
 */
public abstract class AbstractNode extends org.htmlparser.AbstractNode
{
    /**
     * The page this node came from.
     */
    protected Page mPage;
    
    /**
     * Create a lexeme.
     * Remember the page and start & end cursor positions.
     * @param page The page this tag was read from.
     * @param start The starting offset of this node within the page.
     * @param end The ending offset of this node within the page.
     */
    public AbstractNode (Page page, int start, int end)
    {
        super (start, end);
        mPage = page;
    }

    /**
     * Get the page this node came from.
     * @return The page that supplied this node.
     */
    public Page getPage ()
    {
        return (mPage);
    }
}
