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

package org.htmlparser.scanners;

import java.util.Vector;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class TableRowScanner extends CompositeTagScanner {
    private final static String MATCH_STRING [] = { "TR" };

    public TableRowScanner(Parser parser) {
        this("",parser);
    }

    public TableRowScanner(String filter,Parser parser) {
        this(filter, parser, MATCH_STRING, MATCH_STRING, new String[] {});
    }

    public TableRowScanner(
        String filter,
        Parser parser,
        String[] nameOfTagToMatch,
        String [] tagEnders,
        String [] endTagEnders) {
        super(
            filter,
            nameOfTagToMatch,
            tagEnders,
            endTagEnders);
        parser.addScanner(new TableColumnScanner());
    }

    public Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
    {
        TableRow ret;

        ret = new TableRow ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);
        ret.setStartTag (startTag);
        ret.setEndTag (endTag);
        ret.setChildren (children);

        return (ret);
    }

    public String[] getID() {
        return MATCH_STRING;
    }

}
