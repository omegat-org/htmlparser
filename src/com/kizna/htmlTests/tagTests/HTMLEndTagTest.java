// HTMLParser Library v1_2_20020804 - A java-based parser for HTML
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

package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLEndTag;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLEndTagTest extends TestCase {

	/**
	 * Constructor for HTMLEndTagTest.
	 * @param arg0
	 */
	public HTMLEndTagTest(String name) {
		super(name);
	}
	public void testToHTML() throws HTMLParserException {
		String testHTML = new String("<HTML></HTML>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com/");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.registerScanners();			
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
				node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 2 nodes identified",new Integer(2),new Integer(i));
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLEndTag",node[1] instanceof HTMLEndTag);
		HTMLEndTag endTag = (HTMLEndTag)node[1];
		assertEquals("Raw String","</HTML>",endTag.toHTML());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLEndTagTest.class);
	}

}
