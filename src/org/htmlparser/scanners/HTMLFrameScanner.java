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


//////////////////
// Java Imports //
//////////////////
import java.util.Hashtable;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLFrameTag;
import org.htmlparser.tags.HTMLTag;
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
	 * Scan the tag and extract the information related to the <IMG> tag. The url of the
	 * initiating scan has to be provided in case relative links are found. The initial
	 * url is then prepended to it to give an absolute link.
	 * The HTMLReader is provided in order to do a lookahead operation. We assume that
	 * the identification has already been performed using the evaluate() method.
	 * @param tag HTML Tag to be scanned for identification
	 * @param url The initiating url of the scan (Where the html page lies)
	 * @param reader The reader object responsible for reading the html page
	 * @param currentLine The current line (automatically provided by HTMLTag)
	 */
	public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currentLine) throws HTMLParserException
	{
		try {
			HTMLNode node;
			String frame, frameName, linkText="";
			int frameBegin, frameEnd;
	
			// Yes, the tag is a link
			// Extract the link
			//link = extractImageLocn(tag.getText(),url);
			frame = extractFrameLocn(tag,url);
		    frameName = extractFrameName(tag,url);
			frameBegin = tag.elementBegin();
			frameEnd = tag.elementEnd();
			HTMLFrameTag frameTag = new HTMLFrameTag(frame, frameName, tag.getText(), frameBegin,frameEnd,currentLine);
			return frameTag;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLFrameScanner.scan() : Error while scanning frame tag, current line = "+currentLine,e);
		}
	}
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "FRAME";
		return ids;
	}

}
