package org.htmlparser.tests.scannersTests;

import org.htmlparser.Node;
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
		public CustomTag(TagData tagData, CompositeTagData compositeTagData) {
			super(tagData,compositeTagData);
		}
	}
	private static class AnotherTag extends CompositeTag {
		public AnotherTag(TagData tagData, CompositeTagData compositeTagData) {
			super(tagData,compositeTagData);
		}
	}	
}
