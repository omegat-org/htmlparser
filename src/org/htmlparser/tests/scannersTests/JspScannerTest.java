// HTMLParser Library v1_3_20030419 - A java-based parser for HTML
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

import org.htmlparser.Parser;
import org.htmlparser.scanners.JspScanner;
import org.htmlparser.tags.JspTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class JspScannerTest extends ParserTestCase {

	public JspScannerTest(String name) {
		super(name);
	}

	/**
	 * In response to bug report 621117, wherein jsp tags
	 * are not recognized if they occur within string nodes.
	 */
	public void testScan() throws ParserException {
		createParser(
		"<h1>\n"+
		"This is a <%=object%>\n"+
		"</h1>");
		
		// Register the Jsp Scanner
		parser.addScanner(new JspScanner("-j"));	
		parseAndAssertNodeCount(4);
		// The first node should be an HTMLJspTag
		assertTrue("Third should be an HTMLJspTag",node[2] instanceof JspTag);
		JspTag tag = (JspTag)node[2];
		assertEquals("tag contents","=object",tag.getText());
	}
	
	/**
	 * Testcase submitted by Johan Naudts, demonstrating bug
	 * 717573, <b>NullPointerException when unclosed HTML tag 
	 * inside JSP tag</b>
	 * @throws ParserException
	 */
	public void testUnclosedTagInsideJsp() throws ParserException {
		createParser(
			"<%\n" +
			"public String getHref(String value) \n" +
			"{ \n" +
			"int indexs = value.indexOf(\"<A HREF=\");\n" +
			"int indexe = value.indexOf(\">\");\n" +
			"if (indexs != -1) {\n" +
			"return value.substring(indexs+9,indexe-2);\n" +
			"}\n" +
			"return value;\n" +
			"}\n" +
			"%>\n");
		Parser.setLineSeparator("\r\n");
		// Register the Jsp Scanner
		parser.addScanner(new JspScanner("-j"));
		parseAndAssertNodeCount(1);
	}
}
