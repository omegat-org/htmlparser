package org.htmlparser.tests.utilTests;


import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLSimpleEnumeration;
import org.htmlparser.util.HTMLVector;
import org.htmlparser.visitors.HTMLVisitor;

public class HTMLVectorTest extends HTMLParserTestCase {
	private HTMLVector vector;
	private HTMLNode[] testNodes;
		
	public HTMLVectorTest(String name) {
		super(name);
	}
	
	public void testAddOneItem() {
		HTMLVector vector = new HTMLVector();
		HTMLNode node = createHTMLNodeObject();
		vector.add(node);
		assertEquals("Vector Size",1,vector.size());
		assertTrue("First Element",node==vector.elementAt(0));
	}

	public void testAddTwoItems() {
		HTMLVector vector = new HTMLVector();
		HTMLNode node1 = createHTMLNodeObject();
		HTMLNode node2 = createHTMLNodeObject();
		vector.add(node1);
		vector.add(node2);
		assertEquals("Vector Size",2,vector.size());
		assertTrue("First Element",node1==vector.elementAt(0));
		assertTrue("Second Element",node2==vector.elementAt(1));
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
		assertEquals("Number of Adjustments",1,vector.getNumberOfAdjustments());
	}
	
	public void testAddThirtyOneItems() {
		createTestDataAndPutInVector(31);
		assertTestDataCouldBeExtractedFromVector(31);
		assertEquals("Number of Adjustments",2,vector.getNumberOfAdjustments());
	}
	
	public void testAddFiftyItems() {
		createTestDataAndPutInVector(50);
		assertTestDataCouldBeExtractedFromVector(50);
		assertEquals("Number of Adjustments",2,vector.getNumberOfAdjustments());
	}
	
	public void testAddFiftyOneItems() {
		createTestDataAndPutInVector(51);
		assertTestDataCouldBeExtractedFromVector(51);
		assertEquals("Number of Adjustments",2,vector.getNumberOfAdjustments());
	}
	
	public void testAddTwoHundredItems() {
		createTestDataAndPutInVector(200);
		assertEquals("Number of Adjustments",4,vector.getNumberOfAdjustments());
	}
	
	public void testElements() throws Exception {
		createTestDataAndPutInVector(11);
		HTMLNode [] resultNodes = new HTMLNode[11];
		int i = 0;
		for (HTMLSimpleEnumeration e = vector.elements();e.hasMoreNodes();) {
			resultNodes[i] = e.nextHTMLNode();
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
		vector = new HTMLVector();
		testNodes = new HTMLNode[nodeCount]; 
		for (int i=0;i<nodeCount;i++) {
			testNodes[i]= createHTMLNodeObject();
			vector.add(testNodes[i]);
		}
	}

	private void assertTestDataCouldBeExtractedFromVector(int nodeCount) {
		for (int i=0;i<nodeCount;i++) {
			assertTrue("Element "+i+" did not match",testNodes[i]==vector.elementAt(i));
		}
	}
	
}
