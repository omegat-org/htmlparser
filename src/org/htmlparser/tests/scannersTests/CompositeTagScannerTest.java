package org.htmlparser.tests.scannersTests;

import junit.framework.TestCase;

import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;

public class CompositeTagScannerTest extends TestCase {
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
}
