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

package org.htmlparser.util;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.htmlparser.Node;

public class NodeList implements Serializable {
    private static final int INITIAL_CAPACITY=10;
    //private static final int CAPACITY_INCREMENT=20;
    private Node nodeData[];
    private int size;
    private int capacity;
    private int capacityIncrement;
    private int numberOfAdjustments;

    public NodeList() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        nodeData = newNodeArrayFor(capacity);
        capacityIncrement = capacity*2;
        numberOfAdjustments = 0;
    }

    /**
     * Create a one element node list.
     * @param node The initial node to add.
     */
    public NodeList(Node node)
    {
        this ();
        add (node);
    }
        
    public void add(Node node) {
        if (size==capacity)
            adjustVectorCapacity();
        nodeData[size++]=node;
    }

    /**
     * Add another node list to this one.
     * @param list The list to add.
     */
    public void add (NodeList list)
    {
        for (int i = 0; i < list.size; i++)
            add (list.nodeData[i]);
    }

    /**
     * Insert the given node at the head of the list.
     * @param node The new first element.
     */
    public void prepend(Node node)
    {
        if (size==capacity)
            adjustVectorCapacity();
        System.arraycopy (nodeData, 0, nodeData, 1, size);
        size++;
        nodeData[0]=node;
    }

    private void adjustVectorCapacity() {
        capacity += capacityIncrement;
        capacityIncrement *= 2;
        Node oldData [] = nodeData;
        nodeData = newNodeArrayFor(capacity);
        System.arraycopy(oldData, 0, nodeData, 0, size);
        numberOfAdjustments++;
    }

    private Node[] newNodeArrayFor(int capacity) {
        return new Node[capacity];
    }

    public int size() {
        return size;
    }

    public Node elementAt(int i) {
        return nodeData[i];
    }

    public int getNumberOfAdjustments() {
        return numberOfAdjustments;
    }

    public SimpleNodeIterator elements() {
        return new SimpleNodeIterator() {
            int count = 0;

            public boolean hasMoreNodes() {
                return count < size;
            }

            public Node nextNode() {
            synchronized (NodeList.this) {
                if (count < size) {
                return nodeData[count++];
                }
            }
            throw new NoSuchElementException("Vector Enumeration");
            }
        };
    }

    public Node [] toNodeArray() {
        Node [] nodeArray = newNodeArrayFor(size);
        System.arraycopy(nodeData, 0, nodeArray, 0, size);
        return nodeArray;
    }

    public String asString() {
        StringBuffer buff = new StringBuffer();
        for (int i=0;i<size;i++)
            buff.append(nodeData[i].toPlainTextString());
        return buff.toString();
    }

    public String asHtml() {
        StringBuffer buff = new StringBuffer();
        for (int i=0;i<size;i++)
            buff.append(nodeData[i].toHtml());
        return buff.toString();
    }

    public void remove(int index) {
        System.arraycopy(nodeData, index+1, nodeData, index, size-index-1);
        size--;
    }

    public void removeAll() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        nodeData = newNodeArrayFor(capacity);
        capacityIncrement = capacity*2;
        numberOfAdjustments = 0;
    }

    public String toString() {
        StringBuffer text = new StringBuffer();
        for (int i=0;i<size;i++)
            text.append(nodeData[i].toPlainTextString());
        return text.toString();
    }

    /**
     * Search for nodes of the given type non-recursively.
     * @param classType The class to search for.
     */
    public NodeList searchFor (Class classType)
    {
        return (searchFor (classType, false));
    }

    /**
     * Search for nodes of the given type recursively.
     * @param classType The class to search for.
     * @param recursive If <code>true<code> digs into the children recursively.
     */
    public NodeList searchFor (Class classType, boolean recursive)
    {
        String name;
        Node node;
        NodeList children;
        NodeList ret;

        ret = new NodeList ();
        name = classType.getName ();
        for (int i = 0; i < size; i++)
        {
            node = nodeData[i];
            if (node.getClass ().getName ().equals (name))
                ret.add (node);
            if (recursive)
            {
                children = node.getChildren ();
                if (null != children)
                    ret.add (children.searchFor (classType, recursive));
            }
        }

        return (ret);
    }
}
