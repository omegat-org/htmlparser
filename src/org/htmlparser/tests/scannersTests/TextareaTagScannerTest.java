// HTMLParser Library v1_3_20030511 - A java-based parser for HTML
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

import org.htmlparser.scanners.TextareaTagScanner;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class TextareaTagScannerTest extends ParserTestCase
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
	private TextareaTagScanner scanner;
	
	public TextareaTagScannerTest(String name) 
	{
		super(name);
	}
	
	public void testScan() throws ParserException 
	{
		scanner = new TextareaTagScanner("-i");
		createParser(testHTML);
		scanner = new TextareaTagScanner("-ta");
		parser.addScanner(scanner);
		parseAndAssertNodeCount(5);
	 	assertTrue(node[0] instanceof TextareaTag);
	 	assertTrue(node[1] instanceof TextareaTag);
	 	assertTrue(node[2] instanceof TextareaTag);
	 	assertTrue(node[3] instanceof TextareaTag);
	 	assertTrue(node[4] instanceof TextareaTag);
	 	
		// check the Textarea node
		for(int j=0;j<nodeCount;j++)
		{
			TextareaTag TextareaTag = (TextareaTag) node[j];
			assertEquals("Textarea Scanner",scanner,TextareaTag.getThisScanner());
		}
	}
}
