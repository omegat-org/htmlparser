package org.htmlparser.tests;

import junit.framework.TestSuite;


public class AssertXmlEqualsTest extends ParserTestCase {

	public AssertXmlEqualsTest(String name) {
		super(name);
	}
	
	public void testNestedTagWithText() throws Exception {
		assertXmlEquals("nested with text","<hello>   <hi>My name is Nothing</hi></hello>","<hello><hi>My name is Nothing</hi>  </hello>");
	}
	
	public void testThreeTagsDifferent() throws Exception {
		assertXmlEquals("two tags different","<someTag></someTag><someOtherTag>","<someTag/><someOtherTag>");
	}
	
	public void testOneTag() throws Exception {
		assertXmlEquals("one tag","<someTag>","<someTag>");
	}

	public void testTwoTags() throws Exception {
		assertXmlEquals("two tags","<someTag></someTag>","<someTag></someTag>");
	}

	public void testTwoTagsDifferent() throws Exception {
		assertXmlEquals("two tags different","<someTag></someTag>","<someTag/>");
	}
	
	public void testTwoTagsDifferent2() throws Exception {
		assertXmlEquals("two tags different","<someTag/>","<someTag></someTag>");
	}
	
	public void testTwoTagsWithSameAttributes() throws Exception {
		assertXmlEquals("attributes","<tag name=\"John\" age=\"22\" sex=\"M\"/>","<tag sex=\"M\" name=\"John\" age=\"22\"/>");
	}
	
	public void testTagWithText() throws Exception {
		assertXmlEquals("text","<hello>   My name is Nothing</hello>","<hello>My name is Nothing  </hello>");
	}
	
	public void testStringWithLineBreaks() throws Exception {
		assertXmlEquals("string with line breaks","testing & refactoring","testing &\nrefactoring");
	}
	
	public static TestSuite suite() {
	    TestSuite suite = new TestSuite("XML Tests");
        suite.addTestSuite(AssertXmlEqualsTest.class);
        return (suite);
	}
}
