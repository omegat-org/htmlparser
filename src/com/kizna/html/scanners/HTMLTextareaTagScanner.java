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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Email : somik@industriallogic.com
// Website : http://www.industriallogic.com

package com.kizna.html.scanners;

import java.util.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;



public class HTMLTextareaTagScanner extends HTMLTagScanner
{
	public HTMLTextareaTagScanner()
	{
		super();
	}
	
	public HTMLTextareaTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public HTMLTag scan(HTMLTag tag, String url, HTMLReader reader, String currLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag endTag=null;
			HTMLNode node = null;
			boolean endTagFound=false;
			StringBuffer value=new StringBuffer();
			
			// Remove all existing scanners, so as to parse only till the end tag
			HTMLNode prevNode=tag;
			do 
			{
				node = reader.readElement();
				if (node instanceof HTMLEndTag)
				{
					endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("TEXTAREA")) 
					{
						endTagFound = true;
					}
				}
				else
				{
					if (prevNode!=null)
					{
						if (prevNode.elementEnd() > node.elementBegin()) 
							value.append(HTMLNode.getLineSeparator());
					}
					value.append(node.toHTML());
		
					prevNode = node;
				}
			}
			while (!endTagFound);
			HTMLTextareaTag textareaTag = new HTMLTextareaTag(
										0, node.elementEnd(), tag.getText(), 
										value.toString(), currLine);
			return textareaTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLTextareaTagScanner.scan() : Error while scanning textarea tags, current line = "+currLine,e);
		}
	}
	
	
	
	/**
	 * @see com.kizna.html.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "TEXTAREA";
		return ids;
	}

}