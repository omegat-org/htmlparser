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


package org.htmlparser.scanners;

import java.util.Vector;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Scans title tags.
 */
public class TitleScanner extends CompositeTagScanner {
    private static final String MATCH_NAME [] = {"TITLE"};
    private static final String ENDERS [] = {"BODY"};
    private static final String END_TAG_ENDERS [] = {"HEAD", "HTML"};

    public TitleScanner(String filter) {
        super(filter,MATCH_NAME,ENDERS,END_TAG_ENDERS,false);
    }

    public String [] getID() {
        return MATCH_NAME;
    }

    public boolean evaluate(String tagNameBeingChecked, TagScanner previousOpenScanner) {
        absorbLeadingBlanks(tagNameBeingChecked);
        return (tagNameBeingChecked.toUpperCase ().startsWith (MATCH_NAME[0]) && ((null == previousOpenScanner)
        || !previousOpenScanner.getID ()[0].equals  ("TITLE")));
    }

    public Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
    {
        TitleTag ret;

        ret = new TitleTag ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);
        ret.setStartTag (startTag);
        ret.setEndTag (endTag);
        ret.setChildren (children);

        return (ret);
    }
}
