package org.htmlparser.tests.scannersTests;


import org.htmlparser.HTMLNode;
import org.htmlparser.scanners.SpanScanner;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tests.HTMLParserTestCase;

public class SpanScannerTest extends HTMLParserTestCase {

	private static final String HTML_WITH_SPAN = 
		"<TD BORDER=\"0.0\" VALIGN=\"Top\" COLSPAN=\"4\" WIDTH=\"33.33%\">" +		"	<DIV>" +		"		<SPAN>Flavor: small(90 to 120 minutes)<BR /></SPAN>" +		"		<SPAN>The short version of our Refactoring Challenge gives participants a general feel for the smells in the code base and includes time for participants to find and implement important refactorings.&#013;<BR /></SPAN>" +		"	</DIV>" +		"</TD>";
			public SpanScannerTest(String name) {
		super(name);
	}

	public void testScan() throws Exception {
		createParser(
			HTML_WITH_SPAN 
		);
		parser.addScanner(new TableScanner(parser));
		parser.addScanner(new SpanScanner());
		parseAndAssertNodeCount(1);
		assertType("node",TableColumn.class,node[0]);
		TableColumn col = (TableColumn)node[0];
		HTMLNode spans [] = col.searchFor(Span.class).toNodeArray();
		assertEquals("number of spans found",2,spans.length);
		assertStringEquals(
			"span 1",
			"Flavor: small(90 to 120 minutes)",
			spans[0].toPlainTextString()
		);
		assertStringEquals(
			"span 2",
			"The short version of our Refactoring Challenge gives participants a general feel for the smells in the code base and includes time for participants to find and implement important refactorings.&#013;",
			spans[1].toPlainTextString()
		);

	}
}
