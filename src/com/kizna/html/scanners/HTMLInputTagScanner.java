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
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Email : somik@industriallogic.com
// Website : http://www.industriallogic.com

package com.kizna.html.scanners;

import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

public class HTMLInputTagScanner extends HTMLTagScanner
{
	public HTMLInputTagScanner()
	{
		super();
	}
	
	public HTMLInputTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public HTMLTag scan(HTMLTag pTag, String pUrl, HTMLReader pReader, String pCurrLine)
			throws HTMLParserException
	{
		try
		{
			HTMLInputTag lInputTag = new HTMLInputTag(
										pTag.elementBegin(),pTag.elementEnd(),
										pTag.getText(), pCurrLine);
			return lInputTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLInputTagScanner.scan() : Error while scanning input tags, current line = "+pCurrLine,e);
		}
	}
	
	/**
	 * @see com.kizna.html.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "INPUT";
		return ids;
	}
}