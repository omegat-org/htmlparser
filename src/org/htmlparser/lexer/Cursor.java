// HTMLParser Library v1_4_20030727 - A java-based parser for HTML
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

package org.htmlparser.lexer;

import org.htmlparser.util.sort.Ordered;

/**
 * A bookmark in a page.
 * This class remembers the page it came from and its position within the page.
 */
public class Cursor implements Ordered
{
    /**
     * This cursor's position.
     */
    protected int mPosition;
    
    /**
     * This cursor's page.
     */
    protected Page mPage;

    /**
     * Construct a <code>Cursor</code> from the page and position given.
     * @param page The page this cursor is on.
     * @param offset The character offset within the page.
     */
    public Cursor (Page page, int offset)
    {
        mPage = page;
        mPosition = offset;
    }

    /**
     * Get this cursor's page.
     * @return The page associated with this cursor.
     */
    public Page getPage ()
    {
        return (mPage);
    }

    /**
     * Get the position of this cursor.
     * @return The cursor position.
     */
    public int getPosition ()
    {
        return (mPosition);
    }
        
    //
    // Ordered interface
    //

    /**
     * Compare one reference to another.
     * @see org.htmlparser.util.sort.Ordered
     */
    public int compare (Object that)
    {
        Cursor r = (Cursor)that;
        return (getPosition () - r.getPosition ());
    }
}    

