package com.kizna.html.scanners;

import java.util.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

public class HTMLOptionTagScanner extends HTMLTagScanner
{
	public HTMLOptionTagScanner()
	{
		super();
	}
	
	public HTMLOptionTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public boolean evaluate(String pTagString, HTMLTagScanner pPreviousOpenScanner)
	{
		return (HTMLParserUtils.evaluateTag(this,pTagString,"OPTION"));
	}

	public HTMLTag scan(HTMLTag pTag, String pUrl, HTMLReader pReader, String pCurrLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag lEndTag=null;
			HTMLNode lNode = null;
			HTMLNode lPrevNode = pTag;
			boolean endTagFound=false;
			StringBuffer lText=new StringBuffer("");

			// Remove all existing scanners, so as to parse only till the end tag
			Vector lScannerVector = HTMLParserUtils.adjustScanners(pReader);	
			do 
			{
				lNode = pReader.readElement();
				if (lNode instanceof HTMLEndTag)
				{
					lEndTag = (HTMLEndTag)lNode;
					String lEndTagString = lEndTag.getText().toUpperCase();
					if (lEndTagString.equals("OPTION") || lEndTagString.equals("SELECT")) 
					{
						endTagFound = true;
						if (lEndTagString.equals("SELECT"))
						{
							lNode = lPrevNode;
						}
					}
				}
				else if (lNode instanceof HTMLStringNode)
				{
					lText.append(lNode.toHTML());
				}
				else 
				{
					endTagFound = true;
					lNode = lPrevNode;
				}
				lPrevNode = lNode;
			}
			while (!endTagFound);
			HTMLOptionTag lOptionTag = new HTMLOptionTag(
										0, lNode.elementEnd(), pTag.getText(), 
										lText.toString(), pCurrLine);
			HTMLParserUtils.restoreScanners(pReader, lScannerVector);
			return lOptionTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLOptionTagScanner.scan() : Error while scanning option tags, current line = "+pCurrLine,e);
		}
	}
	
	
	
}