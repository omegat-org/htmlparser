// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import junit.framework.TestCase;

import org.htmlparser.AbstractNode;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.StringNode;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
import org.htmlparser.util.SpecialHashtable;

public class ParserTestCase extends TestCase {

    static boolean mCaseInsensitiveComparisons = false;
    protected Parser parser;
    protected Node node [];
    protected int nodeCount;
    protected Lexer mLexer;

    public ParserTestCase(String name) {
        super(name);
    }

    protected void parse(String response) throws ParserException {
        createParser(response,10000);
        parseNodes();
    }

    protected void createParser(String inputHTML) {
        mLexer =  new Lexer (new Page (inputHTML));
        parser = new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
        node = new AbstractNode[40];
    }

    protected void createParser(String inputHTML,int numNodes)
    {
        Lexer lexer = new Lexer (inputHTML);
        parser = new Parser (lexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
        node = new AbstractNode[numNodes];
    }

    protected void createParser(String inputHTML, String url) {
        Lexer lexer = new Lexer (inputHTML);
        lexer.getPage ().setUrl (url);
        parser = new Parser (lexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
        node = new AbstractNode[40];
    }

    protected void createParser(String inputHTML, String url,int numNodes) {
        Lexer lexer = new Lexer (inputHTML);
        lexer.getPage ().setUrl (url);
        parser = new Parser (lexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
        node = new AbstractNode[numNodes];
    }

    public Parser getParser ()
    {
        return (parser);
    }

    public void setParser (Parser parser)
    {
        this.parser = parser;
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
                    System.out.println ("string differs, expected \"" + expected + "\", actual \"" + actual + "\"");
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
        if (nodeCountExpected != nodeCount)
            System.out.println ("node count differs, expected " + nodeCountExpected + ", actual " + nodeCount);
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

    public void assertXmlEquals(String displayMessage, String expected, String actual) throws Exception
    {
        Node nextExpectedNode;
        Node nextActualNode;
        Tag tag1;
        Tag tag2;

        expected = removeEscapeCharacters(expected);
        actual   = removeEscapeCharacters(actual);

        Parser expectedParser = Parser.createParser(expected);
        Parser resultParser   = Parser.createParser(actual);

        NodeIterator expectedIterator = expectedParser.elements();
        NodeIterator actualIterator =  resultParser.elements();
        displayMessage = createGenericFailureMessage(displayMessage, expected, actual);

        nextExpectedNode = null;
        nextActualNode = null;
        tag1 = null;
        tag2 = null;
        do {
            if (null != tag1)
                nextExpectedNode = tag1;
            else
                nextExpectedNode = getNextNodeUsing (expectedIterator);
            if (null != tag2)
                nextActualNode = tag2;
            else
                nextActualNode = getNextNodeUsing (actualIterator);
            assertNotNull (nextActualNode);
            tag1 = fixIfXmlEndTag (nextExpectedNode);
            tag2 = fixIfXmlEndTag (nextActualNode);
            assertStringValueMatches(
                displayMessage,
                nextExpectedNode,
                nextActualNode
            );
            assertSameType(displayMessage, nextExpectedNode, nextActualNode);
            assertTagEquals(displayMessage, nextExpectedNode, nextActualNode);
        }
        while (expectedIterator.hasMoreNodes() || (null != tag1));
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

    /**
     * Return a following tag if node is an empty XML tag.
     */
    private Tag fixIfXmlEndTag (Node node)
    {
        Tag ret;

        ret = null;
        if (node instanceof Tag)
        {
            Tag tag = (Tag)node;
            if (tag.isEmptyXmlTag())
            {
                tag.setEmptyXmlTag (false);
                ret = new Tag (tag.getPage (), tag.getStartPosition (), tag.getEndPosition (), tag.getAttributesEx ());
            }
        }
        
        return (ret);
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
            if (key==SpecialHashtable.TAGNAME) {
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
            if (key.trim().equals ("/")) continue;
            String expectedValue =
                expectedTag.getAttribute(key);
            String actualValue =
                actualTag.getAttribute(key);
            if (key==SpecialHashtable.TAGNAME) {
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

    public void assertSuperType(
        String message,
        Class expectedType,
        Object object)
    {   
        String expectedTypeName = expectedType.getName();
        String actualTypeName   = object.getClass().getName();
        if (!expectedType.isAssignableFrom (object.getClass ()))
            fail(
                message+" should have been of type\n"+
                expectedTypeName+
                " but was of type \n"+
                actualTypeName+"\n and is :"+((Node)object).toHtml()
            );
    }

    public void assertType(
        String message,
        Class expectedType,
        Object object)
    {
        
        String expectedTypeName = expectedType.getName();
        String actualTypeName   = object.getClass().getName();
        if (!actualTypeName.equals(expectedTypeName))
            fail(
                message+" should have been of type\n"+
                expectedTypeName+
                " but was of type \n"+
                actualTypeName+"\n and is :"+((Node)object).toHtml()
            );
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

    /**
     * Mainline for individual test cases.
     * @param args Command line arguments. The following options
     * are understood:
     * <pre>
     * -text  -- use junit.textui.TestRunner
     * -awt   -- use junit.awtui.TestRunner
     * -swing -- use junit.swingui.TestRunner (default)
     * </pre>
     * All other options are passed on to the junit framework.
     * Decides the test class by examiing the system properties looking
     * for a property that starts with "org.htmlparser.tests.", this is
     * used as the name of the class (the value is ignored).
     * Each class that subclasses ParserTestCase can inherit this mainline
     * by adding a static block in their class similar to:
     * <pre>
     * static
     * {
     *     System.setProperty ("org.htmlparser.tests.ParserTest", "ParserTest");
     * }
     * </pre>
     */
    public static void main(String[] args)
    {
        String runner;
        int i;
        String arguments[];
        Properties properties;
        Enumeration enumeration;
        String name;
        Class cls;

        runner = null;
        for (i = 0; (i < args.length) && (null == runner); i++)
        {
            if (args[i].equalsIgnoreCase ("-text"))
                runner = "junit.textui.TestRunner";
            else if (args[i].equalsIgnoreCase ("-awt"))
                runner = "junit.awtui.TestRunner";
            else if (args[i].equalsIgnoreCase ("-swing"))
                runner = "junit.swingui.TestRunner";
        }
        if (null != runner)
        {
            // remove it from the arguments
            arguments = new String[args.length - 1];
            System.arraycopy (args, 0, arguments, 0, i - 1);
            System.arraycopy (args, i, arguments, i - 1, args.length - i);
            args = arguments;
        }
        else
            runner = "junit.swingui.TestRunner";

        // find the test class that has registered in the system properties
        arguments = args; // default of no class name, works in GUI mode
        properties = System.getProperties ();
        enumeration = properties.propertyNames ();
        while (enumeration.hasMoreElements ())
        {
            name = (String)enumeration.nextElement ();
            if (name.startsWith ("org.htmlparser.tests."))
            {
                // from http://www.mail-archive.com/commons-user%40jakarta.apache.org/msg02958.html
                //
                // The problem is within the UI test runners of JUnit. They bring
                // with them a custom classloader, which causes the
                // LogConfigurationException. Unfortunately Log4j doesn't work
                // either.
                //
                // Solution: Disable "Reload classes every run" or start JUnit with
                // command line option -noloading before the name of the Testsuite.
                if (true)
                {
                    // append the test class
                    arguments = new String[args.length + 2];
                    System.arraycopy (args, 0, arguments, 0, args.length);
                    arguments[arguments.length - 2] = "-noloading";
                    arguments[arguments.length - 1] = name;
                }
                else
                {
                    // append the test class
                    arguments = new String[args.length + 1];
                    System.arraycopy (args, 0, arguments, 0, args.length);
                    arguments[args.length] = name;
                }
                break; // JUnit only handles one class on the command line
            }
        }

        // invoke main() of the test runner
        try
        {
            cls = Class.forName (runner);
            java.lang.reflect.Method method = cls.getDeclaredMethod (
                "main", new Class[] { String[].class });
            method.invoke (
                null,
                new Object[] { arguments });
        }
        catch (Throwable t)
        {
            System.err.println (
                "cannot run unit test ("
                + t.getMessage ()
                + ")");
        }
    }
}
