/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.kizna.html.scanners;

import java.io.IOException;
import java.util.Hashtable;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLMetaTag;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.HTMLParserException;

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
			Hashtable table = tag.parseParameters();
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
	 * @see com.kizna.html.scanners.HTMLTagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "META";
		return ids;
	}

}
