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

package org.htmlparser.parserapplications;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;

public class StringExtractor {
	private String resource;
	public StringExtractor(String resource) {
		this.resource = resource;
	}
	public String extractStrings() throws HTMLParserException {
		HTMLParser parser = new HTMLParser(resource);
		parser.registerScanners();
		HTMLNode node;
		StringBuffer results= new StringBuffer();
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node = e.nextHTMLNode();
			results.append(node.toPlainTextString());
		}
		return results.toString();
	}
	public static void main(String[] args) {
		System.out.println("Running StringExtractor..");
		StringExtractor se = new StringExtractor(args[0]);	
		try {
			System.out.println("Strings=  "+se.extractStrings());
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
			
	}
}
