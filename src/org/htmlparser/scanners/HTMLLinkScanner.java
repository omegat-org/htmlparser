// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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
import java.util.*;
import java.io.IOException;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
/**
 * Scans for the Link Tag. This is a subclass of HTMLTagScanner, and is called using a 
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLLinkScanner extends HTMLTagScanner
{
	public static final String DIRTY_TAG_MESSAGE=" is a dirty link tag - the tag was not closed. \nWe encountered an open tag, before the previous end tag was found.\nCorrecting this..";
	private HTMLTagScanner previousOpenLinkScanner=null;
	private HTMLLinkProcessor processor;
	/**
	 * Overriding the default constructor
	 */
	public HTMLLinkScanner()
	{
		super();
		processor = new HTMLLinkProcessor();
	}
	/**
	 * Overriding the constructor to accept the filter 
	 */
	public HTMLLinkScanner(String filter)
	{
		super(filter);
		processor = new HTMLLinkProcessor();		
	}
	protected HTMLTag createLinkTag(String currentLine, HTMLNode node, boolean mailLink, boolean javascriptLink, String link, String linkText, String accessKey, int linkBegin, String tagContents, String linkContents, Vector nodeVector) {
		int linkEnd;
		// The link has been completed
		// Create the link object and return it
		// HTMLLinkNode Constructor got one extra parameter 
		// Kaarle Kaila 23.10.2001
		linkEnd = node.elementEnd();
		HTMLLinkTag linkTag = new HTMLLinkTag(link,linkText,linkBegin,linkEnd,accessKey,currentLine,nodeVector,mailLink,javascriptLink,tagContents,linkContents);
		linkTag.setThisScanner(this);
		return linkTag;
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
		if ((ch=='a' || ch=='A')) {
			if (previousOpenLinkScanner!=null) {
				StringBuffer msg= new StringBuffer();
				msg.append("<");
				msg.append(s);
				msg.append(">");
				msg.append(DIRTY_TAG_MESSAGE);
				feedback.warning(msg.toString());
				// This is dirty HTML. Assume the current tag is
				// not a new link tag - but an end tag. This is actually a really wild bug - 
				// Internet Explorer actually parses such tags.
				// So - we shall then proceed to fool the scanner into sending an endtag of type </A>
				// For this - set the dirty flag to true and return
				return true;  
			} 
		}
		
		if (s.length()<5) retVal = false; else
		if ((ch=='a' || ch=='A') && (s.charAt(1)==' ' || s.charAt(1)=='\n' || s.charAt(1)=='\r')) retVal = true; else retVal = false;

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
	public String extractLink(HTMLTag tag,String url) throws HTMLParserException
	{
		try {
			Hashtable table = tag.parseParameters();
			String relativeLink =  (String)table.get("HREF");
			if (relativeLink!=null) { 
				relativeLink = removeChars(relativeLink,'\n');
				relativeLink = removeChars(relativeLink,'\r');
			}
			return processor.extract(relativeLink,url);
		}
		catch (Exception e) {
			String msg; 
			if (tag!=null) msg = tag.getText(); else msg="null";
			throw new HTMLParserException("HTMLLinkScanner.extractLink() : Error while extracting link from tag "+msg+", url = "+url,e);
		}
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
	 * Gets the dirty.
	 * @return Returns a boolean
	 */
	public boolean isPreviousLinkScannerOpen() {
		return previousOpenLinkScanner!=null;
	}
	/**
	 * Scan the tag and extract the information relevant to the link tag.
	 * @param tag HTML Tag to be scanned
	 * @param url The URL of the html page in which this tag is located
	 * @param reader The HTML reader used to read this url
	 * @param currentLine The current line (automatically provided by HTMLTag)	 
	 */
	public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currentLine) throws HTMLParserException
	{
		try {
			if (previousOpenLinkScanner!=null) {
				// Fool the reader into thinking this is an end tag (to handle dirty html, where there is 
				// actually no end tag for the link
				if (tag.getText().length()==1) {
					// Replace tag - it was a <A> tag - replace with </a>
					String newLine = replaceFaultyTagWithEndTag(tag, currentLine);
					reader.changeLine(newLine);
					return new HTMLEndTag(tag.elementBegin(),tag.elementBegin()+3,"A",currentLine);
				}
				 else 
				{
					// Insert end tag
					String newLine = insertEndTagBeforeNode(tag, currentLine);
					reader.changeLine(newLine);
					return new HTMLEndTag(tag.elementBegin(),tag.elementBegin()+3,"A",currentLine);
				}
			}
			previousOpenLinkScanner = this;
			HTMLNode node;
			boolean mailLink = false;
			boolean javascriptLink = false;

			
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
			int javascript = link.indexOf("javascript:");
			int http = link.indexOf("http://");
			int https = link.indexOf("https://");
			
			if (mailto==0)
			{
				// yes it is
				mailto = link.indexOf(":");
				link = link.substring(mailto+1);
				mailLink = true;			
			} else if (javascript == 0) {
				link = link.substring(11); // this magic number is "javascript:".length()
				javascriptLink = true;
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
				    tmp = ((HTMLEndTag)node).getText();
				    linkContents += "</" + tmp  ;   // Kaarle Kaila 23.10.2001
					char ch = tmp.charAt(0);
					if (ch=='a' || ch=='A') endFlag=true; else {
						// This is the case that we found some wierd end tag inside
						// a link tag.
						endFlag=false;
						// If this happens to be a td, or a tr tag,
						// then we definitely dont want to treat it as part
						// of the link tag. We would instead add the missing </A>
						if (tmp.toUpperCase().indexOf("TD")!=-1 || tmp.toUpperCase().indexOf("TR")!=-1) {
							// Yes, we need to assume that the link tag has ended here.
							String newLine = insertEndTagBeforeNode(node,reader.getCurrentLine());
							reader.changeLine(newLine);
							endFlag = true;
							node = new HTMLEndTag(node.elementBegin(),node.elementBegin()+3,"A",newLine);
						} else nodeVector.addElement(node);
					}
				} 
				else if (node!=null) nodeVector.addElement(node);
			}
			while (endFlag==false && node!=null);
			if (node instanceof HTMLEndTag || node==null)
			{
				if (node==null)  {
					// Add an end link tag
					HTMLEndTag endTag = new HTMLEndTag(0,3,"A","</A>");
					node = endTag;
				}
				previousOpenLinkScanner = null;
				return createLinkTag(currentLine, node, mailLink, javascriptLink,  link, linkText, accessKey, linkBegin, tagContents, linkContents, nodeVector);
			}
			HTMLParserException ex = new HTMLParserException("HTMLLinkScanner.scan() : Could not create link tag from "+currentLine);
			feedback.error("HTMLLinkScanner.scan() : Could not create link tag from "+currentLine,ex);
			throw ex;
		}
		catch (Exception e) {
			HTMLParserException ex = new HTMLParserException("HTMLLinkScanner.scan() : Error while scanning a link tag, current line = "+currentLine,e);
			feedback.error("HTMLLinkScanner.scan() : Error while scanning a link tag, current line = "+currentLine,ex);
			throw ex;
		}	
	}
	public String replaceFaultyTagWithEndTag(HTMLTag tag, String currentLine) {
		String newLine = currentLine.substring(0,tag.elementBegin());
		newLine+="</A>";
		newLine+=currentLine.substring(tag.elementEnd()+1,currentLine.length());
		
		return newLine;
	}
	/**
	 * Insert an EndTag in the currentLine, just before the occurence of the provided tag
	 */
	public String insertEndTagBeforeNode(HTMLNode node, String currentLine) {
		String newLine = currentLine.substring(0,node.elementBegin());
		newLine += "</A>";
		newLine += currentLine.substring(node.elementBegin(),currentLine.length());
		return newLine;
	}
	// Creates a Base Ref Scanner sharing the same link processor
	public HTMLBaseHREFScanner createBaseHREFScanner(String filter) {
		return new HTMLBaseHREFScanner(filter,processor);
	}
	public HTMLImageScanner createImageScanner(String filter) {
		return new HTMLImageScanner(filter,processor);
	}
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "A";
		return ids;
	}

}
