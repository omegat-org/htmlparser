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

package org.htmlparser.tests.utilTests;

import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.DefaultParserFeedback;

public class HTMLTagParserTest extends ParserTestCase {
//    private TagParser tagParser;

    public HTMLTagParserTest(String name) {
        super(name);
    }

    public void testCorrectTag() {
        fail ("not implemented");
//        Tag tag = new Tag(new TagData(0,20,"font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"","<font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\">"));
//        tagParser.correctTag(tag);
//        assertStringEquals("Corrected Tag","font face=\"Arial,helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"",tag.getText());
    }

    public void testInsertInvertedCommasCorrectly() {
        fail ("not implemented");
//        StringBuffer test = new StringBuffer("a b=c d e = f");
//        StringBuffer result = tagParser.insertInvertedCommasCorrectly(test);
//        assertStringEquals("Expected Correction","a b=\"c d\" e=\"f\"",result.toString());
    }

    public void testPruneSpaces() {
        fail ("not implemented");
//        String test = "  fdfdf dfdf   ";
//        assertEquals("Expected Pruned string","fdfdf dfdf",TagParser.pruneSpaces(test));
    }

    protected void setUp() {
        fail ("not implemented");
//        tagParser = new TagParser(new DefaultParserFeedback());
    }
}
