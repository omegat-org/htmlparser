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

//////////////////
// Java Imports //
//////////////////
import java.util.Enumeration;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Hashtable;
import java.util.HashMap;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLInputTag;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.tags.HTMLFormTag;

/**
 * Scans for the Image Tag. This is a subclass of HTMLTagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLFormScanner extends HTMLTagScanner
{
 	/**
	 * HTMLFormScanner constructor comment.
	 */
	public HTMLFormScanner() {
		super();
	}
	/**
	 * Overriding the constructor to accept the filter
	 */
	public HTMLFormScanner(String filter)
	{
		super(filter);
	}
  /**
   * Extract the location of the image, given the string to be parsed, and the url
   * of the html page in which this tag exists.
   * @param s String to be parsed
   * @param url URL of web page being parsed
   */
	public String extractFormLocn(HTMLTag tag,String url) throws HTMLParserException
	{
		try {
			String formURL= tag.getParameter("ACTION");
			if (formURL==null) return ""; else
			return (new HTMLLinkProcessor()).extract(formURL, url);
		}
		catch (Exception e) {
			String msg;
			if (tag!=null) msg=  tag.getText(); else msg="";
			throw new HTMLParserException("HTMLFormScanner.extractFormLocn() : Error in extracting form location, tag = "+msg+", url = "+url,e);
		}
	}

	public String extractFormName(HTMLTag tag)
	{
		return tag.getParameter("NAME");
	}

	public String extractFormMethod(HTMLTag tag)
	{
		String method = tag.getParameter("METHOD");
		if (method==null) method = "GET";
		return method;

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
	      	Vector inputVector = new Vector(), nodeVector = new Vector();
			String link,name="",method="GET";
			int linkBegin=-1, linkEnd=-1;
	
			link = extractFormLocn(tag,url);
	    	name = extractFormName(tag);
		    method = extractFormMethod(tag);
			linkBegin = tag.elementBegin();
		    boolean endFlag = false;
			nodeVector.addElement(tag);

			Hashtable oldScanners = adjustScanners(reader);
			reader.getParser().addScanner(new HTMLInputTagScanner(""));
			reader.getParser().addScanner(new HTMLSelectTagScanner(""));
			reader.getParser().addScanner(new HTMLOptionTagScanner(""));
			reader.getParser().addScanner(new HTMLTextareaTagScanner(""));
		    do
			{
				node = reader.readElement();
				if (node instanceof HTMLEndTag)
				{
					HTMLEndTag endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("FORM")) {
						endFlag=true;
						linkEnd = endTag.elementEnd();
					}
				}
				else 
				if (node instanceof HTMLInputTag) {
					inputVector.addElement(node);
				}
				nodeVector.addElement(node);
			}
			while (endFlag==false && node!=null);
			restoreScanners(reader,oldScanners);
			
			if (node==null && endFlag==false) {
				StringBuffer msg = new StringBuffer();
				for (Enumeration e = inputVector.elements();e.hasMoreElements();) {
					msg.append((HTMLNode)e.nextElement()+"\n");
				}
				throw new HTMLParserException("HTMLFormScanner.scan() : Went into a potential infinite loop - tags must be malformed.\n"+
				"Input Vector contents : "+msg.toString());
			}		
			HTMLFormTag formTag = new HTMLFormTag(link,name,method,linkBegin,linkEnd,currentLine,inputVector,nodeVector);
			return formTag;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLFormScanner.scan() : Error while scanning the form tag, current line = "+currentLine,e);
		}
	}

	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "FORM";
		return ids;
	}

}
