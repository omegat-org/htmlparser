package org.htmlparser.tests.scannersTests;

import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class DivScannerTest extends HTMLParserTestCase {

	public DivScannerTest(String name) {
		super(name);
	}

	public void testScan() throws HTMLParserException {
		createParser("<table><div align=\"left\">some text</div></table>");
		parser.registerScanners();
		parser.addScanner(new TableScanner(parser));
		parseAndAssertNodeCount(1);
		assertType("node should be table",TableTag.class,node[0]);
		TableTag tableTag = (TableTag)node[0];
		Div div = (Div)tableTag.searchFor(Div.class).toNodeArray()[0];
		assertEquals("div contents","some text",div.toPlainTextString());
	}
}
