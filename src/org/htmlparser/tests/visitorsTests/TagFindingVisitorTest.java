package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.visitors.TagFindingVisitor;

public class TagFindingVisitorTest extends HTMLParserTestCase {
	private String html = 
		"<HTML><HEAD><TITLE>This is the Title</TITLE></HEAD><BODY>Hello World, this is an excellent parser</BODY><UL><LI><LI></UL></HTML>";

	public TagFindingVisitorTest(String name) {
		super(name);
	}

	public void testTagFound() throws Exception {
		createParser(html);
		TagFindingVisitor visitor = new TagFindingVisitor("HEAD");
		parser.visitAllNodesWith(visitor);
		assertEquals("HEAD found", 1, visitor.getCount());
	}

	public void testTagsFound() throws Exception {
		createParser(html);
		TagFindingVisitor visitor = new TagFindingVisitor("LI");
		parser.visitAllNodesWith(visitor);
		assertEquals("LI tags found", 2, visitor.getCount());
	}
}
