// HTMLParser Library v1_2_20020728 - A java-based parser for HTML
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
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLLinkScanner;
import com.kizna.html.tags.HTMLDoctypeTag;
import com.kizna.html.tags.HTMLLinkTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLDoctypeTagTest extends TestCase {

	/**
	 * Constructor for HTMLDoctypeTagTest.
	 * @param arg0
	 */
	public HTMLDoctypeTagTest(String name) {
		super(name);
	}
	public void testToHTML() {
		String testHTML = new String(
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">\n"+
		"<HTML>\n"+
		"<HEAD>\n"+
		"<TITLE>Cogs of Chicago</TITLE>\n"+
		"</HEAD>\n"+
		"<BODY>\n"+
		"...\n"+
		"</BODY>\n"+
		"</HTML>\n");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cj.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		parser.registerScanners();
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 9 nodes identified",new Integer(9),new Integer(i));
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLDoctypeTag",node[0] instanceof HTMLDoctypeTag);
		HTMLDoctypeTag docTypeTag = (HTMLDoctypeTag)node[0];
		assertEquals("Raw HTML","<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">",docTypeTag.toHTML());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLDoctypeTagTest.class);
	}

}
