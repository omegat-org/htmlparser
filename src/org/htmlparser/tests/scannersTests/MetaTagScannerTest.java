// HTMLParser Library v1_3_20030427 - A java-based parser for HTML
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

import org.htmlparser.scanners.MetaTagScanner;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class MetaTagScannerTest extends ParserTestCase {

	public MetaTagScannerTest(String name) {
		super(name);
	}

	public void testScan() throws ParserException {
		createParser(
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">\n"+
		"<html>\n"+
		"<head><title>SpamCop - Welcome to SpamCop\n"+
		"</title>\n"+
		"<META name=\"description\" content=\"Protecting the internet community through technology, not legislation.  SpamCop eliminates spam.  Automatically file spam reports with the network administrators who can stop spam at the source.  Subscribe, and filter your email through powerful statistical analysis before it reaches your inbox.\">\n"+
		"<META name=\"keywords\" content=\"SpamCop spam cop email filter abuse header headers parse parser utility script net net-abuse filter mail program system trace traceroute dns\">\n"+
		"<META name=\"language\" content=\"en\">\n"+
		"<META name=\"owner\" content=\"service@admin.spamcop.net\">\n"+
		"<META HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=ISO-8859-1\">","http://www.google.com/test/index.html");
		MetaTagScanner scanner = new MetaTagScanner("-t");
		parser.addScanner(scanner);
		
		parseAndAssertNodeCount(11);
		assertTrue("Node 5 should be End Tag",node[5] instanceof EndTag);
		assertTrue("Node 6 should be META Tag",node[6] instanceof MetaTag);
		MetaTag metaTag;
		metaTag = (MetaTag) node[6];
		assertEquals("Meta Tag 6 Name","description",metaTag.getMetaTagName());
		assertEquals("Meta Tag 6 Contents","Protecting the internet community through technology, not legislation.  SpamCop eliminates spam.  Automatically file spam reports with the network administrators who can stop spam at the source.  Subscribe, and filter your email through powerful statistical analysis before it reaches your inbox.",metaTag.getMetaContent());
		
		assertTrue("Node 7 should be META Tag",node[7] instanceof MetaTag);
		assertTrue("Node 8 should be META Tag",node[8] instanceof MetaTag);
		assertTrue("Node 9 should be META Tag",node[9] instanceof MetaTag);
		
		metaTag = (MetaTag) node[7];
		assertEquals("Meta Tag 7 Name","keywords",metaTag.getMetaTagName());
		assertEquals("Meta Tag 7 Contents","SpamCop spam cop email filter abuse header headers parse parser utility script net net-abuse filter mail program system trace traceroute dns",metaTag.getMetaContent());
		assertNull("Meta Tag 7 Http-Equiv",metaTag.getHttpEquiv());
		
		metaTag = (MetaTag) node[8];
		assertEquals("Meta Tag 8 Name","language",metaTag.getMetaTagName());
		assertEquals("Meta Tag 8 Contents","en",metaTag.getMetaContent());
		assertNull("Meta Tag 8 Http-Equiv",metaTag.getHttpEquiv());
		
		metaTag = (MetaTag) node[9];
		assertEquals("Meta Tag 9 Name","owner",metaTag.getMetaTagName());
		assertEquals("Meta Tag 9 Contents","service@admin.spamcop.net",metaTag.getMetaContent());
		assertNull("Meta Tag 9 Http-Equiv",metaTag.getHttpEquiv());

		metaTag = (MetaTag) node[10];
		assertNull("Meta Tag 10 Name",metaTag.getMetaTagName());
		assertEquals("Meta Tag 10 Contents","text/html; charset=ISO-8859-1",metaTag.getMetaContent());
		assertEquals("Meta Tag 10 Http-Equiv","content-type",metaTag.getHttpEquiv());

		assertEquals("This Scanner",scanner,metaTag.getThisScanner());
	}

	public void testScanTagsInMeta() throws ParserException {
		createParser(
		"<META NAME=\"Description\" CONTENT=\"Ethnoburb </I>versus Chinatown: Two Types of Urban Ethnic Communities in Los Angeles\">",
		"http://www.google.com/test/index.html"
		);
		MetaTagScanner scanner = new MetaTagScanner("-t");
		parser.addScanner(scanner);
		parseAndAssertNodeCount(1);
		assertTrue("Node should be meta tag",node[0] instanceof MetaTag);
		MetaTag metaTag = (MetaTag)node[0];
		assertEquals("Meta Tag Name","Description",metaTag.getMetaTagName());
		assertEquals("Content","Ethnoburb </I>versus Chinatown: Two Types of Urban Ethnic Communities in Los Angeles",metaTag.getMetaContent());
	}
	
	/**
	 * Tried to reproduce bug 707447 but test passes
	 * @throws ParserException
	 */
	public void testMetaTagBug() throws ParserException {
		createParser(
			"<html>" +			"<head>" +			"<meta http-equiv=\"content-type\"" +			" content=\"text/html;" +			" charset=windows-1252\">" +			"</head>" +			"</html>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(5);
		assertType("Meta Tag expected", MetaTag.class, node[2]);
		MetaTag metaTag = (MetaTag)node[2];
		
		assertStringEquals("http-equiv","content-type",metaTag.getHttpEquiv());
		assertStringEquals("content","text/html; charset=windows-1252",metaTag.getMetaContent());
	}

	/**
	 * Bug report 702547 by Joe Robbins being reproduced.
	 * @throws ParserException
	 */
	public void testMetaTagWithOpenTagSymbol() throws ParserException {
		createParser(
			"<html>" +			"<head>" +			"<title>Parser Test 2</title>" +			"<meta name=\"foo\" content=\"a<b\">" +			"</head>" +			"<body>" +			"<a href=\"http://www.yahoo.com/\">Yahoo!</a><br>" +			"<a href=\"http://www.excite.com\">Excite</a>" +			"</body>" +			"</html>"		);
		parser.registerScanners();
		parseAndAssertNodeCount(11);
		assertType("meta tag",MetaTag.class,node[3]);
		MetaTag metaTag = (MetaTag)node[3];
		assertStringEquals(
			"meta content",
			"a<b",
			metaTag.getMetaContent()
		);
	}
}
