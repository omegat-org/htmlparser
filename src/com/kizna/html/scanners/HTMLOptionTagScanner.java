// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package com.kizna.html.scanners;

import java.util.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

public class HTMLOptionTagScanner extends HTMLTagScanner
{
	public HTMLOptionTagScanner()
	{
		super();
	}
	
	public HTMLOptionTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public HTMLTag scan(HTMLTag pTag, String pUrl, HTMLReader pReader, String pCurrLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag lEndTag=null;
			HTMLNode lNode = null;
			HTMLNode lPrevNode = pTag;
			boolean endTagFound=false;
			StringBuffer lText=new StringBuffer("");
			// Remove all existing scanners, so as to parse only till the end tag
			Hashtable tempScanners = HTMLParserUtils.adjustScanners(pReader);	

			do 
			{
				lNode = pReader.readElement();
				if (lNode instanceof HTMLEndTag)
				{
					lEndTag = (HTMLEndTag)lNode;
					String lEndTagString = lEndTag.getText().toUpperCase();
					if (lEndTagString.equals("OPTION") || lEndTagString.equals("SELECT")) 
					{
						endTagFound = true;
						if (lEndTagString.equals("SELECT"))
						{
							lNode = lPrevNode;
						}
					}
				}
				else if (lNode instanceof HTMLStringNode)
				{
					lText.append(lNode.toHTML());
				}
				else 
				{
					endTagFound = true;
					lNode = lPrevNode;
				}
				lPrevNode = lNode;
			}
			while (!endTagFound);
			HTMLOptionTag lOptionTag = new HTMLOptionTag(
										0, lNode.elementEnd(), pTag.getText(), 
										lText.toString(), pCurrLine);
			HTMLParserUtils.restoreScanners(pReader, tempScanners);
			return lOptionTag;										
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLOptionTagScanner.scan() : Error while scanning option tags, current line = "+pCurrLine,e);
		}
	}
	
	
	
	/**
	 * @see com.kizna.html.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "OPTION";
		return ids;
	}
}