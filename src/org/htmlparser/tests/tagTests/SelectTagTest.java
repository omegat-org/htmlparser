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
//
// Author of this class : Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package org.htmlparser.tests.tagTests;

import org.htmlparser.scanners.OptionTagScanner;
import org.htmlparser.scanners.SelectTagScanner;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class SelectTagTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.SelectTagTest", "SelectTagTest");
    }

    private String testHTML = new String(
                                    "<SELECT name=\"Nominees\">\n"+
                                    "<option value=\"Spouse\">Spouse"+
                                    "<option value=\"Father\"></option>\n"+
                                    "<option value=\"Mother\">Mother\n" +
                                    "<option value=\"Son\">\nSon\n</option>"+
                                    "<option value=\"Daughter\">\nDaughter\n"+
                                    "<option value=\"Nephew\">\nNephew</option>\n"+
                                    "<option value=\"Niece\">Niece\n" +
                                    "</select>"
                                    );
    private String correctedHTML = new String(
                                    "<SELECT name=\"Nominees\">\n"+
                                    "<option value=\"Spouse\">Spouse</option>"+
                                    "<option value=\"Father\"></option>\n"+
                                    "<option value=\"Mother\">Mother\n</option>" +
                                    "<option value=\"Son\">\nSon\n</option>"+
                                    "<option value=\"Daughter\">\nDaughter\n</option>"+
                                    "<option value=\"Nephew\">\nNephew</option>\n"+
                                    "<option value=\"Niece\">Niece\n</option>" +
                                    "</select>"
                                    );
    private SelectTag selectTag;

    public SelectTagTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception{
        super.setUp();
        createParser(testHTML);
        parser.addScanner(new SelectTagScanner("-s"));
        parser.addScanner(new OptionTagScanner("-o"));
        parseAndAssertNodeCount(1);
        assertTrue("Node 1 should be Select Tag",node[0] instanceof SelectTag);
        selectTag = (SelectTag) node[0];
    }

    public void testToHTML() throws ParserException
    {
        assertStringEquals("HTML String", correctedHTML, selectTag.toHtml());
    }


    public void testToString() throws ParserException
    {
        assertTrue("Node 1 should be Select Tag",node[0] instanceof SelectTag);
        SelectTag selectTag;
        selectTag = (SelectTag) node[0];
        assertStringEquals("HTML Raw String","SELECT TAG\n--------\nNAME : Nominees\n" +
                                "OPTION VALUE: Spouse TEXT: Spouse\n\n" +
                                "OPTION VALUE: Father TEXT: \n\n" +
                                "OPTION VALUE: Mother TEXT: Mother\n\n\n" +
                                "OPTION VALUE: Son TEXT: \nSon\n\n\n" +
                                "OPTION VALUE: Daughter TEXT: \nDaughter\n\n\n" +
                                "OPTION VALUE: Nephew TEXT: \nNephew\n\n" +
                                "OPTION VALUE: Niece TEXT: Niece\n\n\n",
                            selectTag.toString());
    }

    public void testGetOptionTags() {
        OptionTag [] optionTags = selectTag.getOptionTags();
        assertEquals("option tag array length",7,optionTags.length);
        assertEquals("option tag 1","Spouse",optionTags[0].getOptionText());
        assertEquals("option tag 7","Niece\n",optionTags[6].getOptionText());
    }
}
