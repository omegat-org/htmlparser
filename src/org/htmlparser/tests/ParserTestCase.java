// HTMLParser Library v1_4_20030907 - A java-based parser for HTML
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

package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;

import junit.framework.TestCase;

import org.htmlparser.AbstractNode;
import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.Parser;
import org.htmlparser.StringNode;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;

public class ParserTestCase extends TestCase {

    static boolean mCaseInsensitiveComparisons = true;
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
        node = new AbstractNode[40];
    }

    protected void createParser(String inputHTML,int numNodes) {
        String testHTML = new String(inputHTML);
        StringReader sr = new StringReader(testHTML);
        reader =  new NodeReader(new BufferedReader(sr),5000);
        parser = new Parser(reader,new DefaultParserFeedback());
        node = new AbstractNode[numNodes];
    }

    protected void createParser(String inputHTML, String url) {
        String testHTML = new String(inputHTML);
        StringReader sr = new StringReader(testHTML);
        reader =  new NodeReader(new BufferedReader(sr),url);
        parser = new Parser(reader,new DefaultParserFeedback());
        node = new AbstractNode[40];
    }

    protected void createParser(String inputHTML, String url,int numNodes) {
        String testHTML = new String(inputHTML);
        StringReader sr = new StringReader(testHTML);
        reader =  new NodeReader(new BufferedReader(sr),url);
        parser = new Parser(reader,new DefaultParserFeedback());
        node = new AbstractNode[numNodes];
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
                    (mCaseInsensitiveComparisons && Character.toUpperCase (actual.charAt(i)) != Character.toUpperCase (expected.charAt(i))) ||
                    (!mCaseInsensitiveComparisons && (actual.charAt(i) != expected.charAt(i)))
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
                    fail(errorMsg.toString());
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
        assertEquals("Number of nodes parsed didn't match, nodes found were :\n"+msg.toString(),nodeCountExpected,nodeCount);
    }

    public void parseAndAssertNodeCount(int nodeCountExpected) throws ParserException {
        parseNodes();
        assertNodeCount(nodeCountExpected);
    }

    public void assertSameType(String displayMessage, Node expected, Node actual) {
        String expectedNodeName = expected.getClass().getName();
        String actualNodeName = actual.getClass().getName();
        displayMessage =
            "The types did not match: Expected "+
            expectedNodeName+" \nbut was "+
            actualNodeName+"\nEXPECTED XML:"+expected.toHtml()+"\n"+
            "ACTUAL XML:"+actual.toHtml()+displayMessage;
        assertStringEquals(displayMessage, expectedNodeName, actualNodeName);
    }

    public void assertTagEquals(String displayMessage, Node expected, Node actual) {
        if (expected instanceof Tag) {
            Tag expectedTag = (Tag)expected;
            Tag actualTag   = (Tag)actual;
            assertTagNameMatches(displayMessage, expectedTag, actualTag);
            assertAttributesMatch(displayMessage, expectedTag, actualTag);
        }
    }

    private void assertTagNameMatches(
        String displayMessage,
        Tag nextExpectedTag,
        Tag nextActualTag) {
        String expectedTagName = nextExpectedTag.getTagName();
        String actualTagName = nextActualTag.getTagName();
        displayMessage = "The tag names did not match: Expected "+expectedTagName+" \nbut was "+actualTagName+displayMessage;
        assertStringEquals(displayMessage, expectedTagName, actualTagName);
    }

    public void assertXmlEquals(String displayMessage, String expected, String actual) throws Exception {
        expected = removeEscapeCharacters(expected);
        actual   = removeEscapeCharacters(actual);

        Parser expectedParser = Parser.createParser(expected);
        Parser resultParser   = Parser.createParser(actual);

        NodeIterator expectedIterator = expectedParser.elements();
        NodeIterator actualIterator =  resultParser.elements();
        displayMessage = createGenericFailureMessage(displayMessage, expected, actual);

        Node nextExpectedNode = null, nextActualNode = null;
        do {
            nextExpectedNode = getNextNodeUsing(expectedIterator);
            nextActualNode = getNextNodeUsing(actualIterator);

            assertStringValueMatches(
                displayMessage,
                nextExpectedNode,
                nextActualNode
            );
            fixIfXmlEndTag(resultParser, nextActualNode);
            fixIfXmlEndTag(expectedParser, nextExpectedNode);
            assertSameType(displayMessage, nextExpectedNode, nextActualNode);
            assertTagEquals(displayMessage, nextExpectedNode, nextActualNode);
        }
        while (expectedIterator.hasMoreNodes());
        assertActualXmlHasNoMoreNodes(displayMessage, actualIterator);
    }

    private Node getNextNodeUsing(NodeIterator nodeIterator)
        throws ParserException {
        Node nextNode;
        String text=null;
        do {
            nextNode = nodeIterator.nextNode();
            if (nextNode instanceof StringNode) {
                text = nextNode.toPlainTextString().trim();
            } else text = null;
        }
        while (text!=null && text.length()==0);
        return nextNode;
    }

    private void assertStringValueMatches(
        String displayMessage, Node expectedNode,Node actualNode) {

        String expected = expectedNode.toPlainTextString().trim();
        String actual = actualNode.toPlainTextString().trim();
        expected = expected.replace('\n', ' ');
        actual = actual.replace('\n',' ');
        displayMessage = "String value mismatch\nEXPECTED:"+expected+"\nACTUAL:"+actual+displayMessage;
        assertStringEquals(displayMessage,expected,actual);

    }

    private void assertActualXmlHasNoMoreNodes(
        String displayMessage,
        NodeIterator actualIterator)
        throws ParserException {
        if (actualIterator.hasMoreNodes()) {
            String extraTags = "\nExtra Tags\n**********\n";
            do {
                extraTags += actualIterator.nextNode().toHtml();
            }
            while (actualIterator.hasMoreNodes());

            displayMessage = "Actual had more data than expected\n"+extraTags+displayMessage;
            fail(displayMessage);
        }
    }

    private String createGenericFailureMessage(
        String displayMessage,
        String expected,
        String actual) {
        return "\n\n"+displayMessage+"\n\nComplete Xml\n************\nEXPECTED:\n"+expected+"\nACTUAL:\n"+actual;
    }

    private void fixIfXmlEndTag(Parser parser, Node node) {
        if (node instanceof Tag) {
            Tag tag = (Tag)node;
            if (tag.isEmptyXmlTag()) {
                // Add end tag
                String currLine = parser.getReader().getCurrentLine();
                int pos = parser.getReader().getLastReadPosition();
                currLine =
                    currLine.substring(0,pos+1)+
                    "</"+tag.getTagName()+">"+
                    currLine.substring(pos+1,currLine.length());
                parser.getReader().changeLine(currLine);
            }
        }
    }



    private void assertAttributesMatch(String displayMessage, Tag expectedTag, Tag actualTag) {
        assertAllExpectedTagAttributesFoundInActualTag(
            displayMessage,
            expectedTag,
            actualTag);
        if (expectedTag.getAttributes().size()!=actualTag.getAttributes().size()) {
            assertActualTagHasNoExtraAttributes(displayMessage, expectedTag, actualTag);
        }
    }

    private void assertActualTagHasNoExtraAttributes(String displayMessage, Tag expectedTag, Tag actualTag) {
        Iterator i = actualTag.getAttributes().keySet().iterator();
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
                assertStringEquals(displayMessage+"\ntag name",actualValue,expectedValue);
                continue;
            }

            if (expectedValue==null)
                fail(
                    "\nActual tag had extra key: "+key+displayMessage
                );
        }
    }

    private void assertAllExpectedTagAttributesFoundInActualTag(
        String displayMessage,
        Tag expectedTag,
        Tag actualTag) {
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
                assertStringEquals(displayMessage+"\ntag name",expectedValue,actualValue);
                continue;
            }

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
        inputString = inputString.replace ('\n',' ');
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
        assertStringEquals("Hidden Tag Contents", inputTagValue, inputTag.getAttribute("VALUE"));
        assertStringEquals("Hidden Tag Type", "hidden", inputTag.getAttribute("TYPE"));
    }

    protected void assertNodeCount(String message, int expectedLength, Node[] nodes) {
        if (expectedLength!=nodes.length) {
            StringBuffer failMsg = new StringBuffer(message);
            failMsg.append("\n");
            failMsg.append("Number of nodes expected ").append(expectedLength).append(" \n");
            failMsg.append("but was : ");
            failMsg.append(nodes.length).append("\n");
            failMsg.append("Nodes found are:\n");
            for (int i=0;i<nodes.length;i++) {
                failMsg.append("Node ").append(i).append(" : ");
                failMsg.append(nodes[i].getClass().getName()).append("\n");
                failMsg.append(nodes[i].toString()).append("\n\n");
            }
            fail(failMsg.toString());
        }
    }
}
