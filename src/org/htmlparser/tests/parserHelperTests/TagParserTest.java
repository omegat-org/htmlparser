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


package org.htmlparser.tests.parserHelperTests;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class TagParserTest extends ParserTestCase {

	public TagParserTest(String name) {
		super(name);
	}
	
	public void testTagWithQuotes() throws Exception {
		String testHtml = 
		"<img src=\"http://g-images.amazon.com/images/G/01/merchants/logos/marshall-fields-logo-20.gif\" width=87 height=20 border=0 alt=\"Marshall Field's\">";
		
		createParser(testHtml);
		parseAndAssertNodeCount(1);
		assertType("should be HTMLTag",Tag.class,node[0]);
		Tag tag = (Tag)node[0];
		assertStringEquals("alt","Marshall Field's",tag.getAttribute("ALT"));
		assertStringEquals(
			"html",
			"<IMG BORDER=\"0\" ALT=\"Marshall Field's\" WIDTH=\"87\" SRC=\"http://g-images.amazon.com/images/G/01/merchants/logos/marshall-fields-logo-20.gif\" HEIGHT=\"20\">",
			tag.toHtml()
		);
	}
	
	public void testEmptyTag() throws Exception {
		createParser("<custom/>");
		parseAndAssertNodeCount(1);
		assertType("should be Tag",Tag.class,node[0]);
		Tag tag = (Tag)node[0];
		assertStringEquals("tag name","CUSTOM",tag.getTagName());
		assertStringEquals(
			"html",
			"<CUSTOM/>",
			tag.toHtml()
		);
	}
	
	public void testTagWithCloseTagSymbolInAttribute() throws ParserException {
		createParser("<tag att=\"a>b\">");
		parseAndAssertNodeCount(1);
		assertType("should be Tag",Tag.class,node[0]);
		Tag tag = (Tag)node[0];
		assertStringEquals("attribute","a>b",tag.getAttribute("att"));
	}
	
	public void testTagWithOpenTagSymbolInAttribute() throws ParserException {
		createParser("<tag att=\"a<b\">");
		parseAndAssertNodeCount(1);
		assertType("should be Tag",Tag.class,node[0]);
		Tag tag = (Tag)node[0];
		assertStringEquals("attribute","a<b",tag.getAttribute("att"));
	}

	public void testTagWithSingleQuote() throws ParserException {
		createParser("<tag att=\'a<b\'>");
		parseAndAssertNodeCount(1);
		assertType("should be Tag",Tag.class,node[0]);
		Tag tag = (Tag)node[0];
		assertStringEquals("html","<TAG ATT=\"a<b\">",tag.toHtml());
		assertStringEquals("attribute","a<b",tag.getAttribute("att"));
	}	
}