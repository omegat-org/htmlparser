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
// This class was contributed by Joshua Kerievsky

package org.htmlparser.visitors;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.StringNode;
import org.htmlparser.scanners.ImageScanner;
import org.htmlparser.scanners.LinkScanner;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;

public class UrlModifyingVisitor extends NodeVisitor {
    private String linkPrefix;
    private StringBuffer modifiedResult;
    private Parser parser;

    public UrlModifyingVisitor(Parser parser, String linkPrefix) {
        super(true,true);
        this.parser = parser;
        parser.addScanner(new LinkScanner());
        parser.addScanner(new ImageScanner(ImageTag.IMAGE_TAG_FILTER));
        this.linkPrefix =linkPrefix;
        modifiedResult = new StringBuffer();
    }

    public void visitLinkTag(LinkTag linkTag) {
        linkTag.setLink(linkPrefix + linkTag.getLink());
    }

    public void visitImageTag(ImageTag imageTag) {
        imageTag.setImageURL(linkPrefix + imageTag.getImageURL());
    }

    public void visitStringNode(StringNode stringNode) {
        modifiedResult.append(stringNode.toHtml());
    }

    public void visitTag(Tag tag)
    {   // process only those nodes that won't be processed by an end tag,
        // nodes without parents or parents without an end tag, since
        // the complete processing of all children should happen before
        // we turn this node back into html text
        if (null == tag.getParent ()
            && (!(tag instanceof CompositeTag) || null == ((CompositeTag)tag).getEndTag ()))
            modifiedResult.append(tag.toHtml());
    }

    public void visitEndTag(Tag tag)
    {
        Node parent;
        
        parent = tag.getParent ();
        if (null == parent)
            modifiedResult.append(tag.toHtml());
        else
            modifiedResult.append(parent.toHtml());
    }

    public String getModifiedResult() {
        return modifiedResult.toString();
    }
}
