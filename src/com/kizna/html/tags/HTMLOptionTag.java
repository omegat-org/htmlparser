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

package com.kizna.html.tags;

import com.kizna.html.util.*;

public class HTMLOptionTag extends HTMLTag
{
	private String mText;
	
	public HTMLOptionTag(int pTagBegin, int pTagEnd, String pTagContents, 
							String pText, String pTagLine)
	{
		super(pTagBegin,pTagEnd,pTagContents,pTagLine);
		this.mText = pText;
	}
	
	public java.lang.String getText() {
		return mText;
	}

	public void setText(java.lang.String newText) {
		mText = newText;
	}

	public String toHTML()
	{
		String lHTMLString = HTMLParserUtils.toHTML(this);
		
		StringBuffer lOptionTag = new StringBuffer(lHTMLString);

		if (mText != null)
			lOptionTag.append(mText);
		
		lOptionTag.append("</OPTION>");
		
		return lOptionTag.toString();
	}
	
	public String toString() 
	{
		StringBuffer lString = new StringBuffer(HTMLParserUtils.toString(this));
		lString.append("TEXT : ").append(mText).append("\n");
		
		return lString.toString();
	}
}
