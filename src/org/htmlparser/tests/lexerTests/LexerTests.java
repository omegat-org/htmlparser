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

package org.htmlparser.tests.lexerTests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import junit.framework.TestCase;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.PageIndex;
import org.htmlparser.lexer.Stream;
import org.htmlparser.lexer.nodes.RemarkNode;
import org.htmlparser.lexer.nodes.StringNode;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.util.ParserException;

public class LexerTests extends TestCase
{
    
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

    /**
     * Try a real page.
     */
    public void testReal () throws ParserException, IOException
    {
        Lexer lexer;
        Node node;

        URL url = new URL ("http://sourceforge.net/projects/htmlparser");
        lexer = new Lexer (url.openConnection ());
        while (null != (node = lexer.nextNode ()))
            System.out.println (node.toString ());
    }

    
}
