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
import java.util.Enumeration;
import java.util.Vector;
import java.io.IOException;
import java.util.Hashtable;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
/**
 * Scans for the Image Tag. This is a subclass of HTMLTagScanner, and is called using a 
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLImageScanner extends HTMLTagScanner
{
	public static final String IMAGE_SCANNER_ID = "IMG";
	private Hashtable table;
	private HTMLLinkProcessor processor;
	/**
	 * Overriding the default constructor
	 */
	public HTMLImageScanner()
	{
		super();
		processor = new HTMLLinkProcessor();
	}
	/**
	 * Overriding the constructor to accept the filter 
	 */	
	public HTMLImageScanner(String filter,HTMLLinkProcessor processor)
	{
		super(filter);
		this.processor = processor;
	}
  /**
   * Extract the location of the image, given the string to be parsed, and the url
   * of the html page in which this tag exists.
   * @param s String to be parsed
   * @param url URL of web page being parsed
   */
	public String extractImageLocn(HTMLTag tag,String url) throws HTMLParserException
	{
		String relativeLink=null;
		try {
			table = tag.parseParameters();
			relativeLink =  (String)table.get("SRC");
			if (relativeLink!=null) {
				relativeLink = removeChars(relativeLink,'\n');
				relativeLink = removeChars(relativeLink,'\r');
			}
			if (relativeLink==null || relativeLink.length()==0) {
				// try fix
				String tagText = tag.getText().toUpperCase();
				int indexSrc = tagText.indexOf("SRC");
				if (indexSrc != -1) {
					// There is a missing equals.
					tag.setText(tag.getText().substring(0,indexSrc+3)+"="+tag.getText().substring(indexSrc+3,tag.getText().length()));
					table = tag.parseParameters();
					relativeLink = (String) table.get("SRC");
					
				} 
			}
			if (relativeLink==null) return ""; else
			return processor.extract(relativeLink,url);		
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLImageScanner.extractImageLocn() : Error in extracting image location, relativeLink = "+relativeLink+", url = "+url,e);
		}
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
			String link,linkText="";
			int linkBegin, linkEnd;
	
			// Yes, the tag is a link
			// Extract the link
			//link = extractImageLocn(tag.getText(),url);
			link = extractImageLocn(tag,url);
			linkBegin = tag.elementBegin();
			linkEnd = tag.elementEnd();
			HTMLImageTag imageTag = new HTMLImageTag(link,linkBegin,linkEnd,currentLine,tag.getText());		
			return imageTag;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLImageScanner.scan() : Some Error occurred while scanning an image tag\n"+
			"specifically, tag being parsed was "+tag.getText()+",\n currentLine = "+currentLine,e);
		}
	}
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = IMAGE_SCANNER_ID;
		return ids;
	}

}
