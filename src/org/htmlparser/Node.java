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
//
// This interface was contributed by Joshua Kerievsky

package org.htmlparser;

import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public interface Node
{
    /**
     * Returns a string representation of the node. This is an important method, it allows a simple string transformation
     * of a web page, regardless of a node.<br>
     * Typical application code (for extracting only the text from a web page) would then be simplified to  :<br>
     * <pre>
     * Node node;
     * for (Enumeration e = parser.elements();e.hasMoreElements();) {
     *    node = (Node)e.nextElement();
     *    System.out.println(node.toPlainTextString()); // Or do whatever processing you wish with the plain text string
     * }
     * </pre>
     */
    public abstract String toPlainTextString();

    /**
     * This method will make it easier when using html parser to reproduce html pages (with or without modifications)
     * Applications reproducing html can use this method on nodes which are to be used or transferred as they were
     * recieved, with the original html
     */
    public abstract String toHtml();

    /**
     * Return the string representation of the node.
     * Subclasses must define this method, and this is typically to be used in the manner<br>
     * <pre>System.out.println(node)</pre>
     * @return java.lang.String
     */
    public abstract String toString();

    /**
     * Collect this node and its child nodes (if-applicable) into the collectionList parameter, provided the node
     * satisfies the filtering criteria.<P>
     *
     * This mechanism allows powerful filtering code to be written very easily,
     * without bothering about collection of embedded tags separately.
     * e.g. when we try to get all the links on a page, it is not possible to
     * get it at the top-level, as many tags (like form tags), can contain
     * links embedded in them. We could get the links out by checking if the
     * current node is a {@link CompositeTag}, and going through its children.
     * So this method provides a convenient way to do this.<P>
     *
     * Using collectInto(), programs get a lot shorter. Now, the code to
     * extract all links from a page would look like:
     * <pre>
     * NodeList collectionList = new NodeList();
     * NodeFilter filter = new TagNameFilter ("A");
     * for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
     *      e.nextNode().collectInto(collectionList, filter);
     * </pre>
     * Thus, collectionList will hold all the link nodes, irrespective of how
     * deep the links are embedded.<P>
     *
     * Another way to accomplish the same objective is:
     * <pre>
     * NodeList collectionList = new NodeList();
     * NodeFilter filter = new TagClassFilter (LinkTag.class);
     * for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
     *      e.nextNode().collectInto(collectionList, filter);
     * </pre>
     * This is slightly less specific because the LinkTag class may be
     * registered for more than one node name, e.g. &lt;LINK&gt; tags too.
     */
    public abstract void collectInto(NodeList collectionList, NodeFilter filter);

    /**
     * Returns the beginning position of the tag.
     * <br>deprecated Use {@link #getStartPosition}
     */
    public abstract int elementBegin();

    /**
     * Returns the ending position fo the tag
     * <br>deprecated Use {@link #getEndPosition}
     */
    public abstract int elementEnd();

    /**
     * Gets the starting position of the node.
     * @return The start position.
     */
    public abstract int getStartPosition ();

    /**
     * Sets the starting position of the node.
     * @param position The new start position.
     */
    public abstract void setStartPosition (int position);

    /**
     * Gets the ending position of the node.
     * @return The end position.
     */
    public abstract int getEndPosition ();

    /**
     * Sets the ending position of the node.
     * @param position The new end position.
     */
    public abstract void setEndPosition (int position);

    /**
     * Apply the visitor object (of type NodeVisitor) to this node.
     */
    public abstract void accept(Object visitor);

    /**
     * Get the parent of this node.
     * This will always return null when parsing without scanners,
     * i.e. if semantic parsing was not performed.
     * The object returned from this method can be safely cast to a <code>CompositeTag</code>.
     * @return The parent of this node, if it's been set, <code>null</code> otherwise.
     */
    public abstract Node getParent ();

    /**
     * Sets the parent of this node.
     * @param node The node that contains this node. Must be a <code>CompositeTag</code>.
     */
    public abstract void setParent (Node node);

    /**
     * Get the children of this node.
     * @return The list of children contained by this node, if it's been set, <code>null</code> otherwise.
     */
    public abstract NodeList getChildren ();

    /**
     * Set the children of this node.
     * @param children The new list of children this node contains.
     */
    public abstract void setChildren (NodeList children);

    /**
     * Returns the text of the node.
     */
    public String getText();

    /**
     * Sets the string contents of the node.
     * @param text The new text for the node.
     */
    public void setText(String text);

    /**
     * Perform the meaning of this tag.
     * This is defined by the tag, for example the bold tag &lt;B&gt; may switch
     * bold text on and off.
     * Only a few tags have semantic meaning to the parser. These have to do
     * with the character set to use (&lt;META&gt;), the base URL to use
     * (&lt;BASE&gt;). Other than that, the semantic meaning is up to the
     * application and it's custom nodes.
     */
    public void doSemanticAction () throws ParserException;
}
