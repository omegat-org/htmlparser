package org.htmlparser.tests.scannersTests;

import junit.framework.TestSuite;

import org.htmlparser.scanners.LabelScanner;
import org.htmlparser.tags.LabelTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class LabelScannerTest extends HTMLParserTestCase {

	public LabelScannerTest(String name) {
		super(name);
	}
	public void testSimpleLabels() throws HTMLParserException {
		createParser("<label>This is a label tag</label>");
		LabelScanner labelScanner = new LabelScanner("-l");
		parser.addScanner(labelScanner);
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof LabelTag);
		// check the title node
		LabelTag labelTag = (LabelTag) node[0];
		assertEquals("Label","This is a label tag",labelTag.getLabel());
		assertStringEquals("Label","<LABEL>This is a label tag</LABEL>",labelTag.toHTML());
		assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
	}
	
	public void testLabelWithJspTag() throws HTMLParserException {
		createParser("<label><%=labelValue%></label>");
		parser.registerScanners();
		LabelScanner labelScanner = new LabelScanner("-l");
		parser.addScanner(labelScanner);
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof LabelTag);
		// check the title node
		LabelTag labelTag = (LabelTag) node[0];
		assertStringEquals("Label","<LABEL><%=labelValue%></LABEL>",labelTag.toHTML());
		assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
	}
	
	public void testLabelWithOtherTags() throws HTMLParserException {
		createParser("<label><span>Span within label</span></label>");
		parser.registerScanners();
		LabelScanner labelScanner = new LabelScanner("-l");
		parser.addScanner(labelScanner);
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof LabelTag);
		// check the title node
		LabelTag labelTag = (LabelTag) node[0];
		assertEquals("Label value","Span within label",labelTag.getLabel());
		assertStringEquals("Label","<LABEL><SPAN>Span within label</SPAN></LABEL>",labelTag.toHTML());
		assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
	}
	
	public static TestSuite suite() {
		return new TestSuite(LabelScannerTest.class);
	}
}
