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
//
// This class was contributed by
// Derrick Oswald
//

package org.htmlparser.lexer.nodes;

import org.htmlparser.lexer.Page;

/**
 * An attribute within a tag.
 * <br>If Name is null, it is whitepace and Value has the text.
 * <br>If Name is not null, and Value is null it's a standalone attribute.
 * <br>If Name is not null, and Value is "", and Quote is zero it's an empty attribute.
 * <br>If Name is not null, and Value is "", and Quote is ' it's an empty single quoted attribute.
 * <br>If Name is not null, and Value is "", and Quote is " it's an empty double quoted attribute.
 * <br>If Name is not null, and Value is something, and Quote is zero it's a naked attribute.
 * <br>If Name is not null, and Value is something, and Quote is ' it's a single quoted attribute.
 * <br>If Name is not null, and Value is something, and Quote is " it's a double quoted attribute.
 * <br>All other states are illegal.
 * <p>
 * The attribute can be 'lazy loaded' by providing the page and cursor offsets
 * into the page for the name and value. In this case if the starting offset of
 * the name is less than zero, the name is null, and if the ending offset of the
 * value is less than zero, the value is null.. This is done for speed, since
 * if the name and value are not been needed we can avoid the cost and memory
 * overhead of creating the strings.
 */
public class Attribute
{
    /**
     * The page this attribute is extracted from.
     */
    protected Page mPage;

    /**
     * The starting offset of the name within the page.
     * If negative, the name is considered <code>null</code>.
     */
    protected int mNameStart;

    /**
     * The ending offset of the name within the page.
     */
    protected int mNameEnd;

    /**
     * The starting offset of the value within the page.
     * If negative, the value is considered <code>null</code>.
     */
    protected int mValueStart;

    /**
     * The ending offset of the name within the page.
     */
    protected int mValueEnd;

    /**
     * The name of this attribute.
     * The part before the equals sign, or the stand-alone attribute.
     * This will be <code>null</code> if the name has not been extracted from
     * the page, or the name starting offset is negative.
     */
    protected String mName;

    /**
     * The value of the attribute.
     * The part after the equals sign.
     * This will be <code>null</code> if the value has not been extracted from
     * the page, or the value starting offset is negative.
     */
    protected String mValue;

    /**
     * The quote, if any, surrounding the value of the attribute, if any.
     */
    protected char mQuote;

    /**
     * Create an attribute.
     * @param page The page containing the attribute.
     * @param name_start The starting offset of the name within the page.
     * If this is negative, the name is considered null.
     * @param name_end The ending offset of the name within the page.
     * @param value_start he starting offset of the value within the page.
     * If this is negative, the value is considered null.
     * @param value_end The ending offset of the value within the page.
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
        mPage = null;
        mNameStart = 0;
        mNameEnd = 0;
        mValueStart = 0;
        mValueEnd = 0;
        mName = name;
        mValue = value;
        mQuote = quote;
    }

    /**
     * Create a standalone attribute with the name given.
     * @param name The name of this attribute.
     */
    public Attribute (String name)
    {
        this (name, (String)null, (char)0);
    }

    /**
     * Get the name of this attribute.
     * The part before the equals sign, or the stand-alone attribute.
     * @return The name, or <code>null</code> if it's just a whitepace 'attribute'.
     */
    public String getName ()
    {
        if (null == mName)
            if ((null != mPage) && (0 <= mNameStart))
                mName = mPage.getText (mNameStart, mNameEnd);
        return (mName);
    }

    /**
     * Predicate to determine if this attribute is whitespace.
     * @return <code>true</code> if this attribute is whitespace,
     * <code>false</code> if it is a real attribute.
     */
    public boolean isWhitespace ()
    {
        return (null == getName ());
    }

    /**
     * Predicate to determine if this attribute has no equals sign (or value).
     * @return <code>true</code> if this attribute is a standalone attribute.
     * <code>false</code> if has an equals sign.
     */
    public boolean isStandAlone ()
    {
        return (-1 == mValueStart);
    }

    /**
     * Predicate to determine if this attribute has an equals sign but no value.
     * @return <code>true</code> if this attribute is an empty attribute.
     * <code>false</code> if has an equals sign and a value.
     */
    public boolean isEmpty ()
    {
        return ((-1 != mValueStart) && (-1 == mValueEnd));
    }

