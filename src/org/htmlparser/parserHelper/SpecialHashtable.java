// HTMLParser Library v1_4_20030907 - A java-based parser for HTML
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

package org.htmlparser.parserHelper;

import java.util.Hashtable;

/**
 * Acts like a regular HashTable, except some values are translated in get(String).
 * Specifically, <code>Tag.NULLVALUE</code> is translated to <code>null</code> and
 * <code>Tag.NOTHING</code> is translated to <code>""</code>.
 * This is done for backwards compatibility, users are expecting a HashTable,
 * but Tag.toHTML needs to know when there is no attribute value (&lt;<TAG ATTRIBUTE&gt;)
 * and when the value was not present (&lt;<TAG ATTRIBUTE=&gt;).
 */
public class SpecialHashtable extends Hashtable
{
    /**
     * Constructs a new, empty hashtable with a default initial capacity (11) and load factor, which is 0.75.
     */
    public SpecialHashtable ()
    {
        super ();
    }

    /**
     * Constructs a new, empty hashtable with the specified initial capacity and default load factor, which is 0.75.
     */
    public SpecialHashtable (int initialCapacity)
    {
        super (initialCapacity);
    }

    /**
     * Constructs a new, empty hashtable with the specified initial capacity and the specified load factor.
     */
    public SpecialHashtable (int initialCapacity, float loadFactor)
    {
        super (initialCapacity, loadFactor);
    }

    /**
     * Returns the value to which the specified key is mapped in this hashtable.
     * This is translated to provide backwards compatibility.
     */
    public Object get (Object key)
    {
        Object ret;

        ret = getRaw (key);
        if ("$<NULL>$" == ret)
            ret = null;
        else if ("$<NOTHING>$" == ret)
            ret = "";

        return (ret);
    }

    /**
     * Returns the raw value to which the specified key is mapped in this hashtable.
     */
    public Object getRaw (Object key)
    {
        return (super.get (key));
    }
}
