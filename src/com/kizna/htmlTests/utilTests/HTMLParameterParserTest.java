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
            assertEquals("Type","checkbox",table.get("TYPE"));                
            assertEquals("Name","Authorize",table.get("NAME"));
            assertEquals("Value","Y",table.get("VALUE"));
            assertEquals("Checked","",table.get("CHECKED"));
        }
        /**
         * This is a simulation of a bug reported by Dhaval Udani - wherein 
         * a space before the end of the tag causes a problem - there is a key
         * in the table with just a space in it and an empty value
         */
        public void testIncorrectSpaceKeyBug() {
            HTMLParameterParser parser = new HTMLParameterParser();
            HTMLTag tag = new HTMLTag(0,0,"TEXTAREA name=\"Remarks\" ","");
            Hashtable table = parser.parseParameters(tag);
			// There should only be two keys..
			assertEquals("There should only be two keys",2,table.size());
			// The first key is name
			String key1 = "NAME";
			String value1 = (String)table.get(key1);
			assertEquals("Expected value 1", "Remarks",value1);
			String key2 = HTMLTag.TAGNAME;
			assertEquals("Expected Value 2","TEXTAREA",table.get(key2));
        }
        public void testNullTag(){
            HTMLParameterParser parser = new HTMLParameterParser();
            HTMLTag tag = new HTMLTag(0,0,"INPUT type=","");
            Hashtable table = parser.parseParameters(tag);
            assertEquals("Name of Tag","INPUT",table.get(HTMLTag.TAGNAME));
            assertEquals("Type","",table.get("TYPE"));                

        }
        
	public static TestSuite suite() {
            //TestSuite t = new TestSuite();
            //t.addTest(new HTMLParameterParserTest("testValueMissing"));
            //return t;
            return new TestSuite(HTMLParameterParserTest.class);
	}
}
