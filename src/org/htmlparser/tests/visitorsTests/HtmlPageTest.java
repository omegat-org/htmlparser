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

}
