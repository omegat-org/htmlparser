// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import org.htmlparser.scanners.HTMLTextareaTagScanner;
import org.htmlparser.tags.HTMLTextareaTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLTextareaTagScannerTest extends HTMLParserTestCase
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
	
	public HTMLTextareaTagScannerTest(String name) 
	{
		super(name);
	}
	
	public void testScan() throws HTMLParserException 
	{
		scanner = new HTMLTextareaTagScanner("-i");
		createParser(testHTML);
		scanner = new HTMLTextareaTagScanner("-ta");
		parser.addScanner(scanner);
		parseAndAssertNodeCount(5);
	 	assertTrue(node[0] instanceof HTMLTextareaTag);
	 	assertTrue(node[1] instanceof HTMLTextareaTag);
	 	assertTrue(node[2] instanceof HTMLTextareaTag);
	 	assertTrue(node[3] instanceof HTMLTextareaTag);
	 	assertTrue(node[4] instanceof HTMLTextareaTag);
	 	
		// check the Textarea node
		for(int j=0;j<nodeCount;j++)
		{
			HTMLTextareaTag TextareaTag = (HTMLTextareaTag) node[j];
			assertEquals("Textarea Scanner",scanner,TextareaTag.getThisScanner());
		}
	}
}
