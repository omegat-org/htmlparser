package org.htmlparser.tests.visitorsTests;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class HtmlPageTest extends HTMLParserTestCase {

	private static final String SIMPLE_PAGE = 
		"<html>" +			"<head>" +				"<title>Welcome to the HTMLParser website</title>" +			"</head>" +			"<body>" +				"Welcome to HTMLParser" +
			"</body>" +		"</html>";

	private static final String PAGE_WITH_TABLE = 
		"<html>" +
			"<head>" +
				"<title>Welcome to the HTMLParser website</title>" +
			"</head>" +
			"<body>" +
				"Welcome to HTMLParser" +				"<table>" +					"<tr>" +						"<td>cell 1</td>" +						"<td>cell 2</td>" +					"</tr>" +				"</table>" +
			"</body>" +
		"</html>";
				public HtmlPageTest(String name) {
		super(name);
	}

	public void testCreateSimplePage() throws Exception {
		createParser(	
			SIMPLE_PAGE
		);
		HtmlPage page = new HtmlPage(parser);
		parser.visitAllNodesWith(page);
		assertStringEquals(
			"title",
			"Welcome to the HTMLParser website",
			page.getTitle()
		);
		NodeList bodyNodes = page.getBody();
		assertEquals("number of nodes in body",1,bodyNodes.size());
		HTMLNode node = bodyNodes.elementAt(0);
		assertTrue("expected stringNode but was "+node.getClass().getName(),
			node instanceof HTMLStringNode
		);
		assertStringEquals(
			"body contents",
			"Welcome to HTMLParser",
			page.getBody().asString()
		);
	}
	
	public void testCreatePageWithTables() throws Exception {
		createParser(	
			PAGE_WITH_TABLE
		);
		HtmlPage page = new HtmlPage(parser);
		parser.visitAllNodesWith(page);
		NodeList bodyNodes = page.getBody();
		assertEquals("number of nodes in body",2,bodyNodes.size());
		assertXMLEquals(
			"body html",
			"Welcome to HTMLParser" +
			"<table>" +
				"<tr>" +
				"	<td>cell 1</td>" +
				"	<td>cell 2</td>" +
				"</tr>" +
			"</table>",
			bodyNodes.asHtml()
		);			
		TableTag tables [] = page.getTables();
		assertEquals("number of tables",1,tables.length);
		assertEquals("number of rows",1,tables[0].getRowCount());
		TableRow row = tables[0].getRow(0);
		assertEquals("number of columns",2,row.getColumnCount());
		TableColumn [] col = row.getColumns();
		assertEquals("column contents","cell 1",col[0].toPlainTextString());
		assertEquals("column contents","cell 2",col[1].toPlainTextString());
	}
}
