// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
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

package org.htmlparser.tests.lexerTests;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.PageIndex;
import org.htmlparser.lexer.Source;
import org.htmlparser.lexer.Stream;
import org.htmlparser.lexer.nodes.RemarkNode;
import org.htmlparser.lexer.nodes.StringNode;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class LexerTests extends ParserTestCase
{

    static
    {
        System.setProperty ("org.htmlparser.tests.lexerTests.LexerTests", "LexerTests");
    }

    /**
     * Test the Lexer class.
     */
    public LexerTests (String name)
    {
        super (name);
    }

    /**
     * Test operation without tags.
     */
    public void testPureText () throws ParserException
    {
        String reference;
        Lexer lexer;
        StringNode node;

        reference = "Hello world";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
    }

    /**
     * Test operation with Unix line endings.
     */
    public void testUnixEOL () throws ParserException
    {
        String reference;
        Lexer lexer;
        StringNode node;

        reference = "Hello\nworld";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
    }

    /**
     * Test operation with Dos line endings.
     */
    public void testDosEOL () throws ParserException
    {
        String reference;
        Lexer lexer;
        StringNode node;

        reference = "Hello\r\nworld";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
        reference = "Hello\rworld";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
    }

    /**
     * Test operation with line endings near the end of input.
     */
    public void testEOF_EOL () throws ParserException
    {
        String reference;
        Lexer lexer;
        StringNode node;

        reference = "Hello world\n";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
        reference = "Hello world\r";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
        reference = "Hello world\r\n";
        lexer = new Lexer (reference);
        node = (StringNode)lexer.nextNode ();
        assertEquals ("StringNode contents wrong", reference, node.getText ());
    }

    /**
     * Test that tags stop string nodes.
     */
    public void testTagStops () throws ParserException
    {
        String[] references =
        {
            "Hello world",
            "Hello world\n",
            "Hello world\r\n",
            "Hello world\r",

        };
        String[] suffixes =
        {
            "<head>",
            "</head>",
            "<%=head%>",
            "<!--head-->",
        };
        Lexer lexer;
        StringNode node;

        for (int i = 0; i < references.length; i++)
        {
            for (int j = 0; j < suffixes.length; j++)
            {
                lexer = new Lexer (references[i] + suffixes[j]);
                node = (StringNode)lexer.nextNode ();
                assertEquals ("StringNode contents wrong", references[i], node.getText ());
            }
        }
    }

    /**
     * Test operation with only tags.
     */
    public void testPureTag () throws ParserException
    {
        String reference;
        String suffix;
        Lexer lexer;
        TagNode node;

        reference = "<head>";
        lexer = new Lexer (reference);
        node = (TagNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());

        reference = "<head>";
        suffix = "<body>";
        lexer = new Lexer (reference + suffix);
        node = (TagNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());
        node = (TagNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", suffix, node.toHtml ());
    }

    /**
     * Test operation with attributed tags.
     */
    public void testAttributedTag () throws ParserException
    {
        String reference;
        Lexer lexer;
        TagNode node;

        reference = "<head lang='en_US' dir=ltr\nprofile=\"http://htmlparser.sourceforge.org/dictionary.html\">";
        lexer = new Lexer (reference);
        node = (TagNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());
    }

    /**
     * Test operation with comments.
     */
    public void testRemarkNode () throws ParserException
    {
        String reference;
        Lexer lexer;
        RemarkNode node;
        String suffix;

        reference = "<!-- This is a comment -->";
        lexer = new Lexer (reference);
        node = (RemarkNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());

        reference = "<!-- This is a comment --  >";
        lexer = new Lexer (reference);
        node = (RemarkNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());

        reference = "<!-- This is a\nmultiline comment -->";
        lexer = new Lexer (reference);
        node = (RemarkNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());

        suffix = "<head>";
        reference = "<!-- This is a comment -->";
        lexer = new Lexer (reference + suffix);
        node = (RemarkNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());

        reference = "<!-- This is a comment --  >";
        lexer = new Lexer (reference + suffix);
        node = (RemarkNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());

        reference = "<!-- This is a\nmultiline comment -->";
        lexer = new Lexer (reference + suffix);
        node = (RemarkNode)lexer.nextNode ();
        assertEquals ("Tag contents wrong", reference, node.toHtml ());
    }

//    /**
//     * Try a real page.
//     */
//    public void testReal () throws ParserException, IOException
//    {
//        Lexer lexer;
//        Node node;
//
//        URL url = new URL ("http://sourceforge.net/projects/htmlparser");
//        lexer = new Lexer (url.openConnection ());
//        while (null != (node = lexer.nextNode ()))
//            System.out.println (node.toString ());
//    }

    /**
     * Test the fidelity of the toHtml() method.
     */
    public void testFidelity () throws ParserException, IOException
    {
        Lexer lexer;
        Node node;
        int position;
        StringBuffer buffer;
        String string;
        char[] ref;
        char[] test;

        URL url = new URL ("http://sourceforge.net/projects/htmlparser");
        lexer = new Lexer (url.openConnection ());
        position = 0;
        buffer = new StringBuffer (80000);
        while (null != (node = lexer.nextNode ()))
        {
            string = node.toHtml ();
            if (position != node.elementBegin ())
                fail ("non-contiguous" + string);
            buffer.append (string);
            position = node.elementEnd ();
            if (buffer.length () != position)
                fail ("text length differed after encountering node " + string);
        }
        ref = lexer.getPage ().getText ().toCharArray ();
        test = new char[buffer.length ()];
        buffer.getChars (0, buffer.length (), test, 0);
        assertEquals ("different amounts of text", ref.length, test.length);
        for (int i = 0; i < ref.length; i++)
            if (ref[i] != test[i])
                fail ("character differs at position " + i + ", expected <" + ref[i] + "> but was <" + test[i] + ">");
    }

//    /**
//     * Test the relative speed reading from a string parsing tags too.
//     */
//    public void testSpeedStringWithoutTags () throws ParserException, IOException
//    {
//        final String link = "http://htmlparser.sourceforge.net/javadoc_1_3/index-all.html";
//        URL url;
//        URLConnection connection;
//        Source source;
//        StringBuffer buffer;
//        int i;
//        String html;
//
//        long old_total;
//        long new_total;
//        long begin;
//        long end;
//        StringReader reader;
//        NodeReader nodes;
//        Parser parser;
//        int nodecount;
//        Node node;
//        int charcount;
//
//        url = new URL (link);
//        connection = url.openConnection ();
//        connection.connect ();
//        source = new Source (new Stream (connection.getInputStream ()));
//        buffer = new StringBuffer (350000);
//        while (-1 != (i = source.read ()))
//            buffer.append ((char)i);
//        source.close ();
//        html = buffer.toString ();
//        old_total = 0;
//        new_total = 0;
//        for (i = 0; i < 5; i++)
//        {
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            Lexer lexer = new Lexer (html);
//            nodecount = 0;
//            while (null != (node = lexer.nextNode ()))
//                nodecount++;
//            end = System.currentTimeMillis ();
//            System.out.println ("     lexer: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                new_total += (end - begin);
//
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            reader = new StringReader (html);
//            nodes =  new NodeReader (new BufferedReader (reader), 350000);
//            parser = new Parser (nodes, null);
//            nodecount = 0;
//            while (null != (node = nodes.readElement ()))
//                nodecount++;
//            end = System.currentTimeMillis ();
//            System.out.println ("old reader: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                old_total += (end - begin);
//        }
//        assertTrue ("old parser is" + ((double)(new_total - old_total)/(double)old_total*100.0) + "% faster", new_total < old_total);
//        System.out.println ("lexer is " + ((double)(old_total - new_total)/(double)old_total*100.0) + "% faster");
//    }
//
//    /**
//     * Test the relative speed reading from a string parsing tags too.
//     */
//    public void testSpeedStringWithTags () throws ParserException, IOException
//    {
//        final String link = "http://htmlparser.sourceforge.net/javadoc_1_3/index-all.html";
//        URL url;
//        URLConnection connection;
//        Source source;
//        StringBuffer buffer;
//        int i;
//        String html;
//
//        long old_total;
//        long new_total;
//        long begin;
//        long end;
//        StringReader reader;
//        NodeReader nodes;
//        Parser parser;
//        int nodecount;
//        Node node;
//        int charcount;
//
//        url = new URL (link);
//        connection = url.openConnection ();
//        connection.connect ();
//        source = new Source (new Stream (connection.getInputStream ()));
//        buffer = new StringBuffer (350000);
//        while (-1 != (i = source.read ()))
//            buffer.append ((char)i);
//        source.close ();
//        html = buffer.toString ();
//        old_total = 0;
//        new_total = 0;
//        for (i = 0; i < 5; i++)
//        {
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            Lexer lexer = new Lexer (html);
//            nodecount = 0;
//            while (null != (node = lexer.nextNode ()))
//            {
//                nodecount++;
//                if (node instanceof TagNode)
//                    ((TagNode)node).getAttributes ();
//            }
//            end = System.currentTimeMillis ();
//            System.out.println ("     lexer: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                new_total += (end - begin);
//
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            reader = new StringReader (html);
//            nodes =  new NodeReader (new BufferedReader (reader), 350000);
//            parser = new Parser (nodes, null);
//            nodecount = 0;
//            while (null != (node = nodes.readElement ()))
//            {
//                nodecount++;
//                if (node instanceof Tag)
//                    ((Tag)node).getAttributes ();
//            }
//            end = System.currentTimeMillis ();
//            System.out.println ("old reader: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                old_total += (end - begin);
//        }
//        assertTrue ("old parser is" + ((double)(new_total - old_total)/(double)old_total*100.0) + "% faster", new_total < old_total);
//        System.out.println ("lexer is " + ((double)(old_total - new_total)/(double)old_total*100.0) + "% faster");
//    }
//
//    public void testSpeedStreamWithoutTags () throws ParserException, IOException
//    {
//        final String link = "http://htmlparser.sourceforge.net/javadoc_1_3/index-all.html";
//        URL url;
//        URLConnection connection;
//        Source source;
//        StringBuffer buffer;
//        int i;
//        String html;
//        InputStream stream;
//
//        long old_total;
//        long new_total;
//        long begin;
//        long end;
//        InputStreamReader reader;
//        NodeReader nodes;
//        Parser parser;
//        int nodecount;
//        Node node;
//        int charcount;
//
//        url = new URL (link);
//        connection = url.openConnection ();
//        connection.connect ();
//        source = new Source (new Stream (connection.getInputStream ()));
//        buffer = new StringBuffer (350000);
//        while (-1 != (i = source.read ()))
//            buffer.append ((char)i);
//        source.close ();
//        html = buffer.toString ();
//        old_total = 0;
//        new_total = 0;
//
//        for (i = 0; i < 5; i++)
//        {
//
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            stream = new ByteArrayInputStream (html.getBytes (Page.DEFAULT_CHARSET));
//            Lexer lexer = new Lexer (new Page (stream, Page.DEFAULT_CHARSET));
//            nodecount = 0;
//            while (null != (node = lexer.nextNode ()))
//                nodecount++;
//            end = System.currentTimeMillis ();
//            System.out.println ("     lexer: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                new_total += (end - begin);
//
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            stream = new ByteArrayInputStream (html.getBytes (Page.DEFAULT_CHARSET));
//            reader = new InputStreamReader (stream);
//            nodes =  new NodeReader (reader, 350000);
//            parser = new Parser (nodes, null);
//            nodecount = 0;
//            while (null != (node = nodes.readElement ()))
//                nodecount++;
//            end = System.currentTimeMillis ();
//            System.out.println ("old reader: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                old_total += (end - begin);
//
//        }
//        assertTrue ("old parser is" + ((double)(new_total - old_total)/(double)old_total*100.0) + "% faster", new_total < old_total);
//        System.out.println ("lexer is " + ((double)(old_total - new_total)/(double)old_total*100.0) + "% faster");
//    }
//
//    public void testSpeedStreamWithTags () throws ParserException, IOException
//    {
//        final String link = "http://htmlparser.sourceforge.net/javadoc_1_3/index-all.html";
//        URL url;
//        URLConnection connection;
//        Source source;
//        StringBuffer buffer;
//        int i;
//        String html;
//        InputStream stream;
//
//        long old_total;
//        long new_total;
//        long begin;
//        long end;
//        InputStreamReader reader;
//        NodeReader nodes;
//        Parser parser;
//        int nodecount;
//        Node node;
//        int charcount;
//
//        url = new URL (link);
//        connection = url.openConnection ();
//        connection.connect ();
//        source = new Source (new Stream (connection.getInputStream ()));
//        buffer = new StringBuffer (350000);
//        while (-1 != (i = source.read ()))
//            buffer.append ((char)i);
//        source.close ();
//        html = buffer.toString ();
//        old_total = 0;
//        new_total = 0;
//
//        for (i = 0; i < 5; i++)
//        {
//
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            stream = new ByteArrayInputStream (html.getBytes (Page.DEFAULT_CHARSET));
//            Lexer lexer = new Lexer (new Page (stream, Page.DEFAULT_CHARSET));
//            nodecount = 0;
//            while (null != (node = lexer.nextNode ()))
//            {
//                nodecount++;
//                if (node instanceof TagNode)
//                    ((TagNode)node).getAttributes ();
//            }
//            end = System.currentTimeMillis ();
//            System.out.println ("     lexer: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                new_total += (end - begin);
//
//            System.gc ();
//            begin = System.currentTimeMillis ();
//            stream = new ByteArrayInputStream (html.getBytes (Page.DEFAULT_CHARSET));
//            reader = new InputStreamReader (stream);
//            nodes =  new NodeReader (reader, 350000);
//            parser = new Parser (nodes, null);
//            nodecount = 0;
//            while (null != (node = nodes.readElement ()))
//            {
//                nodecount++;
//                if (node instanceof Tag)
//                    ((Tag)node).getAttributes ();
//            }
//            end = System.currentTimeMillis ();
//            System.out.println ("old reader: " + (end - begin) + " msec, " + nodecount + " nodes");
//            if (0 != i) // the first timing is way different
//                old_total += (end - begin);
//        }
//        assertTrue ("old parser is" + ((double)(new_total - old_total)/(double)old_total*100.0) + "% faster", new_total < old_total);
//        System.out.println ("lexer is " + ((double)(old_total - new_total)/(double)old_total*100.0) + "% faster");
//    }

//    public static void main (String[] args) throws ParserException, IOException
//    {
//        LexerTests tests = new LexerTests ("hallow");
//        tests.testSpeedStreamWithTags ();
//    }

}

