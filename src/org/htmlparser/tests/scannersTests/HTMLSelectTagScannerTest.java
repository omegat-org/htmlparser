// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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

import org.htmlparser.scanners.HTMLSelectTagScanner;
import org.htmlparser.tags.HTMLSelectTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;


public class HTMLSelectTagScannerTest extends HTMLParserTestCase 
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

	public HTMLSelectTagScannerTest(String name) 
	{
		super(name);
	}
	
	public void testScan() throws HTMLParserException 
	{
		
		scanner = new HTMLSelectTagScanner("-i");
		createParser(testHTML,"http://www.google.com/test/index.html");
		scanner = new HTMLSelectTagScanner("-ta");
		parser.addScanner(scanner);
	
		parseAndAssertNodeCount(5);
	 	assertTrue(node[0] instanceof HTMLSelectTag);
	 	assertTrue(node[1] instanceof HTMLSelectTag);
	 	assertTrue(node[2] instanceof HTMLSelectTag);
	 	assertTrue(node[3] instanceof HTMLSelectTag);
	 	assertTrue(node[4] instanceof HTMLSelectTag);
	 	
		// check the Select node
		for(int j=0;j<nodeCount;j++)
		{
			HTMLSelectTag SelectTag = (HTMLSelectTag) node[j];
			assertEquals("Select Scanner",scanner,SelectTag.getThisScanner());
		}
	}
}
