package org.htmlparser.tests.scannersTests;

import org.htmlparser.Node;
import org.htmlparser.StringNode;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class CompositeTagScannerTest extends ParserTestCase {
	private CompositeTagScanner scanner;
	
	public CompositeTagScannerTest(String name) {
		super(name);
	}

	protected void setUp() {
		String [] arr = { 
			"SOMETHING"
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
	
	public void testEmptyCompositeTag() throws ParserException {
		createParser(
			"<Custom/>"
		);
		parser.addScanner(new CustomScanner());
		parseAndAssertNodeCount(1);
		assertType("node",CustomTag.class,node[0]);
		CustomTag customTag = (CustomTag)node[0];
		int x = customTag.getChildCount();
		assertEquals("child count",0,customTag.getChildCount());
		assertTrue("custom tag should be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",8,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
	}
	
	public void testEmptyCompositeTagAnotherStyle() throws ParserException {
		createParser(
			"<Custom></Custom>"
		);
		parser.addScanner(new CustomScanner());
		parseAndAssertNodeCount(1);
		assertType("node",CustomTag.class,node[0]);
		CustomTag customTag = (CustomTag)node[0];
		int x = customTag.getChildCount();
		assertEquals("child count",0,customTag.getChildCount());
		assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
	}

	public void testCompositeTagWithOneTextChild() throws ParserException {
		createParser(
			"<Custom>" +				"Hello" +			"</Custom>"
		);
		parser.addScanner(new CustomScanner());
		parseAndAssertNodeCount(1);
		assertType("node",CustomTag.class,node[0]);
		CustomTag customTag = (CustomTag)node[0];
		int x = customTag.getChildCount();
		assertEquals("child count",1,customTag.getChildCount());
		assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
		Node child = customTag.childAt(0);
		assertType("child",StringNode.class,child);
		StringNode text = (StringNode)child;
		assertStringEquals("child text","Hello",child.toPlainTextString());
	}

	public void _testXmlTypeCompositeTags() throws ParserException {
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
	
	public static class CustomScanner extends CompositeTagScanner {
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
	
	public static class AnotherScanner extends CompositeTagScanner {
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
	public static class CustomTag extends CompositeTag {
		public TagData tagData;
		public CustomTag(TagData tagData, CompositeTagData compositeTagData) {
			super(tagData,compositeTagData);
			this.tagData = tagData;
		}
	}
	
	public static class AnotherTag extends CompositeTag {
		public AnotherTag(TagData tagData, CompositeTagData compositeTagData) {
			super(tagData,compositeTagData);
		}
	}	
}
