package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;

import junit.framework.TestCase;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.Parser;
import org.htmlparser.StringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

public class ParserTestCase extends TestCase {
	protected Parser parser;
	protected Node node [];
	protected int nodeCount;
	protected NodeReader reader;
	
	public ParserTestCase(String name) {
		super(name);
	}

	protected void parse(String response) throws ParserException {
		createParser(response,10000);
		parser.registerScanners();
		parseNodes();
	}

	protected void createParser(String inputHTML) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new NodeReader(new BufferedReader(sr),5000);
		parser = new Parser(reader,new DefaultParserFeedback());
		node = new Node[40];
	}

	protected void createParser(String inputHTML,int numNodes) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new NodeReader(new BufferedReader(sr),5000);
		parser = new Parser(reader,new DefaultParserFeedback());
		node = new Node[numNodes];
	}

	protected void createParser(String inputHTML, String url) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new NodeReader(new BufferedReader(sr),url);
		parser = new Parser(reader,new DefaultParserFeedback());
		node = new Node[40];
	}

	protected void createParser(String inputHTML, String url,int numNodes) {
		String testHTML = new String(inputHTML);
		StringReader sr = new StringReader(testHTML);
		reader =  new NodeReader(new BufferedReader(sr),url);
		parser = new Parser(reader,new DefaultParserFeedback());
		node = new Node[numNodes];
	}
	
	public void assertStringEquals(String message, String expected, 
									  String actual) {
		String mismatchInfo = "";

		if (expected.length() < actual.length()) {
			mismatchInfo = "\n\nACTUAL result has "+(actual.length()-expected.length())+" extra characters at the end. They are :";

			for (int i = expected.length(); i < actual.length(); i++) {
				mismatchInfo += ("\nPosition : " + i + " , Code = " + (int) actual.charAt(i));
			}
		} else if(expected.length() > actual.length()) {
			mismatchInfo = "\n\nEXPECTED result has "+(expected.length()-actual.length())+" extra characters at the end. They are :";

			for (int i = actual.length(); i < expected.length(); i++) {
				mismatchInfo += ("\nPosition : " + i + " , Code = " + (int) expected.charAt(i));
			}
        	
		}
		for (int i = 0; i < expected.length(); i++) {      	
			if (
					(expected.length() != actual.length() && 
						(
							i >= (expected.length()-1 ) || 
							i >= (actual.length()-1 )
						)
					) || 
					(actual.charAt(i) != expected.charAt(i))
				) {
					StringBuffer errorMsg = new StringBuffer();
					errorMsg.append(
					 	message +mismatchInfo + " \nMismatch of strings at char posn " + i + 
					   	" \n\nString Expected upto mismatch = " + 
					   	expected.substring(0, i) + 
					   	" \n\nString Actual upto mismatch = " + 
					   	actual.substring(0, i)
					);
					if (i<expected.length())			   	
					   errorMsg.append(
							" \n\nString Expected MISMATCH CHARACTER = "+ 
					   		expected.charAt(i) + ", code = " +
							(int) expected.charAt(i)
						); 

					if (i<actual.length())
						errorMsg.append(
					   		" \n\nString Actual MISMATCH CHARACTER = " + 
					   		actual.charAt(i) + ", code = " + 
					   		(int) actual.charAt(i)
					   	); 
					   
					errorMsg.append(
						" \n\n**** COMPLETE STRING EXPECTED ****\n" + 
					   	expected + 
					   	" \n\n**** COMPLETE STRING ACTUAL***\n" + actual
					);
					assertTrue(errorMsg.toString(),false);
			}
        	
		}
	}   

	public void parseNodes() throws ParserException{
		nodeCount = 0;
		for (NodeIterator e = parser.elements();e.hasMoreNodes();)
		{
			node[nodeCount++] = e.nextNode();
		}	
	}

	public void assertNodeCount(int nodeCountExpected) {
		StringBuffer msg = new StringBuffer();
		for (int i=0;i<nodeCount;i++) {
			msg.append(node[i].getClass().getName());
			msg.append("-->\n").append(node[i].toHtml()).append("\n");
		}
		assertEquals("Number of nodes parsed didnt match, nodes found were :\n"+msg.toString(),nodeCountExpected,nodeCount);
	}

	public void parseAndAssertNodeCount(int nodeCountExpected) throws ParserException {
		parseNodes();
		assertNodeCount(nodeCountExpected);
	}

	public void assertXMLEquals(String displayMessage, String expected, String result) throws Exception {
		displayMessage = "\n\n"+displayMessage+
		"\n\nExpected XML:\n"+expected+
		"\n\nActual XML:\n"+result;
		expected = removeEscapeCharacters(expected);
		result   = removeEscapeCharacters(result);
		Parser expectedParser = Parser.createParser(expected);
		Parser resultParser   = Parser.createParser(result);
		Node expectedNode, actualNode;
		NodeIterator actualEnumeration = resultParser.elements();
		int cnt=0;
		Node prevNode=null;
		for (NodeIterator e = expectedParser.elements();e.hasMoreNodes();) {
			expectedNode = e.nextNode();
			actualNode   = actualEnumeration.nextNode();
			cnt++;
			String expectedNodeHtml="null", actualNodeHtml="null";
			String expectedNodeName="null", actualNodeName="null";
			if (expectedNode!=null) {
				expectedNodeHtml = expectedNode.toHtml();
				expectedNodeName = expectedNode.getClass().getName();
			}
			if (actualNode!=null) {
				actualNodeHtml = actualNode.toHtml();
				actualNodeName = actualNode.getClass().getName();
			}
			String prevNodeHtml="null";
			if (prevNode!=null)
				prevNodeHtml = prevNode.toHtml();
		
			System.out.println("Matching: \n" +				"expectedNode = "+expectedNodeHtml+"\n" +				"actualNode = "+actualNodeHtml);
			assertEquals(
				"the two nodes should be the same type\n"+
				"expected node:"+expectedNodeHtml+"\n"+
				"  actual node:"+actualNodeHtml+"\n"+
				"comparison no: "+cnt+"\n"+
				"expected node type: "+expectedNodeName+"\n"+
				"actual node type  : "+actualNodeName+"\n"+
				"previous matched node : "+prevNodeHtml+
				displayMessage,
				expectedNodeName,
				actualNodeName
			);
			assertTagEquals(
				displayMessage+"\n"+
				"previous matched node : "+prevNodeHtml,
				expectedNode,
				actualNode,
				actualEnumeration
			);
			assertStringNodeEquals(
				displayMessage, 
				expectedNode, 
				actualNode
			);			
			prevNode = actualNode;
			
		}
	}

	private void assertStringNodeEquals(
		String displayMessage,
		Node expectedNode,
		Node actualNode) {
		if (expectedNode instanceof StringNode) {
			StringNode expectedString = 
				(StringNode)expectedNode;
			StringNode actualString = 
				(StringNode)actualNode;
			assertStringEquals(
				displayMessage,
				expectedString.getText(), 
				actualString.getText()
			);
		}
	}

	private void assertTagEquals(
		String displayMessage,
		Node expectedNode,
		Node actualNode,
		NodeIterator actualEnumeration)
		throws ParserException {
		
		if (expectedNode instanceof Tag) {
			Tag expectedTag = (Tag)expectedNode;
			Tag actualTag   = (Tag)actualNode;
			if (isTagAnXmlEndTag(expectedTag)) {
				if (!isTagAnXmlEndTag(actualTag)) {
					assertTagEquals(displayMessage, expectedTag, actualTag);
					Node tempNode =
						actualEnumeration.nextNode();
					assertTrue(
						"should be an end tag but was "+
						tempNode.getClass().getName(),
						tempNode instanceof EndTag
					);
					actualTag = (EndTag)tempNode;
					String expectedTagName = ParserUtils.removeChars(
						expectedTag.getTagName(),'/'
					);
					assertEquals(
						"expected end tag",
						expectedTagName,
						actualTag.getTagName()
					);
					
				}
			} else
			assertTagEquals(displayMessage, expectedTag, actualTag);
		}
	}

	private boolean isTagAnXmlEndTag(Tag expectedTag) {
		return expectedTag.getText().lastIndexOf('/')==expectedTag.getText().length()-1;
	}


	private void assertTagEquals(String displayMessage, Tag expectedTag, Tag actualTag) {
		Iterator i = expectedTag.getAttributes().keySet().iterator();
		while (i.hasNext()) {
			String key = (String)i.next();
			if (key=="/") continue;
			String expectedValue = 
				expectedTag.getAttribute(key);
			String actualValue =
				actualTag.getAttribute(key);
			if (key==Tag.TAGNAME) {
				expectedValue = ParserUtils.removeChars(expectedValue,'/');
				actualValue = ParserUtils.removeChars(actualValue,'/');
				assertEquals(displayMessage+"\ntag name",expectedValue,actualValue);
				continue;
			}
				
			String expectedHTML = expectedTag.toHtml();
			assertStringEquals(
				"\nvalue for key "+key+" in tag "+expectedTag.getTagName()+" expected="+expectedValue+" but was "+actualValue+
				"\n\nComplete Tag expected:\n"+expectedTag.toHtml()+
				"\n\nComplete Tag actual:\n"+actualTag.toHtml()+
				displayMessage,
				expectedValue,
				actualValue
			);
								
		}
	}

	public static String removeEscapeCharacters(String inputString) {
		inputString = ParserUtils.removeChars(inputString,'\r');
		inputString = ParserUtils.removeChars(inputString,'\n');
		inputString = ParserUtils.removeChars(inputString,'\t');
		return inputString;
	}

	public void assertType(
		String message, 
		Class expectedType, 
		Object object) {
		String expectedTypeName = expectedType.getName();
		String actualTypeName   = object.getClass().getName();
		if (!actualTypeName.equals(expectedTypeName)) {
			fail(
				message+" should have been of type\n"+
				expectedTypeName+
				" but was of type \n"+
				actualTypeName+"\n and is :"+((Node)object).toHtml()
			);
		} 
	}

	protected void assertHiddenIDTagPresent(FormTag formTag, String name, String inputTagValue) {
	    InputTag inputTag = formTag.getInputTag(name);
	    assertNotNull("Hidden Tag "+name+" should have been there", inputTag);
	    assertEquals("Hidden Tag Contents", inputTagValue, inputTag.getAttribute("VALUE"));
	    assertEquals("Hidden Tag Type", "hidden", inputTag.getAttribute("TYPE"));
	}	
}
