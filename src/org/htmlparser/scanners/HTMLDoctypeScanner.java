// HTMLParser Library v1_2_20021201 - A java-based parser for HTML
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
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLDoctypeTag;

/**
 * The HTMLDoctypeScanner identifies Doctype tags
 */

public class HTMLDoctypeScanner extends HTMLTagScanner {
	private java.lang.String language;
	private java.lang.String type;
	/**
	 * HTMLDoctypeScanner constructor 
	 */
	public HTMLDoctypeScanner() {
		super();
	}
	/**
	 * HTMLScriptScanner constructor comment.
	 * @param filter java.lang.String
	 */
	public HTMLDoctypeScanner(String filter) {
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
			String tagContents = tag.getText();
			// Cut from 9th char -- a space after !DOCTYPE _
			tagContents=tagContents.substring(9,tagContents.length());
			HTMLDoctypeTag docTypeTag = new HTMLDoctypeTag(tag.elementBegin(),tag.elementEnd(),tagContents,currentLine);
			return docTypeTag;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLDoctypeScanner.scan() : Error in scanning doctype tag, current line = "+currentLine,e);
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
		ids[0] = "!DOCTYPE";
		return ids;
	}

}
