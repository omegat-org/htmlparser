// HTMLParser Library v1_4_20030727 - A java-based parser for HTML
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

import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
/**
 * Scans for the Image Tag. This is a subclass of TagScanner, and is called using a 
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class ImageScanner extends TagScanner
{
	public static final String IMAGE_SCANNER_ID = "IMG";
	private Hashtable table;
	private LinkProcessor processor;
	/**
	 * Overriding the default constructor
	 */
	public ImageScanner()
	{
		super();
		processor = new LinkProcessor();
	}
	/**
	 * Overriding the constructor to accept the filter 
	 */	
	public ImageScanner(String filter,LinkProcessor processor)
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
	public String extractImageLocn(Tag tag,String url) throws ParserException
	{
		String relativeLink=null;
		try {
			table = tag.getAttributes();
			relativeLink =  (String)table.get("SRC");
			if (relativeLink!=null) {
				relativeLink = ParserUtils.removeChars(relativeLink,'\n');
				relativeLink = ParserUtils.removeChars(relativeLink,'\r');
			}
			if (relativeLink==null || relativeLink.length()==0) {
				// try fix
				String tagText = tag.getText().toUpperCase();
				int indexSrc = tagText.indexOf("SRC");
				if (indexSrc != -1) {
					// There is a missing equals.
					tag.setText(tag.getText().substring(0,indexSrc+3)+"="+tag.getText().substring(indexSrc+3,tag.getText().length()));
					table = tag.redoParseAttributes();
					relativeLink = (String) table.get("SRC");
					
				} 
			}
			if (relativeLink==null) return ""; else
			return processor.extract(relativeLink,url);		
		}
		catch (Exception e) {
			throw new ParserException("HTMLImageScanner.extractImageLocn() : Error in extracting image location, relativeLink = "+relativeLink+", url = "+url,e);
		}
	}
	
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = IMAGE_SCANNER_ID;
		return ids;
	}

	protected Tag createTag(TagData tagData, Tag tag, String url)
		throws ParserException {
		String link = extractImageLocn(tag,url);
		return new ImageTag(tagData, link);
	}

}
