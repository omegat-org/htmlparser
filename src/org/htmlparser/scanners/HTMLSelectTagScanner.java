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

import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLOptionTag;
import org.htmlparser.tags.HTMLSelectTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.util.HTMLParserUtils;


public class HTMLSelectTagScanner extends HTMLTagScanner
{
	public HTMLSelectTagScanner()
	{
		super();
	}
	
	public HTMLSelectTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public HTMLTag scan(HTMLTag tag, String pUrl, HTMLReader pReader, String pCurrLine)
			throws HTMLParserException
	{
		try
		{
			HTMLTag startTag = tag;
			HTMLEndTag endTag=null;
			HTMLNode lNode = null;
			boolean endTagFound=false;
			Vector lOptionTags=new Vector();
			// Remove all existing scanners, so as to parse only till the end tag
			Hashtable tempScanners = HTMLParserUtils.adjustScanners(pReader);	

			//However we need to activate Option tag scanner since select will 
			//have multiple option tags.
			pReader.getParser().addScanner(new HTMLOptionTagScanner());
			do 
			{
				lNode = pReader.readElement();
				if (lNode instanceof HTMLEndTag)
				{
					endTag = (HTMLEndTag)lNode;
					if (endTag.getText().toUpperCase().equals("SELECT")) 
					{
						endTagFound = true;
					}
				}
				else if (lNode instanceof HTMLOptionTag)
				{
					lOptionTags.add((HTMLOptionTag)lNode);
				}
				else
				{
					if (!(lNode instanceof HTMLStringNode))
						throw new HTMLParserException("Error occurred scanning select tag. Undefined tag : " + lNode.toHTML());
				}
			}
			while (!endTagFound);
			HTMLSelectTag lSelectTag = new HTMLSelectTag(
										0, lNode.elementEnd(), tag.getText(), 
										lOptionTags, pCurrLine,startTag,endTag);
			HTMLParserUtils.restoreScanners(pReader, tempScanners);
			return lSelectTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLSelectTagScanner.scan() : Error while scanning select tags, current line = "+pCurrLine,e);
		}
	}
	
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "SELECT";
		return ids;
	}

}