// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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

import junit.framework.TestSuite;

import org.htmlparser.scanners.BodyScanner;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;


public class BodyScannerTest extends ParserTestCase {

    public BodyScannerTest(String name) {
        super(name);
    }

    public void testSimpleBody() throws ParserException {
        createParser("<html><head><title>Test 1</title></head><body>This is a body tag</body></html>");
        parser.registerScanners();
        BodyScanner bodyScanner = new BodyScanner("-b");
        parser.addScanner(bodyScanner);
        parseAndAssertNodeCount(6);
        assertTrue(node[4] instanceof BodyTag);
        // check the body node
        BodyTag bodyTag = (BodyTag) node[4];
        assertEquals("Body","This is a body tag",bodyTag.getBody());
        assertEquals("Body","<BODY>This is a body tag</BODY>",bodyTag.toHtml());
        assertEquals("Body Scanner",bodyScanner,bodyTag.getThisScanner());
    }
        
    public void testBodywithJsp() throws ParserException {
        createParser("<html><head><title>Test 1</title></head><body><%=BodyValue%></body></html>");
        parser.registerScanners();
        BodyScanner bodyScanner = new BodyScanner("-b");
        parser.addScanner(bodyScanner);
        parseAndAssertNodeCount(6);
        assertTrue(node[4] instanceof BodyTag);
        // check the body node
        BodyTag bodyTag = (BodyTag) node[4];
        assertStringEquals("Body","<BODY><%=BodyValue%></BODY>",bodyTag.toHtml());
        assertEquals("Body Scanner",bodyScanner,bodyTag.getThisScanner());
    }
    
    public void testBodyMixed() throws ParserException {
        createParser("<html><head><title>Test 1</title></head><body>before jsp<%=BodyValue%>after jsp</body></html>");
        parser.registerScanners();
        BodyScanner bodyScanner = new BodyScanner("-b");
        parser.addScanner(bodyScanner);
        parseAndAssertNodeCount(6);
        assertTrue(node[4] instanceof BodyTag);
        // check the body node
        BodyTag bodyTag = (BodyTag) node[4];
        assertEquals("Body","<BODY>before jsp<%=BodyValue%>after jsp</BODY>",bodyTag.toHtml());
        assertEquals("Body Scanner",bodyScanner,bodyTag.getThisScanner());
    }
    
    public void testBodyEnding() throws ParserException {
        createParser("<html><body>before jsp<%=BodyValue%>after jsp</html>");
        parser.registerScanners();
        BodyScanner bodyScanner = new BodyScanner("-b");
        parser.addScanner(bodyScanner);
        parseAndAssertNodeCount(3);
        assertTrue(node[1] instanceof BodyTag);
        // check the body node
        BodyTag bodyTag = (BodyTag) node[1];
        assertEquals("Body","<BODY>before jsp<%=BodyValue%>after jsp</BODY>",bodyTag.toHtml());
        assertEquals("Body Scanner",bodyScanner,bodyTag.getThisScanner());
    }
        
    public static TestSuite suite() 
    {
        return new TestSuite(BodyScannerTest.class);
    }
    
    public static void main(String[] args) 
    {
        new junit.awtui.TestRunner().start(new String[] {BodyScannerTest.class.getName()});
    }
    

}
