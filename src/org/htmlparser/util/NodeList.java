// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import org.htmlparser.HTMLNode;

public class NodeList implements Serializable {
	private static final int INITIAL_CAPACITY=10;
	//private static final int CAPACITY_INCREMENT=20;
	private HTMLNode nodeData[];
	private int size;
	private int capacity;
	private int capacityIncrement;
	private int numberOfAdjustments;
	
	public NodeList() {
		size = 0;
		capacity = INITIAL_CAPACITY;
		nodeData = new HTMLNode[capacity];
		capacityIncrement = capacity*2;
		numberOfAdjustments = 0;
	}
	
	public void add(HTMLNode node) {
		if (size==capacity) 
			adjustVectorCapacity();
		nodeData[size++]=node;
	}

	private void adjustVectorCapacity() {
		capacity += capacityIncrement;
		capacityIncrement *= 2;
		HTMLNode oldData [] = nodeData;
		nodeData = new HTMLNode[capacity];
		System.arraycopy(oldData, 0, nodeData, 0, size);
		numberOfAdjustments++;
	}
	
	public int size() {
		return size;
	}
	
	public HTMLNode elementAt(int i) {
		return nodeData[i];
	}

	public int getNumberOfAdjustments() {
		return numberOfAdjustments;
	}
	
	public SimpleEnumeration elements() {
		return new SimpleEnumeration() {
			int count = 0;
	
			public boolean hasMoreNodes() {
				return count < size;
			}
	
			public HTMLNode nextNode() {
			synchronized (NodeList.this) {
				if (count < size) {
				return nodeData[count++];
				}
			}
			throw new NoSuchElementException("Vector Enumeration");
			}
		};
	}
	
	public HTMLNode [] toNodeArray() {
		HTMLNode [] nodeArray = new HTMLNode[size];
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
			buff.append(nodeData[i].toHTML());	
		return buff.toString();
	}
	
	
}
