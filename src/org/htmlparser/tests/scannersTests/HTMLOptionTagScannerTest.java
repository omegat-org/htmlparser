// HTMLParser Library v1_3_20030202 - A java-based parser for HTML
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

import org.htmlparser.HTMLNode;
import org.htmlparser.scanners.HTMLOptionTagScanner;
import org.htmlparser.tags.HTMLOptionTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLOptionTagScannerTest extends HTMLParserTestCase 
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
	
	public HTMLOptionTagScannerTest(String name) 
	{
		super(name);
	}
	
	public void testScan() throws HTMLParserException 
	{
		scanner = new HTMLOptionTagScanner("-i");
		createParser(testHTML,"http://www.google.com/test/index.html");
		parser.addScanner(scanner);
		parseAndAssertNodeCount(9);	
		for(int j=0;j<i;j++)
		{
			assertTrue("Node " + j + " should be Option Tag",node[j] instanceof HTMLOptionTag);
			HTMLOptionTag OptionTag = (HTMLOptionTag) node[j];
			assertEquals("Option Scanner",scanner,OptionTag.getThisScanner());
		}
	}
}
