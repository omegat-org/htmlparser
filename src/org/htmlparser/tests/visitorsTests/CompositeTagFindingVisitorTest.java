package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.ParserTestCase;



public class CompositeTagFindingVisitorTest extends ParserTestCase {

	public CompositeTagFindingVisitorTest(String name) {
		super(name);
	}

	public void testFindTagsWithinGivenTag() {
		createParser("<html></html>");
	}
}
