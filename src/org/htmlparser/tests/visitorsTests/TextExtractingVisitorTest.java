package org.htmlparser.tests.visitorsTests;

import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.visitors.TextExtractingVisitor;

public class TextExtractingVisitorTest extends ParserTestCase {

	public TextExtractingVisitorTest(String name) {
		super(name);
	}

	public void testSimpleVisit() throws Exception {
		createParser("<HTML><HEAD><TITLE>Hello World</TITLE></HEAD></HTML>");
		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		assertStringEquals(
			"extracted text",
			"Hello World",
			visitor.getExtractedText()
		);
	}
	
	public void testSimpleVisitWithRegisteredScanners() throws Exception {
		createParser("<HTML><HEAD><TITLE>Hello World</TITLE></HEAD></HTML>");
		parser.registerScanners();
		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		assertStringEquals(
			"extracted text",
			"Hello World",
			visitor.getExtractedText()
		);
	}
	
	public void testVisitHtmlWithSpecialChars() throws Exception {
		createParser("<BODY>Hello World&nbsp;&nbsp;</BODY>");
		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		assertStringEquals(
			"extracted text",
			"Hello World  ",
			visitor.getExtractedText()
		);
	}
	
	public void testVisitHtmlWithPreTags() throws Exception {
		createParser(
			"Some text with &nbsp;<pre>this &nbsp; should be preserved</pre>"
		);
		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		assertStringEquals(
			"extracted text",
			"Some text with  this &nbsp; should be preserved",
			visitor.getExtractedText()
		);
	}
}
