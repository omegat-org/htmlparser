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

package org.htmlparser.lexer.nodes;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SpecialHashtable;
import org.htmlparser.util.Translate;

/**
 * TagNode represents a generic tag.
 * 
 */
public class TagNode
    extends
        AbstractNode
{
    /**
     * The tag attributes.
     * Objects of type {@link Attribute}.
     */
    protected Vector mAttributes;

    /**
     * Set of tags that breaks the flow.
     */
    protected static Hashtable breakTags;
    static
    {
        breakTags = new Hashtable (30);
        breakTags.put ("BLOCKQUOTE", Boolean.TRUE);
        breakTags.put ("BODY", Boolean.TRUE);
        breakTags.put ("BR", Boolean.TRUE);
        breakTags.put ("CENTER", Boolean.TRUE);
        breakTags.put ("DD", Boolean.TRUE);
        breakTags.put ("DIR", Boolean.TRUE);
        breakTags.put ("DIV", Boolean.TRUE);
        breakTags.put ("DL", Boolean.TRUE);
        breakTags.put ("DT", Boolean.TRUE);
        breakTags.put ("FORM", Boolean.TRUE);
        breakTags.put ("H1", Boolean.TRUE);
        breakTags.put ("H2", Boolean.TRUE);
        breakTags.put ("H3", Boolean.TRUE);
        breakTags.put ("H4", Boolean.TRUE);
        breakTags.put ("H5", Boolean.TRUE);
        breakTags.put ("H6", Boolean.TRUE);
        breakTags.put ("HEAD", Boolean.TRUE);
        breakTags.put ("HR", Boolean.TRUE);
        breakTags.put ("HTML", Boolean.TRUE);
        breakTags.put ("ISINDEX", Boolean.TRUE);
        breakTags.put ("LI", Boolean.TRUE);
        breakTags.put ("MENU", Boolean.TRUE);
        breakTags.put ("NOFRAMES", Boolean.TRUE);
        breakTags.put ("OL", Boolean.TRUE);
        breakTags.put ("P", Boolean.TRUE);
        breakTags.put ("PRE", Boolean.TRUE);
        breakTags.put ("TD", Boolean.TRUE);
        breakTags.put ("TH", Boolean.TRUE);
        breakTags.put ("TITLE", Boolean.TRUE);
        breakTags.put ("UL", Boolean.TRUE);
    }

    /**
     * Create an empty tag.
     */
    public TagNode ()
    {
        this (null, -1, -1, new Vector ());
    }

    /**
     * Create a tag with the location and attributes provided
     * @param page The page this tag was read from.
     * @param start The starting offset of this node within the page.
     * @param end The ending offset of this node within the page.
     * @param attributes The list of attributes that were parsed in this tag.
     * @see Attribute
     */
    public TagNode (Page page, int start, int end, Vector attributes)
    {
        super (page, start, end);
        mAttributes = attributes;
    }

    /**
     * Returns the value of an attribute.
     * @param name Name of attribute, case insensitive.
     * @return The value associated with the attribute or null if it does
     * not exist, or is a stand-alone or
     */
    public String getAttribute (String name)
    {
        Attribute attribute;
        String ret;

        ret = null;

        if (name.equalsIgnoreCase (SpecialHashtable.TAGNAME))
            ret = ((Attribute)getAttributesEx ().elementAt (0)).getName ();
        else
        {
            attribute = getAttributeEx (name);
            if (null != attribute)
                ret = attribute.getValue ();
        }

        return (ret);
    }

    /**
     * Set attribute with given key, value pair.
     * Figures out a quote character to use if necessary.
     * @param key The name of the attribute.
     * @param value The value of the attribute.
     */
    public void setAttribute (String key, String value)
    {
        char ch;
        boolean needed;
        boolean singleq;
        boolean doubleq;
        String ref;
        StringBuffer buffer;
        char quote;

        // first determine if there's whitespace in the value
        // and while we'return at it find a suitable quote character
        needed = false;
        singleq = true;
        doubleq = true;
        for (int i = 0; i < value.length (); i++)
        {
            ch = value.charAt (i);
            if (Character.isWhitespace (ch))
                needed = true;
            else if ('\'' == ch)
                singleq  = false;
            else if ('"' == ch)
                doubleq = false;
        }

        // now apply quoting
        if (needed)
        {
            if (doubleq)
                quote = '"';
            else if (singleq)
                quote = '\'';
            else
            {
                // uh-oh, we need to convert some quotes into character references
                // convert all double quotes into &#34;
                quote = '"';
                ref = Translate.convertToString (quote);
                // JDK 1.4: value = value.replaceAll ("\"", ref);
                buffer = new StringBuffer (value.length() * 5);
                for (int i = 0; i < value.length (); i++)
                {
                    ch = value.charAt (i);
                    if ('"' == ch)
                        buffer.append (ref);
                    else
                        buffer.append (ch);
                }
                value = buffer.toString ();
            }
        }
        else
            quote = 0;
        setAttribute (key, value, quote);
    }

    /**
     * Set attribute with given key, value pair where the value is quoted by quote.
     * @param key The name of the attribute.
     * @param value The value of the attribute.
     * @param quote The quote character to be used around value.
     * If zero, it is an unquoted value.
     */
    public void setAttribute (String key, String value, char quote)
    {
        setAttribute (new Attribute (key, value, quote));
    }

    /**
     * Returns the attribute with the given name.
     * @param name Name of attribute, case insensitive.
     * @return The attribute or null if it does
     * not exist.
     */
    public Attribute getAttributeEx (String name)
    {
        Vector attributes;
        int size;
        Attribute attribute;
        String string;
        Attribute ret;

        ret = null;

        attributes = getAttributesEx ();
        size = attributes.size ();
        for (int i = 0; i < size; i++)
        {
            attribute = (Attribute)attributes.elementAt (i);
            string = attribute.getName ();
            if ((null != string) && name.equalsIgnoreCase (string))
            {
                ret = attribute;
                i = size; // exit fast
            }
        }

        return (ret);
    }

    /**
     * Set an attribute.
     * This replaces an attribute of the same name.
     * To set the zeroth attribute (the tag name), use setTagName().
     * @param attribute The attribute to set.
     */
    public void setAttribute (Attribute attribute)
    {
        boolean replaced;
        Vector attributes;
        int length;
        String name;
        Attribute test;
        String test_name;

        replaced = false;
        attributes = getAttributesEx ();
        length =  attributes.size ();
        if (0 < length)
        {
            name = attribute.getName ();
            for (int i = 1; i < attributes.size (); i++)
            {
                test = (Attribute)attributes.elementAt (i);
                test_name = test.getName ();
                if (null != test_name)
                    if (test_name.equalsIgnoreCase (name))
                    {
                        attributes.setElementAt (attribute, i);
                        replaced = true;
                    }
            }
        }
        if (!replaced)
        {
            // add whitespace between attributes
            if (!((Attribute)attributes.elementAt (length - 1)).isWhitespace ())
                attributes.addElement (new Attribute ((String)null, " ", (char)0));
            attributes.addElement (attribute);
        }
    }

    /**
     * Eqivalent to <code>getAttribute (name)</code>.
     * @param name Name of attribute.
     * @deprecated use getAttribute instead
     */
    public String getParameter (String name)
    {
        return (getAttribute (name));
    }

    /**
     * Gets the attributes in the tag.
     * @return Returns the list of {@link Attribute Attributes} in the tag.
     */
    public Vector getAttributesEx ()
    {
        return (mAttributes);
    }

    /**
     * Gets the attributes in the tag.
     * This is not the preferred  method to get attributes, see {@link
     * #getAttributesEx getAttributesEx} which returns a list of {@link
     * Attribute} objects, which offer more information than the simple
     * <code>String</code> objects available from this <code>Hashtable</code>.
     * @return Returns a list of name/value pairs representing the attributes.
     * These are not in order, the keys (names) are capitalized and the values
     * are not quoted, even if they need to be. The table <em>will</em> return
     * <code>null</code> if there was no value for an attribute (no equals
     * sign or nothing to the right of the equals sign). A special entry with
     * a key of SpecialHashtable.TAGNAME ("$<TAGNAME>$") holds the tag name.
     */
    public Hashtable getAttributes ()
    {
        Vector attributes;
        Attribute attribute;
        String value;
        StringBuffer _value;
        Hashtable ret;

        ret = new SpecialHashtable ();
        attributes = getAttributesEx ();
        if (0 < attributes.size ())
        {
            // special handling for the node name
            attribute = (Attribute)attributes.elementAt (0);
            ret.put (SpecialHashtable.TAGNAME, attribute.getName ().toUpperCase ());
            // the rest
            for (int i = 1; i < attributes.size (); i++)
            {
                attribute = (Attribute)attributes.elementAt (i);
                if (!attribute.isWhitespace ())
                {
                    value = attribute.getValue ();
                    if (attribute.isEmpty ())
                        value = SpecialHashtable.NOTHING;
                    if (null == value)
                        value = SpecialHashtable.NULLVALUE;
                    ret.put (attribute.getName ().toUpperCase (), value);
                }
            }
        }
        else
            ret.put (SpecialHashtable.TAGNAME, "");

        return (ret);
    }

    /**
     * Return the name of this tag.
     * <p>
     * <em>
     * Note: This value is converted to uppercase and does not
     * begin with "/" if it is an end tag. Nor does it end with
     * a slash in the case of an XML type tag.
     * To get at the original text of the tag name use
     * {@link #getRawTagName getRawTagName()}.
     * </em>
     * @return The tag name.
     */
    public String getTagName ()
    {
        String ret;

        ret = getRawTagName ();
        if (null != ret)
        {
            ret = ret.toUpperCase ();
            if (ret.startsWith ("/"))
                ret = ret.substring (1);
            if (ret.endsWith ("/"))
                ret = ret.substring (0, ret.length () - 1);
        }

        return (ret);
    }

    /**
     * Return the name of this tag.
     * @return The tag name or null if this tag contains nothing or only
     * whitespace.
     */
    public String getRawTagName ()
    {
        Vector attributes;
        String ret;

        ret = null;
        
        attributes = getAttributesEx ();
        if (0 != attributes.size ())
            ret = ((Attribute)attributes.elementAt (0)).getName ();

        return (ret);
    }

    /**
     * Set the name of this tag.
     * This creates or replaces the first attribute of the tag (the
     * zeroth element of the attribute vector).
     * @param name The tag name.
     */
    public void setTagName (String name)
    {
        Attribute attribute;
        Vector attributes;
        Attribute zeroth;

        attribute = new Attribute (name, null, (char)0);
        attributes = getAttributesEx ();
        if (0 == attributes.size ())
            // nothing added yet
            attributes.addElement (attribute);
        else
        {
            zeroth = (Attribute)attributes.elementAt (0);
            // check forn attribute that looks like a name
            if ((null == zeroth.getValue ()) && (0 == zeroth.getQuote ()))
                attributes.setElementAt (attribute, 0);
            else
                attributes.insertElementAt (attribute, 0);
        }
    }

    /**
     * Return the text contained in this tag.
     * @return The complete contents of the tag (within the angle brackets).
     */
    public String getText ()
    {
        String ret;
        
        //ret = mPage.getText (elementBegin () + 1, elementEnd () - 1);
        ret = toHtml ();
        ret = ret.substring (1, ret.length () - 1);
        
        return (ret);
    }

    /**
     * Sets the attributes.
     * @param attributes The attribute collection to set.
     */
    public void setAttributes (Hashtable attributes)
    {
        Vector att;
        String key;
        String value;
        char quote;
        Attribute attribute;

        att = new Vector ();
        for (Enumeration e = attributes.keys (); e.hasMoreElements (); )
        {
            key = (String)e.nextElement ();
            value = (String)attributes.get (key);
            if (value.startsWith ("'") && value.endsWith ("'") && (2 <= value.length ()))
            {
                quote = '\'';
                value = value.substring (1, value.length () - 1);
            }
            else if (value.startsWith ("\"") && value.endsWith ("\"") && (2 <= value.length ()))
            {
                quote = '"';
                value = value.substring (1, value.length () - 1);
            }
            else
                quote = (char)0;
            if (key.equals (SpecialHashtable.TAGNAME))
            {
                attribute = new Attribute (value, null, quote);
                att.insertElementAt (attribute, 0);
            }
            else
            {
                // add whitespace between attributes
                attribute = new Attribute ((String)null, " ", (char)0);
                att.addElement (attribute);
                attribute = new Attribute (key, value, quote);
                att.addElement (attribute);
            }
        }
        this.mAttributes = att;
    }

    /**
     * Sets the attributes.
     * NOTE: Values of the extended hashtable are two element arrays of String,
     * with the first element being the original name (not uppercased),
     * and the second element being the value.
     * @param attribs The attribute collection to set.
     */
    public void setAttributesEx (Vector attribs)
    {
        mAttributes = attribs;
    }

    /**
     * Sets the nodeBegin.
     * @param tagBegin The nodeBegin to set
     */
    public void setTagBegin (int tagBegin)
    {
        nodeBegin = tagBegin;
    }

    /**
     * Gets the nodeBegin.
     * @return The nodeBegin value.
     */
    public int getTagBegin ()
    {
        return (nodeBegin);
    }

    /**
     * Sets the nodeEnd.
     * @param tagEnd The nodeEnd to set
     */
    public void setTagEnd (int tagEnd)
    {
        nodeEnd = tagEnd;
    }

    /**
     * Gets the nodeEnd.
     * @return The nodeEnd value.
     */
    public int getTagEnd ()
    {
        return (nodeEnd);
    }

    public void setText (String text)
    {
        Lexer lexer;
        TagNode output;
        
        lexer = new Lexer (text);
        try
        {
            output = (TagNode)lexer.nextNode ();
            mPage = output.getPage ();
            nodeBegin = output.elementBegin ();
            nodeEnd = output.elementEnd ();
            mAttributes = output.getAttributesEx ();
        }
        catch (ParserException pe)
        {
            throw new IllegalArgumentException (pe.getMessage ());
        }
    }

    /**
     * Get the plain text from this node.
     * @return An empty string (tag contents do not display in a browser).
     * If you want this tags HTML equivalent, use {@link #toHtml toHtml()}.
     */
    public String toPlainTextString ()
    {
        return ("");
    }

    /**
     * A call to a tag's toHTML() method will render it in HTML.
     * @see org.htmlparser.Node#toHtml()
     */
    public String toHtml ()
    {
        StringBuffer ret;
        Vector attributes;
        Attribute attribute;

        ret = new StringBuffer ();
        attributes = getAttributesEx ();
        ret.append ("<");
        if (0 < attributes.size ())
        {
            // special handling for the node name
            attribute = (Attribute)attributes.elementAt (0);
            ret.append (attribute.getName ());
            // the rest
            for (int i = 1; i < attributes.size (); i++)
            {
                attribute = (Attribute)attributes.elementAt (i);
                attribute.toString (ret);
            }
        }
        ret.append (">");

        return (ret.toString ());
    }

    /**
     * Print the contents of the tag
     */
    public String toString ()
    {
        String type;
        Cursor start;
        Cursor end;

        if (isEndTag ())
            type = "End";
        else
            type = "Tag";
        start = new Cursor (getPage (), elementBegin ());
        end = new Cursor (getPage (), elementEnd ());
        return (type + " (" + start.toString () + "," + end.toString () + "): " + getText ());
    }

    /**
     * Determines if the given tag breaks the flow of text.
     * @return <code>true</code> if following text would start on a new line,
     * <code>false</code> otherwise.
     */
    public boolean breaksFlow ()
    {
        return (breakTags.containsKey (getTagName ().toUpperCase ()));
    }

    /**
     * This method verifies that the current tag matches the provided
     * filter. The match is based on the string object and not its contents,
     * so ensure that you are using static final filter strings provided
     * in the tag classes.
     * @see org.htmlparser.Node#collectInto(NodeList, String)
     */
    public void collectInto (NodeList collectionList, String filter)
    {
    }

    /**
     * Returns table of attributes in the tag
     * @return Hashtable
     * @deprecated This method is deprecated. Use getAttributes() instead.
     */
    public Hashtable getParsed ()
    {
        return getAttributes ();
    }

    public void accept (Object visitor)
    {
    }

    /**
     * Is this an empty xml tag of the form &lt;tag/&gt;.
     * @return true if the last character of the last attribute is a '/'.
     */
    public boolean isEmptyXmlTag ()
    {
        Vector attributes;
        int size;
        Attribute attribute;
        String name;
        int length;
        boolean ret;

        ret = false;

        attributes = getAttributesEx ();
        size = attributes.size ();
        if (0 < size)
        {
            attribute = (Attribute)attributes.elementAt (size - 1);
            name = attribute.getName ();
            if (null != name)
            {
                length = name.length ();
                ret = name.charAt (length - 1) == '/';
            }
        }

        return (ret);
    }

    /**
     * Set this tag to be an empty xml node, or not.
     * Adds or removes an ending slash on the tag.
     * @param emptyXmlTag If true, ensures there is an ending slash in the node,
     * i.e. &lt;tag/&gt;, otherwise removes it.
     */
    public void setEmptyXmlTag (boolean emptyXmlTag)
    {
        Vector attributes;
        int size;
        Attribute attribute;
        String name;
        String value;
        int length;
        
        attributes = getAttributesEx ();
        size = attributes.size ();
        if (0 < size)
        {
            attribute = (Attribute)attributes.elementAt (size - 1);
            name = attribute.getName ();
            if (null != name)
            {
                length = name.length ();
                value = attribute.getValue ();
                if (null == value)
                    if (name.charAt (length - 1) == '/')
                    {
                        // already exists, remove if requested
                        if (!emptyXmlTag)
                            if (1 == length)
                                attributes.removeElementAt (size - 1);
                            else
                            {
                                // this shouldn't happen, but covers the case
                                // where no whitespace separates the slash
                                // from the previous attribute
                                name = name.substring (0, length - 1);
                                attribute = new Attribute (name);
                                attributes.removeElementAt (size - 1);
                                attributes.addElement (attribute);
                            }
                    }
                    else
                    {
                        // ends with attribute, add whitespace + slash if requested
                        if (emptyXmlTag)
                        {
                            attribute = new Attribute ((String)null, " ", (char)0);
                            attributes.addElement (attribute);
                            attribute = new Attribute ("/");
                            attributes.addElement (attribute);
                        }
                    }
                else
                {
                    // some valued attribute, add whitespace + slash if requested
                    if (emptyXmlTag)
                    {
                        attribute = new Attribute ((String)null, " ", (char)0);
                        attributes.addElement (attribute);
                        attribute = new Attribute ("/");
                        attributes.addElement (attribute);
                    }
                }
            }
            else
            {
                // ends with whitespace, add if requested
                if (emptyXmlTag)
                {
                    attribute = new Attribute ("/");
                    attributes.addElement (attribute);
                }
            }
        }
        else
            // nothing there, add if requested
            if (emptyXmlTag)
            {
                attribute = new Attribute ("/");
                attributes.addElement (attribute);
            }
    }

    /**
     * Predicate to determine if this tag is an end tag (i.e. &lt;/HTML&gt;).
     * @return <code>true</code> if this tag is an end tag.
     */
    public boolean isEndTag ()
    {
        String raw;
        
        raw = getRawTagName ();

        return ((null == raw) ? false : ('/' == raw.charAt (0)));
    }
}
