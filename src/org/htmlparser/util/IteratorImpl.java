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

package org.htmlparser.util;

import org.htmlparser.Node;
import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeIterator;

public class IteratorImpl implements NodeIterator
{
    Lexer mLexer;
    ParserFeedback mFeedback;
    Cursor mCursor;

    public IteratorImpl (Lexer lexer, ParserFeedback fb)
    {
        mLexer = lexer;
        mFeedback = fb;
        mCursor = new Cursor (mLexer.getPage (), 0);
    }

    /**
     * Check if more nodes are available.
     * @return <code>true</code> if a call to <code>nextNode()</code> will succeed.
     */
    public boolean hasMoreNodes() throws ParserException
    {
        boolean ret;

        mCursor.setPosition (mLexer.getPosition ());
        ret = 0 != mLexer.getPage ().getCharacter (mCursor); // more characters?

        return (ret);
    }

    /**
     * Get the next node.
     * @return The next node in the HTML stream, or null if there are no more nodes.
     */
    public Node nextNode() throws ParserException
    {
        Node ret;

        try
        {
            ret = mLexer.nextNode ();
            if (null != ret)
            {
                // kick off recursion for the top level node
                if (ret instanceof Tag)
                {
                    Tag tag;
                    String name;
                    TagScanner scanner;

                    tag = (Tag)ret;
                    if (!tag.isEndTag ())
                    {
                        // now recurse if there is a scanner for this type of tag
                        scanner = tag.getThisScanner ();
                        if ((null != scanner) && scanner.evaluate (tag, null))
                            ret = scanner.scan (tag, mLexer.getPage ().getUrl (), mLexer);
                    }
                }
            }
        }
        catch (Exception e)
        {
            StringBuffer msgBuffer = new StringBuffer();
            msgBuffer.append("Unexpected Exception occurred while reading ");
            msgBuffer.append(mLexer.getPage ().getUrl ());
            msgBuffer.append(", in nextHTMLNode");
//                reader.appendLineDetails(msgBuffer);
            ParserException ex = new ParserException(msgBuffer.toString(),e);
            mFeedback.error(msgBuffer.toString(),ex);
            throw ex;
        }
        
        return (ret);
    }
}
