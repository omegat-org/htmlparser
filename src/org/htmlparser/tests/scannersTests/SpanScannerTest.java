// HTMLParser Library v1_4_20030525 - A java-based parser for HTML
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


import org.htmlparser.Node;
import org.htmlparser.scanners.SpanScanner;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tests.ParserTestCase;

public class SpanScannerTest extends ParserTestCase {

	private static final String HTML_WITH_SPAN = 
		"<TD BORDER=\"0.0\" VALIGN=\"Top\" COLSPAN=\"4\" WIDTH=\"33.33%\">" +		"	<DIV>" +		"		<SPAN>Flavor: small(90 to 120 minutes)<BR /></SPAN>" +		"		<SPAN>The short version of our Refactoring Challenge gives participants a general feel for the smells in the code base and includes time for participants to find and implement important refactorings.&#013;<BR /></SPAN>" +		"	</DIV>" +		"</TD>";
			public SpanScannerTest(String name) {
		super(name);
	}

	public void testScan() throws Exception {
		createParser(
			HTML_WITH_SPAN 
		);
		parser.addScanner(new TableScanner(parser));
		parser.addScanner(new SpanScanner());
		parseAndAssertNodeCount(1);
		assertType("node",TableColumn.class,node[0]);
		TableColumn col = (TableColumn)node[0];
		Node spans [] = col.searchFor(Span.class).toNodeArray();
		assertEquals("number of spans found",2,spans.length);
		assertStringEquals(
			"span 1",
			"Flavor: small(90 to 120 minutes)",
			spans[0].toPlainTextString()
		);
		assertStringEquals(
			"span 2",
			"The short version of our Refactoring Challenge gives participants a general feel for the smells in the code base and includes time for participants to find and implement important refactorings.&#013;",
			spans[1].toPlainTextString()
		);

	}
}
