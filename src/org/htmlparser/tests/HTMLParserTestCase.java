package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.TestCase;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

public class HTMLParserTestCase extends TestCase {
	protected HTMLParser parser;
	protected HTMLNode node [];
	protected int nodeCount;
	protected HTMLReader reader;
	
	public HTMLParserTestCase(String name) {
		super(name);
	}

	protected void parse(String response) throws HTMLParserException {
		createParser(response,10000);
		parser.registerScanners();
		parseNodes();
	}

	protected void createParser(String inputHTML) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new HTMLReader(new BufferedReader(sr),5000);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];
	}

	protected void createParser(String inputHTML,int numNodes) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new HTMLReader(new BufferedReader(sr),5000);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[numNodes];
	}

	protected void createParser(String inputHTML, String url) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new HTMLReader(new BufferedReader(sr),url);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];
	}

	protected void createParser(String inputHTML, String url,int numNodes) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new HTMLReader(new BufferedReader(sr),url);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[numNodes];
	}
	
	public void assertStringEquals(String message, String expected, 
									  String actual) {
		String mismatchInfo = "";

		if (expected.length() < actual.length()) {
			mismatchInfo = "\n\nACTUAL result has "+(actual.length()-expected.length())+" extra characters at the end. They are :";

			for (int i = expected.length(); i < actual.length(); i++) {
				mismatchInfo += ("\nPosition : " + i + " , Code = " + (int) actual.charAt(i));
			}
		} else if(expected.length() > actual.length()) {
			mismatchInfo = "\n\nEXPECTED result has "+(expected.length()-actual.length())+" extra characters at the end. They are :";

			for (int i = actual.length(); i < expected.length(); i++) {
				mismatchInfo += ("\nPosition : " + i + " , Code = " + (int) expected.charAt(i));
			}
        	
		}
		for (int i = 0; i < expected.length(); i++) {      	
			if (
					(expected.length() != actual.length() && 
						(
							i == (expected.length()-1 ) || 
							i == (actual.length()-1 )
						)
					) || 
					(actual.charAt(i) != expected.charAt(i))
				) {
				assertTrue(message +mismatchInfo + " \nMismatch of strings at char posn " + i + 
						   " \n\nString Expected upto mismatch = " + 
						   expected.substring(0, i) + 
						   " \n\nString Actual upto mismatch = " + 
						   actual.substring(0, i) + 
						   " \n\nString Expected MISMATCH CHARACTER = " + 
						   expected.charAt(i) + ", code = " + 
						   (int) expected.charAt(i) + 
						   " \n\nString Actual MISMATCH CHARACTER = " + 
						   actual.charAt(i) + ", code = " + 
						   (int) actual.charAt(i) + 
						   " \n\n**** COMPLETE STRING EXPECTED ****\n" + 
						   expected + 
						   " \n\n**** COMPLETE STRING ACTUAL***\n" + actual, 
						   false);
			}
        	
		}
	}   

	public void parseNodes() throws HTMLParserException{
		nodeCount = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[nodeCount++] = e.nextHTMLNode();
		}	
	}

	public void assertNodeCount(int nodeCountExpected) {
		assertEquals("Number of nodes parsed",nodeCountExpected,nodeCount);
	}

	public void parseAndAssertNodeCount(int nodeCountExpected) throws HTMLParserException {
		parseNodes();
		assertNodeCount(nodeCountExpected);
	}
}
