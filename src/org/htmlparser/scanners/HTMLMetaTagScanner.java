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

import java.util.Hashtable;

import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLMetaTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.HTMLParserException;

/**
 * Scans meta tags.
 */
public class HTMLMetaTagScanner extends HTMLTagScanner {
	public HTMLMetaTagScanner(String filter) {
		super(filter);
	}
		/*
	 * @see HTMLTagScanner#scan(HTMLTag, String, HTMLReader, String)
	 */
	public HTMLTag scan(HTMLTag tag,String url,HTMLReader reader, String currLine)
		throws HTMLParserException {
		try {
			// Since its a simple tag, all META TAG info will 
			// be in the tag itself
			Hashtable table = tag.getParsed();
			String metaTagName = (String)table.get("NAME");					
			String metaTagContents = (String)table.get("CONTENT");
			String httpEquiv = (String)table.get("HTTP-EQUIV");
			HTMLMetaTag metaTag = new HTMLMetaTag(tag.elementBegin(),tag.elementEnd(),tag.getText(),httpEquiv, metaTagName,metaTagContents,currLine);
			return metaTag;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLMetaTagScanner.scan() : Error while scanning meta tags, current line = "+currLine,e);
		}
	}

	/**
	 * @see org.htmlparser.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "META";
		return ids;
	}

}
