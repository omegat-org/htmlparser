// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.tests.scannersTests;

import java.io.*;
import junit.framework.*;
import org.htmlparser.*;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.*;

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
