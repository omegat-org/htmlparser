package org.htmlparser.tests.scannersTests;

import org.htmlparser.Node;
import org.htmlparser.scanners.TitleScanner;
import org.htmlparser.scanners.HtmlScanner;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;

public class HtmlTest extends ParserTestCase {

	public HtmlTest(String name) {
		super(name);
	}

	public void testScan() throws Exception {
		createParser(
			"<html>" +			"	<head>" +			"		<title>Some Title</title>" +			"	</head>" +			"	<body>" +			"		Some data" +			"	</body>" +			"</html>");
		parser.addScanner(new TitleScanner(""));
		parser.addScanner(new HtmlScanner());
		parseAndAssertNodeCount(1);
		assertType("html tag",Html.class,node[0]);
		Html html = (Html)node[0];
		NodeList nodeList = new NodeList();
		html.collectInto(nodeList, TitleTag.class);
		assertEquals("nodelist size",1,nodeList.size());
		Node node = nodeList.elementAt(0);
		assertType("expected title tag",TitleTag.class,node);
		TitleTag titleTag = (TitleTag)node;
		assertStringEquals("title","Some Title",titleTag.getTitle());
	}
}
