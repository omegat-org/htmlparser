package org.htmlparser.tests.scannersTests;

import org.htmlparser.Node;
import org.htmlparser.StringNode;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.EndTag;
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
				public Tag createTag(TagData tagData, CompositeTagData compositeTagData) throws ParserException {
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
	
	private CustomTag parseCustomTag(int expectedNodeCount) throws ParserException {
		parser.addScanner(new CustomScanner());
		parseAndAssertNodeCount(expectedNodeCount);
		assertType("node",CustomTag.class,node[0]);
		CustomTag customTag = (CustomTag)node[0];
		return customTag;
	}

	public void testEmptyCompositeTag() throws ParserException {
		createParser(
			"<Custom/>"
		);
		CustomTag customTag = parseCustomTag(1);
		int x = customTag.getChildCount();
		assertEquals("child count",0,customTag.getChildCount());
		assertTrue("custom tag should be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",8,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
		assertStringEquals("html","<CUSTOM/>",customTag.toHtml());
	}
	
	public void testEmptyCompositeTagAnotherStyle() throws ParserException {
		createParser(
			"<Custom></Custom>"
		);
		CustomTag customTag = parseCustomTag(1);
		int x = customTag.getChildCount();
		assertEquals("child count",0,customTag.getChildCount());
		assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
		assertEquals("html","<CUSTOM></CUSTOM>",customTag.toHtml());
	}

	public void testCompositeTagWithOneTextChild() throws ParserException {
		createParser(
			"<Custom>" +				"Hello" +			"</Custom>"
		);
		CustomTag customTag = parseCustomTag(1);
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

	public void testCompositeTagWithTagChild() throws ParserException {
		createParser(
			"<Custom>" +
				"<Hello>" +
			"</Custom>"
		);
		CustomTag customTag = parseCustomTag(1);
		int x = customTag.getChildCount();
		assertEquals("child count",1,customTag.getChildCount());
		assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("custom tag starting loc",0,customTag.elementBegin());
		assertEquals("custom tag ending loc",23,customTag.elementEnd());

		Node child = customTag.childAt(0);
		assertType("child",Tag.class,child);
		Tag tag = (Tag)child;
		assertStringEquals("child html","<HELLO>",child.toHtml());
	}

	public void testCompositeTagWithAnotherTagChild() throws ParserException {
		createParser(
			"<Custom>" +
				"<Another/>" +
			"</Custom>"
		);
		parser.addScanner(new AnotherScanner());
		CustomTag customTag = parseCustomTag(1);
		int x = customTag.getChildCount();
		assertEquals("child count",1,customTag.getChildCount());
		assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("custom tag starting loc",0,customTag.elementBegin());
		assertEquals("custom tag ending loc",26,customTag.elementEnd());

		Node child = customTag.childAt(0);
		assertType("child",AnotherTag.class,child);
		AnotherTag tag = (AnotherTag)child;
		assertEquals("another tag start pos",8,tag.elementBegin());
		assertEquals("another tag ending pos",17,tag.elementEnd());
		
		assertEquals("custom end tag start pos",18,customTag.getEndTag().elementBegin());
		assertStringEquals("child html","<ANOTHER/>",child.toHtml());
	}
		
	public void testParseTwoCompositeTags() throws ParserException {
		createParser(
			"<Custom>" +			"</Custom>" +
			"<Custom/>"
		);
		parser.addScanner(new CustomScanner());
		parseAndAssertNodeCount(2);
		assertType("tag 1",CustomTag.class,node[0]);
		assertType("tag 2",CustomTag.class,node[1]);
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
	
	public void testCompositeTagWithNestedTag() throws ParserException {
		createParser(
			"<Custom>" +
				"<Another>" +					"Hello" +				"</Another>" +
				"<Custom/>" +
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
		AnotherTag anotherTag = (AnotherTag)node;
		assertEquals("another tag children count",1,anotherTag.getChildCount());
		node = anotherTag.childAt(0);
		assertType("nested child",StringNode.class,node);
		StringNode text = (StringNode)node;
		assertEquals("text","Hello",text.toPlainTextString());
	}

	public void testCompositeTagWithTwoNestedTags() throws ParserException {
		createParser(
			"<Custom>" +
				"<Another>" +
					"Hello" +
				"</Another>" +				"<unknown>" +					"World" +				"</unknown>" +
				"<Custom/>" +
			"</Custom>" +
			"<Custom/>"
		);
		parser.addScanner(new CustomScanner());
		parser.addScanner(new AnotherScanner());
		parseAndAssertNodeCount(2);
		assertType("first node",CustomTag.class,node[0]);
		assertType("second node",CustomTag.class,node[1]);
		CustomTag customTag = (CustomTag)node[0];
		assertEquals("first custom tag children count",4,customTag.getChildCount());
		Node node = customTag.childAt(0);
		assertType("first child",AnotherTag.class,node);
		AnotherTag anotherTag = (AnotherTag)node;
		assertEquals("another tag children count",1,anotherTag.getChildCount());
		node = anotherTag.childAt(0);
		assertType("nested child",StringNode.class,node);
		StringNode text = (StringNode)node;
		assertEquals("text","Hello",text.toPlainTextString());
	}
	
	public void testErroneousCompositeTag() throws ParserException {
		createParser("<custom>");
		CustomTag customTag = parseCustomTag(1);
		int x = customTag.getChildCount();
		assertEquals("child count",0,customTag.getChildCount());
		assertFalse("custom tag should be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
		assertStringEquals("html","<CUSTOM></CUSTOM>",customTag.toHtml());		
	}

	public void testCompositeTagWithErroneousAnotherTag() throws ParserException {
		createParser(
			"<custom>" +				"<another>" +			"</custom>"
		);
		parser.addScanner(new AnotherScanner());
		CustomTag customTag = parseCustomTag(1);
		int x = customTag.getChildCount();
		assertEquals("child count",1,customTag.getChildCount());
		assertFalse("custom tag should be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
		assertStringEquals("html","<CUSTOM><ANOTHER></ANOTHER></CUSTOM>",customTag.toHtml());		
	}

	public void testCompositeTagWithDeadlock() throws ParserException {
		createParser(
			"<custom>" +
				"<another>something" +
			"</custom>"+
			"<custom>" +				"<another>else</another>" +			"</custom>"
		);
		parser.addScanner(new AnotherScanner());
		CustomTag customTag = parseCustomTag(2);
		int x = customTag.getChildCount();
		assertEquals("child count",1,customTag.getChildCount());
		assertFalse("custom tag should be xml end tag",customTag.isEmptyXmlTag());
		assertEquals("starting loc",0,customTag.getStartTag().elementBegin());
		assertEquals("ending loc",7,customTag.getStartTag().elementEnd());
		assertEquals("starting line position",0,customTag.tagData.getStartLine());
		assertEquals("ending line position",0,customTag.tagData.getEndLine());
		AnotherTag anotherTag = (AnotherTag)customTag.childAt(0);
		assertEquals("anotherTag child count",1,anotherTag.getChildCount());
		StringNode stringNode = (StringNode)anotherTag.childAt(0);
		assertStringEquals("anotherTag child text","something",stringNode.toPlainTextString());
		assertStringEquals(
			"first custom tag html",
			"<CUSTOM><ANOTHER>something</ANOTHER></CUSTOM>",			customTag.toHtml()
		);		
		customTag = (CustomTag)node[1];
		assertStringEquals(
			"second custom tag html",
			"<CUSTOM><ANOTHER>else</ANOTHER></CUSTOM>",
			customTag.toHtml()
		);
	}
	
	public void testCompositeTagWithSelfChildren() throws ParserException {
		createParser(
			"<custom>" +
			"<custom>something</custom>" +
			"</custom>"
		);
		parser.addScanner(new CustomScanner(false));
		parser.addScanner(new AnotherScanner());
		parseAndAssertNodeCount(3);
		
		CustomTag customTag = (CustomTag)node[0];
		int x = customTag.getChildCount();
		assertEquals("child count",0,customTag.getChildCount());
		assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
		
		assertStringEquals(
			"first custom tag html",
			"<CUSTOM></CUSTOM>",
			customTag.toHtml()
		);
		customTag = (CustomTag)node[1];
		assertStringEquals(
			"first custom tag html",
			"<CUSTOM>something</CUSTOM>",
			customTag.toHtml()
		);
		EndTag endTag = (EndTag)node[2];
		assertStringEquals(
			"first custom tag html",
			"</CUSTOM>",
			endTag.toHtml()
		);
	}
	
	public static class CustomScanner extends CompositeTagScanner {
		private static final String MATCH_NAME [] = { "CUSTOM" };
		public CustomScanner() { 
			this(true); 
		}
		
		public CustomScanner(boolean selfChildrenAllowed) { 
			super("", MATCH_NAME, selfChildrenAllowed); 
		}
		
		public String[] getID() { 
			return MATCH_NAME; 
		}
		
		public Tag createTag(TagData tagData, CompositeTagData compositeTagData) {
			return new CustomTag(tagData, compositeTagData);
		}
		
		protected boolean isBrokenTag() {
			return false;
		}
	}
	
	public static class AnotherScanner extends CompositeTagScanner {
		private static final String MATCH_NAME [] = { "ANOTHER" };
		public AnotherScanner() { 
			super("", MATCH_NAME,true); 
		}
		
		public String[] getID() { 
			return MATCH_NAME; 
		}
		
		public Tag createTag(TagData tagData, CompositeTagData compositeTagData) {
			return new AnotherTag(tagData, compositeTagData);
		}
		protected boolean isBrokenTag() {
			return false;
		}
		
		public boolean isTagToBeEndedFor(String tagName) {
			if (tagName.equals("CUSTOM")) 
				return true;
			else 
				return false;
		}

	}

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
