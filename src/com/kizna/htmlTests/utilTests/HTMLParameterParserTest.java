package com.kizna.htmlTests.utilTests;

import java.util.Hashtable;

import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.HTMLParameterParser;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLParameterParserTest extends TestCase {

	/**
	 * Constructor for HTMLParameterParserTest.
	 * @param arg0
	 */
	public HTMLParameterParserTest(String arg0) {
		super(arg0);
	}
	public void testParseParameters() {
		HTMLParameterParser parser = new HTMLParameterParser();
		HTMLTag tag = new HTMLTag(0,0,"a b = \"c\"","");
		Hashtable table = parser.parseParameters(tag);
		assertEquals("Value","c",table.get("B"));
	}
	public void testParseTokenValues() {
		HTMLParameterParser parser = new HTMLParameterParser();
		HTMLTag tag = new HTMLTag(0,0,"a b = \"'\"","");
		Hashtable table = parser.parseParameters(tag);
		assertEquals("Value","'",table.get("B"));
	}
        
	public void testParseEmptyValues() {
		HTMLParameterParser parser = new HTMLParameterParser();
		HTMLTag tag = new HTMLTag(0,0,"a b = \"\"","");
		Hashtable table = parser.parseParameters(tag);
		assertEquals("Value","",table.get("B"));		
	}

	public void testParseMissingEqual() {
		HTMLParameterParser parser = new HTMLParameterParser();
		HTMLTag tag = new HTMLTag(0,0,"a b\"c\"","");
		Hashtable table = parser.parseParameters(tag);
		assertEquals("Value","",table.get("B"));
                assertEquals("Value","",table.get("C"));
	}
        

        
	public static TestSuite suite() {
		return new TestSuite(HTMLParameterParserTest.class);
	}
}
