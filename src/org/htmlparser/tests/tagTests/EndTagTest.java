// HTMLParser Library v1_3_20030504 - A java-based parser for HTML
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

import org.htmlparser.tags.EndTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class EndTagTest extends ParserTestCase {

	public EndTagTest(String name) {
		super(name);
	}

	public void testToHTML() throws ParserException {
		createParser("<HTML></HTML>");
		// Register the image scanner
		parser.registerScanners();			
		parseAndAssertNodeCount(2);
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLEndTag",node[1] instanceof EndTag);
		EndTag endTag = (EndTag)node[1];
		assertEquals("Raw String","</HTML>",endTag.toHtml());
	}
	
	public void testEndTagFind() {
		String testHtml = 
			"<SCRIPT>document.write(d+\".com\")</SCRIPT>";
		int pos = testHtml.indexOf("</SCRIPT>");
		EndTag endTag = (EndTag)EndTag.find(testHtml,pos);
		assertEquals("endtag element begin",32,endTag.elementBegin());
		assertEquals("endtag element end",40,endTag.elementEnd());
	}
}
