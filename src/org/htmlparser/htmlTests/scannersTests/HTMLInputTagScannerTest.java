// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package org.htmlparser.htmlTests.scannersTests;

import java.io.*;
import junit.framework.*;
import org.htmlparser.html.*;
import org.htmlparser.html.scanners.*;
import org.htmlparser.html.tags.*;
import org.htmlparser.html.util.*;

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
	
	public void testScan() throws HTMLParserException 
	{
		assertEquals("Number of nodes expected",1,i);		
	 	assertTrue(node[0] instanceof HTMLInputTag);
	 	
		// check the input node
		HTMLInputTag inputTag = (HTMLInputTag) node[0];
		assertEquals("Input Scanner",scanner,inputTag.getThisScanner());
	}
}
