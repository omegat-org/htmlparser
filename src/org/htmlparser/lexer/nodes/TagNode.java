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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Page;
import org.htmlparser.parserHelper.SpecialHashtable;
import org.htmlparser.parserHelper.TagParser;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
/**
 * Tag represents a generic tag. This class allows users to register specific
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
	
	private static TagParser tagParser;
	private boolean emptyXmlTag = false;

    /**
	 * The tag attributes.
     * Objects of type Attribute.
	 */
	protected Vector mAttributes;

	/**
	 * Scanner associated with this tag (useful for extraction of filtering data from a
	 * HTML node)
	 */
	protected TagScanner thisScanner = null;

    /**
     * Set of tags that breaks the flow.
     */
    protected static HashSet breakTags;
    static
    {
        breakTags = new HashSet (30);
        breakTags.add ("BLOCKQUOTE");
        breakTags.add ("BODY");
        breakTags.add ("BR");
        breakTags.add ("CENTER");
        breakTags.add ("DD");
        breakTags.add ("DIR");
        breakTags.add ("DIV");
        breakTags.add ("DL");
        breakTags.add ("DT");
        breakTags.add ("FORM");
        breakTags.add ("H1");
        breakTags.add ("H2");
        breakTags.add ("H3");
        breakTags.add ("H4");
        breakTags.add ("H5");
        breakTags.add ("H6");
        breakTags.add ("HEAD");
        breakTags.add ("HR");
        breakTags.add ("HTML");
        breakTags.add ("ISINDEX");
        breakTags.add ("LI");
        breakTags.add ("MENU");
        breakTags.add ("NOFRAMES");
        breakTags.add ("OL");
        breakTags.add ("P");
        breakTags.add ("PRE");
        breakTags.add ("TD");
        breakTags.add ("TH");
        breakTags.add ("TITLE");
        breakTags.add ("UL");
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
	 * Locate the tag withing the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 */
//	public static Tag find(NodeReader reader,String input,int position) {
//		return tagParser.find(reader,input,position);
//	}

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
        Hashtable ret;
        
        ret = new SpecialHashtable ();
        attributes = getAttributesEx ();
        if (0 < attributes.size ())
        {
            // special handling for the node name
            attribute = (Attribute)attributes.elementAt (0);
            ret.put (org.htmlparser.tags.Tag.TAGNAME, attribute.getName ().toUpperCase ());
            // the rest
            for (int i = 1; i < attributes.size (); i++)
            {
                attribute = (Attribute)attributes.elementAt (i);
                if (null != attribute.getName ())
                {
                    value = attribute.getValue ();
                    if ('\'' == attribute.getQuote ())
                        value = "'" + value + "'";
                    else if ('"' == attribute.getQuote ())
                        value = "\"" + value + "\"";
                    else if ((null != value) && value.equals (""))
                        value = NOTHING;
                    if (null == value)
                        value = NULLVALUE;
                    ret.put (attribute.getName (), value);
                }
            }
        }
        else
            ret.put (org.htmlparser.tags.Tag.TAGNAME, "");

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
	 * Return the scanner associated with this tag.
	 */
	public TagScanner getThisScanner()
	{
		return thisScanner;
	}

    /**
     * Extract the first word from the given string.
     * Words are delimited by whitespace or equals signs.
     * @param s The string to get the word from.
     * @return The first word.
     */
//    public static String extractWord (String s)
//    {
//        int length;
//        boolean parse;
//        char ch;
//        StringBuffer ret;
//
//        length = s.length ();
//        ret = new StringBuffer (length);
//		parse = true;
//		for (int i = 0; i < length && parse; i++)
//        {
//			ch = s.charAt (i);
//			if (Character.isWhitespace (ch) || ch == '=')
//                parse = false;
//            else
//                ret.append (Character.toUpperCase (ch));
//		}
//
//		return (ret.toString ());
//	}
	
	/**
	 * Scan the tag to see using the registered scanners, and attempt identification.
	 * @param url URL at which HTML page is located
	 * @param reader The NodeReader that is to be used for reading the url
	 */
//	public AbstractNode scan(Map scanners,String url,NodeReader reader) throws ParserException
//	{
//		if (tagContents.length()==0) return this;
//		try {
//			boolean found=false;
//			AbstractNode retVal=null;
//			// Find the first word in the scanners
//			String firstWord = extractWord(tagContents.toString());
//			// Now, get the scanner associated with this.
//			TagScanner scanner = (TagScanner)scanners.get(firstWord);
//			
//			// Now do a deep check
//			if (scanner != null &&
//					scanner.evaluate(
//						tagContents.toString(),
//						reader.getPreviousOpenScanner()
//					)
//				)
//			{
//				found=true;
//                TagScanner save;
//                save = reader.getPreviousOpenScanner ();
//				reader.setPreviousOpenScanner(scanner);
//				retVal=scanner.createScannedNode(this,url,reader,tagLine);
//				reader.setPreviousOpenScanner(save);
//			}
//
//			if (!found) return this;
//			else {
//			    return retVal;
//			}
//		}
//		catch (Exception e) {
//			String errorMsg;
//			if (tagContents!=null) errorMsg = tagContents.toString(); else errorMsg="null";
//			throw new ParserException("Tag.scan() : Error while scanning tag, tag contents = "+errorMsg+", tagLine = "+tagLine,e);
//		}
//	}

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
	public void setThisScanner(TagScanner scanner)
	{
		thisScanner = scanner;
	}

	public String toPlainTextString() {
		return EMPTY_STRING;
	}

	/**
	 * A call to a tag's toHTML() method will render it in HTML
	 * Most tags that do not have children and inherit from Tag,
	 * do not need to override toHTML().
	 * @see org.htmlparser.Node#toHtml()
	 */
	public String toHtml()
    {
		StringBuffer ret;
        Vector attributes;
        Attribute attribute;
        String value;

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
	 * Sets the tagParser.
	 * @param tagParser The tagParser to set
	 */
	public static void setTagParser(TagParser tagParser) {
//todo: fix this		Tag.tagParser = tagParser;
	}

    /**
     * Determines if the given tag breaks the flow of text.
     * @return <code>true</code> if following text would start on a new line,
     * <code>false</code> otherwise.
     */
    public boolean breaksFlow ()
    {
        return (breakTags.contains (getText ().toUpperCase ()));
    }

    /**
     * This method verifies that the current tag matches the provided
     * filter. The match is based on the string object and not its contents,
     * so ensure that you are using static final filter strings provided
     * in the tag classes.
     * @see org.htmlparser.Node#collectInto(NodeList, String)
     */
	public void collectInto(NodeList collectionList, String filter) {
		if (thisScanner!=null && thisScanner.getFilter()==filter) 
			collectionList.add(this);
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

	public void accept(NodeVisitor visitor) {
// todo: fix this		visitor.visitTag(this);
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
