package org.htmlparser.util;

import java.util.NoSuchElementException;

import org.htmlparser.HTMLNode;

public class HTMLVector {
	private static final int INITIAL_CAPACITY=10;
	//private static final int CAPACITY_INCREMENT=20;
	private HTMLNode nodeData[];
	private int size;
	private int capacity;
	private int capacityIncrement;
	private int numberOfAdjustments;
	
	public HTMLVector() {
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
	public HTMLEnumeration elements() {
		return new HTMLEnumeration() {
			int count = 0;
	
			public boolean hasMoreNodes() {
				return count < size;
			}
	
			public HTMLNode nextHTMLNode() {
			synchronized (HTMLVector.this) {
				if (count < size) {
				return nodeData[count++];
				}
			}
			throw new NoSuchElementException("Vector Enumeration");
			}
		};
	}
}
