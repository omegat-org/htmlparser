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

import org.htmlparser.scanners.OptionTagScanner;
import org.htmlparser.scanners.SelectTagScanner;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;


public class SelectTagScannerTest extends ParserTestCase 
{
	
	private String testHTML = new String(
									"<Select name=\"Remarks\">" +
										"<option value='option1'>option1</option>" +
									"</Select>" +
									"<Select name=\"something\">" +
										"<option value='option2'>option2</option>" +
									"</Select>" +
									"<Select></Select>" +
									"<Select name=\"Remarks\">The death threats of the organization\n" +
									"refused to intimidate the soldiers</Select>" +
									"<Select name=\"Remarks\">The death threats of the LTTE\n" +
									"refused to intimidate the Tamilians\n</Select>"
									);
	private SelectTagScanner scanner;

	public SelectTagScannerTest(String name) 
	{
		super(name);
	}
	
	public void testScan() throws ParserException 
	{
		
		scanner = new SelectTagScanner("-i");
		createParser(testHTML,"http://www.google.com/test/index.html");
		scanner = new SelectTagScanner("-ta");
		parser.addScanner(scanner);
		parser.addScanner(new OptionTagScanner(""));
		
		
		parseAndAssertNodeCount(5);
	 	assertTrue(node[0] instanceof SelectTag);
	 	assertTrue(node[1] instanceof SelectTag);
	 	assertTrue(node[2] instanceof SelectTag);
	 	assertTrue(node[3] instanceof SelectTag);
	 	assertTrue(node[4] instanceof SelectTag);
	 	
		// check the Select node
		for(int j=0;j<nodeCount;j++)
		{
			SelectTag SelectTag = (SelectTag) node[j];
			assertEquals("Select Scanner",scanner,SelectTag.getThisScanner());
		}
		
		SelectTag selectTag = (SelectTag)node[0];
		OptionTag [] optionTags = selectTag.getOptionTags();
		assertEquals("option tag array length",1,optionTags.length);
		assertEquals("option tag value","option1",optionTags[0].getOptionText());
	}
	
	/**
	 * Bug reproduction based on report by gumirov@ccfit.nsu.ru 
	 */
	public void testSelectTagWithComments() throws Exception {
		createParser(
			"<form>" +
			"<select> " +
			"<!-- 1 --><option selected>123 " +
			"<option>345 " +
			"</select> " +
			"</form>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(1);
			
	}
}
