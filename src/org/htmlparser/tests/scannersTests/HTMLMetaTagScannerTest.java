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

package org.htmlparser.tests.scannersTests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;
import java.util.*;
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.HTMLMetaTagScanner;
/**
 * @version 	1.0
 * @author
 */
public class HTMLMetaTagScannerTest extends TestCase {

	/**
	 * Constructor for HTMLMetaTagScannerTest.
	 * @param arg0
	 */
	public HTMLMetaTagScannerTest(String arg0) {
		super(arg0);
	}
	public void testScan() throws HTMLParserException {
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
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		HTMLMetaTagScanner scanner = new HTMLMetaTagScanner("-t");
		parser.addScanner(scanner);
		
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 11 nodes identified",11,i);	
		assertTrue("Node 5 should be End Tag",node[5] instanceof HTMLEndTag);
		assertTrue("Node 6 should be META Tag",node[6] instanceof HTMLMetaTag);
		HTMLMetaTag metaTag;
		metaTag = (HTMLMetaTag) node[6];
		assertEquals("Meta Tag 6 Name","description",metaTag.getMetaTagName());
		assertEquals("Meta Tag 6 Contents","Protecting the internet community through technology, not legislation.  SpamCop eliminates spam.  Automatically file spam reports with the network administrators who can stop spam at the source.  Subscribe, and filter your email through powerful statistical analysis before it reaches your inbox.",metaTag.getMetaTagContents());
		
		assertTrue("Node 7 should be META Tag",node[7] instanceof HTMLMetaTag);
		assertTrue("Node 8 should be META Tag",node[8] instanceof HTMLMetaTag);
		assertTrue("Node 9 should be META Tag",node[9] instanceof HTMLMetaTag);
		
		metaTag = (HTMLMetaTag) node[7];
		assertEquals("Meta Tag 7 Name","keywords",metaTag.getMetaTagName());
		assertEquals("Meta Tag 7 Contents","SpamCop spam cop email filter abuse header headers parse parser utility script net net-abuse filter mail program system trace traceroute dns",metaTag.getMetaTagContents());
		assertNull("Meta Tag 7 Http-Equiv",metaTag.getHttpEquiv());
		
		metaTag = (HTMLMetaTag) node[8];
		assertEquals("Meta Tag 8 Name","language",metaTag.getMetaTagName());
		assertEquals("Meta Tag 8 Contents","en",metaTag.getMetaTagContents());
		assertNull("Meta Tag 8 Http-Equiv",metaTag.getHttpEquiv());
		
		metaTag = (HTMLMetaTag) node[9];
		assertEquals("Meta Tag 9 Name","owner",metaTag.getMetaTagName());
		assertEquals("Meta Tag 9 Contents","service@admin.spamcop.net",metaTag.getMetaTagContents());
		assertNull("Meta Tag 9 Http-Equiv",metaTag.getHttpEquiv());

		metaTag = (HTMLMetaTag) node[10];
		assertNull("Meta Tag 10 Name",metaTag.getMetaTagName());
		assertEquals("Meta Tag 10 Contents","text/html; charset=ISO-8859-1",metaTag.getMetaTagContents());
		assertEquals("Meta Tag 10 Http-Equiv","content-type",metaTag.getHttpEquiv());

		assertEquals("This Scanner",scanner,metaTag.getThisScanner());
	}
	public void testScanTagsInMeta() throws HTMLParserException {
		String testHTML = new String(
		"<META NAME=\"Description\" CONTENT=\"Ethnoburb </I>versus Chinatown: Two Types of Urban Ethnic Communities in Los Angeles\">"
		);
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		HTMLMetaTagScanner scanner = new HTMLMetaTagScanner("-t");
		parser.addScanner(scanner);
		
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",1,i);	
		assertTrue("Node should be meta tag",node[0] instanceof HTMLMetaTag);
		HTMLMetaTag metaTag = (HTMLMetaTag)node[0];
		assertEquals("Meta Tag Name","Description",metaTag.getMetaTagName());
		assertEquals("Content","Ethnoburb </I>versus Chinatown: Two Types of Urban Ethnic Communities in Los Angeles",metaTag.getMetaTagContents());
	}
	public static void main(String[] args) {
		new junit.awtui.TestRunner().start(new String[] {"org.htmlparser.tests.scannersTests.HTMLMetaTagScannerTest"});
	}
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLMetaTagScannerTest.class);
		return suite;
	}
}