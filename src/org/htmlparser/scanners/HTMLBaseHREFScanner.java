// HTMLParser Library v1_2 - A java-based parser for HTML
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

import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLBaseHREFTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;

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
	 * @see org.htmlparser.scanners.HTMLTagScanner#scan(HTMLTag, String, HTMLReader, String)
	 */
	public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currLine)	throws HTMLParserException {
		String baseUrl = (String)tag.getParameter("HREF");
		String absoluteBaseUrl="";
		if (baseUrl != null && baseUrl.length()>0) {
	      	absoluteBaseUrl = removeLastSlash(baseUrl.trim());
    	  	processor.setBaseUrl(absoluteBaseUrl);
		} 
		return new HTMLBaseHREFTag(new HTMLTagData(tag.elementBegin(),tag.elementEnd(),tag.getText(),currLine),absoluteBaseUrl);
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
	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "BASE";
		return ids;
	}

}
