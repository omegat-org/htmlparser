package com.kizna.html.tags;

import java.util.*;
import com.kizna.html.util.*;

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
