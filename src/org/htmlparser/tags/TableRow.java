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

import org.htmlparser.util.NodeList;

/**
 * A table row tag.
 */
public class TableRow extends CompositeTag
{
    /**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"TR"};

    /**
     * The set of end tag names that indicate the end of this tag.
     */
    private static final String[] mEndTagEnders = new String[] {"TABLE"};

    /**
     * Create a new table row tag.
     */
    public TableRow ()
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
     * Return the set of tag names that cause this tag to finish.
     * @return The names of following tags that stop further scanning.
     */
    public String[] getEnders ()
    {
        return (mIds);
    }

    /**
     * Return the set of end tag names that cause this tag to finish.
     * @return The names of following end tags that stop further scanning.
     */
    public String[] getEndTagEnders ()
    {
        return (mEndTagEnders);
    }

    /**
     * Get the number of columns in this row.
     */
    public int getColumnCount ()
    {
        return (
            (null == getChildren ()) ? 0 :
            getChildren ().searchFor (TableColumn.class).size ());
    }

    /**
     * Get the children (columns) of this row.
     */
    public TableColumn [] getColumns ()
    {
        NodeList list;
        TableColumn [] ret;

        if (null != getChildren ())
        {
            list = getChildren ().searchFor (TableColumn.class);
            ret = new TableColumn[list.size ()];
            list.copyToNodeArray (ret);
        }
        else
            ret = new TableColumn[0];

        return (ret);
    }

    /**
     * Checks if this table has a header
     * @return <code>true</code> if there is a header tag.
     */
    public boolean hasHeader ()
    {
        return (0 != getHeaderCount ());
    }

    /**
     * Get the number of headers in this row.
     * @return The count of header tags in this row.
     */
    public int getHeaderCount ()
    {
        return (
            (null == getChildren ()) ? 0 :
            getChildren ().searchFor (TableHeader.class, false).size ());
    }

    /**
     * Get the header of this table
     * @return Table header tags contained in this row.
     */
    public TableHeader[] getHeader ()
    {
        NodeList list;
        TableHeader [] ret;

        if (null != getChildren ())
        {
            list = getChildren ().searchFor (TableHeader.class, false);
            ret = new TableHeader[list.size ()];
            list.copyToNodeArray (ret);
        }
        else
            ret = new TableHeader[0];

        return (ret);
    }
}
