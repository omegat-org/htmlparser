// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.tests.parserHelperTests;

import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.RemarkNode;
import org.htmlparser.StringNode;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class RemarkNodeParserTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.parserHelperTests.RemarkNodeParserTest", "RemarkNodeParserTest");
    }

    public RemarkNodeParserTest(String name) {
        super(name);
    }

    /**
     * The bug being reproduced is this : <BR>
     * &lt;!-- saved from url=(0022)http://internet.e-mail --&gt;
     * &lt;HTML&gt;
     * &lt;HEAD&gt;&lt;META name="title" content="Training Introduction"&gt;
     * &lt;META name="subject" content=""&gt;
     * &lt;!--
         Whats gonna happen now ?
     * --&gt;
     * &lt;TEST&gt;
     * &lt;/TEST&gt;
     *
     * The above line is incorrectly parsed - the remark is not correctly identified.
     * This bug was reported by Serge Kruppa (2002-Feb-08).
     */
    public void testRemarkNodeBug() throws ParserException
    {
        createParser(
            "<!-- saved from url=(0022)http://internet.e-mail -->\n"+
            "<HTML>\n"+
            "<HEAD><META name=\"title\" content=\"Training Introduction\">\n"+
            "<META name=\"subject\" content=\"\">\n"+
            "<!--\n"+
            "   Whats gonna happen now ?\n"+
            "-->\n"+
            "<TEST>\n"+
            "</TEST>\n");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        Parser.setLineSeparator("\r\n");
        parseAndAssertNodeCount(15);
        // The first node should be a HTMLRemarkNode
        assertTrue("First node should be a RemarkNode",node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertEquals("Text of the remarkNode #1"," saved from url=(0022)http://internet.e-mail ",remarkNode.getText());
        // The tenth node should be a HTMLRemarkNode
        assertTrue("Tenth node should be a RemarkNode",node[9] instanceof RemarkNode);
        remarkNode = (RemarkNode)node[9];
        assertEquals("Text of the remarkNode #10","\n   Whats gonna happen now ?\n",remarkNode.getText());
    }

    public void testToPlainTextString() throws ParserException {
        createParser(
            "<!-- saved from url=(0022)http://internet.e-mail -->\n"+
            "<HTML>\n"+
            "<HEAD><META name=\"title\" content=\"Training Introduction\">\n"+
            "<META name=\"subject\" content=\"\">\n"+
            "<!--\n"+
            "   Whats gonna happen now ?\n"+
            "-->\n"+
            "<TEST>\n"+
            "</TEST>\n");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        Parser.setLineSeparator("\r\n");
        parseAndAssertNodeCount(15);
        // The first node should be a HTMLRemarkNode
        assertTrue("First node should be a RemarkNode",node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertEquals("Plain Text of the remarkNode #1"," saved from url=(0022)http://internet.e-mail ",remarkNode.toPlainTextString());
        // The tenth node should be a HTMLRemarkNode
        assertTrue("Tenth node should be a RemarkNode",node[9] instanceof RemarkNode);
        remarkNode = (RemarkNode)node[9];
        assertEquals("Plain Text of the remarkNode #10","\n   Whats gonna happen now ?\n",remarkNode.getText());

    }

    public void testToRawString()  throws ParserException {
        createParser(
            "<!-- saved from url=(0022)http://internet.e-mail -->\n"+
            "<HTML>\n"+
            "<HEAD><META name=\"title\" content=\"Training Introduction\">\n"+
            "<META name=\"subject\" content=\"\">\n"+
            "<!--\n"+
            "   Whats gonna happen now ?\n"+
            "-->\n"+
            "<TEST>\n"+
            "</TEST>\n");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        Parser.setLineSeparator("\r\n");
        parseAndAssertNodeCount(15);
        // The first node should be a HTMLRemarkNode
        assertTrue("First node should be a RemarkNode",node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertStringEquals("Raw String of the remarkNode #1","<!-- saved from url=(0022)http://internet.e-mail -->",remarkNode.toHtml());
        // The tenth node should be a HTMLRemarkNode
        assertTrue("Tenth node should be a RemarkNode",node[9] instanceof RemarkNode);
        remarkNode = (RemarkNode)node[9];
        assertStringEquals("Raw String of the remarkNode #6","<!--\n   Whats gonna happen now ?\n-->",remarkNode.toHtml());
    }

    public void testNonRemarkNode() throws ParserException {
        createParser("&nbsp;<![endif]>");
        parseAndAssertNodeCount(2);
        // The first node should be a HTMLRemarkNode
        assertTrue("First node should be a string node",node[0] instanceof StringNode);
        assertTrue("Second node should be a Tag",node[1] instanceof Tag);
        StringNode stringNode = (StringNode)node[0];
        Tag tag = (Tag)node[1];
        assertEquals("Text contents","&nbsp;",stringNode.getText());
        assertEquals("Tag Contents","![endif]",tag.getText());

    }

    /**
     * This is the simulation of bug report 586756, submitted
     * by John Zook.
     * If all the comment contains is a blank line, it breaks
     * the state
     */
    public void testRemarkNodeWithBlankLine() throws ParserException {
        createParser("<!--\n"+
        "\n"+
        "-->");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        Parser.setLineSeparator("\r\n");
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a HTMLRemarkNode",node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertEquals("Expected contents","\n\n",remarkNode.getText());

    }

    /**
     * This is the simulation of a bug report submitted
     * by Claude Duguay.
     * If it is a comment with nothing in it, parser crashes
     */
    public void testRemarkNodeWithNothing() throws ParserException {
        createParser("<!-->");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a RemarkNode",node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertEquals("Expected contents","",remarkNode.getText());

    }

    /**
     * Reproduction of bug reported by John Zook [594301]
     * When we have tags like :
     * &lt;!-- &lt;A&gt; --&gt;
     * it doesent get parsed correctly
     */
    public void testTagWithinRemarkNode() throws ParserException {
        createParser("<!-- \n"+
        "<A>\n"+
        "bcd -->");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        Parser.setLineSeparator("\n");
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a HTMLRemarkNode",node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertStringEquals("Expected contents"," \n<A>\nbcd ",remarkNode.getText());

    }

    /**
     * Bug reported by John Zook [594301], invalid remark nodes are accepted as remark nodes.
     * &lt;<br>
     * -<br>
     * -<br>
     * ssd --&gt;<br>
     * This is not supposed to be a remarknode
     */
    public void testInvalidTag() throws ParserException {
        createParser("<!\n"+
        "-\n"+
        "-\n"+
        "ssd -->");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        Parser.setLineSeparator("\n");
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a Tag but was "+node[0],node[0] instanceof Tag);
        Tag tag = (Tag)node[0];
        assertStringEquals("Expected contents","!\n"+
        "-\n"+
        "-\n"+
        "ssd --",tag.getText());
        Parser.setLineSeparator("\r\n");
    }

    /**
     * Bug reported by John Zook [594301]
     * If dashes exist in a comment, they dont get added to the comment text
     */
    public void testDashesInComment() throws ParserException{
        createParser("<!-- -- -->");
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        parseAndAssertNodeCount(1);
        assertTrue("Node should be a HTMLRemarkNode but was "+node[0],node[0] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[0];
        assertEquals("Remark Node contents"," -- ",remarkNode.getText());
    }


    // from http://www.w3.org/MarkUp/html-spec/html-spec_3.html
//Comments
//
//To include comments in an HTML document, use a comment declaration.
//A comment declaration consists of `<!' followed by zero or more comments
//followed by `>'. Each comment starts with `--' and includes all text up to
//and including the next occurrence of `--'. In a comment declaration, white
//space is allowed after each comment, but not before the first comment. The
//entire comment declaration is ignored. (10)
//
//For example:
//
//<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
//<HEAD>
//<TITLE>HTML Comment Example</TITLE>
//<!-- Id: html-sgml.sgm,v 1.5 1995/05/26 21:29:50 connolly Exp  -->
//<!-- another -- -- comment -->
//<!>
//</HEAD>
//<BODY>
//<p> <!- not a comment, just regular old data characters ->

    /**
     * Test a comment declaration with a comment.
     */
    public void testSingleComment ()
        throws
            ParserException
    {
        createParser(
              "<HTML>\n"
            + "<HEAD>\n"
            + "<TITLE>HTML Comment Test</TITLE>\n"
            + "</HEAD>\n"
            + "<BODY>\n"
            + "<!-- Id: html-sgml.sgm,v 1.5 1995/05/26 21:29:50 connolly Exp  -->\n"
            + "</BODY>\n"
            + "</HTML>\n"
            );
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        parseAndAssertNodeCount(18);
        assertTrue("Node should be a RemarkNode but was "+node[12],node[12] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[12];
        assertEquals("Remark Node contents"," Id: html-sgml.sgm,v 1.5 1995/05/26 21:29:50 connolly Exp  ",remarkNode.getText());
    }

    /**
     * Test a comment declaration with two comments.
     */
    public void testDoubleComment ()
        throws
            ParserException
    {
        createParser(
              "<HTML>\n"
            + "<HEAD>\n"
            + "<TITLE>HTML Comment Test</TITLE>\n"
            + "</HEAD>\n"
            + "<BODY>\n"
            + "<!-- another -- -- comment -->\n"
            + "</BODY>\n"
            + "</HTML>\n"
            );
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        parseAndAssertNodeCount(18);
        assertTrue("Node should be a RemarkNode but was "+node[12],node[12] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[12];
        assertEquals("Remark Node contents"," another -- -- comment ",remarkNode.getText());
    }

    /**
     * Test a comment declaration without any comments.
     */
    public void testEmptyComment ()
        throws
            ParserException
    {
        createParser(
              "<HTML>\n"
            + "<HEAD>\n"
            + "<TITLE>HTML Comment Test 'testEmptyComment'</TITLE>\n"
            + "</HEAD>\n"
            + "<BODY>\n"
            + "<!>\n"
            + "</BODY>\n"
            + "</HTML>\n"
            );
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        parseAndAssertNodeCount(18);
        assertTrue("Node should be a RemarkNode but was "+node[12],node[12] instanceof RemarkNode);
        RemarkNode remarkNode = (RemarkNode)node[12];
        assertEquals("Remark Node contents","",remarkNode.getText());
    }

//    /**
//     * Test what the specification calls data characters.
//     * Actually, no browser I've tried handles this correctly (as text).
//     * Some handle it as a comment and others handle it as a tag.
//     * So for now we leave this test case out.
//     */
//    public void testNotAComment ()
//        throws
//            HTMLParserException
//    {
//      createParser(
//              "<HTML>\n"
//            + "<HEAD>\n"
//            + "<TITLE>HTML Comment Test 'testNotAComment'</TITLE>\n"
//            + "</HEAD>\n"
//            + "<BODY>\n"
//            + "<!- not a comment, just regular old data characters ->\n"
//            + "</BODY>\n"
//            + "</HTML>\n"
//            );
//      parseAndAssertNodeCount(10);
//      assertTrue("Node should not be a HTMLRemarkNode",!(node[7] instanceof HTMLRemarkNode));
//      assertTrue("Node should be a HTMLStringNode but was "+node[7],node[7].getType()==HTMLStringNode.TYPE);
//      HTMLStringNode stringNode = (HTMLStringNode)node[7];
//      assertEquals("String Node contents","<!- not a comment, just regular old data characters ->\n",stringNode.getText());
//    }

    /**
     * Test a comment ending with !--.
     * See bug #788746 parser crashes on comments like <!-- foobar --!>
     */
    public void testExclamationComment ()
        throws
            ParserException
    {
        createParser (
              "<html>\n"
            + "<head>\n"
            + "<title>foobar</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "<!-- foobar --!>\n"
            + "</body>\n"
            + "</html>\n"
            );
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        parseAndAssertNodeCount (18);
        assertTrue("Node should be a RemarkNode but was " + node[12], node[12] instanceof RemarkNode);
        assertStringEquals ("remark text", "<!-- foobar --!>", node[12].toHtml ());
    }

}
