// HTMLParser Library v1_2_20021208 - A java-based parser for HTML
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

package org.htmlparser.scanners;
/////////////////////////
// HTML Parser Imports //
/////////////////////////
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLScriptTag;
/**
 * The HTMLScriptScanner identifies javascript code
 */

public class HTMLScriptScanner extends HTMLTagScanner {
	private java.lang.String language;
	private java.lang.String type;
	/**
	 * HTMLScriptScanner constructor comment.
	 */
	public HTMLScriptScanner() {
		super();
	}
	/**
	 * HTMLScriptScanner constructor comment.
	 * @param filter java.lang.String
	 */
	public HTMLScriptScanner(String filter) {
		super(filter);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:30:03 AM)
	 * @param tag org.htmlparser.HTMLTag
	 */
	public void extractLanguage(HTMLTag tag) 
	{
		language = tag.getParameter("LANGUAGE");
		if (language==null) language="";
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 12:12:20 PM)
	 */
	public void extractType(HTMLTag tag) 
	{
		type = tag.getParameter("TYPE");
		if (type==null) type="";
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:55:32 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getLanguage() {
		return language;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 12:20:57 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getType() {
		return type;
	}
		/** 
		 * Scan the tag and extract the information related to this type. The url of the 
		 * initiating scan has to be provided in case relative links are found. The initial 
		 * url is then prepended to it to give an absolute link.
		 * The HTMLReader is provided in order to do a lookahead operation. We assume that
		 * the identification has already been performed using the evaluate() method.
		 * @param tag HTML Tag to be scanned for identification
		 * @param url The initiating url of the scan (Where the html page lies)
		 * @param reader The reader object responsible for reading the html page
		 * @param currentLine The current line (automatically provided by HTMLTag)	 
		 */
	public HTMLTag scan(HTMLTag tag, String url, HTMLReader reader,String currentLine) throws HTMLParserException
	{
		try {
			// We know we have script stuff. So first extract the information from the tag about the language
			extractLanguage(tag);
			extractType(tag);
			HTMLEndTag endTag=null;
			HTMLNode node = null;
			boolean endScriptFound=false;
			StringBuffer buff=new StringBuffer();
			// Remove all existing scanners, so as to parse only till the end tag
			Hashtable tempScanners = reader.getParser().getScanners();
			reader.getParser().flushScanners();
			HTMLNode prevNode=tag;
			do {
				node = reader.readElement();
				if (node instanceof HTMLEndTag) {
					endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("SCRIPT")) 
					{
						endScriptFound = true;
						// Check if there was anything in front of endTag, and if so, add it to the code buffer
					}
				} else {
					if (prevNode!=null) {
						if (prevNode.elementEnd() > node.elementBegin()) buff.append(HTMLNode.getLineSeparator());
					}
					buff.append(node.toHTML());
		
					prevNode = node;
				}
			}
			while (!endScriptFound && node!=null);
			if (node==null && !endScriptFound) {
				throw new HTMLParserException("HTMLScriptScanner.scan() : Went into a potential infinite loop, could not create script tag.\n"+
				"buff contents so far "+buff.toString()+", currentLine= "+currentLine);
			}
			HTMLScriptTag scriptTag = new HTMLScriptTag(0,node.elementEnd(),tag.getText(),buff.toString(),language,type,currentLine);
			reader.getParser().setScanners(tempScanners);
			return scriptTag; 
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLScriptScanner.scan() : Error while scanning a script tag, currentLine = "+currentLine,e);
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:55:32 AM)
	 * @param newLanguage java.lang.String
	 */
	public void setLanguage(java.lang.String newLanguage) {
		language = newLanguage;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 12:20:57 PM)
	 * @param newType java.lang.String
	 */
	public void setType(java.lang.String newType) {
		type = newType;
	}
	

	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "SCRIPT";
		return ids;
	}

}
