// HTMLParser Library v1_4_20030810 - A java-based parser for HTML
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

package org.htmlparser.tests.utilTests;

import org.htmlparser.AbstractNode;
import org.htmlparser.Node;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

public class NodeListTest extends ParserTestCase {
	private NodeList nodeList;
	private Node[] testNodes;
		
	public NodeListTest(String name) {
		super(name);
	}

	protected void setUp() {
		nodeList = new NodeList();	
	}
	
	public void testAddOneItem() {
		Node node = createHTMLNodeObject();
		nodeList.add(node);
		assertEquals("Vector Size",1,nodeList.size());
		assertTrue("First Element",node==nodeList.elementAt(0));
	}

	public void testAddTwoItems() {
		Node node1 = createHTMLNodeObject();
		Node node2 = createHTMLNodeObject();
		nodeList.add(node1);
		nodeList.add(node2);
		assertEquals("Vector Size",2,nodeList.size());
		assertTrue("First Element",node1==nodeList.elementAt(0));
		assertTrue("Second Element",node2==nodeList.elementAt(1));
	}
	
	public void testAddTenItems() {
		createTestDataAndPutInVector(10);	
		assertTestDataCouldBeExtractedFromVector(10);
	}
	
	public void testAddElevenItems() {
		createTestDataAndPutInVector(11);
		assertTestDataCouldBeExtractedFromVector(11);
	}
	
	public void testAddThirtyItems() {
		createTestDataAndPutInVector(30);
		assertTestDataCouldBeExtractedFromVector(30);
		assertEquals("Number of Adjustments",1,nodeList.getNumberOfAdjustments());
	}
	
	public void testAddThirtyOneItems() {
		createTestDataAndPutInVector(31);
		assertTestDataCouldBeExtractedFromVector(31);
		assertEquals("Number of Adjustments",2,nodeList.getNumberOfAdjustments());
	}
	
	public void testAddFiftyItems() {
		createTestDataAndPutInVector(50);
		assertTestDataCouldBeExtractedFromVector(50);
		assertEquals("Number of Adjustments",2,nodeList.getNumberOfAdjustments());
	}
	
	public void testAddFiftyOneItems() {
		createTestDataAndPutInVector(51);
		assertTestDataCouldBeExtractedFromVector(51);
		assertEquals("Number of Adjustments",2,nodeList.getNumberOfAdjustments());
	}
	
	public void testAddTwoHundredItems() {
		createTestDataAndPutInVector(200);
		assertEquals("Number of Adjustments",4,nodeList.getNumberOfAdjustments());
	}
	
	public void testElements() throws Exception {
		createTestDataAndPutInVector(11);
		Node [] resultNodes = new AbstractNode[11];
		int i = 0;
		for (SimpleNodeIterator e = nodeList.elements();e.hasMoreNodes();) {
			resultNodes[i] = e.nextNode();
			assertTrue("Node "+i+" did not match",testNodes[i]==resultNodes[i]);
			i++;
		}
		
	}
	
	private Node createHTMLNodeObject() {
		Node node = new AbstractNode(10,20) {
			public void accept(Object visitor) {
			}

			public void collectInto(NodeList collectionList, String filter) {
			}
	
			public String toHtml() {
				return null;
			}
	
			public String toPlainTextString() {
				return null;
			}
	
			public String toString() {
				return "";
			}
		};
		return node;
	}

	private void createTestDataAndPutInVector(int nodeCount) {
		testNodes = new AbstractNode[nodeCount]; 
		for (int i=0;i<nodeCount;i++) {
			testNodes[i]= createHTMLNodeObject();
			nodeList.add(testNodes[i]);
		}
	}

	private void assertTestDataCouldBeExtractedFromVector(int nodeCount) {
		for (int i=0;i<nodeCount;i++) {
			assertTrue("Element "+i+" did not match",testNodes[i]==nodeList.elementAt(i));
		}
	}
	
	public void testToNodeArray() {
		createTestDataAndPutInVector(387);
		Node nodes [] = nodeList.toNodeArray();
		assertEquals("Length of array",387,nodes.length);
		for (int i=0;i<nodes.length;i++)
			assertNotNull("node "+i+" should not be null",nodes[i]);
	}
	
	public void testRemove() {
		Node node1 = createHTMLNodeObject();
		Node node2 = createHTMLNodeObject();
		nodeList.add(node1);
		nodeList.add(node2);
		assertEquals("Vector Size",2,nodeList.size());
		assertTrue("First Element",node1==nodeList.elementAt(0));
		assertTrue("Second Element",node2==nodeList.elementAt(1));
		nodeList.remove(1);
		assertEquals("List Size",1,nodeList.size());
		assertTrue("First Element",node1==nodeList.elementAt(0));
	}
	
	public void testRemoveAll() {
		Node node1 = createHTMLNodeObject();
		Node node2 = createHTMLNodeObject();
		nodeList.add(node1);
		nodeList.add(node2);
		assertEquals("Vector Size",2,nodeList.size());
		assertTrue("First Element",node1==nodeList.elementAt(0));
		assertTrue("Second Element",node2==nodeList.elementAt(1));
		nodeList.removeAll();
		assertEquals("List Size",0,nodeList.size());
		assertTrue("First Element",null==nodeList.elementAt(0));
		assertTrue("Second Element",null==nodeList.elementAt(1));
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {NodeListTest.class.getName()});
	}

}
