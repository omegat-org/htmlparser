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
	
	public boolean evaluate(String pTagString, HTMLTagScanner pPreviousOpenScanner)
	{
		return (HTMLParserUtils.evaluateTag(this,pTagString,"INPUT"));
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
	
}