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
		assertEquals("ValueB","",table.get("B"));
                
	}
        
        public void testTwoParams(){
		HTMLParameterParser parser = new HTMLParameterParser();
		HTMLTag tag = new HTMLTag(0,0,"PARAM NAME=\"Param1\" VALUE=\"Somik\">\n","");
		Hashtable table = parser.parseParameters(tag);
		assertEquals("Param1","Param1",table.get("NAME"));
                assertEquals("Somik","Somik",table.get("VALUE"));                
        }

        public void testPlainParams(){
			HTMLParameterParser parser = new HTMLParameterParser();
			HTMLTag tag = new HTMLTag(0,0,"PARAM NAME=Param1 VALUE=Somik","");
			Hashtable table = parser.parseParameters(tag);
			assertEquals("Param1","Param1",table.get("NAME"));
            assertEquals("Somik","Somik",table.get("VALUE"));                
        }
        
        public void testValueMissing() {
			HTMLParameterParser parser = new HTMLParameterParser();
			HTMLTag tag = new HTMLTag(0,0,"INPUT type=\"checkbox\" name=\"Authorize\" value=\"Y\" checked","");
			Hashtable table = parser.parseParameters(tag);
			assertEquals("Name of Tag","INPUT",table.get(HTMLTag.TAGNAME));
            assertEquals("Type","checkbox",table.get("type"));                
            assertEquals("Name","Authorize",table.get("name"));
            assertEquals("Value","Y",table.get("value"));
			assertEquals("Checked","",table.get("checked"));
        }
	public static TestSuite suite() {
		return new TestSuite(HTMLParameterParserTest.class);
	}
}
