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
			"<table>" +			"	<DIV>" +
			"		<SPAN>The Refactoring Challenge</SPAN>" +
			"		<SPAN>&#013;id: 6</SPAN>" +
			"	</DIV>" +			"</table>"
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
