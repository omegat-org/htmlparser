package org.htmlparser.tests.scannersTests;

import org.htmlparser.tags.Div;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class XmlEndTagScanningTest extends HTMLParserTestCase{

	public XmlEndTagScanningTest(String name) {
		super(name);
	}

	public void testSingleTagParsing() throws HTMLParserException {
		createParser("<div style=\"page-break-before: always; \" />");
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertType("div tag",Div.class,node[0]);
		Div div = (Div)node[0];
		assertStringEquals(
			"style",
			"page-break-before: always; ",
			div.getAttribute("style")
		);
	}

}
