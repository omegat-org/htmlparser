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

package org.htmlparser.tags;

import org.htmlparser.tags.data.*;
import org.htmlparser.util.*;

public class OptionTag extends Tag
{
	private String value;
	
	public OptionTag(TagData tagData, String text)
	{
		super(tagData);
		this.value = text;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toHTML()
	{
		String lHTMLString = ParserUtils.toHTML(this);
		
		StringBuffer lOptionTag = new StringBuffer(lHTMLString);

		if (value != null)
			lOptionTag.append(value);
		
		lOptionTag.append("</OPTION>");
		
		return lOptionTag.toString();
	}
	
	public String toString() 
	{
		StringBuffer lString = new StringBuffer(ParserUtils.toString(this));
		lString.append("TEXT : ").append(value).append("\n");
		
		return lString.toString();
	}
}
