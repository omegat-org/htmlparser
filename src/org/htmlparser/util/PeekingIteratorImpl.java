// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2003 Derrick Oswald
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

package org.htmlparser.util;

import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;

/**
 * @deprecated shouldn't need to pre-read tags.
 */
public class PeekingIteratorImpl implements PeekingIterator
{
    Lexer mLexer;
    Vector preRead;
    ParserFeedback feedback;

    public PeekingIteratorImpl (Lexer lexer, ParserFeedback fb)
    {
        mLexer = lexer;
        preRead = new Vector (25);
        feedback = fb;
    }

    public Node peek () throws ParserException
    {
        Node ret;

        if (null == mLexer)
            ret = null;
        else
            try
            {
                ret = mLexer.nextNode ();
                if (null != ret)
                {
                    // kick off recursion for the top level node
                    if (ret instanceof org.htmlparser.tags.Tag)
                    {
                        org.htmlparser.tags.Tag tag;
                        String name;
                        org.htmlparser.scanners.TagScanner scanner;

                        tag = (org.htmlparser.tags.Tag)ret;
                        if (!tag.isEndTag ())
                        {
                            // now recurse if there is a scanner for this type of tag
                            scanner = tag.getThisScanner ();
                            if ((null != scanner) && scanner.evaluate (tag, null))
                                ret = scanner.scan (tag, mLexer.getPage ().getUrl (), mLexer);
                        }
                    }

                    preRead.addElement (ret);
                }
            }
            catch (Exception e) {
                StringBuffer msgBuffer = new StringBuffer();
                msgBuffer.append("Unexpected Exception occurred while reading ");
                msgBuffer.append(mLexer.getPage ().getUrl ());
                msgBuffer.append(", in nextHTMLNode");
//                reader.appendLineDetails(msgBuffer);
                ParserException ex = new ParserException(msgBuffer.toString(),e);
                feedback.error(msgBuffer.toString(),ex);
                throw ex;
            }

        return (ret);
    }

    /**
     * Makes <code>node</code> the next <code>Node</code> that will be returned.
     * @param node The node to return next.
     */
    public void push (Node node)
    {
        preRead.insertElementAt (node, 0);
    }

    /**
     * Check if more nodes are available.
     * @return <code>true</code> if a call to <code>nextNode()</code> will succeed.
     */
    public boolean hasMoreNodes() throws ParserException {
        boolean ret;

        if (null == mLexer)
            ret = false;
        else if (0 != preRead.size ())
            ret = true;
        else
            ret = !(null == peek ());

        return (ret);
    }

    /**
     * Get the next node.
     * @return The next node in the HTML stream, or null if there are no more nodes.
     */
    public Node nextNode() throws ParserException {
        Node ret;

        if (hasMoreNodes ())
            ret = (Node)preRead.remove (0);
        else
            // should perhaps throw an exception?
            ret = null;

        return (ret);
    }
}
