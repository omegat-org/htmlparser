// HTMLParser Library v1_2_20021215 - A java-based parser for HTML
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

import org.htmlparser.HTMLNode;

public interface HTMLEnumeration {
    /**
     * Check if more nodes are available.
     * @return <code>true</code> if a call to <code>nextHTMLNode()</code> will succeed.
     */
	public boolean hasMoreNodes() throws HTMLParserException;
    
    /**
     * Get the next node.
     * @return The next node in the HTML stream, or null if there are no more nodes.
     */
	public HTMLNode nextHTMLNode() throws HTMLParserException;
    
    /**
     * Fetch a node without consuming it.
     * Subsequent calls to <code>peek()</code> will return subsequent nodes.
     * The node returned by <code>peek()</code> will never be a node already
     * consumed by <code>nextHTMLNode()</code>.<p>
     * For example, say there are nodes &lt;H1&gt;&lt;H2&gt;&lt;H3&gt;&lt;H4&gt;&lt;H5&gt;,
     * this is the nodes that would be returned for the indicated calls:
     * <pre>
     * peek()         H1
     * peek()         H2
     * nextHTMLNode() H1
     * peek()         H3
     * nextHTMLNode() H2
     * nextHTMLNode() H3
     * nextHTMLNode() H4
     * peek()         H5
     * </pre>
     * @return The next node that would be returned by <code>nextHTMLNode()</code>
     * or the node after the last node returned by <code>peek()</code>, whichever
     * is later in the stream. or null if there are no more nodes available via
     * the above rules.
     */
    public HTMLNode peek () throws HTMLParserException;
}
