package com.kizna.html.tags;

import com.kizna.html.util.*;

public class HTMLTextareaTag extends HTMLTag
{
	private String mValue;
	
	public HTMLTextareaTag(int pTagBegin, int pTagEnd, String pTagContents, 
							String pValue, String pTagLine)
	{
		super(pTagBegin,pTagEnd,pTagContents,pTagLine);
		this.mValue = pValue;
	}
	
	public java.lang.String getValue() {
		return mValue;
	}

	public void setValue(java.lang.String newValue) {
		mValue = newValue;
	}

	public String toHTML()
	{
		String lHTMLString = HTMLParserUtils.toHTML(this);
		
		StringBuffer lTextareaTag = new StringBuffer(lHTMLString);
		
		if (mValue != null)
			lTextareaTag.append(mValue);
		
		lTextareaTag.append("</TEXTAREA>");
		
		return lTextareaTag.toString();
	}
	
	public String toString() 
	{
		StringBuffer lString = new StringBuffer(HTMLParserUtils.toString(this));
		lString.append("VALUE : ").append(mValue).append("\n");
		
		return lString.toString();
	}
}
