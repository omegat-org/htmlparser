// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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

import org.htmlparser.scanners.BodyScanner;
import org.htmlparser.scanners.HeadScanner;
import org.htmlparser.scanners.HtmlScanner;
import org.htmlparser.scanners.MetaTagScanner;
import org.htmlparser.scanners.StyleScanner;
import org.htmlparser.scanners.TitleScanner;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class TitleScannerTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.scannersTests.TitleScannerTest", "TitleScannerTest");
    }

    public TitleScannerTest(String name) {
        super(name);
    }

    public void testScan() throws ParserException {
        createParser("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
        TitleScanner titleScanner = new TitleScanner("-t");
        parser.addScanner(titleScanner);
        parser.addScanner(new StyleScanner("-s"));
        parser.addScanner(new MetaTagScanner("-m"));
        parseAndAssertNodeCount(7);
        assertTrue(node[2] instanceof TitleTag);
        // check the title node
        TitleTag titleTag = (TitleTag) node[2];
        assertEquals("Title","Yahoo!",titleTag.getTitle());
    }

    /**
     * Testcase to reproduce a bug reported by Cedric Rosa,
     * on not ending the title tag correctly, we would get
     * null pointer exceptions..
     */
    public void testIncompleteTitle() throws ParserException {
        String text =
            "<HTML>\n"+
            "<HEAD>\n"+
            // note the missing angle bracket on the close title:
            "<TITLE>SISTEMA TERRA, VOL. VI , No. 1-3, December 1997</TITLE\n"+
            "</HEAD>\n"+
            "<BODY>\n"+
            "The body.\n"+
            "</BODY>\n"+
            "</HTML>";        
        createParser(text);
        parser.addScanner (new HtmlScanner ());
        parser.addScanner (new TitleScanner ("-t"));
        parser.addScanner (new HeadScanner ());
        parser.addScanner (new BodyScanner ());
        parseAndAssertNodeCount(1);
        assertTrue ("Only node is a html tag",node[0] instanceof Html);
        Html html = (Html)node[0];
        assertEquals ("Html node has five children", 5, html.getChildCount ());
        assertTrue ("Second child is a head tag", html.childAt (1) instanceof HeadTag);
        HeadTag head = (HeadTag)html.childAt (1);
        assertEquals ("Head node has two children", 2, head.getChildCount ());
        assertTrue ("Second child is a title tag", head.childAt (1) instanceof TitleTag);
        TitleTag titleTag = (TitleTag)head.childAt (1);
        assertEquals("Title","SISTEMA TERRA, VOL. VI , No. 1-3, December 1997",titleTag.getTitle());
// Note: this will fail because of the extra > inserted to finish the /TITLE tag:
//        assertStringEquals ("toHtml", text, html.toHtml ());
    }

    /**
     * If there are duplicates of the title tag, the parser crashes.
     * This bug was reported by Claude Duguay
     */
    public void testDoubleTitleTag() throws ParserException{
        createParser(
        "<html><head><TITLE>\n"+
        "<html><head><TITLE>\n"+
        "Double tags can hang the code\n"+
        "</TITLE></head><body>\n"+
        "<body><html>");
        TitleScanner titleScanner = new TitleScanner("-t");
        parser.addScanner(titleScanner);
        parseAndAssertNodeCount(9);
        assertTrue("Third tag should be a title tag",node[2] instanceof TitleTag);
        TitleTag titleTag = (TitleTag)node[2];
        assertEquals("Title","\n",titleTag.getTitle());
        assertTrue("Fourth tag should be a title tag",node[3] instanceof TitleTag);
        titleTag = (TitleTag)node[3];
        assertEquals("Title","\nDouble tags can hang the code\n",titleTag.getTitle());
    }

    /**
     * Testcase based on Claude Duguay's report. This proves
     * that the parser throws exceptions when faced with malformed html
     */
    public void testNoEndTitleTag() throws ParserException {
        createParser(
        "<TITLE>KRP VALIDATION<PROCESS/TITLE>");
        TitleScanner titleScanner = new TitleScanner("-t");
        parser.addScanner(titleScanner);
        parseAndAssertNodeCount(1);
        TitleTag titleTag = (TitleTag)node[0];
        assertEquals("Expected title","KRP VALIDATION",titleTag.getTitle());
    }

    public void testTitleTagContainsJspTag() throws ParserException {
        String title = "<title><%=gTitleString%></title>";
        createParser("<html><head>" + title + "<base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>");
        parser.registerScanners();
        parseAndAssertNodeCount(7);
        assertTrue(node[2] instanceof TitleTag);
        TitleTag titleTag = (TitleTag) node[2];
        assertStringEquals("HTML Rendering",title,titleTag.toHtml());
    }
}
