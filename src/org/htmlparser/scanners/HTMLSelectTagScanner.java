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

import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLOptionTag;
import org.htmlparser.tags.HTMLSelectTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLTagData;
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
	
	public HTMLTag scan(HTMLTag tag, String pUrl, HTMLReader reader, String currLine)
			throws HTMLParserException
	{
		try
		{
			HTMLTag startTag = tag;
			HTMLEndTag endTag=null;
			HTMLNode node = null;
			boolean endTagFound=false;
			Vector optionTags=new Vector();
			// Remove all existing scanners, so as to parse only till the end tag
			Hashtable tempScanners = HTMLParserUtils.adjustScanners(reader);	

			//However we need to activate Option tag scanner since select will 
			//have multiple option tags.
			reader.getParser().addScanner(new HTMLOptionTagScanner());
			do 
			{
				node = reader.readElement();
				if (node instanceof HTMLEndTag)
				{
					endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("SELECT")) 
					{
						endTagFound = true;
					}
				}
				else if (node instanceof HTMLOptionTag)
				{
					optionTags.add((HTMLOptionTag)node);
				}
				else
				{
					if (!(node instanceof HTMLStringNode))
						throw new HTMLParserException("Error occurred scanning select tag. Undefined tag : " + node.toHTML());
				}
			}
			while (!endTagFound);
			HTMLSelectTag selectTag = new HTMLSelectTag(new HTMLTagData(
										0, node.elementEnd(), tag.getText(), 
										currLine), optionTags,startTag,endTag);
			HTMLParserUtils.restoreScanners(reader, tempScanners);
			return selectTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLSelectTagScanner.scan() : Error while scanning select tags, current line = "+currLine,e);
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