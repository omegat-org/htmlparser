package org.htmlparser.tests.tagTests;

import org.htmlparser.HTMLNode;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class ObjectSearchTest extends HTMLParserTestCase {

	public ObjectSearchTest(String name) {
		super(name);
	}

	public void testSimpleSearch() throws HTMLParserException {
		createParser(
			"<DIV>" +			"	<SPAN>The Refactoring Challenge</SPAN>" +			"	<SPAN>&#013;id: 6</SPAN>" +			"</DIV>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		Div div = (Div)node[0];
		HTMLNode [] spans = div.searchFor(Span.class).toNodeArray();
		assertSpanContent(spans);
	}

	private void assertSpanContent(HTMLNode[] spans) {
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
	
	public void testNestedSearch() throws HTMLParserException {
		createParser(
			"<table>" +			"	<DIV>" +
			"		<SPAN>The Refactoring Challenge</SPAN>" +
			"		<SPAN>&#013;id: 6</SPAN>" +
			"	</DIV>" +			"</table>"
		);
		parser.registerScanners();
		parser.addScanner(new TableScanner(parser));
		parseAndAssertNodeCount(1);
		TableTag tableTag = (TableTag)node[0];
		HTMLNode [] spans = 
			tableTag.searchFor(Span.class).toNodeArray();
		assertSpanContent(spans);
	}
}
