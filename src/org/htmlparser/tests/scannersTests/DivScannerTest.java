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
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class DivScannerTest extends HTMLParserTestCase {

	public DivScannerTest(String name) {
		super(name);
	}

	public void testScan() throws HTMLParserException {
		createParser("<table><div align=\"left\">some text</div></table>");
		parser.registerScanners();
		parser.addScanner(new TableScanner(parser));
		parseAndAssertNodeCount(1);
		assertType("node should be table",TableTag.class,node[0]);
		TableTag tableTag = (TableTag)node[0];
		Div div = (Div)tableTag.searchFor(Div.class).toNodeArray()[0];
		assertEquals("div contents","some text",div.toPlainTextString());
	}
}