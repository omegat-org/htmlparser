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
import org.htmlparser.scanners.DivScanner;
import org.htmlparser.scanners.SpanScanner;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ObjectCollectionTest extends ParserTestCase {

	public ObjectCollectionTest(String name) {
		super(name);
	}

	private void assertSpanContent(Node[] spans) {
		assertEquals("number of span objects expected",2,spans.length);
		assertType("span",Span.class,spans[0]);
		assertType("span",Span.class,spans[1]);
		assertStringEquals(
			"span[0] text",
			"The Refactoring Challenge",
			spans[0].toPlainTextString()
		);
		assertStringEquals(
			"span[1] text",
			"&#013;id: 6",
			spans[1].toPlainTextString()
		);
	}
	
	public void testSimpleSearch() throws ParserException {
		createParser(
			"<SPAN>The Refactoring Challenge</SPAN>" +
			"<SPAN>&#013;id: 6</SPAN>" 
		);
		parser.registerScanners();
		parser.addScanner(new SpanScanner());
		assertSpanContent(parser.extractAllNodesThatAre(Span.class));
	}
	
	public void testOneLevelNesting() throws ParserException {
		createParser(
			"<DIV>" +
			"	<SPAN>The Refactoring Challenge</SPAN>" +
			"	<SPAN>&#013;id: 6</SPAN>" +
			"</DIV>"
		);
		parser.registerScanners();
		parser.addScanner(new DivScanner());
		parser.addScanner(new SpanScanner());
		parseAndAssertNodeCount(1);
		Div div = (Div)node[0];
		NodeList nodeList = new NodeList();
		div.collectInto(nodeList,Span.class);
		Node[] spans = nodeList.toNodeArray();
		assertSpanContent(spans);
	}

	public void testTwoLevelNesting() throws ParserException {
		createParser(
			"<table>" +
			"	<DIV>" +
			"		<SPAN>The Refactoring Challenge</SPAN>" +
			"		<SPAN>&#013;id: 6</SPAN>" +
			"	</DIV>" +
			"</table>"
		);
		parser.registerScanners();
		parser.addScanner(new DivScanner());
		parser.addScanner(new SpanScanner());
		parser.addScanner(new TableScanner(parser));
		parseAndAssertNodeCount(1);
		TableTag tableTag = (TableTag)node[0];
		NodeList nodeList = new NodeList();
		tableTag.collectInto(nodeList,Span.class);
		Node [] spans = nodeList.toNodeArray();
		assertSpanContent(spans);
	}
}
