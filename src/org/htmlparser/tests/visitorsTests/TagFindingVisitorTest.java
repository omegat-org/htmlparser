package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.visitors.TagFindingVisitor;

public class TagFindingVisitorTest extends ParserTestCase {
	private String html = 
		"<HTML><HEAD><TITLE>This is the Title</TITLE></HEAD>" +
		"<BODY>Hello World, this is an excellent parser</BODY>" +
		"<UL><LI><LI></UL>" +
		"<A href=\"http://www.industriallogic.com\">Industrial Logic</a>" +
		"</HTML>";

	public TagFindingVisitorTest(String name) {
		super(name);
	}
	
	public void setUp() {
		createParser(html);
	}

	public void testTagFound() throws Exception {
		TagFindingVisitor visitor = new TagFindingVisitor(new String[] {"HEAD"});
		parser.visitAllNodesWith(visitor);
		assertEquals("HEAD found", 1, visitor.getTagCount(0));
	}

	public void testTagsFound() throws Exception {
		TagFindingVisitor visitor = new TagFindingVisitor(new String [] {"LI"});
		parser.visitAllNodesWith(visitor);
		assertEquals("LI tags found", 2, visitor.getTagCount(0));
	}
	
	public void testMultipleTags() throws Exception {
		TagFindingVisitor visitor = 
			new TagFindingVisitor(
				new String [] {
					"LI","BODY","UL","A"
				}
			);
		parser.visitAllNodesWith(visitor);
		assertEquals("LI tags found", 2, visitor.getTagCount(0));
		assertEquals("BODY tag found", 1, visitor.getTagCount(1));
		assertEquals("UL tag found", 1, visitor.getTagCount(2));
		assertEquals("A tag found", 1, visitor.getTagCount(3));
	}

	public void testEndTags() throws Exception {
		TagFindingVisitor visitor = 
			new TagFindingVisitor(
				new String [] {
					"LI","BODY","UL","A"
				},
				true
			);
		parser.visitAllNodesWith(visitor);
		assertEquals("LI tags found", 2, visitor.getTagCount(0));
		assertEquals("BODY tag found", 1, visitor.getTagCount(1));
		assertEquals("UL tag found", 1, visitor.getTagCount(2));
		assertEquals("A tag found", 1, visitor.getTagCount(3));
		assertEquals("BODY end tag found", 1, visitor.getEndTagCount(1));
	}

}
