package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.visitors.StringFindingVisitor;

public class StringFindingVisitorTest extends HTMLParserTestCase {
	private String html = 
		"<HTML><HEAD><TITLE>This is the Title</TITLE></HEAD><BODY>Hello World, this is an excellent parser</BODY></HTML>";

	public StringFindingVisitorTest(String name) {
		super(name);
	}

	public void testSimpleStringFind() throws Exception {
		createParser(html);
		StringFindingVisitor visitor = new StringFindingVisitor("Hello");
		parser.visitAllNodesWith(visitor);
		assertTrue("Hello found", visitor.stringWasFound());
	}
	
	public void testStringNotFound() throws Exception {
		createParser(html);
		StringFindingVisitor visitor = new StringFindingVisitor("industrial logic");
		parser.visitAllNodesWith(visitor);
		assertTrue("industrial logic should not have been found", !visitor.stringWasFound());
	}
	
	public void testStringInTagNotFound() throws Exception {
		createParser(html);
		StringFindingVisitor visitor = new StringFindingVisitor("HTML");
		parser.visitAllNodesWith(visitor);
		assertTrue("HTML should not have been found", !visitor.stringWasFound());
	}
}
