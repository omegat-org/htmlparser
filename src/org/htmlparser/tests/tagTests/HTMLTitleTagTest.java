// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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

package org.htmlparser.tests.tagTests;

import java.io.BufferedReader;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLNode;
import org.htmlparser.tags.HTMLTitleTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.*;
import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLTitleTagTest extends HTMLParserTestCase {
	private HTMLTitleTag titleTag;

	public HTMLTitleTagTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		createParser("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
		parser.addScanner(new HTMLTitleScanner("-t"));
		parser.addScanner(new HTMLStyleScanner("-s"));
		parser.addScanner(new HTMLMetaTagScanner("-m"));
	 	parseAndAssertNodeCount(7);
	 	assertTrue(node[2] instanceof HTMLTitleTag);
		titleTag = (HTMLTitleTag) node[2];
	}
	public void testToPlainTextString() throws HTMLParserException {
		// check the title node
		assertEquals("Title","Yahoo!",titleTag.toPlainTextString());				
	}

	public void testToHTML() throws HTMLParserException {
		assertEquals("Raw String","<TITLE>Yahoo!</TITLE>",titleTag.toHTML());				
	}

	public void testToString() throws HTMLParserException  {
		assertEquals("Title","TITLE: Yahoo!",titleTag.toString());		
	}
}
