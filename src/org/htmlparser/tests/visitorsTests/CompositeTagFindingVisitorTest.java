package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.HTMLParserTestCase;



public class CompositeTagFindingVisitorTest extends HTMLParserTestCase {

	public CompositeTagFindingVisitorTest(String name) {
		super(name);
	}

	public void testFindTagsWithinGivenTag() {
		createParser("<html></html>");
	}
}
