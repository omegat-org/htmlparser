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

package org.htmlparser.lexer.nodes;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.htmlparser.lexer.Cursor;

import org.htmlparser.lexer.Page;
import org.htmlparser.parserHelper.SpecialHashtable;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * TagNode represents a generic tag. This class allows users to register specific
 * tag scanners, which can identify links, or image references. This tag asks the
 * scanners to run over the text, and identify. It can be used to dynamically
 * configure a parser.
 * @author Kaarle Kaila 23.10.2001
 */
public class TagNode extends AbstractNode
{
	public static final String TYPE = "TAG";
	/**
	 * Constant used as value for the value of the tag name
	 * in parseParameters  (Kaarle Kaila 3.8.2001)
	 */
	public final static String TAGNAME = "$<TAGNAME>$";
	public final static String EMPTYTAG = "$<EMPTYTAG>$";
    public final static String NULLVALUE = "$<NULL>$";
    public final static String NOTHING = "$<NOTHING>$";
	private final static String EMPTY_STRING="";
	
	private boolean emptyXmlTag = false;

    /**
	 * The tag attributes.
     * Objects of type Attribute.
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
	 * In case the tag is parsed at the scan method this will return value of a
	 * parameter not implemented yet
	 * @param name of parameter
	 */
	public String getAttribute (String name)
    {
	    return ((String)getAttributes().get(name.toUpperCase()));
	}

	/**
	 * Set attribute with given key, value pair.
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, String value)
    {
		getAttributes ().put(key,value);
	}

	/**
	 * In case the tag is parsed at the scan method this will return value of a
	 * parameter not implemented yet
	 * @param name of parameter
	 * @deprecated use getAttribute instead
	 */
	public String getParameter(String name)
    {
		return (String)getAttributes().get (name.toUpperCase());
	}
	
	/**
	 * Gets the attributes in the tag.
     * NOTE: Values of the extended hashtable are two element arrays of String,
     * with the first element being the original name (not uppercased), 
     * and the second element being the value.
	 * @return Returns a special hashtable of attributes in two element String arrays.
	 */
	public Vector getAttributesEx()
    {
		return mAttributes;
	}

	/**
	 * Gets the attributes in the tag.
	 * @return Returns a Hashtable of attributes
	 */
	public Hashtable getAttributes()
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
            ret.put (TAGNAME, attribute.getName ().toUpperCase ());
            // the rest
            for (int i = 1; i < attributes.size (); i++)
            {
                attribute = (Attribute)attributes.elementAt (i);
                if (null != attribute.getName ())
                {
                    value = attribute.getValue ();
                    if ('\'' == attribute.getQuote ())
                    {
                        _value = new StringBuffer (value.length () + 2);
                        _value.append ("'");
                        _value.append (value);
                        _value.append ("'");
                        value =  _value.toString ();
                    }
                    else if ('"' == attribute.getQuote ())
                    {
                        _value = new StringBuffer (value.length () + 2);
                        _value.append ("\"");
                        _value.append (value);
                        _value.append ("\"");
                        value =  _value.toString ();
                    }
                    else if ((null != value) && value.equals (""))
                        value = NOTHING;
                    if (null == value)
                        value = NULLVALUE;
                    ret.put (attribute.getName (), value);
                }
            }
        }
        else
            ret.put (TAGNAME, "");

        return (ret);
	}

    public String getTagName(){
	    return getParameter(TAGNAME);
	}

    /**
	 * Return the text contained in this tag
	 */
	public String getText()
    {
		return (mPage.getText (elementBegin () + 1, elementEnd () - 1));
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
            attribute = new Attribute (key, value, quote);
			att.addElement (attribute);
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
	public void setTagBegin(int tagBegin) {
		this.nodeBegin = tagBegin;
	}

	/**
	 * Gets the nodeBegin.
	 * @return The nodeBegin value.
	 */
	public int getTagBegin() {
		return (nodeBegin);
	}
	
	/**
	 * Sets the nodeEnd.
	 * @param tagEnd The nodeEnd to set
	 */
	public void setTagEnd(int tagEnd) {
		this.nodeEnd = tagEnd;
	}
	
	/**
	 * Gets the nodeEnd.
	 * @return The nodeEnd value.
	 */
	public int getTagEnd() {
		return (nodeEnd);
	}

    public void setText (String text)
    {
        try
        {
            mPage = new Page (text);
            nodeBegin = 0;
            nodeEnd = text.length ();
        }
        catch (ParserException pe)
        {
        }
    }

    public String toPlainTextString() {
		return EMPTY_STRING;
	}

	/**
	 * A call to a tag's toHTML() method will render it in HTML.
	 * @see org.htmlparser.Node#toHtml()
	 */
	public String toHtml()
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
		if (isEmptyXmlTag ())
            ret.append ("/");
		ret.append (">");

		return (ret.toString ());
    }

	/**
	 * Print the contents of the tag
	 */
	public String toString()
	{
        String tag;
        Cursor start;
        Cursor end;

        tag = getTagName ();
        if (tag.startsWith ("/"))
            tag = "End";
        else
            tag = "Tag";
        start = new Cursor (getPage (), elementBegin ());
        end = new Cursor (getPage (), elementEnd ());
		return (tag + " (" + start.toString () + "," + end.toString () + "): " + getText ());
	}

    /**
     * Determines if the given tag breaks the flow of text.
     * @return <code>true</code> if following text would start on a new line,
     * <code>false</code> otherwise.
     */
    public boolean breaksFlow ()
    {
        return (breakTags.containsKey (getText ().toUpperCase ()));
    }

    /**
     * This method verifies that the current tag matches the provided
     * filter. The match is based on the string object and not its contents,
     * so ensure that you are using static final filter strings provided
     * in the tag classes.
     * @see org.htmlparser.Node#collectInto(NodeList, String)
     */
	public void collectInto(NodeList collectionList, String filter)
    {
	}

	/**
	 * Returns table of attributes in the tag
	 * @return Hashtable
	 * @deprecated This method is deprecated. Use getAttributes() instead.
	 */
	public Hashtable getParsed() {
		return getAttributes ();
	}

	/**
	 * Sometimes, a scanner may need to request a re-evaluation of the
	 * attributes in a tag. This may happen when there is some correction
	 * activity. An example of its usage can be found in ImageTag.
	 * <br>
	 * <B>Note:<B> This is an intensive task, hence call only when
	 * really necessary
	 * @return Hashtable
	 */
	public Hashtable redoParseAttributes()
    {
        mAttributes = null;
        getAttributesEx ();
		return (getAttributes ());
	}

	public void accept(Object visitor) {
	}

	public String getType() {
		return TYPE;
	}

	/**
	 * Is this an empty xml tag of the form<br>
	 * &lt;tag/&gt; 
	 * @return boolean
	 */
	public boolean isEmptyXmlTag() {
		return emptyXmlTag;
	}

	public void setEmptyXmlTag(boolean emptyXmlTag) {
		this.emptyXmlTag = emptyXmlTag;
	}

}
