package com.kizna.html.tags;

import com.kizna.html.util.*;

public class HTMLInputTag extends HTMLTag
{
	public HTMLInputTag(int pTagBegin, int pTagEnd, 
						String pTagContents, String pTagLine)
	{
		super(pTagBegin,pTagEnd,pTagContents,pTagLine);
	}
		
	public String toHTML()
	{
		return (HTMLParserUtils.toHTML(this));
	}
	
	public String toString() 
	{
		return (HTMLParserUtils.toString(this));
	}
}
