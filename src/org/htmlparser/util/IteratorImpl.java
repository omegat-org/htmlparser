// HTMLParser Library v1_4_20030907 - A java-based parser for HTML
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

import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;

public class IteratorImpl implements PeekingIterator
{
    NodeReader reader;
    Vector preRead;
    String resourceLocn;
    ParserFeedback feedback;

    public IteratorImpl (NodeReader rd, String resource, ParserFeedback fb)
    {
        reader = rd;
        preRead = new Vector (25);
        resourceLocn = resource;
        feedback = fb;
    }

    public Node peek () throws ParserException
    {
        Node ret;

        if (null == reader)
            ret = null;
        else
            try
            {
                ret = reader.readElement();
                if (null != ret)
                    preRead.addElement (ret);
            }
            catch (Exception e) {
                StringBuffer msgBuffer = new StringBuffer();
                msgBuffer.append("Unexpected Exception occurred while reading ");
                msgBuffer.append(resourceLocn);
                msgBuffer.append(", in nextHTMLNode");
                reader.appendLineDetails(msgBuffer);
                ParserException ex = new ParserException(msgBuffer.toString(),e);
                feedback.error(msgBuffer.toString(),ex);
                throw ex;
            }
        
        return (ret);
    }
    
    /**
     * Check if more nodes are available.
     * @return <code>true</code> if a call to <code>nextHTMLNode()</code> will succeed.
     */
    public boolean hasMoreNodes() throws ParserException {
        boolean ret;

        if (null == reader)
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
