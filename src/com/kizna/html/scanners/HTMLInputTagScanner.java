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