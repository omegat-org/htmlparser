package org.htmlparser.tests.scannersTests;

import org.htmlparser.Node;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;
import java.util.Arrays;

public class CompositeTagScannerTest extends ParserTestCase {
	private CompositeTagScanner scanner;
	
	public CompositeTagScannerTest(String name) {
		super(name);
	}

	protected void setUp() {
		String [] arr = { "SOMETHING"
		};
		scanner = 
			new CompositeTagScanner(arr) {
				protected Tag createTag(TagData tagData, CompositeTagData compositeTagData) throws ParserException {
					return null;
				}
				public String[] getID() {
					return null;
				}

			};
	}

	public void testIsXmlEndTagForRealXml() { 
		Tag tag = new Tag(
			new TagData(
				0,0,"something/",""
			)
		);
		assertTrue("should be an xml end tag",scanner.isXmlEndTag(tag));
	}

	public void testIsXmlEndTagForFalseMatches() { 
		Tag tag = new Tag(
			new TagData(
				0,0,"a href=http://someurl.com/",""
			)
		);
		assertFalse("should not be an xml end tag",scanner.isXmlEndTag(tag));
	}
	
	public void testXmlTypeCompositeTags() throws ParserException {
		createParser(
			"<Custom>" +
				"<Another name=\"subtag\"/>" +
				"<Custom />" +
			"</Custom>" +
			"<Custom/>"
		);
		parser.addScanner(new CustomScanner());
		parser.addScanner(new AnotherScanner());
		parseAndAssertNodeCount(2);
		assertType("first node",CustomTag.class,node[0]);
		assertType("second node",CustomTag.class,node[1]);
		CustomTag customTag = (CustomTag)node[0];
		Node node = customTag.childAt(0);
		assertType("first child",AnotherTag.class,node);
		node = customTag.childAt(1);
		assertType("second child",CustomTag.class,node);
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
	
	private static class CustomScanner extends CompositeTagScanner {
		private static final String MATCH_NAME [] = { "CUSTOM" };
		public CustomScanner() { super("", MATCH_NAME); }
		public String[] getID() { return MATCH_NAME; }
		protected Tag createTag(TagData tagData, CompositeTagData compositeTagData) {
			return new CustomTag(tagData, compositeTagData);
		}
		protected boolean isBrokenTag() {
			return false;
		}
	}
	private static class AnotherScanner extends CompositeTagScanner {
		private static final String MATCH_NAME [] = { "ANOTHER" };
		public AnotherScanner() { super("", MATCH_NAME); }
		public String[] getID() { return MATCH_NAME; }
		protected Tag createTag(TagData tagData, CompositeTagData compositeTagData) {
			return new AnotherTag(tagData, compositeTagData);
		}
		protected boolean isBrokenTag() {
			return false;
		}
	}

	// Custom Tags
	private static class CustomTag extends CompositeTag {
		public TagData tagData;
		public CustomTag(TagData tagData, CompositeTagData compositeTagData) {
			super(tagData,compositeTagData);
			this.tagData = tagData;
		}
	}
	private static class AnotherTag extends CompositeTag {
		public AnotherTag(TagData tagData, CompositeTagData compositeTagData) {
			super(tagData,compositeTagData);
		}
	}	
}
