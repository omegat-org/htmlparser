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

package org.htmlparser.visitors;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.RemarkNode;
import org.htmlparser.StringNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;

public class HtmlPage extends NodeVisitor {
    private String title;
    private NodeList nodesInBody;
    private NodeList tables;

    public HtmlPage(Parser parser) {
        super(true);
        title = "";
        nodesInBody = new NodeList();
        tables = new NodeList();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void visitTag(Tag tag)
    {
        if (isTable(tag))
            tables.add(tag);
        else if (isBodyTag(tag))
            nodesInBody = tag.getChildren ();
    }

    private boolean isTable(Tag tag)
    {
        return (tag instanceof TableTag);
    }

    private boolean isBodyTag(Tag tag)
    {
        return (tag instanceof BodyTag);
    }

    public NodeList getBody() {
        return nodesInBody;
    }

    public TableTag [] getTables()
    {
        TableTag [] tableArr = new TableTag[tables.size()];
        tables.copyToNodeArray (tableArr);
        return tableArr;
    }

    public void visitTitleTag(TitleTag titleTag)
    {
        title = titleTag.getTitle();
    }
}
