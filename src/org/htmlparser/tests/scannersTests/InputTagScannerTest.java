// HTMLParser Library v1_4_20030907 - A java-based parser for HTML
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

import org.htmlparser.scanners.InputTagScanner;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class InputTagScannerTest extends ParserTestCase
{

    private String testHTML = new String("<INPUT type=\"text\" name=\"Google\">");
    private InputTagScanner scanner;

    public InputTagScannerTest(String name)
    {
        super(name);
    }

    public void testScan() throws ParserException
    {
        scanner = new InputTagScanner("-i");
        createParser(testHTML,"http://www.google.com/test/index.html");
        parser.addScanner(scanner);
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof InputTag);

        // check the input node
        InputTag inputTag = (InputTag) node[0];
        assertEquals("Input Scanner",scanner,inputTag.getThisScanner());
        assertEquals("Type","text",inputTag.getAttribute("TYPE"));
        assertEquals("Name","Google",inputTag.getAttribute("NAME"));
    }
}
