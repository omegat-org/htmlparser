package org.htmlparser.tests.utilTests;

import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserUtils;

public class HTMLParserUtilsTest extends ParserTestCase {

	public HTMLParserUtilsTest(String name) {
		super(name);
	}

	public void testRemoveTrailingSpaces() {
		String text = "Hello World  ";
		assertStringEquals(
			"modified text",
			"Hello World",
			ParserUtils.removeTrailingBlanks(text)
		);
	}
}
