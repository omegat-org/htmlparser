// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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

package org.htmlparser.tests.parserHelperTests;

import org.htmlparser.parserHelper.CompositeTagScannerHelper;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

/**
 * @author Somik Raha
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompositeTagScannerHelperTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.parserHelperTests.CompositeTagScannerHelperTest", "CompositeTagScannerHelperTest");
    }

    private CompositeTagScannerHelper helper;
    public CompositeTagScannerHelperTest(String name) {
        super(name);
    }

    protected void setUp() {
    }

    public void testIsXmlEndTagForRealXml () throws ParserException
    {
        String html = "<something/>";
        createParser (html);
        parseAndAssertNodeCount (1);
        assertTrue("should be a tag", node[0] instanceof Tag);
        assertTrue("should be an xml end tag", ((Tag)node[0]).isEmptyXmlTag ());
    }

    public void testIsXmlEndTagForFalseMatches () throws ParserException
    {
        String html = "<a href=http://someurl.com/>";
        createParser (html);
        parseAndAssertNodeCount (1);
        assertTrue("should be a tag", node[0] instanceof Tag);
        assertTrue("should not be an xml end tag", !((Tag)node[0]).isEmptyXmlTag ());
    }
}
