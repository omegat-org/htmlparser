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

package org.htmlparser.tags;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.htmlparser.AbstractNode;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.parserHelper.SpecialHashtable;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

/**
 * Tag represents a generic tag. This class allows users to register specific
 * tag scanners, which can identify links, or image references. This tag asks the
 * scanners to run over the text, and identify. It can be used to dynamically
 * configure a parser.
 */
public class Tag extends TagNode
{
    TagScanner mScanner;
    TagData mData;

    public Tag (TagNode node, TagScanner scanner)
    {
        super (node.getPage (), node.getTagBegin (), node.getTagEnd (), node.getAttributesEx ());
        mScanner = scanner;
    }

    public Tag (Page page, int start, int end, Vector attributes)
    {
        super (page, start, end, attributes);
        mScanner = null;
    }

    public Tag (TagData data)
    {
        super (data.getPage (), data.getTagBegin (), data.getTagEnd (), data.getAttributes ());
        mData = data;
        mScanner = null;
    }

    /**
     * Return the scanner associated with this tag.
     */
    public TagScanner getThisScanner()
    {
        return (mScanner);
    }

    public void setThisScanner(TagScanner scanner)
    {
        mScanner = scanner;
    }

    /**
     * This method verifies that the current tag matches the provided
     * filter. The match is based on the string object and not its contents,
     * so ensure that you are using static final filter strings provided
     * in the tag classes.
     * @see org.htmlparser.Node#collectInto(NodeList, String)
     */
    public void collectInto(NodeList collectionList, String filter)
    {
        if (null != getThisScanner () && getThisScanner ().getFilter () == filter)
            collectionList.add (this);
    }

    /**
     * Jeez I hope this goes away.
     */
    public String getTagContents ()
    {
        String ret;

        if (null != mData)
            ret = mData.getTagContents();
        else
            ret = "";
        
        return (ret);
    }

    /**
     * Handle a visitor.
     * <em>NOTE: This currently defers to accept(NodeVisitor). If
     * subclasses of Node override accept(Object) directly, they must
     * handle the delegation to <code>visitTag()</code> and
     * <code>visitEndTag()</code>.</em>
     * @param visitor The <code>NodeVisitor</code> object
     * (a cast is performed without checking).
     */
    public void accept (Object visitor)
    {
        accept ((NodeVisitor)visitor);
    }

    /**
     * Default tag visiting code.
     * Based on <code>isEndTag()</code>, calls either <code>visitTag()</code> or
     * <code>visitEndTag()</code>.
     */
    public void accept (NodeVisitor visitor)
    {
        if (isEndTag ())
            ((NodeVisitor)visitor).visitEndTag (this);
        else
            ((NodeVisitor)visitor).visitTag (this);
    }
}
