// HTMLParser Library v1_4_20030810 - A java-based parser for HTML
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
 * An attribute within a tag.
 * <p>If Name is null, it's whitepace and Value has the text.
 * <p>If Name is not null, and Value is null it's a standalone attribute.
 * <p>If Name is not null, and Value is "", and Quote is zero it's an empty attribute.
 * <p>If Name is not null, and Value is "", and Quote is ' it's an empty single quoted attribute.
 * <p>If Name is not null, and Value is "", and Quote is " it's an empty double quoted attribute.
 * <p>If Name is not null, and Value is something, and Quote is zero it's a naked attribute.
 * <p>If Name is not null, and Value is something, and Quote is ' it's a single quoted attribute.
 * <p>If Name is not null, and Value is something, and Quote is " it's a double quoted attribute.
 */
public class Attribute
{
    Page mPage;
    int mNameStart;
    int mNameEnd;
    int mValueStart;
    int mValueEnd;

    /**
     * The name of this attribute.
     * The part before the equals sign, or the stand-alone attribute.
     */
    String mName;
    
    /**
     * The value of the attribute.
     * The part after the equals sign.
     */
    String mValue;
    
    /**
     * The quote, if any, surrounding the value of the attribute, if any.
     */
    char mQuote;

    /**
     * Create an attribute.
     * todo
     * @param quote The quote, if any, surrounding the value of the attribute,
     * (i.e. ' or "), or zero if none.
     */
    public Attribute (Page page, int name_start, int name_end, int value_start, int value_end, char quote)
    {
        mPage = page;
        mNameStart = name_start;
        mNameEnd = name_end;
        mValueStart = value_start;
        mValueEnd = value_end;
        mName = null;
        mValue = null;
        mQuote = quote;
    }

    /**
     * Create an attribute with the name, value and quote character given.
     * @param name The name of this attribute, or null if it's just whitespace.
     * @param value The value of the attribute or null if it's a stand-alone.
     * @param quote The quote, if any, surrounding the value of the attribute,
     * (i.e. ' or "), or zero if none.
     */
    public Attribute (String name, String value, char quote)
    {
        mName = name;
        mValue = value;
        mQuote = quote;
    }

    /**
     * Get the name of this attribute.
     * The part before the equals sign, or the stand-alone attribute.
     * @return The name, or <code>null</code> if it's just a whitepace 'attribute'.
     */
    public String getName ()
    {
        if (null == mName)
            if (-1 != mNameStart)
                mName = mPage.getText (mNameStart, mNameEnd);
        return (mName);
    }

    /**
     * Get the value of the attribute.
     * The part after the equals sign, or the text if it's just a whitepace 'attribute'.
     * @return The value, or <code>null</code> if it's a stand-alone attribute,
     * or the text if it's just a whitepace 'attribute'.
     */
    public String getValue ()
    {
        if (null == mValue)
            if (-1 != mValueStart)
                mValue = mPage.getText (mValueStart, mValueEnd);
        return (mValue);
    }

    /**
     * Get the quote, if any, surrounding the value of the attribute, if any.
     * @return Either ' or " if the attribute value was quoted, or zero
     * if there are no quotes around it.
     */
    public char getQuote ()
    {
        return (mQuote);
    }

    /**
     * Get a text representation of this attribute.
     * Suitable for insertion into a start tag, the output is one of
     * the forms:
     * <code>
     * <pre>
     * value
     * name
     * name= value
     * name= 'value'
     * name= "value"
     * </pre>
     * </code>
     * @param buffer The accumulator for placing the text into.
     */
    public void toString (StringBuffer buffer)
    {
        String value;
        String name;
        
        value = getValue ();
        name = getName ();
        if (null == name)
        {
            if (value != null)
                buffer.append (value);
        }
        else
        {
            buffer.append (name);
            if (null != value)
            {
                buffer.append ("=");
                if (0 != getQuote ())
                    buffer.append (getQuote ());
                buffer.append (value);
                if (0 != getQuote ())
                    buffer.append (getQuote ());
            }
        }
    }

    /**
     * Get a text representation of this attribute.
     * @return A string that can be used within a start tag.
     * @see #toString(StringBuffer)
     */
    public String toString ()
    {
        String value;
        String name;
        int length;
        StringBuffer ret;

        // calculate the size we'll need to avoid extra StringBuffer allocations
        length = 0;
        value = getValue ();
        name = getName ();
        if (null == getName ())
        {
            if (value != null)
                length += value.length ();
        }
        else
        {
            length += name.length ();
            if (null != value)
            {
                length += 1;
                length += value.length ();
                if (0 != getQuote ())
                    length += 2;
            }
        }
        ret = new StringBuffer (length);
        toString (ret);
        
        return (ret.toString ());
    }
}
