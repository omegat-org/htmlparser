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

package org.htmlparser.tests.tagTests;

import java.util.Stack;

import org.htmlparser.scanners.TextareaTagScanner;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class TextareaTagTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.TextareaTagTest", "TextareaTagTest");
    }

    private String area1 = "<TEXTAREA name=\"Remarks\" >The intervention by the UN proved beneficial</TEXTAREA>";
    private String area2 = "<TEXTAREA>The capture of the Somali warloard was elusive</TEXTAREA>";
    private String area3 = "<TEXTAREA></TEXTAREA>";
    private String area4 = "<TEXTAREA name=\"Remarks\">The death threats of the organization\n" +
                            "refused to intimidate the soldiers</TEXTAREA>";
    private String area5 = "<TEXTAREA name=\"Remarks\">The death threats of the LTTE\n" +
                            "refused to intimidate the Tamilians\n</TEXTAREA>";

    private String testHTML = area1 + area2 + area3 + area4 + area5;

    public TextareaTagTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        super.setUp();
        createParser(testHTML);
        parser.addScanner(new TextareaTagScanner("-t", new Stack ()));
        parseAndAssertNodeCount(5);
    }

    public void testToHTML() throws ParserException
    {
        assertTrue("Node 1 should be Textarea Tag",node[0] instanceof TextareaTag);
        assertTrue("Node 2 should be Textarea Tag",node[1] instanceof TextareaTag);
        assertTrue("Node 3 should be Textarea Tag",node[2] instanceof TextareaTag);
        assertTrue("Node 4 should be Textarea Tag",node[3] instanceof TextareaTag);
        assertTrue("Node 5 should be Textarea Tag",node[4] instanceof TextareaTag);
        TextareaTag textareaTag;
        textareaTag = (TextareaTag) node[0];
        assertStringEquals("HTML String 1",area1,textareaTag.toHtml());
        textareaTag = (TextareaTag) node[1];
        assertStringEquals("HTML String 2",area2,textareaTag.toHtml());
        textareaTag = (TextareaTag) node[2];
        assertStringEquals("HTML String 3",area3,textareaTag.toHtml());
        textareaTag = (TextareaTag) node[3];
        assertStringEquals("HTML String 4",area4,textareaTag.toHtml());
        textareaTag = (TextareaTag) node[4];
        assertStringEquals("HTML String 5",area5,textareaTag.toHtml());

    }


    public void testToString() throws ParserException
    {
        assertTrue("Node 1 should be Textarea Tag",node[0] instanceof TextareaTag);
        assertTrue("Node 2 should be Textarea Tag",node[1] instanceof TextareaTag);
        assertTrue("Node 3 should be Textarea Tag",node[2] instanceof TextareaTag);
        assertTrue("Node 4 should be Textarea Tag",node[3] instanceof TextareaTag);
        assertTrue("Node 5 should be Textarea Tag",node[4] instanceof TextareaTag);
        TextareaTag textareaTag;
        textareaTag = (TextareaTag) node[0];
        assertStringEquals("HTML Raw String 1","TEXTAREA TAG\n--------\nNAME : Remarks\nVALUE : The intervention by the UN proved beneficial\n",textareaTag.toString());
        textareaTag = (TextareaTag) node[1];
        assertStringEquals("HTML Raw String 2","TEXTAREA TAG\n--------\nVALUE : The capture of the Somali warloard was elusive\n",textareaTag.toString());
        textareaTag = (TextareaTag) node[2];
        assertStringEquals("HTML Raw String 3","TEXTAREA TAG\n--------\nVALUE : \n",textareaTag.toString());
        textareaTag = (TextareaTag) node[3];
        assertStringEquals("HTML Raw String 4","TEXTAREA TAG\n--------\nNAME : Remarks\nVALUE : The death threats of the organization\n"+
                                        "refused to intimidate the soldiers\n",textareaTag.toString());
        textareaTag = (TextareaTag) node[4];
        assertStringEquals("HTML Raw String 5","TEXTAREA TAG\n--------\nNAME : Remarks\nVALUE : The death threats of the LTTE\n"+
                                        "refused to intimidate the Tamilians\n\n",textareaTag.toString());
    }

}
