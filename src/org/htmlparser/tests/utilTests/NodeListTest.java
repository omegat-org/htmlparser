package org.htmlparser.tests.utilTests;


import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.SimpleEnumeration;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HTMLVisitor;

public class NodeListTest extends HTMLParserTestCase {
	private NodeList nodeList;
	private HTMLNode[] testNodes;
		
	public NodeListTest(String name) {
		super(name);
	}

	protected void setUp() {
		nodeList = new NodeList();	
	}
	
	public void testAddOneItem() {
		HTMLNode node = createHTMLNodeObject();
		nodeList.add(node);
		assertEquals("Vector Size",1,nodeList.size());
		assertTrue("First Element",node==nodeList.elementAt(0));
	}

	public void testAddTwoItems() {
		HTMLNode node1 = createHTMLNodeObject();
		HTMLNode node2 = createHTMLNodeObject();
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
		HTMLNode [] resultNodes = new HTMLNode[11];
		int i = 0;
		for (SimpleEnumeration e = nodeList.elements();e.hasMoreNodes();) {
			resultNodes[i] = e.nextNode();
			assertTrue("Node "+i+" did not match",testNodes[i]==resultNodes[i]);
			i++;
		}
		
	}
	
	private HTMLNode createHTMLNodeObject() {
		HTMLNode node = new HTMLNode(10,20) {
			public void accept(HTMLVisitor visitor) {
			}

			public void collectInto(Vector collectionVector, String filter) {
			}
	
			public String toHTML() {
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
		testNodes = new HTMLNode[nodeCount]; 
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
		HTMLNode nodes [] = nodeList.toNodeArray();
		assertEquals("Length of array",387,nodes.length);
		for (int i=0;i<nodes.length;i++)
			assertNotNull("node "+i+" should not be null",nodes[i]);
	}
	
}