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
import org.htmlparser.*;
import org.htmlparser.AbstractNode;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;
import org.htmlparser.visitors.NodeVisitor;

/*
 * The base class for tags that have an end tag.
 * Provided extra accessors for the children above and beyond what the basic
 * {@link Tag} provides. Also handles the conversion of it's children for
 * the {@link #toHtml toHtml} method.
 */
public abstract class CompositeTag extends Tag {
    protected TagNode mEndTag;

    public CompositeTag ()
    {
    }
    
    /**
     * Get an iterator over the children of this node.
     * @return Am iterator over the children of this node.
     */
    public SimpleNodeIterator children ()
    {
        SimpleNodeIterator ret;

        if (null != getChildren ())
            ret = getChildren ().elements ();
        else
            ret = (new NodeList ()).elements ();

        return (ret);
    }

    /**
     * Get the child of this node at the given position.
     * @param index The in the node list of the child.
     * @return The child at that index.
     */
    public Node getChild (int index)
    {
        return (getChildren ().elementAt (index));
    }

    /**
     * Get the children as an array of <code>Node</code> objects.
     * @return The children in an array.
     */
    public Node [] getChildrenAsNodeArray ()
    {
        return (getChildren ().toNodeArray ());
    }

    /**
     * Remove the child at the position given.
     * @param i The index of the child to remove.
     */
    public void removeChild (int i)
    {
        getChildren ().remove (i);
    }

    /**
     * Return the child tags as an iterator.
     * Equivalent to calling getChildren ().elements ().
     * @return An iterator over the children.
     */
    public SimpleNodeIterator elements()
    {
        return (getChildren ().elements ());
    }

    public String toPlainTextString() {
        StringBuffer stringRepresentation = new StringBuffer();
        for (SimpleNodeIterator e=children();e.hasMoreNodes();) {
            stringRepresentation.append(e.nextNode().toPlainTextString());
        }
        return stringRepresentation.toString();
    }

    protected void putChildrenInto(StringBuffer sb)
    {
        Node node;
        for (SimpleNodeIterator e = children (); e.hasMoreNodes ();)
        {
            node = e.nextNode ();
            // eliminate virtual tags
//            if (!(node.getStartPosition () == node.getEndPosition ()))
                sb.append (node.toHtml ());
        }
    }

    protected void putEndTagInto(StringBuffer sb)
    {
        // eliminate virtual tags
//        if (!(endTag.getStartPosition () == endTag.getEndPosition ()))
            sb.append(getEndTag ().toHtml());
    }

