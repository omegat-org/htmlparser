package com.kizna.html.scanners;

import java.util.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;



public class HTMLTextareaTagScanner extends HTMLTagScanner
{
	public HTMLTextareaTagScanner()
	{
		super();
	}
	
	public HTMLTextareaTagScanner(String pFilter)
	{
		super(pFilter);
	}
	
	public HTMLTag scan(HTMLTag pTag, String pUrl, HTMLReader pReader, String pCurrLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag lEndTag=null;
			HTMLNode lNode = null;
			boolean endTagFound=false;
			StringBuffer lValue=new StringBuffer();
			
			// Remove all existing scanners, so as to parse only till the end tag
			HTMLNode lPrevNode=pTag;
			do 
			{
				lNode = pReader.readElement();
				if (lNode instanceof HTMLEndTag)
				{
					lEndTag = (HTMLEndTag)lNode;
					if (lEndTag.getText().toUpperCase().equals("TEXTAREA")) 
					{
						endTagFound = true;
					}
				}
				else
				{
					if (lPrevNode!=null)
					{
						if (lPrevNode.elementEnd() > lNode.elementBegin()) 
							lValue.append(HTMLNode.getLineSeparator());
					}
					lValue.append(lNode.toHTML());
		
					lPrevNode = lNode;
				}
			}
			while (!endTagFound);
			HTMLTextareaTag lTextareaTag = new HTMLTextareaTag(
										0, lNode.elementEnd(), pTag.getText(), 
										lValue.toString(), pCurrLine);
			return lTextareaTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLTextareaTagScanner.scan() : Error while scanning textarea tags, current line = "+pCurrLine,e);
		}
	}
	
	
	
	/**
	 * @see com.kizna.html.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "TEXTAREA";
		return ids;
	}

}