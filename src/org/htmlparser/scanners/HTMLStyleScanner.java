// HTMLParser Library v1_2 - A java-based parser for HTML
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
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLStyleTag;
/**
 * The HTMLStyleScanner scans identifies &lt;style&gt; code
 */

public class HTMLStyleScanner extends HTMLTagScanner {
	/**
	 * HTMLScriptScanner constructor comment.
	 */
	public HTMLStyleScanner() {
		super();
	}
	/**
	 * HTMLScriptScanner constructor comment.
	 * @param filter java.lang.String
	 */
	public HTMLStyleScanner(String filter) {
		super(filter);
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
			// We know we have style stuff. 
			// Parse on till the end tag </style> is found
			String line;
			HTMLEndTag endTag=null;
			HTMLNode node = null;
			boolean endStyleFound=false;
			StringBuffer buff=new StringBuffer();
			
			do {
				node = reader.readElement();
				if (node instanceof HTMLEndTag) {
					endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("STYLE")) {
						endStyleFound = true;
					}
				} else buff.append(node.toHTML());
			}
			while (!endStyleFound && node!=null);
			if (node==null && !endStyleFound) {
				throw new HTMLParserException("HTMLStyleScanner.scan() : Went into a potential infinite loop, could not create syle tag.\n"+
				"buff contents so far "+buff.toString()+", currentLine= "+currentLine);
			}
			HTMLStyleTag styleTag = new HTMLStyleTag(tag.elementBegin(),endTag.elementEnd(),buff.toString(),currentLine);
			styleTag.setParsed(tag.getParsed());
			return styleTag;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLStyleScanner.scan() : Error while scanning a style tag, currentLine = "+currentLine,e);
		}
	}
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "STYLE";
		return ids;
	}
}
