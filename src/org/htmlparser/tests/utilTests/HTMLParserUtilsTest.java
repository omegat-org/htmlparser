package org.htmlparser.tests.utilTests;

import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.ParserUtils;

public class HTMLParserUtilsTest extends HTMLParserTestCase {

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
