// HTMLParser Library v1_3_20030202 - A java-based parser for HTML
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


package org.htmlparser.parserapplications;


import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

/**
 * LinkExtractor extracts all the links from the given webpage
 * and prints them on standard output.
 */
public class LinkExtractor {
	private String location;
	private HTMLParser parser;
	public LinkExtractor(String location) {
		this.location = location;
		try {
			this.parser   = new HTMLParser(location); // Create the parser object
			parser.registerScanners(); // Register standard scanners (Very Important)
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
		
	}
	public void extractLinks() throws HTMLParserException {
		HTMLNode node;
		HTMLLinkTag linkTag;
		System.out.println("Parsing "+location+" for links...");
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node = e.nextNode();	// Get the next HTML Node
			if (node instanceof HTMLLinkTag) {
				linkTag = (HTMLLinkTag)node; // Downcast to a Link Tag
				
				linkTag.print(); // Print it
				
				// To extract only mail addresses, uncomment the following line
				//if (linkTag.isMailLink()) System.out.println(linkTag.getLink());
			}
		}
	}
	public static void main(String[] args) {
		if (args.length<0) {
			System.err.println("Syntax Error : Please provide the location(URL or file) to parse");
			System.exit(-1);
		}
		LinkExtractor linkExtractor = new LinkExtractor(args[0]);
		try {
			linkExtractor.extractLinks();
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
	}
}

