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

import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.HTMLParserTestCase;

public class TableScannerTest extends HTMLParserTestCase {
	
	public TableScannerTest(String name) {
		super(name);
	}

	private String createHtmlWithTable() {
		return 
		"<table width=\"100.0%\" align=\"Center\" cellpadding=\"5.0\" cellspacing=\"0.0\" border=\"0.0\">"+
	}
	
	public void testScan() throws Exception {
		createParser(createHtmlWithTable());
		parser.addScanner(new TableScanner(parser));
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof TableTag);
		TableTag tableTag = (TableTag)node[0];
		assertEquals("rows",1,tableTag.getRowCount());
		TableRow row = tableTag.getRow(0);
		assertEquals("columns in row 1",2,row.getColumnCount());
		assertEquals("table width","100.0%",tableTag.getAttribute("WIDTH"));
	}
}