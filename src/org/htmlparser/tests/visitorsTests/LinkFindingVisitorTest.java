package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.visitors.LinkFindingVisitor;

public class LinkFindingVisitorTest extends HTMLParserTestCase {
	private String html = 
		"<HTML><HEAD><TITLE>This is the Title</TITLE></HEAD><BODY>Hello World, <A href=\"http://www.industriallogic.com\">Industrial Logic</a></BODY></HTML>";

	public LinkFindingVisitorTest(String name) {
		super(name);
	}

	public void testLinkFoundCorrectly() throws Exception {
		createParser(html);
		parser.registerScanners();
		LinkFindingVisitor visitor = new LinkFindingVisitor("Industrial Logic");
		parser.visitAllNodesWith(visitor);
		assertTrue("Found Industrial Logic Link",visitor.linkTextFound());
		assertEquals("Link Count",1,visitor.getCount());
	}
	
}
