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

import java.util.List;

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserUtils;

public class SelectTag extends CompositeTag
{
	private OptionTag[] optionTags=null;
	private List optionTagList;
	
	public SelectTag(
		TagData tagData, 
		CompositeTagData compositeTagData,
		List optionTagList)
	{
		super(tagData,compositeTagData);
		this.optionTagList = optionTagList;
	}
	
	public OptionTag [] getOptionTags()
	{
		if (optionTags==null) {
			optionTags = new OptionTag[optionTagList.size()];
			for (int i=0;i<optionTagList.size();i++) {
				optionTags[i] = (OptionTag)optionTagList.get(i);	
			}
		}
		return optionTags;
	}
	
	public String toString() 
	{
		StringBuffer lString = new StringBuffer(ParserUtils.toString(this));
		for(int i=0;i<childTags.size(); i++)
		{
			OptionTag optionTag = (OptionTag)childTags.elementAt(i);
			lString.append(optionTag.toString()).append("\n");
		}
		
		return lString.toString();
	}
}
