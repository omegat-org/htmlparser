// HTMLParser Library v1_2_20021016 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.html.tags;

import java.util.*;
import java.io.IOException;
import com.kizna.html.scanners.HTMLTagScanner;
import com.kizna.html.util.HTMLParameterParser;
import com.kizna.html.util.HTMLParserException;
import com.kizna.html.util.HTMLTagParser;
import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLReader;
/**
 * HTMLTag represents a generic tag. This class allows users to register specific
 * tag scanners, which can identify links, or image references. This tag asks the
 * scanners to run over the text, and identify. It can be used to dynamically 
 * configure a parser.
 */
public class HTMLTag extends HTMLNode
{
    /**
    * Constant used as value for the value of the tag name
    * in parseParameters  (Kaarle Kaila 3.8.2001)
    */
    public final static String TAGNAME = "$<TAGNAME>$";    
	public final static int TAG_BEFORE_PARSING_STATE=1;
    public final static int TAG_BEGIN_PARSING_STATE=2;
    public final static int TAG_FINISHED_PARSING_STATE=3;
	public final static int TAG_ILLEGAL_STATE=4;
	public final static int TAG_IGNORE_DATA_STATE=5;	    
	public final static int TAG_IGNORE_BEGIN_TAG_STATE=6;
	private static boolean encounteredQuery=false;
	private static Vector strictTags=null;
	private static HTMLParameterParser paramParser = new HTMLParameterParser();
	private static HTMLTagParser tagParser = new HTMLTagParser();
	/**
	 * Tag contents will have the contents of the comment tag.
   */
	StringBuffer tagContents;
	/** 
	 * The beginning position of the tag in the line
	 */		
	int tagBegin;
	/**
	 * The ending position of the tag in the line
	 */	
	int tagEnd;
	/**
	* tag parameters parsed into this hashtable
	* not implemented yet
	* added by Kaarle Kaila 23.10.2001
	*/
	protected Hashtable parsed=null;
	
	/**
	 * Scanner associated with this tag (useful for extraction of filtering data from a
	 * HTML node)
	 */
	protected HTMLTagScanner thisScanner=null;
	private java.lang.String tagLine;
	private boolean endOfLineCharState;
	/**
	 * Set the HTMLTag with the beginning posn, ending posn and tag contents
	 * @param tagBegin Beginning position of the tag
	 * @param tagEnd Ending positiong of the tag
	 * @param tagContents The contents of the tag
	 * @param tagLine The current line being parsed, where the tag was found
	 */
	public HTMLTag(int tagBegin, int tagEnd, String tagContents, String tagLine)
	{
		this.tagBegin = tagBegin;
		this.tagEnd = tagEnd;
		this.tagContents = new StringBuffer();
		this.tagContents.append(tagContents);
		this.tagLine = tagLine;
		this.endOfLineCharState = false;
	}
	public void append(char ch) {
		tagContents.append(ch);
	}
	public void append(String ch) {
		tagContents.append(ch);
	}
  	/**
	 * Returns the beginning position of the string.
	 */	
	public int elementBegin()
	{
		return tagBegin;
	}
	/**
	 * Returns the ending position fo the tag
	 */		
	public int elementEnd()
	{
		return tagEnd;
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
	 public Hashtable parseParameters(){
	 	return paramParser.parseParameters(this);
	 }
	/*
	* in case the tag is parsed at the scan method
	* this will return value of a parameter
	* not implemented yet
	* @param name of parameter
	* @author Kaarle Kaila 23.10.2001
	*/
	
	public String getParameter(String name){
	    return (String)getParsed().get(name.toUpperCase());
	}
	/**
	 * Gets the parsed.
	 * @return Returns a Hashtable
	 */
	public Hashtable getParsed() {
		if (parsed == null) {
	    	parsed = parseParameters();
	    } 		
		return parsed;
	}
	/**
	 * Gets the strictTags.
	 * @return Returns a Vector
	 */
	public static Vector getStrictTags() {
		return strictTags;
	}
	/*
	 * in case the tag is parsed at the scan method
	 * this will return the tag-name (TAG)
	 * not implemented yet
	 * @author Kaarle Kaila 23.10.2001
	 */
	public String getTagName(){
	    if (parsed == null) parsed=parseParameters();
	    return (String)parsed.get(TAGNAME);
	}
	/**
	 * Returns the tagLine
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
	/**
	 * Insert the method's description here.
	 * Creation date: (6/16/2002 3:28:38 PM)
	 * @return boolean
	 */
	public boolean isEndOfLineCharState() {
		return endOfLineCharState;
	}

	/**
	 * Scan the tag to see using the registered scanners, and attempt identification.
	 * @param url URL at which HTML page is located
	 * @param reader The HTMLReader that is to be used for reading the url
	 */
	public HTMLNode scan(Enumeration scanners,String url,HTMLReader reader) throws HTMLParserException
	{
		if (tagContents.length()==0) return this;
		try {
			boolean found=false;
			HTMLNode retVal=null;
			
			for (Enumeration e=scanners;(e.hasMoreElements() && !found);)
			{
				HTMLTagScanner scanner = (HTMLTagScanner)e.nextElement();
				if (scanner.evaluate(tagContents.toString(),reader.getPreviousOpenScanner()))
				{
					found=true;
					reader.setPreviousOpenScanner(scanner);
					retVal=scanner.createScannedNode(this,url,reader,tagLine);
					reader.setPreviousOpenScanner(null);
				}
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
	 * Insert the method's description here.
	 * Creation date: (6/16/2002 3:28:38 PM)
	 * @param newEndOfLineCharState boolean
	 */
	public void setEndOfLineCharState(boolean newEndOfLineCharState) {
		endOfLineCharState = newEndOfLineCharState;
	}
	/**
	 * Sets the parsed.
	 * @param parsed The parsed to set
	 */
	public void setParsed(Hashtable parsed) {
		this.parsed = parsed;
	}
	/**
	 * Sets the strictTags.
	 * @param strictTags The strictTags to set
	 */
	public static void setStrictTags(Vector strictTags) {
		HTMLTag.strictTags = strictTags;
	}
	/**
	 * Sets the tagBegin.
	 * @param tagBegin The tagBegin to set
	 */
	public void setTagBegin(int tagBegin) {
		this.tagBegin = tagBegin;
	}
	/**
	 * Sets the tagEnd.
	 * @param tagEnd The tagEnd to set
	 */
	public void setTagEnd(int tagEnd) {
		this.tagEnd = tagEnd;
	}
    /**
    * Insert the method's description here.
    * Creation date: (6/6/2001 12:09:38 PM)
    * @param newTagLine java.lang.String
    */
    public void setTagLine(java.lang.String newTagLine) {
	    tagLine = newTagLine;
    }
	public void setText(String text) {
		tagContents = new StringBuffer(text);
	}
	/**
	 * Set the scanner associated with this tag
	 */
	public void setThisScanner(HTMLTagScanner scanner)
	{
		thisScanner = scanner;
	}
	public String toPlainTextString() {
		return "";
	}
	public String toHTML() {
		if (endOfLineCharState) return "<"+tagContents+">" + lineSeparator; else
		return "<"+tagContents+">";
	}
	/**
	 * Print the contents of the tag
	 */
	public String toString()
	{
		return "Begin Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
