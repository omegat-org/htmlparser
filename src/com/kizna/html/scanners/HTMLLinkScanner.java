// HTMLParser Library v1.1 - A java-based parser for HTML
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

package com.kizna.html.scanners;
//////////////////
// Java Imports //
//////////////////
import java.util.*;
import java.io.IOException;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLEndTag;
import com.kizna.html.tags.HTMLLinkTag;
import com.kizna.html.HTMLStringNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.util.HTMLLinkProcessor;
/**
 * Scans for the Link Tag. This is a subclass of HTMLTagScanner, and is called using a 
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLLinkScanner extends HTMLTagScanner
{
	private boolean dirty = false;
	/**
	 * Overriding the default constructor
	 */
	public HTMLLinkScanner()
	{
		super();
	}
	/**
	 * Overriding the constructor to accept the filter 
	 */
	public HTMLLinkScanner(String filter)
	{
		super(filter);
	}
	/**
	 * Template Method, used to decide if this scanner can handle the Link tag type. If 
	 * the evaluation returns true, the calling side makes a call to scan().
	 * @param s The complete text contents of the HTMLTag.
	 * @param previousOpenScanner Indicates any previous scanner which hasnt completed, before the current
	 * scan has begun, and hence allows us to write scanners that can work with dirty html
	 */
	public boolean evaluate(String s,HTMLTagScanner previousOpenScanner)
	{
		// Eat up leading blanks
		s = absorbLeadingBlanks(s);		
		boolean retVal;
		char ch = s.charAt(0);
		
		// Is it a dirty html tag (should have been end tag but is begin)
		if ((ch=='a' || ch=='A') && s.length()==1) {
			if (previousOpenScanner instanceof HTMLLinkScanner) {
				// This is dirty HTML. Assume the current tag is
				// not a new link tag - but an end tag. This is actually a really wild bug - 
				// Internet Explorer actually parses such tags.
				// So - we shall then proceed to fool the scanner into sending an endtag of type </A>
				// For this - set the dirty flag to true and return
				dirty = true;	
				return true;  
			} 
		} else dirty = false;

		if ((ch=='a' || ch=='A') && (s.charAt(1)==' ' || s.charAt(1)=='\n')) retVal = true; else retVal = false;

		if (retVal)
		{
			if (s.toUpperCase().indexOf("HREF")==-1)
				retVal=false;
		} 

		return retVal;
	
	}
  /**
   * Extract the link from the given string. The URL of the actual html page is also 
   * provided.    
   */
	public String extractLink(HTMLTag tag,String url)
	{
		Hashtable table = tag.parseParameters();
		String relativeLink =  (String)table.get("HREF");
		if (relativeLink!=null) relativeLink = removeChars(relativeLink,'\n');
		return (new HTMLLinkProcessor()).extract(relativeLink,url);
	}
	/**
	 * Extract the access key from the given text.
	 * @param text Text to be parsed to pick out the access key
	 */
	public String getAccessKey(String text)
	{
		// Find the occurence of ACCESSKEY in given 
		String sub = "ACCESSKEY";
		String accessKey=null;
		int n = text.toUpperCase().indexOf(sub);
		if (n!=-1)
		{
			n+=sub.length();
			// Parse the = sign
			char ch;
			do
			{
				ch = text.charAt(n);
				n++;
			}
			while (ch!='=');
			// Start parsing for a number
			accessKey = "";
			do
			{
				ch = text.charAt(n);
				if (ch>='0' && ch<='9') accessKey+=ch;
				n++;
			}
			while (ch>='0' && ch<='9' && n<text.length());
			return accessKey;
		} else return null;
	}
	/**
	 * Scan the tag and extract the information relevant to the link tag.
	 * @param tag HTML Tag to be scanned
	 * @param url The URL of the html page in which this tag is located
	 * @param reader The HTML reader used to read this url
	 * @param currentLine The current line (automatically provided by HTMLTag)	 
	 */
	public HTMLNode scan(HTMLTag tag,String url,HTMLReader reader,String currentLine) throws IOException
	{
		if (dirty) {
			// Fool the reader into thinking this is an end tag (to handle dirty html, where there is 
			// actually no end tag for the link
			return new HTMLEndTag(tag.elementBegin(),tag.elementEnd(),"A");
		}
		HTMLNode node;
		boolean mailLink = false;
		String link,linkText="",accessKey=null,tmp;
		int linkBegin, linkEnd;
		String tagContents =  tag.getText();
		String linkContents=""; // Kaarle Kaila 23.10.2001
		// Yes, the tag is a link
		// Extract the link
		link = extractLink(tag,url);
		
		//link = extractLink(tag.getText(),url);
		// Check if its a mailto link
		int mailto = link.indexOf("mailto");
		if (mailto==0)
		{
			mailto = link.indexOf(":");
			// yes it is
			link = link.substring(mailto+1);
			mailLink = true;			
		}
		accessKey = getAccessKey(tag.getText());
		linkBegin = tag.elementBegin();
		// Get the next element, which is string, till </a> is encountered
		boolean endFlag=false;
		Vector nodeVector = new Vector();
		do
		{
			node = reader.readElement();

			if (node instanceof HTMLStringNode)
			{
				
				tmp =((HTMLStringNode)node).getText();
				linkText += tmp;
				linkContents += tmp;   // Kaarle Kaila 23.10.2001
				
			}
			if (node instanceof HTMLEndTag)
			{
			    tmp = ((HTMLEndTag)node).getContents();
			    linkContents += "</" + tmp  ;   // Kaarle Kaila 23.10.2001
				char ch = tmp.charAt(0);
				if (ch=='a' || ch=='A') endFlag=true; else endFlag=false;
			} 
			else nodeVector.addElement(node);
		}
		while (endFlag==false);
		if (node instanceof HTMLEndTag)
		{
			return createLinkTag(currentLine, node, mailLink, link, linkText, accessKey, linkBegin, tagContents, linkContents, nodeVector);
		}
		
		return null;
	}
	protected HTMLNode createLinkTag(String currentLine, HTMLNode node, boolean mailLink, String link, String linkText, String accessKey, int linkBegin, String tagContents, String linkContents, Vector nodeVector) {
		int linkEnd;
		// The link has been completed
		// Create the link object and return it
		// HTMLLinkNode Constructor got one extra parameter 
		// Kaarle Kaila 23.10.2001
		linkEnd = node.elementEnd();
		HTMLLinkTag linkTag = new HTMLLinkTag(link,linkText,linkBegin,linkEnd,accessKey,currentLine,nodeVector,mailLink,tagContents,linkContents);
		linkTag.setThisScanner(this);
		return linkTag;
	}
	/**
	 * Gets the dirty.
	 * @return Returns a boolean
	 */
	public boolean getDirty() {
		return dirty;
	}

}
