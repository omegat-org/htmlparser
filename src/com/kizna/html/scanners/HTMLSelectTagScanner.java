package com.kizna.html.scanners;

import java.io.*;
import java.util.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;


public class HTMLSelectTagScanner extends HTMLTagScanner
{
	public HTMLSelectTagScanner()
	{
		super();
	}
	
	public HTMLSelectTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public boolean evaluate(String pTagString, HTMLTagScanner pPreviousOpenScanner)
	{
		return (HTMLParserUtils.evaluateTag(this,pTagString,"SELECT"));
	}

	public HTMLTag scan(HTMLTag pTag, String pUrl, HTMLReader pReader, String pCurrLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag lEndTag=null;
			HTMLNode lNode = null;
			boolean endTagFound=false;
			Vector lOptionTags=new Vector();
			
			// Remove all existing scanners, so as to parse only till the end tag
			Vector lScannerVector = HTMLParserUtils.adjustScanners(pReader);

			//However we need to activate Option tag scanner since select will 
			//have multiple option tags.
			pReader.getParser().addScanner(new HTMLOptionTagScanner());
			do 
			{
				lNode = pReader.readElement();
				if (lNode instanceof HTMLEndTag)
				{
					lEndTag = (HTMLEndTag)lNode;
					if (lEndTag.getText().toUpperCase().equals("SELECT")) 
					{
						endTagFound = true;
					}
				}
				else if (lNode instanceof HTMLOptionTag)
				{
					lOptionTags.add((HTMLOptionTag)lNode);
				}
				else
				{
					if (!(lNode instanceof HTMLStringNode))
						throw new HTMLParserException("Error occurred scanning select tag. Undefined tag : " + lNode.toHTML());
				}
			}
			while (!endTagFound);
			HTMLSelectTag lSelectTag = new HTMLSelectTag(
										0, lNode.elementEnd(), pTag.getText(), 
										lOptionTags, pCurrLine);
			HTMLParserUtils.restoreScanners(pReader, lScannerVector);
			return lSelectTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLSelectTagScanner.scan() : Error while scanning select tags, current line = "+pCurrLine,e);
		}
	}
	
}