// HTMLParser Library v1_2_20020707 - A java-based parser for HTML
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
import com.kizna.html.tags.HTMLMetaTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLMetaTagTest extends TestCase {

	/**
	 * Constructor for HTMLMetaTagTest.
	 * @param arg0
	 */
	public HTMLMetaTagTest(String name) {
		super(name);
	}
	public void testToHTML() {
		String testHTML = new String(
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">\n"+
		"<html>\n"+
		"<head><title>SpamCop - Welcome to SpamCop\n"+
		"</title>\n"+
		"<META name=\"description\" content=\"Protecting the internet community through technology, not legislation.  SpamCop eliminates spam.  Automatically file spam reports with the network administrators who can stop spam at the source.  Subscribe, and filter your email through powerful statistical analysis before it reaches your inbox.\">\n"+
		"<META name=\"keywords\" content=\"SpamCop spam cop email filter abuse header headers parse parser utility script net net-abuse filter mail program system trace traceroute dns\">\n"+
		"<META name=\"language\" content=\"en\">\n"+
		"<META name=\"owner\" content=\"service@admin.spamcop.net\">\n"+
		"<META HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=ISO-8859-1\">");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[20];

		parser.registerScanners();
		
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 9 nodes identified",9,i);	
		assertTrue("Node 5 should be META Tag",node[4] instanceof HTMLMetaTag);
		HTMLMetaTag metaTag;
		metaTag = (HTMLMetaTag) node[4];
		assertEquals("Meta Tag 4 Name","description",metaTag.getMetaTagName());
		assertEquals("Meta Tag 4 Contents","Protecting the internet community through technology, not legislation.  SpamCop eliminates spam.  Automatically file spam reports with the network administrators who can stop spam at the source.  Subscribe, and filter your email through powerful statistical analysis before it reaches your inbox.",metaTag.getMetaTagContents());
		assertEquals("Raw String","<META name=\"description\" content=\"Protecting the internet community through technology, not legislation.  SpamCop eliminates spam.  Automatically file spam reports with the network administrators who can stop spam at the source.  Subscribe, and filter your email through powerful statistical analysis before it reaches your inbox.\">",metaTag.toHTML());
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLMetaTagTest.class);
	}
}
