package com.kizna.htmlTests.utilTests;

import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLTagParser;
import com.kizna.htmlTests.tagTests.HTMLTagTest;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLTagParserTest extends TestCase {
	private HTMLTagParser tagParser;
	/**
	 * Constructor for HTMLTagParserTest.
	 * @param arg0
	 */
	public HTMLTagParserTest(String arg0) {
		super(arg0);
	}
    public void testCorrectTag() {
    	HTMLTag tag = new HTMLTag(0,20,"font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"","<font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\">");
		tagParser.correctTag(tag);
		HTMLTagTest.assertStringEquals("Corrected Tag","font face=\"Arial,helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"",tag.getText());
    }	
	public void testInsertInvertedCommasCorrectly() {
		StringBuffer test = new StringBuffer("a b=c d e = f"); 
		StringBuffer result = tagParser.insertInvertedCommasCorrectly(test);
		HTMLTagTest.assertStringEquals("Expected Correction","a b=\"c d\" e=\"f\"",result.toString());
		
	}
	public void testPruneSpaces() {
		String test = "  fdfdf dfdf   ";
		assertEquals("Expected Pruned string","fdfdf dfdf",tagParser.pruneSpaces(test));
	}   
	protected void setUp() {
		tagParser = new HTMLTagParser(new DefaultHTMLParserFeedback());	
	} 
	public static TestSuite suite() {
		return new TestSuite(HTMLTagParserTest.class);
	}
}
