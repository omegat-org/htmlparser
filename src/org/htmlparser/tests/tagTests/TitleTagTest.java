// HTMLParser Library v1_3_20030316 - A java-based parser for HTML
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

import org.htmlparser.scanners.MetaTagScanner;
import org.htmlparser.scanners.StyleScanner;
import org.htmlparser.scanners.TitleScanner;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class TitleTagTest extends ParserTestCase {
	private TitleTag titleTag;

	public TitleTagTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		createParser("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
		parser.addScanner(new TitleScanner("-t"));
		parser.addScanner(new StyleScanner("-s"));
		parser.addScanner(new MetaTagScanner("-m"));
	 	parseAndAssertNodeCount(7);
	 	assertTrue(node[2] instanceof TitleTag);
		titleTag = (TitleTag) node[2];
	}
	public void testToPlainTextString() throws ParserException {
		// check the title node
		assertEquals("Title","Yahoo!",titleTag.toPlainTextString());				
	}

	public void testToHTML() throws ParserException {
		assertStringEquals("Raw String","<TITLE>Yahoo!</TITLE>",titleTag.toHtml());				
	}

	public void testToString() throws ParserException  {
		assertEquals("Title","TITLE: Yahoo!",titleTag.toString());		
	}
}
