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


package org.htmlparser.scanners;


//////////////////
// Java Imports //
//////////////////
import java.util.Hashtable;

import org.htmlparser.tags.HTMLFrameTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
/**
 * Scans for the Frame Tag. This is a subclass of HTMLTagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLFrameScanner extends HTMLTagScanner
{
	/**
	 * Overriding the default constructor
	 */
	public HTMLFrameScanner()
	{
		super();
	}
	/**
	 * Overriding the constructor to accept the filter
	 */
	public HTMLFrameScanner(String filter)
	{
		super(filter);
	}
  /**
   * Extract the location of the image, given the string to be parsed, and the url
   * of the html page in which this tag exists.
   * @param s String to be parsed
   * @param url URL of web page being parsed
   */
	public String extractFrameLocn(HTMLTag tag,String url) throws HTMLParserException
	{
		try {
			Hashtable table = tag.getAttributes();
			String relativeFrame =  (String)table.get("SRC");
			if (relativeFrame==null) return ""; else
			return (new HTMLLinkProcessor()).extract(relativeFrame,url);
		}
		catch (Exception e) {
			String msg;
			if (tag!=null) msg = tag.getText(); else msg = "null";
			throw new HTMLParserException("HTMLFrameScanner.extractFrameLocn() : Error in extracting frame location from tag "+msg,e); 
		}
	}
	


	public String extractFrameName(HTMLTag tag,String url)
	{
		return tag.getParameter("NAME");
	}

	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "FRAME";
		return ids;
	}

	protected HTMLTag createTag(HTMLTagData tagData, HTMLTag tag, String url) throws HTMLParserException {
		String frameUrl = extractFrameLocn(tag,url);
		String frameName = extractFrameName(tag,url);
		
		return new HTMLFrameTag(tagData,frameUrl,frameName);
	}

}
