package com.kizna.htmlTests.scannersTests;

import java.io.*;
import junit.framework.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;


public class HTMLSelectTagScannerTest extends TestCase 
{
	
	private String testHTML = new String(
									"<Select name=\"Remarks\">The intervention by the UN proved beneficial</Select>" +
									"<Select>The capture of the Somali warloard was elusive</Select>" +
									"<Select></Select>" +
									"<Select name=\"Remarks\">The death threats of the organization\n" +
									"refused to intimidate the soldiers</Select>" +
									"<Select name=\"Remarks\">The death threats of the LTTE\n" +
									"refused to intimidate the Tamilians\n</Select>"
									);
	private HTMLSelectTagScanner scanner;
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLInputTagScannerTest.
	 * @param arg0
	 */
	public HTMLSelectTagScannerTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLSelectTagScannerTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLSelectTagScannerTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		scanner = new HTMLSelectTagScanner("-i");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		node = new HTMLNode[20];
		scanner = new HTMLSelectTagScanner("-ta");
		parser.addScanner(scanner);
		
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
	}
	
	public void testEvaluate() 
	{
		boolean retVal = scanner.evaluate("Select",null);
		assertEquals("Select evaluation - Upper Case",true,retVal);
		retVal = scanner.evaluate("Select",null);
		assertEquals("Select evaluation - Lower Case",true,retVal);
		retVal = scanner.evaluate("abcd",null);
		assertEquals("Incorrect Case",false,retVal);
	}
	
	public void testScan() throws HTMLParserException 
	{
		assertEquals("Number of nodes expected",5,i);		
	 	assertTrue(node[0] instanceof HTMLSelectTag);
	 	assertTrue(node[1] instanceof HTMLSelectTag);
	 	assertTrue(node[2] instanceof HTMLSelectTag);
	 	assertTrue(node[3] instanceof HTMLSelectTag);
	 	assertTrue(node[4] instanceof HTMLSelectTag);
	 	
		// check the Select node
		for(int j=0;j<i;j++)
		{
			HTMLSelectTag SelectTag = (HTMLSelectTag) node[j];
			assertEquals("Select Scanner",scanner,SelectTag.getThisScanner());
		}
	}
}
