// HTMLParser Library v1_3_20021228 - A java-based parser for HTML
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

import java.util.*;

import org.htmlparser.scanners.HTMLTagScanner;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.HTMLParameterParser;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.util.HTMLTagParser;
import org.htmlparser.visitors.HTMLVisitor;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
/**
 * HTMLTag represents a generic tag. This class allows users to register specific
 * tag scanners, which can identify links, or image references. This tag asks the
 * scanners to run over the text, and identify. It can be used to dynamically
 * configure a parser.
 * @author Kaarle Kaila 23.10.2001
 */
public class HTMLTag extends HTMLNode
{
    /**
    * Constant used as value for the value of the tag name
    * in parseParameters  (Kaarle Kaila 3.8.2001)
    */
    public final static String TAGNAME = "$<TAGNAME>$";
    public final static String EMPTYTAG = "$<EMPTYTAG>$";
	private final static int TAG_BEFORE_PARSING_STATE=1;
    private final static int TAG_BEGIN_PARSING_STATE=2;
    private final static int TAG_FINISHED_PARSING_STATE=3;
	private final static int TAG_ILLEGAL_STATE=4;
	private final static int TAG_IGNORE_DATA_STATE=5;
	private final static int TAG_IGNORE_BEGIN_TAG_STATE=6;
	private final static String EMPTY_STRING="";

	private static HTMLParameterParser paramParser = new HTMLParameterParser();
	private static HTMLTagParser tagParser;
	/**
	 * Tag contents will have the contents of the comment tag.
   */
	StringBuffer tagContents;
	/**
	* tag parameters parsed into this hashtable
	* not implemented yet
	* added by Kaarle Kaila 23.10.2001
	*/
	protected Hashtable attributes=null;