    public String toHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append (super.toHtml ());
        if (!isEmptyXmlTag())
        {
            putChildrenInto(sb);
            if (null != getEndTag ()) // this test if for link tags that refuse to scan because there's no HREF attribute
                putEndTagInto(sb);
        }
        return sb.toString();
    }

    /**
     * Searches all children who for a name attribute. Returns first match.
     * @param name Attribute to match in tag
     * @return Tag Tag matching the name attribute
     */
    public Tag searchByName(String name) {
        Node node;
        Tag tag=null;
        boolean found = false;
        for (SimpleNodeIterator e = children();e.hasMoreNodes() && !found;) {
            node = (Node)e.nextNode();
            if (node instanceof TagNode) {
                tag = (Tag)node;
                String nameAttribute = tag.getAttribute("NAME");
                if (nameAttribute!=null && nameAttribute.equals(name))
                    found=true;
            }
        }
        if (found)
            return tag;
        else
            return null;
    }

    /**
     * Searches for any node whose text representation contains the search
     * string. Collects all such nodes in a NodeList.
     * e.g. if you wish to find any textareas in a form tag containing "hello
     * world", the code would be :
     * <code>
     *  NodeList nodeList = formTag.searchFor("Hello World");
     * </code>
     * @param searchString search criterion
     * @param caseSensitive specify whether this search should be case
     * sensitive
     * @return NodeList Collection of nodes whose string contents or
     * representation have the searchString in them
     */

    public NodeList searchFor(String searchString, boolean caseSensitive) {
        NodeList foundList = new NodeList();
        Node node;
        if (!caseSensitive) searchString = searchString.toUpperCase();
        for (SimpleNodeIterator e = children();e.hasMoreNodes();) {
            node = e.nextNode();
            String nodeTextString = node.toPlainTextString();
            if (!caseSensitive) nodeTextString=nodeTextString.toUpperCase();
            if (nodeTextString.indexOf(searchString)!=-1) {
                foundList.add(node);
            }
        }
        return foundList;
    }

    /**
     * Collect all objects that are of a certain type
     * Note that this will not check for parent types, and will not
     * recurse through child tags
     * @param classType
     * @return NodeList
     */
    public NodeList searchFor(Class classType)
    {
        return (getChildren ().searchFor (classType));
    }
    /**
     * Searches for any node whose text representation contains the search
     * string. Collects all such nodes in a NodeList.
     * e.g. if you wish to find any textareas in a form tag containing "hello
     * world", the code would be :
     * <code>
     *  NodeList nodeList = formTag.searchFor("Hello World");
     * </code>
     * This search is <b>case-insensitive</b>.
     * @param searchString search criterion
     * @return NodeList Collection of nodes whose string contents or
     * representation have the searchString in them
     */
    public NodeList searchFor(String searchString) {
        return searchFor(searchString, false);
    }

    /**
     * Returns the node number of the string node containing the
     * given text. This can be useful to index into the composite tag
     * and get other children.
     * @param text
     * @return int
     */
    public int findPositionOf(String text) {
        Node node;
        int loc = 0;
        for (SimpleNodeIterator e=children();e.hasMoreNodes();) {
            node = e.nextNode();
            if (node.toPlainTextString().toUpperCase().indexOf(text.toUpperCase())!=-1) {
                return loc;
            }
            loc++;
        }
        return -1;
    }

    /**
     * Returns the node number of a child node given the node object.
     * This would typically be used in conjuction with digUpStringNode,
     * after which the string node's parent can be used to find the
     * string node's position. Faster than calling findPositionOf(text)
     * again. Note that the position is at a linear level alone - there
     * is no recursion in this method.
     * @param searchNode The child node to find.
     * @return The offset of the child tag or -1 if it was not found.
     */
    public int findPositionOf(Node searchNode) {
        Node node;
        int loc = 0;
        for (SimpleNodeIterator e=children();e.hasMoreNodes();) {
            node = e.nextNode();
            if (node==searchNode) {
                return loc;
            }
            loc++;
        }
        return -1;
    }

    /**
     * Get child at given index
     * @param index
     * @return Node
     */
    public Node childAt(int index) {
        return (getChildren ().elementAt (index));
    }

    public void collectInto (NodeList collectionList, String filter)
    {
        Node node;

        super.collectInto (collectionList, filter);
        for (SimpleNodeIterator e = children(); e.hasMoreNodes ();)
        {
            node = e.nextNode ();
            node.collectInto (collectionList, filter);
        }
    }

    public void collectInto (NodeList collectionList, Class nodeType)
    {
        Node node;

        super.collectInto (collectionList,nodeType);
        for (SimpleNodeIterator e = children(); e.hasMoreNodes (); )
        {
            node = e.nextNode ();
            node.collectInto (collectionList, nodeType);
        }
    }

    public String getChildrenHTML() {
        StringBuffer buff = new StringBuffer();
        for (SimpleNodeIterator e = children();e.hasMoreNodes();) {
            AbstractNode node = (AbstractNode)e.nextNode();
            buff.append(node.toHtml());
        }
        return buff.toString();
    }

    /**
     * Tag visiting code.
     * Invokes <code>accept()</code> on the start tag and then
     * walks the child list invoking <code>accept()</code> on each
     * of the children, finishing up with an <code>accept()</code>
     * call on the end tag. If <code>shouldRecurseSelf()</code>
     * returns true it then asks the visitor to visit itself.
     * @param visitor The <code>NodeVisitor</code> object to be signalled
     * for each child and possibly this tag.
     */
    public void accept (NodeVisitor visitor)
    {
        SimpleNodeIterator children;
        Node child;

        if (visitor.shouldRecurseSelf ())
            visitor.visitTag (this);
        if (visitor.shouldRecurseChildren ())
        {
            if (null != getChildren ())
            {
                children = children ();
                while (children.hasMoreNodes ())
                {
                    child = (Node)children.nextNode ();
                    child.accept (visitor);
                }
            }
            if (null != getEndTag ())
                getEndTag ().accept (visitor);
        }
    }

    public int getChildCount() {
        return (getChildren ().size ());
    }

    /**
     * @deprecated The tag *is* ths start tag.
     */
    public TagNode getStartTag()
    {
        return (this);
    }

    /**
     * @deprecated The tag *is* ths start tag.
     */
    public void setStartTag (TagNode start)
    {
        if (null != start)
            throw new IllegalStateException ("the tag *is* ths start tag");
    }

    public TagNode getEndTag()
    {
        return (mEndTag);
    }

    public void setEndTag(TagNode end)
    {
        mEndTag = end;
    }

    /**
     * Finds a string node, however embedded it might be, and returns
     * it. The string node will retain links to its parents, so
     * further navigation is possible.
     * @param searchText
     * @return The list of string nodes (recursively) found.
     */
    public StringNode [] digupStringNode(String searchText) {
        NodeList nodeList = searchFor(searchText);
        NodeList stringNodes = new NodeList();
        for (int i=0;i<nodeList.size();i++) {
            Node node = nodeList.elementAt(i);
            if (node instanceof StringNode) {
                stringNodes.add(node);
            } else {
                if (node instanceof CompositeTag) {
                    CompositeTag ctag = (CompositeTag)node;
                    StringNode [] nodes = ctag.digupStringNode(searchText);
                    for (int j=0;j<nodes.length;j++)
                        stringNodes.add(nodes[j]);
                }
            }
        }
        StringNode [] stringNode = new StringNode[stringNodes.size()];
        for (int i=0;i<stringNode.length;i++) {
            stringNode[i] = (StringNode)stringNodes.elementAt(i);
        }
        return stringNode;
    }
}
