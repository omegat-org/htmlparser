package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.StringReader;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLParserTestCase extends TestCase {
	protected HTMLParser parser;
	protected HTMLNode node [];
	protected int nodeCount;

	public HTMLParserTestCase(String name) {
		super(name);
	}

	protected void createParser(String inputHTML) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];
	}

	protected void createParser(String inputHTML,int numNodes) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[numNodes];
	}

	protected void createParser(String inputHTML, String url) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),url);
		parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];
	}

	public void assertStringEquals(String message,String s1,String s2) {
		for (int i=0;i<s1.length();i++) {
			if (s1.charAt(i)!=s2.charAt(i)) {
				assertTrue(message+
					" \nMismatch of strings at char posn "+i+
					" \nString 1 upto mismatch = "+s1.substring(0,i)+
					" \nString 2 upto mismatch = "+s2.substring(0,i)+
					" \nString 1 mismatch character = "+s1.charAt(i)+", code = "+(int)s1.charAt(i)+
					" \nString 2 mismatch character = "+s2.charAt(i)+", code = "+(int)s2.charAt(i)+
					" \nComplete String 1 = "+s1+
					" \nComplete String 2 = "+s2,false);
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
