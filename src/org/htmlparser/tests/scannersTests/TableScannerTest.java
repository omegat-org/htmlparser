// HTMLParser Library v1_3_20030525 - A java-based parser for HTML
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
import org.htmlparser.Parser;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class TableScannerTest extends ParserTestCase {
	
	public TableScannerTest(String name) {
		super(name);
	}

	private String createHtmlWithTable() {
		return 
		"<table width=\"100.0%\" align=\"Center\" cellpadding=\"5.0\" cellspacing=\"0.0\" border=\"0.0\">"+
		"	<tr>" +
		"		<td border=\"0.0\" valign=\"Top\" colspan=\"5\">" +
		"			<img src=\"file:/c:/data/dev/eclipse_workspace/ShoppingServerTests/resources/pictures/fishbowl.jpg\" width=\"446.0\" height=\"335.0\" />" +
		"		</td>" +
		"		<td border=\"0.0\" align=\"Justify\" valign=\"Top\" colspan=\"7\">" +
		"			<span>The best way to improve your refactoring skills is to practice cleaning up poorly-designed code. And we've got just the thing: code we custom-designed to reek of over 90% of the code smells identified in the refactoring literature. This poorly designed code functions correctly, which you can verify by running a full suite of tests against it. Your challenge is to identify the smells in this code, determining which refactoring(s) can help you clean up the smells and implement the refactorings to arrive at a well-designed new version of the code that continues to pass its unit tests. This exercise takes place using our popular class fishbowl. There is a lot to learn from this challenge, so we recommend that you spend as much time on it as possible.&#013;<br /></span>" +
		"		</td>" +
		"	</tr>" +
		"</table>";
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
	
	public void testErroneousTables() throws ParserException {
		createParser(
			"<HTML>\n"+ 
				"<table border>\n"+ 
					"<tr>\n"+ 
						"<td>Head1</td>\n"+ 
						"<td>Val1</td>\n"+ 
					"</tr>\n"+ 
					"<tr>\n"+ 
						"<td>Head2</td>\n"+ 
						"<td>Val2</td>\n"+ 
					"</tr>\n"+ 
					"<tr>\n"+ 
						"<td>\n"+ 
							"<table border>\n"+ 
								"<tr>\n"+ 
									"<td>table2 Head1</td>\n"+ 
									"<td>table2 Val1</td>\n"+ 
								"</tr>\n"+ 
							"</table>\n"+ 
						"</td>\n"+ 
					"</tr>\n"+ 
			"</BODY>\n"+ 
			"</HTML>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(4);
		assertType("second tag",TableTag.class,node[1]);
		TableTag table = (TableTag)node[1];
		assertEquals("rows",3,table.getRowCount());
		TableRow tr = table.getRow(2);
		assertEquals("columns",1,tr.getColumnCount());
		TableColumn td = tr.getColumns()[0];
		Node node = td.childAt(0);
		assertType("node",TableTag.class,node);
		TableTag table2 = (TableTag)node;
		assertEquals("second table row count",1,table2.getRowCount());
		tr = table2.getRow(0);
		assertEquals("second table col count",2,tr.getColumnCount());
	}

    /**
     * Test many unclosed tags (causes heavy recursion).
     * See feature request #729259 Increase maximum recursion depth.
     * Only perform this test if it's version 1.4 or higher.
     */
	public void testRecursionDepth () throws ParserException
    {
		Parser parser;
		String url = "http://htmlparser.sourceforge.net/test/badtable2.html";
		
		parser = new Parser (url);
        if (1.4 <= Parser.getVersionNumber ())
        {
            parser.registerScanners ();
            for (NodeIterator e = parser.elements();e.hasMoreNodes();)
                e.nextNode();
            // Note: The test will throw a StackOverFlowException,
            // so we are successful if we get to here...
            assertTrue ("Crash", true);
        }
    }

    /**
     * See bug #742254 Nested <TR> &<TD> tags should not be allowed
     */
    public void testUnClosed1 () throws ParserException
    {
        createParser ("<TABLE><TR><TR></TR></TABLE>");
        parser.registerScanners ();
        parseAndAssertNodeCount (1);
        String s = node[0].toHtml ();
        assertEquals ("Unclosed","<TABLE><TR></TR><TR></TR></TABLE>",s);
    }

    /**
     * See bug #742254 Nested <TR> &<TD> tags should not be allowed
     */
    public void testUnClosed2 () throws ParserException
    {
        createParser ("<TABLE><TR><TD><TD></TD></TR></TABLE>");
        parser.registerScanners ();
        parseAndAssertNodeCount (1);
        String s = node[0].toHtml ();
        assertEquals ("Unclosed","<TABLE><TR><TD></TD><TD></TD></TR></TABLE>",s);
    }
    
    /**
     * See bug #742254 Nested <TR> &<TD> tags should not be allowed
     */
    public void testUnClosed3 () throws ParserException
    {
        createParser ("<TABLE><TR><TD>blah blah</TD><TR><TD>blah blah</TD></TR></TABLE>");
        parser.registerScanners ();
        parseAndAssertNodeCount (1);
        String s = node[0].toHtml ();
        assertEquals ("Unclosed","<TABLE><TR><TD>blah blah</TD></TR><TR><TD>blah blah</TD></TR></TABLE>",s);
    }
}
