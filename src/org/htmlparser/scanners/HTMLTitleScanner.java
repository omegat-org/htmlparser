// HTMLParser Library v1_2_20021201 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com


package org.htmlparser.scanners;

import java.io.IOException;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTitleTag;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLTag;

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

	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "TITLE";
		return ids;
	}

}
