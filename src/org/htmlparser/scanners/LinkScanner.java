// HTMLParser Library v1_4_20030622 - A java-based parser for HTML
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

import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.LinkData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
/**
 * Scans for the Link Tag. This is a subclass of TagScanner, and is called using a 
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class LinkScanner extends CompositeTagScanner
{
	private static final String MATCH_NAME [] = {"A"};
	public static final String LINK_SCANNER_ID = "A";
	public static final String DIRTY_TAG_MESSAGE=" is a dirty link tag - the tag was not closed. \nWe encountered an open tag, before the previous end tag was found.\nCorrecting this..";
	private LinkProcessor processor;
	private final static String ENDERS [] = { "TD","TR","FORM","LI","BODY", "HTML" };
	private final static String ENDTAG_ENDERS [] = { "TD","TR","FORM","LI","BODY", "HTML" };
	
	/**
	 * Overriding the default constructor
	 */
	public LinkScanner() {
		this("");
	}
	
	/**
	 * Overriding the constructor to accept the filter 
	 */
	public LinkScanner(String filter) {
		super(filter,MATCH_NAME,ENDERS,ENDTAG_ENDERS, false);
		processor = new LinkProcessor();		
	}
	
	public Tag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) throws ParserException {

		String link = extractLink(compositeTagData.getStartTag(),tagData.getUrlBeingParsed());
		int mailto = link.indexOf("mailto");
		boolean mailLink=false;
		if (mailto==0)
		{
			// yes it is
			mailto = link.indexOf(":");
			link = link.substring(mailto+1);
			mailLink = true;			
		} 
		int javascript = link.indexOf("javascript:");
		boolean javascriptLink = false;
		if (javascript == 0) {
			link = link.substring(11); // this magic number is "javascript:".length()
			javascriptLink = true;
		}  
		String accessKey = getAccessKey(compositeTagData.getStartTag());
		String myLinkText = compositeTagData.getChildren().toString();
		
		LinkTag linkTag = new LinkTag(
			tagData,
			compositeTagData,
			new LinkData(
				link,
				myLinkText,
				accessKey,
				mailLink,
				javascriptLink
			)
		);
		linkTag.setThisScanner(this);
		return linkTag;
	}
	
	/**
	 * Template Method, used to decide if this scanner can handle the Link tag type. If 
	 * the evaluation returns true, the calling side makes a call to scan().
	 * @param s The complete text contents of the Tag.
	 * @param previousOpenScanner Indicates any previous scanner which hasnt completed, before the current
	 * scan has begun, and hence allows us to write scanners that can work with dirty html
	 */
    public boolean evaluate (String s, TagScanner previousOpenScanner)
    {
        char ch;
        boolean ret;
        
        // eat up leading blanks
        s = absorbLeadingBlanks (s);
        if (5 > s.length ())
            ret = false;
        else
        {
            ch = s.charAt (0);
            if ((ch=='a' || ch=='A') && Character.isWhitespace (s.charAt (1)))
                ret = -1 != s.toUpperCase().indexOf ("HREF");
            else
                ret = false;
        }

        return (ret);
    }

    /**
     * Extract the link from the given string. The URL of the actual html page is also 
     * provided.    
     */
	public String extractLink(Tag tag,String url) throws ParserException
	{
		try {
			Hashtable table = tag.getAttributes();
			String relativeLink =  (String)table.get("HREF");
			if (relativeLink!=null) { 
				relativeLink = ParserUtils.removeChars(relativeLink,'\n');
				relativeLink = ParserUtils.removeChars(relativeLink,'\r');
			}
			return processor.extract(relativeLink,url);
		}
		catch (Exception e) {
			String msg; 
			if (tag!=null) msg = tag.getText(); else msg="null";
			throw new ParserException("HTMLLinkScanner.extractLink() : Error while extracting link from tag "+msg+", url = "+url,e);
		}
	}
	
	/**
	 * Extract the access key from the given tag.
	 * @param text Text to be parsed to pick out the access key.
     * @return The value of the ACCESSKEY attribute.
	 */
	private String getAccessKey (Tag tag) {
		return tag.getAttribute("ACCESSKEY");
	}
	
	public BaseHrefScanner createBaseHREFScanner(String filter) {
		return new BaseHrefScanner(filter,processor);
	}
	
	public ImageScanner createImageScanner(String filter) {
		return new ImageScanner(filter,processor);
	}
	
	/**
	 * @see org.htmlparser.scanners.TagScanner#getID()
	 */
	public String [] getID() {
		return MATCH_NAME;
	}

}
