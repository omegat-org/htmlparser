// HTMLParser Library v1_2_20021215 - A java-based parser for HTML
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
import org.htmlparser.tags.HTMLFrameSetTag;
import org.htmlparser.tags.HTMLFrameTag;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
/**
 * Scans for the Frame Tag. This is a subclass of HTMLTagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLFrameSetScanner extends HTMLTagScanner
{
	/**
	 * Overriding the default constructor
	 */
	public HTMLFrameSetScanner()
	{
		super();
	}
	/**
	 * Overriding the constructor to accept the filter
	 */
	public HTMLFrameSetScanner(String filter)
	{
		super(filter);
	}
	/**
	 * Template Method, used to decide if this scanner can handle the Image tag type. If
	 * the evaluation returns true, the calling side makes a call to scan().
	 * @param s The complete text contents of the HTMLTag.
	 */
	public boolean evaluate(String s, HTMLTagScanner previousOpenScanner)
	{
		// Eat up leading blanks
		s = absorbLeadingBlanks(s);
		if (s.toUpperCase().indexOf("FRAMESET")==0)
		return true; else return false;			
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
			Vector frameVector = new Vector();
			boolean endFrameSetFound=false;
			HTMLNode node;
			int frameSetEnd=-1;
			do {
				node = reader.readElement();
				if (node instanceof HTMLEndTag) {
					HTMLEndTag endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("FRAMESET")) {
						endFrameSetFound = true;
						frameSetEnd = endTag.elementEnd();
					}
				} else
				if (node instanceof HTMLFrameTag) {
					frameVector.addElement(node);
				}
			}
			while(!endFrameSetFound && node!=null);
			if (node==null && endFrameSetFound== false) {
				StringBuffer msg = new StringBuffer();
				for (Enumeration e = frameVector.elements();e.hasMoreElements();) {
					msg.append((HTMLNode)e.nextElement()+"\n");
				}
				throw new HTMLParserException("HTMLFrameSetScanner.scan() : Went into a potential infinite loop - tags must be malformed.\n"+
				"Frame Vector contents : "+msg.toString());
			}
			HTMLFrameSetTag frameSetTag = new HTMLFrameSetTag(tag.elementBegin(),frameSetEnd,tag.getText(),currentLine,frameVector);
			return frameSetTag;		
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLFrameSetScanner.scan() : Error while scanning Frameset tag, current line = "+currentLine,e);
		}
	}
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "FRAMESET";
		return ids;
	}

}
