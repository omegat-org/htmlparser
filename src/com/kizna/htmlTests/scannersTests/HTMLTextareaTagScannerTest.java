package com.kizna.htmlTests.scannersTests;

import java.io.*;
import junit.framework.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;


public class HTMLTextareaTagScannerTest extends TestCase 
{
	
	private String testHTML = new String(
									"<TEXTAREA name=\"Remarks\">The intervention by the UN proved beneficial</TEXTAREA>" +
									"<TEXTAREA>The capture of the Somali warloard was elusive</TEXTAREA>" +
									"<TEXTAREA></TEXTAREA>" +
									"<TEXTAREA name=\"Remarks\">The death threats of the organization\n" +
									"refused to intimidate the soldiers</TEXTAREA>" +
									"<TEXTAREA name=\"Remarks\">The death threats of the LTTE\n" +
									"refused to intimidate the Tamilians\n</TEXTAREA>"
									);
	private HTMLTextareaTagScanner scanner;
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLInputTagScannerTest.
	 * @param arg0
	 */
	public HTMLTextareaTagScannerTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLTextareaTagScannerTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLTextareaTagScannerTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		scanner = new HTMLTextareaTagScanner("-i");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		node = new HTMLNode[20];
		scanner = new HTMLTextareaTagScanner("-ta");
		parser.addScanner(scanner);
		
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
	}
	
	public void testEvaluate() 
	{
		boolean retVal = scanner.evaluate("TEXTAREA",null);
		assertEquals("Textarea evaluation - Upper Case",true,retVal);
		retVal = scanner.evaluate("textarea",null);
		assertEquals("Textarea evaluation - Lower Case",true,retVal);
		retVal = scanner.evaluate("abcd",null);
		assertEquals("Incorrect Case",false,retVal);
	}
	
	public void testScan() throws HTMLParserException 
	{
		assertEquals("Number of nodes expected",5,i);		
	 	assertTrue(node[0] instanceof HTMLTextareaTag);
	 	assertTrue(node[1] instanceof HTMLTextareaTag);
	 	assertTrue(node[2] instanceof HTMLTextareaTag);
	 	assertTrue(node[3] instanceof HTMLTextareaTag);
	 	assertTrue(node[4] instanceof HTMLTextareaTag);
	 	
		// check the Textarea node
		for(int j=0;j<i;j++)
		{
			HTMLTextareaTag TextareaTag = (HTMLTextareaTag) node[j];
			assertEquals("Textarea Scanner",scanner,TextareaTag.getThisScanner());
		}
	}
}
