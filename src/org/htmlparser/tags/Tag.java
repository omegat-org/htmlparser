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

package org.htmlparser.tags;

import java.util.Vector;

import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

/**
 * Tag represents a generic tag.
 * If no scanner is registered for a given tag name, this is what you get.
 * This is also the base class for all tags created by the parser (not the
 * lexer which has nodes).
 */
public class Tag extends TagNode implements Cloneable
{
    /**
     * An empty set of tag names.
     */
    private final static String[] NONE = new String[0];
    
    /**
     * The scanner for this tag.
     */
    private TagScanner mScanner;
    
    /**
     * The default scanner for non-composite tags.
     */
    protected final static TagScanner mDefaultScanner = new TagScanner ();

    public Tag ()
    {
        String[] names;
        
        names = getIds ();
        if ((null != names) && (0 != names.length))
            setTagName (names[0]);
        setThisScanner (mDefaultScanner);
    }

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

    public Object clone() throws CloneNotSupportedException
    {
        return (super.clone ());
    }

    /**
     * Return the set of names handled by this tag.
     * Since this a a generic tag, it has no ids.
     * @return The names to be matched that create tags of this type.
     */
    public String[] getIds ()
    {
        return (NONE);
    }

    /**
     * Return the set of tag names that cause this tag to finish.
     * These are the normal (non end tags) that if encountered while
     * scanning (a composite tag) will cause the generation of a virtual
     * tag.
     * Since this a a non-composite tag, the default is no enders.
     * @return The names of following tags that stop further scanning.
     */
    public String[] getEnders ()
    {
        return (NONE);
    }

    /**
     * Return the set of end tag names that cause this tag to finish.
     * These are the end tags that if encountered while
     * scanning (a composite tag) will cause the generation of a virtual
     * tag.
     * Since this a a non-composite tag, it has no end tag enders.
     * @return The names of following end tags that stop further scanning.
     */
    public String[] getEndTagEnders ()
    {
        return (NONE);
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
