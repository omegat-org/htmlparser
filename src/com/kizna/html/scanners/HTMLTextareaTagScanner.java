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
	
	public HTMLTag scan(HTMLTag tag, String url, HTMLReader reader, String currLine)
			throws HTMLParserException
	{
		try
		{
			HTMLEndTag endTag=null;
			HTMLNode node = null;
			boolean endTagFound=false;
			StringBuffer value=new StringBuffer();
			
			// Remove all existing scanners, so as to parse only till the end tag
			HTMLNode prevNode=tag;
			do 
			{
				node = reader.readElement();
				if (node instanceof HTMLEndTag)
				{
					endTag = (HTMLEndTag)node;
					if (endTag.getText().toUpperCase().equals("TEXTAREA")) 
					{
						endTagFound = true;
					}
				}
				else
				{
					if (prevNode!=null)
					{
						if (prevNode.elementEnd() > node.elementBegin()) 
							value.append(HTMLNode.getLineSeparator());
					}
					value.append(node.toHTML());
		
					prevNode = node;
				}
			}
			while (!endTagFound);
			HTMLTextareaTag textareaTag = new HTMLTextareaTag(
										0, node.elementEnd(), tag.getText(), 
										value.toString(), currLine);
			return textareaTag;
		}
		catch (Exception e) 
		{
			throw new HTMLParserException("HTMLTextareaTagScanner.scan() : Error while scanning textarea tags, current line = "+currLine,e);
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