    /**
     * Predicate to determine if this attribute has a value.
     * @return <code>true</code> if this attribute has a value.
     * <code>false</code> if it is empty or standalone.
     */
    public boolean isValued ()
    {
        return ((-1 != mValueStart) && (-1 != mValueEnd));
    }

    /**
     * Get the value of the attribute.
     * The part after the equals sign, or the text if it's just a whitepace 'attribute'.
     * <em>NOTE: This does not include any quotes that may have enclosed the value.</em>
     * @return The value, or <code>null</code> if it's a stand-alone attribute,
     * or the text if it's just a whitepace 'attribute'.
     */
    public String getValue ()
    {
        if (null == mValue)
            if ((null != mPage) && (0 <= mValueEnd))
                mValue = mPage.getText (mValueStart, mValueEnd);
        return (mValue);
    }

    /**
     * Get the raw value of the attribute.
     * The part after the equals sign, or the text if it's just a whitepace 'attribute'.
     * @return The value, or <code>null</code> if it's a stand-alone attribute,
     * or the text if it's just a whitepace 'attribute'.
     */
    public String getRawValue ()
    {
        char quote;
        StringBuffer buffer;
        String ret;

        ret = getValue ();
        if (null != ret && (0 != (quote = getQuote ())))
        {
            buffer = new StringBuffer (ret.length() + 2);
            buffer.append (quote);
            buffer.append (ret);
            buffer.append (quote);
            ret = buffer.toString ();
        }

        return (ret);
    }

    /**
     * Get the raw value of the attribute.
     * The part after the equals sign, or the text if it's just a whitepace 'attribute'.
     * @return The value, or <code>null</code> if it's a stand-alone attribute,
     * or the text if it's just a whitepace 'attribute'.
     */
    public void getRawValue (StringBuffer buffer)
    {
        char quote;

        if (null == mValue)
        {
            if (0 <= mValueEnd)
            {
                if (0 != (quote = getQuote ()))
                    buffer.append (quote);
                mPage.getText (buffer, mValueStart, mValueEnd);
                if (0 != quote)
                    buffer.append (quote);
            }
        }
        else
        {
            if (0 != (quote = getQuote ()))
                buffer.append (quote);
            buffer.append (mValue);
            if (0 != quote)
                buffer.append (quote);
        }
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
     * Set the quote surrounding the value of the attribute.
     * @param quote The new quote value.
     */
    public void setQuote (char quote)
    {
        mQuote = quote;
    }

    public Page getPage ()
    {
        return (mPage);
    }

    public int getNameStartPosition ()
    {
        return (mNameStart);
    }

    public void setNameStartPosition (int start)
    {
        mNameStart = start;
        mName = null;
    }

    public int getNameEndPosition ()
    {
        return (mNameEnd);
    }

    public void setNameEndPosition (int end)
    {
        mNameEnd = end;
        mName = null;
    }

    public int getValueStartPosition ()
    {
        return (mValueStart);
    }

    public void setValueStartPosition (int start)
    {
        mValueStart = start;
        mValue = null;
    }

    public int getValueEndPosition ()
    {
        return (mValueEnd);
    }

    public void setValueEndPosition (int end)
    {
        mValueEnd = end;
        mValue = null;
    }

    /**
     * Get a text representation of this attribute.
     * Suitable for insertion into a start tag, the output is one of
     * the forms:
     * <code>
     * <pre>
     * value
     * name
     * name=value
     * name='value'
     * name="value"
     * </pre>
     * </code>
     * @param buffer The accumulator for placing the text into.
     */
    public void toString (StringBuffer buffer)
    {
        String name;

        name = getName ();
        if (null == name)
            getRawValue (buffer);
        else
        {
            buffer.append (name);
            if (0 <= mValueStart)
                if (null == mPage)
                    buffer.append ("=");
                else
                    mPage.getText (buffer, mNameEnd, mValueStart - (0 == getQuote () ? 0 : 1));
            if (0 <= mValueEnd)
                getRawValue (buffer);
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
                if (null == mPage)
                    length += 1;
                else
                    length += mValueStart - (0 == getQuote () ? 1 : 0) - mNameEnd;
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
