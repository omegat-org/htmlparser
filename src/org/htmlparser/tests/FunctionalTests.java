// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package org.htmlparser.tests;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.scanners.HTMLImageScanner;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.util.DefaultHTMLParserFeedback;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FunctionalTests extends TestCase {

	/**
	 * Constructor for FunctionalTests.
	 * @param arg0
	 */
	public FunctionalTests(String arg0) {
		super(arg0);
	}
	/**
	 * Based on a suspected bug report by Annette Doyle,
	 * to check if the no of image tags are correctly 
	 * identified by the parser
	 */
	public void testNumImageTagsInYahooWithoutRegisteringScanners() throws HTMLParserException {
		// First count the image tags as is
		int imgTagCount;
		imgTagCount = findImageTagCount();
		try {
			int parserImgTagCount = countImageTagsWithHTMLParser();
			assertEquals("Image Tag Count",imgTagCount,parserImgTagCount);	
		}
		catch (HTMLParserException e) {
			throw new HTMLParserException("Error thrown in call to countImageTagsWithHTMLParser()",e);
		}
			
	}
	public int findImageTagCount() {
		int imgTagCount = 0;
		try {
			URL url = new URL("http://www.yahoo.com");
			InputStream is = url.openStream();
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(is));	
			imgTagCount = countImageTagsWithoutHTMLParser(reader);
			is.close();
		}
		catch (MalformedURLException e) {
			System.err.println("URL was malformed!");
		}
		catch (IOException e) {
			System.err.println("IO Exception occurred while trying to open stream");
		}
		return imgTagCount;
	}
	public int countImageTagsWithHTMLParser() throws HTMLParserException {
		HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		int parserImgTagCount = 0;
		HTMLNode node;
		for (HTMLEnumeration e= parser.elements();e.hasMoreNodes();) {
			node = (HTMLNode)e.nextHTMLNode();
			if (node instanceof HTMLImageTag) {
				parserImgTagCount++;				
			}		
		}
		return parserImgTagCount;
	}

	public int countImageTagsWithoutHTMLParser(BufferedReader reader) throws IOException {
		String line;
		int imgTagCount = 0;
		do {
			line = reader.readLine();
			if (line!=null) {
				// Check the line for image tags
				String newline = line.toUpperCase();
				int fromIndex = -1;
				do {
					fromIndex = newline.indexOf("<IMG",fromIndex+1);
					if (fromIndex!=-1) {
						imgTagCount++;
					}
				}
				while (fromIndex!=-1);
			}
		}
		while (line!=null);
		return imgTagCount;
	}
	public static TestSuite suite() {
		return new TestSuite(FunctionalTests.class);
	}
}