// HTMLParser Library v1_3_20030125 - A java-based parser for HTML
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

import junit.framework.*;
import org.htmlparser.scanners.BodyScanner;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class BodyTagTest extends ParserTestCase {
	private BodyTag bodyTag;

	public BodyTagTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		createParser("<html><head><title>body tag test</title></head><body>Yahoo!</body></html>");
		parser.registerScanners();
		parser.addScanner(new BodyScanner("-b"));
	 	parseAndAssertNodeCount(6);
	 	assertTrue(node[4] instanceof BodyTag);
		bodyTag = (BodyTag) node[4];
	}
	
	public void testToPlainTextString() throws ParserException {
		// check the label node
		assertEquals("Body","Yahoo!",bodyTag.toPlainTextString());				
	}

	public void testToHTML() throws ParserException {
		assertStringEquals("Raw String","<BODY>Yahoo!</BODY>",bodyTag.toHTML());				
	}

	public void testToString() throws ParserException  {
		assertEquals("Body","BODY: Yahoo!",bodyTag.toString());		
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(BodyTagTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {BodyTagTest.class.getName()});
	}
}
