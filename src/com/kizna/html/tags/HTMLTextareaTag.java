// HTMLParser Library v1_2_20021031 - A java-based parser for HTML
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

package com.kizna.html.tags;

import com.kizna.html.util.*;

public class HTMLTextareaTag extends HTMLTag
{
	private String value;
	
	public HTMLTextareaTag(int pTagBegin, int pTagEnd, String pTagContents, 
							String value, String pTagLine)
	{
		super(pTagBegin,pTagEnd,pTagContents,pTagLine);
		this.value = value;
	}
	
	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String newValue) {
		value = newValue;
	}

	public String toHTML()
	{
		String htmlString = HTMLParserUtils.toHTML(this);
		StringBuffer returnString = new StringBuffer(htmlString);
		
		if (value != null)
			returnString.append(value);
		
		returnString.append("</TEXTAREA>");
		
		return returnString.toString();
	}
	
	public String toString() 
	{
		StringBuffer lString = new StringBuffer(HTMLParserUtils.toString(this));
		lString.append("VALUE : ").append(value).append("\n");
		
		return lString.toString();
	}
}
