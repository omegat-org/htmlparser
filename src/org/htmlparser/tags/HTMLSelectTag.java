// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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

import java.util.*;
import org.htmlparser.util.*;

public class HTMLSelectTag extends HTMLTag
{
	//This vector consists of all the option tags under the select tag
	private Vector mOptionTags;
	
	public HTMLSelectTag(int pTagBegin, int pTagEnd, String pTagContents, 
							Vector pOptionTags, String pTagLine)
	{
		super(pTagBegin,pTagEnd,pTagContents,pTagLine);
		this.mOptionTags = pOptionTags;
	}
	
	public Vector getOptionTags()
	{
		return mOptionTags;
	}
	
	public void setOptionTags(Vector newOptionTags)
	{
		this.mOptionTags = newOptionTags;
	}

	public String toHTML()
	{
		String lHTMLString = HTMLParserUtils.toHTML(this);
		
		StringBuffer lSelectTag = new StringBuffer(lHTMLString);
		lSelectTag.append(lineSeparator);
		
		for(int i=0;i<mOptionTags.size(); i++)
		{
			HTMLOptionTag lOptionTag = (HTMLOptionTag)mOptionTags.elementAt(i);
			lSelectTag.append(lOptionTag.toHTML()).append(lineSeparator);
		}
		lSelectTag.append("</SELECT>");
		
		return lSelectTag.toString();
	}
	
	public String toString() 
	{
		StringBuffer lString = new StringBuffer(HTMLParserUtils.toString(this));
		for(int i=0;i<mOptionTags.size(); i++)
		{
			HTMLOptionTag lOptionTag = (HTMLOptionTag)mOptionTags.elementAt(i);
			lString.append(lOptionTag.toString()).append("\n");
		}
		
		return lString.toString();
	}
}
