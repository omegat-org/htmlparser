package org.htmlparser.tests.scannersTests;

import org.htmlparser.HTMLNode;
import org.htmlparser.scanners.HTMLTitleScanner;
import org.htmlparser.scanners.HtmlScanner;
import org.htmlparser.tags.HTMLTitleTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.NodeList;

public class HtmlTest extends HTMLParserTestCase {

	public HtmlTest(String name) {
		super(name);
	}

	public void testScan() throws Exception {
		createParser(
			"<html>" +			"	<head>" +			"		<title>Some Title</title>" +			"	</head>" +			"	<body>" +			"		Some data" +			"	</body>" +			"</html>");
		parser.addScanner(new HTMLTitleScanner(""));
		parser.addScanner(new HtmlScanner());
		parseAndAssertNodeCount(1);
		assertType("html tag",Html.class,node[0]);
		Html html = (Html)node[0];
		NodeList nodeList = new NodeList();
		html.collectInto(nodeList, HTMLTitleTag.class);
		assertEquals("nodelist size",1,nodeList.size());
		HTMLNode node = nodeList.elementAt(0);
		assertType("expected title tag",HTMLTitleTag.class,node);
		HTMLTitleTag titleTag = (HTMLTitleTag)node;
		assertStringEquals("title","Some Title",titleTag.getTitle());
	}
}
