package com.kizna.htmlTests.scannersTests;

import java.io.*;
import junit.framework.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

public class HTMLInputTagScannerTest extends TestCase 
{
	
	private String testHTML = new String("<INPUT type=\"text\" name=\"Google\">");
	private HTMLInputTagScanner scanner;
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLInputTagScannerTest.
	 * @param arg0
	 */
	public HTMLInputTagScannerTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLInputTagScannerTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLInputTagScannerTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		scanner = new HTMLInputTagScanner("-i");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];
		scanner = new HTMLInputTagScanner("-i");
		parser.addScanner(scanner);
		
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
	}
	
	public void testEvaluate() 
	{
		boolean retVal = scanner.evaluate("INPUT",null);
		assertEquals("Input evaluation - Upper Case",true,retVal);
		retVal = scanner.evaluate("input",null);
		assertEquals("Input evaluation - Lower Case",true,retVal);
		retVal = scanner.evaluate("abcd",null);
		assertEquals("Incorrect Case",false,retVal);
	}
	
	public void testScan() throws HTMLParserException 
	{
		assertEquals("Number of nodes expected",1,i);		
	 	assertTrue(node[0] instanceof HTMLInputTag);
	 	
		// check the input node
		HTMLInputTag inputTag = (HTMLInputTag) node[0];
		assertEquals("Input Scanner",scanner,inputTag.getThisScanner());
	}
}
