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
