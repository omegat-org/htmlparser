package org.htmlparser.tests.visitorsTests;

import org.htmlparser.Parser;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.visitors.UrlModifyingVisitor;

public class UrlModifyingVisitorTest extends ParserTestCase {
	private static final String HTML_WITH_LINK = 
	"<HTML><BODY>" +
		"<A HREF=\"mylink.html\"><IMG SRC=\"mypic.jpg\">" +
		"</A><IMG SRC=\"mysecondimage.gif\">" +
	"</BODY></HTML>";
	
	private static final String MODIFIED_HTML = 
	"<HTML><BODY>" +
		"<A HREF=\"localhost://mylink.html\">" +
		"<IMG SRC=\"localhost://mypic.jpg\"></A>" +
		"<IMG SRC=\"localhost://mysecondimage.gif\">" +
	"</BODY></HTML>";
	
	public UrlModifyingVisitorTest(String name) {
		super(name);
	}
	
	public void testUrlModificationWithVisitor() throws Exception {
		Parser parser = Parser.createParser(HTML_WITH_LINK);
		UrlModifyingVisitor visitor = 
			new UrlModifyingVisitor(parser, "localhost://");		
		parser.visitAllNodesWith(visitor);
		assertStringEquals("Expected HTML",
			MODIFIED_HTML,
			visitor.getModifiedResult());
	}
}
