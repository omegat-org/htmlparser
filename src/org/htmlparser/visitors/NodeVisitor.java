// HTMLParser Library v1_4_20031207 - A java-based parser for HTML
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

import org.htmlparser.RemarkNode;
import org.htmlparser.StringNode;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;

/**
 * The base class for the 'Visitor' pattern.
 * Classes that wish to use <code>visitAllNodesWith()</code> will subclass
 * this class and provide implementations for methods they are interested in
 * processing.<p>
 * The operation of <code>visitAllNodesWith()</code> is to call
 * <code>beginParsing()</code>, then <code>visitXXX()</code> according to the
 * types of nodes encountered in depth-first order and finally
 * <code>finishedParsing()</code>.<p>
 * There are currently three specialized <code>visitXXX()</code> calls for
 * titles, images and links. Thes call their specialized visit, and then
 * perform the generic processing.
 * Typical code to print all the link tags:
 * <pre>
 * import org.htmlparser.Parser;
 * import org.htmlparser.tags.LinkTag;
 * import org.htmlparser.util.ParserException;
 * import org.htmlparser.visitors.NodeVisitor;
 * 
 * public class Visitor extends NodeVisitor
 * {
 *     public Visitor ()
 *     {
 *     }
 *     public void visitLinkTag (LinkTag linkTag)
 *     {
 *         System.out.println (linkTag);
 *     }
 *     public static void main (String[] args) throws ParserException
 *     {
 *         Parser parser = new Parser ("http://cbc.ca");
 *         Visitor visitor = new Visitor ();
 *         parser.visitAllNodesWith (visitor);
 *     }
 * }
 * </pre>
 */
public abstract class NodeVisitor
{
    private boolean mRecurseChildren;
    private boolean mRecurseSelf;
    
    public NodeVisitor ()
    {
        this (true);
    }
    
    public NodeVisitor (boolean recurseChildren)
    {
        this (recurseChildren, true);
    }
    
    public NodeVisitor (boolean recurseChildren, boolean recurseSelf)
    {
        mRecurseChildren = recurseChildren;
        mRecurseSelf = recurseSelf;
    }

    /**
     * Override this method if you wish to do special
     * processing prior to the start of parsing.
     */
    public void beginParsing ()
    {
    }

    public void visitTag (Tag tag)
    {
        
    }
    
    public void visitEndTag (Tag tag)
    {
        
    }
    
    public void visitStringNode (StringNode stringNode)
    {
    }
    
    public void visitRemarkNode (RemarkNode remarkNode)
    {
        
    }
    
    /**
     * Override this method if you wish to do special
     * processing upon completion of parsing.
     */
    public void finishedParsing ()
    {
    }

    public void visitLinkTag (LinkTag linkTag)
    {
    }
    
    public void visitImageTag (ImageTag imageTag)
    {
    }
    
    public void visitTitleTag (TitleTag titleTag)
    {
        
    }

    public boolean shouldRecurseChildren ()
    {
        return (mRecurseChildren);
    }
    
    public boolean shouldRecurseSelf ()
    {
        return (mRecurseSelf);
    }
}
