// HTMLParser Library v1_4_20030810 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.StringNode;
import org.htmlparser.scanners.FormScanner;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.tests.scannersTests.FormScannerTest;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class FormTagTest extends ParserTestCase {

	public FormTagTest(String name) {
		super(name);
	}

	public void testSetFormLocation() throws ParserException{
		createParser(FormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
		FormTag formTag = (FormTag)node[0];

		formTag.setFormLocation("http://www.yahoo.com/yahoo/do_not_login.jsp");
 
		String expectedHTML = "<FORM ACTION=\"http://www.yahoo.com/yahoo/do_not_login.jsp\" NAME=\"login_form\" ONSUBMIT=\"return CheckData()\" METHOD=\""+FormTag.POST+"\">\r\n"+
		FormScannerTest.EXPECTED_FORM_HTML_REST_OF_FORM;
		assertStringEquals("Raw String",expectedHTML,formTag.toHtml());
	}
	
	public void testToPlainTextString() throws ParserException {
		createParser(FormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
		FormTag formTag = (FormTag)node[0];
		assertStringEquals("Form Tag string representation","&nbsp;User NamePassword&nbsp;&nbsp;Contents of TextArea",formTag.toPlainTextString());
	}
	
	public void testSearchFor() throws ParserException {
		createParser(FormScannerTest.FORM_HTML);

		parser.addScanner(new FormScanner(parser));
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
		FormTag formTag = (FormTag)node[0];
		NodeList nodeList = formTag.searchFor("USER NAME");
		assertEquals("Should have found nodes",1,nodeList.size());
		
		Node[] nodes = nodeList.toNodeArray();
		
		assertEquals("Number of nodes found",1,nodes.length);
		assertType("search result node",StringNode.class,nodes[0]);
		StringNode stringNode = (StringNode)nodes[0];
		assertEquals("Expected contents of string node","User Name",stringNode.getText());
	}

	public void testSearchForCaseSensitive() throws ParserException {
		createParser(FormScannerTest.FORM_HTML);

		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
		FormTag formTag = (FormTag)node[0];
		NodeList nodeList = formTag.searchFor("USER NAME",true);
		assertEquals("Should have not found nodes",0,nodeList.size());
	
		nodeList = formTag.searchFor("User Name",true);
		assertNotNull("Should have not found nodes",nodeList);
	}

	
	public void testSearchByName() throws ParserException {
		createParser(FormScannerTest.FORM_HTML);

		parser.addScanner(new FormScanner(parser));
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
		FormTag formTag = (FormTag)node[0];

		Tag tag= formTag.searchByName("passwd");
		assertNotNull("Should have found the password node",tag);
		assertType("tag found",InputTag.class,tag);
	}	
	
	/**
	 * Bug 713907 reported by Dhaval Udani, erroneous 
	 * attributes being reported.
	 */
	public void testFormRendering() throws Exception {
		String testHTML =
			"<HTML><HEAD><TITLE>Test Form Tag</TITLE></HEAD>" +
			"<BODY><FORM name=\"form0\"><INPUT type=\"text\" name=\"text0\"></FORM>" +
			"</BODY></HTML>";
		createParser(
			testHTML
		);
		parser.registerScanners();
		FormTag formTag = 
			(FormTag)(parser.extractAllNodesThatAre(
				FormTag.class
			)[0]);
		assertNotNull("Should have found a form tag",formTag);
		assertStringEquals("name","form0",formTag.getFormName());
		assertNull("action",formTag.getAttribute("ACTION"));
		assertXmlEquals(
			"html",
			"<FORM NAME=\"form0\">" +				"<INPUT TYPE=\"text\" NAME=\"text0\">" +			"</FORM>",
			formTag.toHtml()
		);
	}
}
