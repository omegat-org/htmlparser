/*
 * Created on Apr 12, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.htmlparser.tests;

import java.util.Arrays;

import junit.framework.TestSuite;

import org.htmlparser.tests.scannersTests.CompositeTagScannerTest.CustomScanner;
import org.htmlparser.tests.scannersTests.CompositeTagScannerTest.CustomTag;
import org.htmlparser.util.ParserException;
/**
 * @author Somik Raha
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LineNumberAssignedByNodeReaderTest extends ParserTestCase {

	public LineNumberAssignedByNodeReaderTest(String name) {
		super(name);
	}
	
	/**
	 * Test to ensure that the <code>Tag</code> being created by the
	 * <code>CompositeTagScanner</code> has the correct startLine and endLine
	 * information in the <code>TagData</code> it is constructed with. 
	 * @throws ParserException if there is a problem parsing the test data
	 */ 
	public void testLineNumbers() throws ParserException {
		testLineNumber("<Custom/>", 1, 0, 1, 1);
		testLineNumber("<Custom />", 1, 0, 1, 1);
		testLineNumber("<Custom></Custom>", 1, 0, 1, 1);
		testLineNumber("<Custom>Content</Custom>", 1, 0, 1, 1);
		testLineNumber(
			"<Custom>\n" +
			"	Content\n" +
			"</Custom>",
			1, 0, 1, 3
		);
		testLineNumber(
			"Foo\n" +
			"<Custom>\n" +
			"	Content\n" +
			"</Custom>",
			2, 1, 2, 4
		);
		testLineNumber(
			"Foo\n" +
			"<Custom>\n" +
			"	<Custom>SubContent</Custom>\n" +
			"</Custom>",
			2, 1, 2, 4
		);
		char[] oneHundredNewLines = new char[100];
		Arrays.fill(oneHundredNewLines, '\n');
		testLineNumber(
			"Foo\n" +
			new String(oneHundredNewLines) +
			"<Custom>\n" +
			"	<Custom>SubContent</Custom>\n" +
			"</Custom>",
			2, 1, 102, 104
		);
	}
	
	/**
	 * Helper method to ensure that the <code>Tag</code> being created by the
	 * <code>CompositeTagScanner</code> has the correct startLine and endLine
	 * information in the <code>TagData</code> it is constructed with.
	 * @param xml String containing HTML or XML to parse, containing a Custom tag
	 * @param numNodes int number of expected nodes returned by parser
	 * @param useNode int index of the node to test (should be of type CustomTag) 
	 * @param startLine int the expected start line number of the tag
	 * @param endLine int the expected end line number of the tag
	 * @throws ParserException if there is an exception during parsing
	 */ 
	private void testLineNumber(String xml, int numNodes, int useNode, int startLine, int endLine) throws ParserException {
		createParser(xml);
		parser.addScanner(new CustomScanner());
		parseAndAssertNodeCount(numNodes);
		assertType("custom node",CustomTag.class,node[useNode]);
		CustomTag tag = (CustomTag)node[useNode];
		assertEquals("start line", tag.tagData.getStartLine(), startLine);
		assertEquals("end line", tag.tagData.getEndLine(), endLine);
		
	}

	public static TestSuite suite() {
		return new TestSuite(LineNumberAssignedByNodeReaderTest.class);
	}
}