	/**
	 * Scanner associated with this tag (useful for extraction of filtering data from a
	 * HTML node)
	 */
	protected HTMLTagScanner thisScanner=null;
	private java.lang.String tagLine;

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
	 * Set the HTMLTag with the beginning posn, ending posn and tag contents (in
	 * a tagData object.
	 * @param tagData The data for this tag
	 */
	public HTMLTag(HTMLTagData tagData)
	{
		super(tagData.getTagBegin(),tagData.getTagEnd());
		this.tagContents = new StringBuffer();
		this.tagContents.append(tagData.getTagContents());
		this.tagLine = tagData.getTagLine();
	}

	public void append(char ch) {
		tagContents.append(ch);
	}

	public void append(String ch) {
		tagContents.append(ch);
	}

	/**
	 * Locate the tag withing the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 */
	public static HTMLTag find(HTMLReader reader,String input,int position)
	{
		return tagParser.find(reader,input,position);
	}

	/**
	 * This method is not to be called by any scanner or tag. It is
	 * an expensive method, hence it has been made private. However,
	 * there might be some circumstances when a scanner wishes to force
	 * parsing of attributes over and above what has already been parsed.
	 * To make the choice clear - we have a method - redoParseAttributes(),
	 * which can be used.
	 * @return Hashtable
	 */
	private Hashtable parseAttributes(){
	 	return paramParser.parseAttributes(this);
	}

	/**
	 * In case the tag is parsed at the scan method this will return value of a
	 * parameter not implemented yet
	 * @param name of parameter
	 */
	public String getParameter(String name){
	    return (String)getAttributes().get(name.toUpperCase());
	}

	/**
	 * Gets the attributes in the tag.
	 * @return Returns a Hashtable of attributes
	 */
	public Hashtable getAttributes() {
		if (attributes == null) {
	    	attributes = parseAttributes();
	    }
		return attributes;
	}

	public String getTagName(){
	    return (String)getAttributes().get(TAGNAME);
	}

	/**
	 * Returns the line where the tag was found
	 * @return java.lang.String
	 */
	public java.lang.String getTagLine() {
		return tagLine;
	}

	/**
	 * Return the text contained in this tag
	 */
	public String getText()
	{
		return tagContents.toString();
	}

	/**
	 * Return the scanner associated with this tag.
	 */
	public HTMLTagScanner getThisScanner()
	{
		return thisScanner;
	}

	public static String extractWord(String s) {
		String word = "";
		boolean parse=true;
		for (int i=0;i<s.length() && parse==true;i++) {
			char ch = s.charAt(i);
			if (ch==' ' || ch=='\r' || ch=='\n' || ch=='=') parse = false; else word +=ch;
		}
		word = word.toUpperCase();
		return word;
	}

	/**
	 * Scan the tag to see using the registered scanners, and attempt identification.
	 * @param url URL at which HTML page is located
	 * @param reader The HTMLReader that is to be used for reading the url
	 */
	public HTMLNode scan(Hashtable scanners,String url,HTMLReader reader) throws HTMLParserException
	{
		if (tagContents.length()==0) return this;
		try {
			boolean found=false;
			HTMLNode retVal=null;
			// Find the first word in the scanners
			String firstWord = extractWord(tagContents.toString());
			// Now, get the scanner associated with this.
			HTMLTagScanner scanner = (HTMLTagScanner)scanners.get(firstWord);
			// Now do a deep check
			if (scanner != null &&
					scanner.evaluate(
						tagContents.toString(),
						reader.getPreviousOpenScanner()
					)
				)
			{
				found=true;
				reader.setPreviousOpenScanner(scanner);
				retVal=scanner.createScannedNode(this,url,reader,tagLine);
				reader.setPreviousOpenScanner(null);
			}

			if (!found) return this;
			else {
			    return retVal;
			}
		}
		catch (Exception e) {
			String errorMsg;
			if (tagContents!=null) errorMsg = tagContents.toString(); else errorMsg="null";
			throw new HTMLParserException("HTMLTag.scan() : Error while scanning tag, tag contents = "+errorMsg+", tagLine = "+tagLine,e);
		}
	}

	/**
	 * Sets the parsed.
	 * @param parsed The parsed to set
	 */
	public void setAttributes(Hashtable parsed) {
		this.attributes = parsed;
	}

	/**
	 * Sets the nodeBegin.
	 * @param nodeBegin The nodeBegin to set
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
	 * @param nodeEnd The nodeEnd to set
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

    public void setTagLine(java.lang.String newTagLine) {
	    tagLine = newTagLine;
    }

	public void setText(String text) {
		tagContents = new StringBuffer(text);
	}

	public void setThisScanner(HTMLTagScanner scanner)
	{
		thisScanner = scanner;
	}

	public String toPlainTextString() {
		return EMPTY_STRING;
	}

	/**
	 * A call to a tag's toHTML() method will render it in HTML
	 * Most tags that do not have children and inherit from HTMLTag,
	 * do not need to override toHTML().
	 * @see org.htmlparser.HTMLNode#toHTML()
	 */
	public String toHTML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(getTagName());
		if (containsMoreThanOneKey()) sb.append(" ");
		String key,value;
                String empty=null;
		int i=0;
		for (Enumeration e = attributes.keys();e.hasMoreElements();) {
			key = (String)e.nextElement();
			i++;
			if (!key.equals(TAGNAME)) {
                          if (key.equals(EMPTYTAG)){
                            empty="/";
                          } else {
				value = getParameter(key);
				sb.append(key+"=\""+value+"\"");
				if (i<attributes.size()) sb.append(" ");
                          }
			}
		}
                if (empty != null) sb.append(empty);
		sb.append(">");
		return sb.toString();
	}

	private boolean containsMoreThanOneKey() {
		return attributes.keySet().size()>1;
	}

	/**
	 * Print the contents of the tag
	 */
	public String toString()
	{
		return "Begin Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}

	/**
	 * Sets the tagParser.
	 * @param tagParser The tagParser to set
	 */
	public static void setTagParser(HTMLTagParser tagParser) {
		HTMLTag.tagParser = tagParser;
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
     * @see org.htmlparser.HTMLNode#collectInto(Vector, String)
     */
	public void collectInto(Vector collectionVector, String filter) {
		if (thisScanner!=null && thisScanner.getFilter()==filter) collectionVector.add(this);
	}

	/**
	 * Returns table of attributes in the tag
	 * @return Hashtable
	 * @deprecated This method is deprecated. Use getAttributes() instead.
	 */
	public Hashtable getParsed() {
		return attributes;
	}

	/**
	 * Sometimes, a scanner may need to request a re-evaluation of the
	 * attributes in a tag. This may happen when there is some correction
	 * activity. An example of its usage can be found in HTMLImageTag.
	 * <br>
	 * <B>Note:<B> This is an intensive task, hence call only when
	 * really necessary
	 * @return Hashtable
	 */
	public Hashtable redoParseAttributes() {
		return parseAttributes();
	}

	public void accept(HTMLVisitor visitor) {
		visitor.visitTag(this);
	}

}
