/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.kizna.html.scanners;

import java.io.IOException;
import com.kizna.html.HTMLStringNode;
import com.kizna.html.tags.HTMLEndTag;
import com.kizna.html.tags.HTMLTitleTag;
import com.kizna.html.util.HTMLParserException;
import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLTag;

/**
 * Scans title tags.
 */
public class HTMLTitleScanner extends HTMLTagScanner {



	/**
	 * Constructor for HTMLTitleScanner.
	 * @param filter
	 */
	public HTMLTitleScanner(String filter) {
		super(filter);
	}

	/**
	 * @see HTMLTagScanner#evaluate(String, HTMLTagScanner)
	 */
	public boolean evaluate(String s, HTMLTagScanner previousOpenScanner) {
		absorbLeadingBlanks(s);
		if (s.toUpperCase().equals("TITLE") && previousOpenScanner==null)
		return true; else return false;
	}

	/**
	 * @see HTMLTagScanner#scan(HTMLTag, String, HTMLReader, String)
	 */
	public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currLine) throws HTMLParserException {
		try {
			boolean endFlag = false;
			String title = "";
			String tmp;
			HTMLNode node;
			HTMLEndTag endTag=null;
			do
			{
				node = reader.readElement();
	
				if (node instanceof HTMLStringNode)
				{
					title =((HTMLStringNode)node).getText();
				}
				if (node instanceof HTMLEndTag)
				{
					endTag = (HTMLEndTag)node;
				    tmp = endTag.getText();
				    if (tmp.toUpperCase().equals("TITLE")) {
				    	endFlag = true;
				    }
				} 
			}
			while (endFlag==false && node!=null);
			if (node==null && !endFlag) {
				throw new HTMLParserException("HTMLTitleScanner.scan(): Error while scanning title tag, went into a potential infinite loop, currentLine = "+currLine+", title so far = "+title);
			}
			HTMLTitleTag titleTag = new HTMLTitleTag(tag.elementBegin(),endTag.elementEnd(),title,tag.getText(),tag.getTagLine());
			return titleTag;		
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLTitleTagScanner.scan() : Error in scanning TitleTag, currentLine = "+currLine,e);
		}
	}

}
