package com.kizna.htmlTests.scannersTests;

import java.io.*;
import junit.framework.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

public class HTMLOptionTagScannerTest extends TestCase 
{
	
	private String testHTML = new String(
									"<OPTION value=\"Google Search\">Google</OPTION>" +
									"<OPTION value=\"AltaVista Search\">AltaVista" +
									"<OPTION value=\"Lycos Search\"></OPTION>" +
									"<OPTION>Yahoo!</OPTION>" + 
									"<OPTION>\nHotmail</OPTION>" +
									"<OPTION>Mailcity\n</OPTION>"+
									"<OPTION>\nIndiatimes\n</OPTION>"+
									"<OPTION>\nRediff\n</OPTION>\n" +
									"<OPTION>Cricinfo"
									);
	private HTMLOptionTagScanner scanner;
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLOptionTagScannerTest.
	 * @param arg0
	 */
	public HTMLOptionTagScannerTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLOptionTagScannerTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLOptionTagScannerTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		scanner = new HTMLOptionTagScanner("-i");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];
		scanner = new HTMLOptionTagScanner("-i");
		parser.addScanner(scanner);
		
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
	}
	
	public void testEvaluate() 
	{
		boolean retVal = scanner.evaluate("OPTION",null);
		assertEquals("Option evaluation - Upper Case",true,retVal);
		retVal = scanner.evaluate("option",null);
		assertEquals("Option evaluation - Lower Case",true,retVal);
		retVal = scanner.evaluate("abcd",null);
		assertEquals("Incorrect Case",false,retVal);
	}
	
	public void testScan() throws HTMLParserException 
	{
		assertEquals("There should be 9 nodes identified",9,i);	
		for(int j=0;j<i;j++)
		{
			assertTrue("Node " + j + " should be Option Tag",node[j] instanceof HTMLOptionTag);
			HTMLOptionTag OptionTag = (HTMLOptionTag) node[j];
			assertEquals("Option Scanner",scanner,OptionTag.getThisScanner());
		}
	}
}
