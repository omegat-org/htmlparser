// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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

import org.htmlparser.tags.BaseHrefTag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class BaseHrefTagTest extends ParserTestCase {

	public BaseHrefTagTest(String name) {
		super(name);
	}
	
	public void testConstruction() {
		BaseHrefTag baseRefTag = new BaseHrefTag(new TagData(0,0,"",""),"http://www.abc.com");
		assertEquals("Expected Base URL","http://www.abc.com",baseRefTag.getBaseUrl());
	}
	
	public void testNotHREFBaseTag() throws ParserException {
		createParser("<base target=\"_top\">");
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Should be a base tag but was "+node[0].getClass().getName(),node[0] instanceof BaseHrefTag);
		BaseHrefTag baseTag = (BaseHrefTag)node[0];
		assertStringEquals("Base Tag HTML","<BASE TARGET=\"_top\">",baseTag.toHtml());
	}

}
