// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import java.util.*;
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tags.data.*;
import org.htmlparser.util.*;

public class HTMLOptionTagScanner extends HTMLTagScanner
{
	public HTMLOptionTagScanner()
	{
		super();
	}
	
	public HTMLOptionTagScanner(String filter)
	{
		super(filter);
	}
	
	public HTMLTag scan(HTMLTag tag, String pUrl, HTMLReader reader, String currLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag endTag=null;
			HTMLNode node = null;
			HTMLNode prevNode = tag;
			boolean endTagFound=false;
			StringBuffer text=new StringBuffer("");
			// Remove all existing scanners, so as to parse only till the end tag
			Hashtable tempScanners = HTMLParserUtils.adjustScanners(reader);	

			do 
			{
				node = reader.readElement();
				if (node instanceof HTMLEndTag)
				{
					endTag = (HTMLEndTag)node;
					String endTagString = endTag.getText().toUpperCase();
					if (endTagString.equals("OPTION") || endTagString.equals("SELECT")) 
					{
						endTagFound = true;
						if (endTagString.equals("SELECT"))
						{
							node = prevNode;
						}
					}
				}
				else if (node instanceof HTMLStringNode)
				{
					text.append(node.toHTML());
				}
				else 
				{
					endTagFound = true;
					node = prevNode;
				}
				prevNode = node;
			}
			while (!endTagFound);
			HTMLOptionTag lOptionTag = 
			new HTMLOptionTag(
				new TagData(
					0, node.elementEnd(), tag.getText(),currLine
				), 
				text.toString()
			);
			HTMLParserUtils.restoreScanners(reader, tempScanners);
			return lOptionTag;										
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLOptionTagScanner.scan() : Error while scanning option tags, current line = "+currLine,e);
		}
	}
	
	
	
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "OPTION";
		return ids;
	}
}