// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
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
//
// This class was contributed by Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package org.htmlparser.tests.scannersTests;

import java.util.*;
import junit.framework.TestSuite;
import org.htmlparser.scanners.LabelScanner;
import org.htmlparser.tags.LabelTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class LabelScannerTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.scannersTests.LabelScannerTest", "LabelScannerTest");
    }

    public LabelScannerTest(String name) {
        super(name);
    }
    public void testSimpleLabels() throws ParserException {
        createParser("<label>This is a label tag</label>");
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof LabelTag);
        //  check the title node
        LabelTag labelTag = (LabelTag) node[0];
        assertEquals("Label","This is a label tag",labelTag.getChildrenHTML());
        assertEquals("Label","This is a label tag",labelTag.getLabel());
        assertStringEquals("Label","<LABEL>This is a label tag</LABEL>",labelTag.toHtml());
        assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
    }

    public void testLabelWithJspTag() throws ParserException {
        String label = "<label><%=labelValue%></label>";
        createParser(label);
        parser.registerScanners();
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof LabelTag);
        //  check the title node
        LabelTag labelTag = (LabelTag) node[0];
        assertStringEquals("Label",label,labelTag.toHtml());
        assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
    }

    public void testLabelWithOtherTags() throws ParserException {
        createParser("<label><span>Span within label</span></label>");
        parser.registerScanners();
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof LabelTag);
        //  check the title node
        LabelTag labelTag = (LabelTag) node[0];
        assertEquals("Label value","Span within label",labelTag.getLabel());
        assertStringEquals("Label","<LABEL><SPAN>Span within label</SPAN></LABEL>",labelTag.toHtml());
        assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
    }

    public void testLabelWithManyCompositeTags() throws ParserException {
        String guts = "<span>Jane <b> Doe </b> Smith</span>";
        String html = "<label>" + guts + "</label>";
        createParser(html);
        parser.registerScanners();
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof LabelTag);
        LabelTag labelTag = (LabelTag) node[0];
        assertEquals("Label value",guts,labelTag.getChildrenHTML());
        assertEquals("Label value","Jane  Doe  Smith",labelTag.getLabel());
        assertStringEquals("Label",html,labelTag.toHtml());
        assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
    }


    public void testLabelsID() throws ParserException {
        createParser("<label>John Doe</label>");
        parser.registerScanners();
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(1);
        assertTrue(node[0] instanceof LabelTag);

        LabelTag labelTag = (LabelTag) node[0];
        assertStringEquals("Label","<LABEL>John Doe</LABEL>",labelTag.toHtml());
        Hashtable attr = labelTag.getAttributes();
        assertNull("ID",attr.get("id"));
    }

    public void testNestedLabels() throws ParserException {
        createParser("<label id=\"attr1\"><label>Jane Doe");
        parser.registerScanners();
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(2);
        assertTrue(node[0] instanceof LabelTag);
        assertTrue(node[1] instanceof LabelTag);

        LabelTag labelTag = (LabelTag) node[0];
        assertStringEquals("Label","<LABEL ID=\"attr1\"></LABEL>",labelTag.toHtml());
        labelTag = (LabelTag) node[1];
        assertStringEquals("Label","<LABEL>Jane Doe</LABEL>",labelTag.toHtml());
        Hashtable attr = labelTag.getAttributes();
        assertNull("ID",attr.get("id"));
    }

    public void testNestedLabels2() throws ParserException {
        String testHTML = new String(
                                    "<LABEL value=\"Google Search\">Google</LABEL>" +
                                    "<LABEL value=\"AltaVista Search\">AltaVista" +
                                    "<LABEL value=\"Lycos Search\"></LABEL>" +
                                    "<LABEL>Yahoo!</LABEL>" +
                                    "<LABEL>\nHotmail</LABEL>" +
                                    "<LABEL value=\"ICQ Messenger\">" +
                                    "<LABEL>Mailcity\n</LABEL>"+
                                    "<LABEL>\nIndiatimes\n</LABEL>"+
                                    "<LABEL>\nRediff\n</LABEL>"+
                                    "<LABEL>Cricinfo" +
                                    "<LABEL value=\"Microsoft Passport\">" +
                                    "<LABEL value=\"AOL\"><SPAN>AOL</SPAN></LABEL>" +
                                    "<LABEL value=\"Time Warner\">Time <B>Warner <SPAN>AOL </SPAN>Inc.</B>"
                                    );
        createParser(testHTML);
        //parser.registerScanners();
        LabelScanner labelScanner = new LabelScanner("-l");
        parser.addScanner(labelScanner);
        parseAndAssertNodeCount(13);

//      for(int j=0;j<nodeCount;j++)
//      {
//          //assertTrue("Node " + j + " should be Label Tag",node[j] instanceof LabelTag);
//          System.out.println(node[j].getClass().getName());
//          System.out.println(node[j].toHtml());
//      }

        LabelTag LabelTag;
        LabelTag = (LabelTag) node[0];
        assertStringEquals("HTML String","<LABEL VALUE=\"Google Search\">Google</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[1];
        assertStringEquals("HTML String","<LABEL VALUE=\"AltaVista Search\">AltaVista</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[2];
        assertStringEquals("HTML String","<LABEL VALUE=\"Lycos Search\"></LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[3];
        assertStringEquals("HTML String","<LABEL>Yahoo!</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[4];
        assertStringEquals("HTML String","<LABEL>\nHotmail</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[5];
        assertStringEquals("HTML String","<LABEL VALUE=\"ICQ Messenger\"></LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[6];
        assertStringEquals("HTML String","<LABEL>Mailcity\n</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[7];
        assertStringEquals("HTML String","<LABEL>\nIndiatimes\n</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[8];
        assertStringEquals("HTML String","<LABEL>\nRediff\n</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[9];
        assertStringEquals("HTML String","<LABEL>Cricinfo</LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[10];
        assertStringEquals("HTML String","<LABEL VALUE=\"Microsoft Passport\"></LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[11];
        assertStringEquals("HTML String","<LABEL VALUE=\"AOL\"><SPAN>AOL</SPAN></LABEL>",LabelTag.toHtml());
        LabelTag = (LabelTag) node[12];
        assertStringEquals("HTML String","<LABEL VALUE=\"Time Warner\">Time <B>Warner <SPAN>AOL </SPAN>Inc.</B></LABEL>",LabelTag.toHtml());
    }

    public static TestSuite suite() {
        return new TestSuite(LabelScannerTest.class);
    }
}
