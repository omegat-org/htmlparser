package org.htmlparser.tests.scannersTests;

import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.HTMLParserTestCase;

public class TableScannerTest extends HTMLParserTestCase {
	
	public TableScannerTest(String name) {
		super(name);
	}

	private String createHtmlWithTable() {
		return 
		"<table width=\"100.0%\" align=\"Center\" cellpadding=\"5.0\" cellspacing=\"0.0\" border=\"0.0\">"+		"	<tr>" +		"		<td border=\"0.0\" valign=\"Top\" colspan=\"5\">" +		"			<img src=\"file:/c:/data/dev/eclipse_workspace/ShoppingServerTests/resources/pictures/fishbowl.jpg\" width=\"446.0\" height=\"335.0\" />" +		"		</td>" +		"		<td border=\"0.0\" align=\"Justify\" valign=\"Top\" colspan=\"7\">" +		"			<span>The best way to improve your refactoring skills is to practice cleaning up poorly-designed code. And we've got just the thing: code we custom-designed to reek of over 90% of the code smells identified in the refactoring literature. This poorly designed code functions correctly, which you can verify by running a full suite of tests against it. Your challenge is to identify the smells in this code, determining which refactoring(s) can help you clean up the smells and implement the refactorings to arrive at a well-designed new version of the code that continues to pass its unit tests. This exercise takes place using our popular class fishbowl. There is a lot to learn from this challenge, so we recommend that you spend as much time on it as possible.&#013;<br /></span>" +		"		</td>" +		"	</tr>" +		"</table>";
	}
	
	public void testScan() throws Exception {
		createParser(createHtmlWithTable());
		parser.addScanner(new TableScanner(parser));
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof TableTag);
		TableTag tableTag = (TableTag)node[0];
		assertEquals("rows",1,tableTag.getRowCount());
		TableRow row = tableTag.getRow(0);
		assertEquals("columns in row 1",2,row.getColumnCount());
		assertEquals("table width","100.0%",tableTag.getAttribute("WIDTH"));
	}
}
