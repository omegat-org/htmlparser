// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.tags;

import java.util.Vector;

import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.scanners.TagScanner;
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
        else
            setTagName (""); // make sure it's not null
        setThisScanner (mDefaultScanner);
    }

    public Tag (TagNode node, TagScanner scanner)
    {
        super (node.getPage (), node.getTagBegin (), node.getTagEnd (), node.getAttributesEx ());
        mScanner = scanner;
        if (null == getTagName ())
            setTagName (""); // make sure it's not null
    }

    public Tag (Page page, int start, int end, Vector attributes)
    {
        super (page, start, end, attributes);
        mScanner = null;
        if (null == getTagName ())
            setTagName (""); // make sure it's not null
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
}
