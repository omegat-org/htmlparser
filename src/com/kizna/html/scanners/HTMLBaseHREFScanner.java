// HTMLParser Library v1_2_20020831 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.html.scanners;

import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLBaseHREFTag;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.HTMLLinkProcessor;
import com.kizna.html.util.HTMLParserException;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLBaseHREFScanner extends HTMLTagScanner {
	private HTMLLinkProcessor processor;
	/**
	 * Constructor for HTMLBaseHREFScanner.
	 */
	public HTMLBaseHREFScanner() {
		super();
	}

	/**
	 * Constructor for HTMLBaseHREFScanner.
	 * @param filter
	 */
	public HTMLBaseHREFScanner(String filter,HTMLLinkProcessor processor) {
		super(filter);
		this.processor = processor;
	}

	/**
	 * @see com.kizna.html.scanners.HTMLTagScanner#evaluate(String, HTMLTagScanner)
	 */
	public boolean evaluate(String s, HTMLTagScanner previousOpenScanner) {
		s = absorbLeadingBlanks(s);
		if (s.toUpperCase().indexOf("BASE")==0)
		return true; else return false;
	}

	/**
	 * @see com.kizna.html.scanners.HTMLTagScanner#scan(HTMLTag, String, HTMLReader, String)
	 */
	public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currLine)	throws HTMLParserException {
		String baseUrl = (String)tag.getParameter("HREF");
      	String absoluteBaseUrl = removeLastSlash(baseUrl.trim());
      	processor.setBaseUrl(absoluteBaseUrl);
		return new HTMLBaseHREFTag(tag.elementBegin(),tag.elementEnd(),tag.getText(),absoluteBaseUrl,currLine);
	}
	public String removeLastSlash(String baseUrl)
   	{
      if(baseUrl.charAt(baseUrl.length()-1)=='/')
      {
         return baseUrl.substring(0,baseUrl.length()-1);
      }
      else
      {
         return baseUrl;
      }
   	}
}
