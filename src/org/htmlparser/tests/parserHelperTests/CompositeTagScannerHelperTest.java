/*
 * Created on Apr 13, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.htmlparser.tests.parserHelperTests;

import org.htmlparser.parserHelper.CompositeTagScannerHelper;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.ParserTestCase;

/**
 * @author Somik Raha
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompositeTagScannerHelperTest extends ParserTestCase {
	private CompositeTagScannerHelper helper;
	public CompositeTagScannerHelperTest(String name) {
		super(name);
	}

	protected void setUp() {
		helper = 
			new CompositeTagScannerHelper(null,null,null,null,null);
	}
	
	public void testIsXmlEndTagForRealXml() { 
		Tag tag = new Tag(
			new TagData(
				0,0,"something/",""
			)
		);
		assertTrue("should be an xml end tag",helper.isXmlEndTag(tag));
	}

	public void testIsXmlEndTagForFalseMatches() { 
		Tag tag = new Tag(
			new TagData(
				0,0,"a href=http://someurl.com/",""
			)
		); 
		assertFalse("should not be an xml end tag",helper.isXmlEndTag(tag)); 
	}
}
