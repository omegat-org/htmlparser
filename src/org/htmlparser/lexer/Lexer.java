// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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
//
// This class was contributed by
// Derrick Oswald
//

package org.htmlparser.lexer;

import java.io.*;
import java.net.*;
import java.util.*;

import org.htmlparser.*;
import org.htmlparser.lexer.nodes.*;
import org.htmlparser.lexer.nodes.RemarkNode;
import org.htmlparser.lexer.nodes.StringNode;
import org.htmlparser.util.*;

/**
 * This class parses the HTML stream into nodes.
 * There are three major types of nodes (lexemes):
 * <li>RemarkNode</li>
 * <li>StringNode</li>
 * <li>TagNode</li>
 * Each time <code>nextNode()</code> is called, another node is returned until
 * the stream is exhausted, and <code>null</code> is returned.
 */
public class Lexer
{
    /**
     * The page lexemes are retrieved from.
     */
    protected Page mPage;

    /**
     * The current position on the page.
     */
    protected Cursor mCursor;

    /**
     * Creates a new instance of a Lexer.
     * @param page The page with HTML text.
     */
    public Lexer (Page page)
    {
        mPage = page;
        mCursor = new Cursor (page, 0);
    }

    /**
     * Creates a new instance of a Lexer.
     * @param text The text to parse.
     */
    public Lexer (String text) throws ParserException
    {
        this (new Page (text));
    }

    /**
     * Creates a new instance of a Lexer.
     * @param connection The url to parse.
     */
    public Lexer (URLConnection connection) throws ParserException
    {
        this (new Page (connection));
    }

    /**
     * Get the page this lexer is working on.
     * @return The page that nodes are being read from.
     */
    public Page getPage ()
    {
        return (mPage);
    }

    /**
     * Get the next node from the source.
     * @return A RemarkNode, StringNode or TagNode, or <code>null</code> if no
     * more lexemes are present.
     * @exception ParserException If there is a problem with the underlying page.
     */
    public Node nextNode ()
        throws
            ParserException
    {
        Cursor probe;
        char ch;
        Node ret;

        probe = mCursor.dup ();
        ch = mPage.getCharacter (probe);
        switch (ch)
        {
            case 0: // end of input
                ret = null;
                break;
            case '<':
                ch = mPage.getCharacter (probe);
                if (0 == ch)
                    ret = parseString ();
                else if ('/' == ch || '%' == ch || Character.isLetter (ch))
                    ret = parseTag ();
                else if ('!' == ch)
                {
                    ch = mPage.getCharacter (probe);
                    if ('-' == ch)
                        ret = parseRemark ();
                    else
                        ret = parseTag ();
                }
                else
                    ret = parseString ();
                break;
            default:
                ret = parseString ();
                break;
        }

        return (ret);
    }

    /**
     * Parse a string node.
     * Scan characters until "&lt;/", "&lt;%", "&lt;!" or &lt; followed by a
     * letter is encountered, or the input stream is exhausted, in which
     * case <code>null</code> is returned.
     */
    protected Node parseString ()
        throws
            ParserException
    {
        Cursor cursor;
        boolean done;
        char ch;
        int length;
        int begin;
        int end;
        StringNode ret;

        cursor = mCursor.dup ();
        done = false;
        while (!done)
        {
            ch = mPage.getCharacter (cursor);
            if (0 == ch)
                done = true;
            else if ('<' == ch)
            {
                ch = mPage.getCharacter (cursor);
                if (0 == ch)
                    done = true;
                // the order of these tests might be optimized for speed:
                else if ('/' == ch || Character.isLetter (ch) || '!' == ch || '%' == ch)
                {
                    done = true;
                    cursor.retreat ();
                    cursor.retreat ();
                }
                else
                {
                    // it's not a tag, so keep going,
                    // the extra characters consumed are in this string
                }
            }
        }
        begin = mCursor.getPosition ();
        end = cursor.getPosition ();
        length = end - begin;
        if (0 != length)
        {   // got some characters
            ret = new StringNode (mPage, begin, end);
            mCursor = cursor;
        }
        else
            ret = null;

        return (ret);
    }

    private void whitespace (Vector attributes, int[] bookmarks)
    {
        if (bookmarks[1] > bookmarks[0])
            attributes.addElement (new Attribute (mPage, -1, -1, bookmarks[0], bookmarks[1], (char)0));
            //attributes.addElement (new Attribute (null, mPage.getText (bookmarks[0], bookmarks[1]), (char)0));
    }

    private void standalone (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new Attribute (mPage, bookmarks[1], bookmarks[2], -1, -1, (char)0));
        //attributes.addElement (new Attribute (mPage.getText (bookmarks[1], bookmarks[2]), null, (char)0));
    }

    private void empty (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new Attribute (mPage, bookmarks[1], bookmarks[2], bookmarks[2] + 1, bookmarks[2] + 1, (char)0));
        //attributes.addElement (new Attribute (mPage.getText (bookmarks[1], bookmarks[2]), "", (char)0));
    }

    private void naked (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new Attribute (mPage, bookmarks[1], bookmarks[2], bookmarks[3], bookmarks[4], (char)0));
        //attributes.addElement (new Attribute (mPage.getText (bookmarks[1], bookmarks[2]), mPage.getText (bookmarks[3], bookmarks[4]), (char)0));
    }

    private void single_quote (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new Attribute (mPage, bookmarks[1], bookmarks[2], bookmarks[4] + 1, bookmarks[5], '\''));
        //attributes.addElement (new Attribute (mPage.getText (bookmarks[1], bookmarks[2]), mPage.getText (bookmarks[4] + 1, bookmarks[5]), '\''));
    }

    private void double_quote (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new Attribute (mPage, bookmarks[1], bookmarks[2], bookmarks[5] + 1, bookmarks[6], '"'));
        //attributes.addElement (new Attribute (mPage.getText (bookmarks[1], bookmarks[2]), mPage.getText (bookmarks[5] + 1, bookmarks[6]), '"'));
    }

    /**
     * Parse a tag.
     * Parse the name and attributes from a start tag.<p>
     * From the <a href="http://www.w3.org/TR/html4/intro/sgmltut.html#h-3.2.2">
     * HTML 4.01 Specification, W3C Recommendation 24 December 1999</a>
     * http://www.w3.org/TR/html4/intro/sgmltut.html#h-3.2.2<p>
     * <cite>
     * 3.2.2 Attributes<p>
     * Elements may have associated properties, called attributes, which may
     * have values (by default, or set by authors or scripts). Attribute/value
     * pairs appear before the final ">" of an element's start tag. Any number
     * of (legal) attribute value pairs, separated by spaces, may appear in an
     * element's start tag. They may appear in any order.<p>
     * In this example, the id attribute is set for an H1 element:
     * <code>
     * &lt;H1 id="section1"&gt;
     * </code>
     * This is an identified heading thanks to the id attribute
     * <code>
     * &lt;/H1&gt;
     * </code>
     * By default, SGML requires that all attribute values be delimited using
     * either double quotation marks (ASCII decimal 34) or single quotation
     * marks (ASCII decimal 39). Single quote marks can be included within the
     * attribute value when the value is delimited by double quote marks, and
     * vice versa. Authors may also use numeric character references to
     * represent double quotes (&amp;#34;) and single quotes (&amp;#39;).
     * For doublequotes authors can also use the character entity reference &amp;quot;.<p>
     * In certain cases, authors may specify the value of an attribute without
     * any quotation marks. The attribute value may only contain letters
     * (a-z and A-Z), digits (0-9), hyphens (ASCII decimal 45),
     * periods (ASCII decimal 46), underscores (ASCII decimal 95),
     * and colons (ASCII decimal 58). We recommend using quotation marks even
     * when it is possible to eliminate them.<p>
     * Attribute names are always case-insensitive.<p>
     * Attribute values are generally case-insensitive. The definition of each
     * attribute in the reference manual indicates whether its value is case-insensitive.<p>
     * All the attributes defined by this specification are listed in the attribute index.<p>
     * </cite>
     * <p>
     * This method uses a state machine with the following states:
     * <ol>
     * <li>state 0 - outside of any attribute</li>
     * <li>state 1 - within attributre name</li>
     * <li>state 2 - equals hit</li>
     * <li>state 3 - within naked attribute value.</li>
     * <li>state 4 - within single quoted attribute value</li>
     * <li>state 5 - within double quoted attribute value</li>
     * </ol>
     * <p>
     * The starting point for the various components is stored in an array
     * of integers that match the initiation point for the states one-for-one,
     * i.e. bookmarks[0] is where state 0 began, bookmarks[1] is where state 1
     * began, etc.
     * Attributes are stored in a <code>Vector</code> having
     * one slot for each whitespace or attribute/value pair.
     * The first slot is for attribute name (kind of like a standalone attribute).
     */
    protected Node parseTag ()
        throws
            ParserException
    {
        Cursor cursor;
        boolean done;
        char ch;
        int state;
        int[] bookmarks;
        Vector attributes;
        int length;
        TagNode ret;

        cursor = mCursor.dup ();
        // sanity check
        ch = mPage.getCharacter (cursor);
        if ('<' != ch)
            return (parseString ());
        done = false;
        attributes = new Vector ();
        state = 0;
        bookmarks = new int[7];
        bookmarks[0] = cursor.getPosition ();
        while (!done)
        {
            bookmarks[state + 1] = cursor.getPosition ();
            ch = mPage.getCharacter (cursor);
            switch (state)
            {
                case 0: // outside of any attribute
                    if ((0 == ch) || ('>' == ch))
                    {
                        whitespace (attributes, bookmarks);
                        done = true;
                    }
                    else if (!Character.isWhitespace (ch))
                    {
                        whitespace (attributes, bookmarks);
                        state = 1;
                    }
                    break;
                case 1: // within attributre name
                    if ((0 == ch) || ('>' == ch))
                    {
                        standalone (attributes, bookmarks);
                        done = true;
                    }
                    else if (Character.isWhitespace (ch))
                    {
                        standalone (attributes, bookmarks);
                        bookmarks[0] = bookmarks[2];
                        state = 0;
                    }
                    else if ('=' == ch)
                        state = 2;
                    break;
                case 2: // equals hit
                    if ((0 == ch) || ('>' == ch))
                    {
                        empty (attributes, bookmarks);
                        done = true;
                    }
                    else if ('\'' == ch)
                    {
                        state = 4;
                        bookmarks[4] = bookmarks[3];
                    }
                    else if ('"' == ch)
                    {
                        state = 5;
                        bookmarks[5] = bookmarks[3];
                    }
                    else
                        state = 3;
                    break;
                case 3: // within naked attribute value
                    if ('>' == ch)
                    {
                        naked (attributes, bookmarks);
                        done = true;
                    }
                    else if (Character.isWhitespace (ch))
                    {
                        naked (attributes, bookmarks);
                        bookmarks[0] = bookmarks[4];
                        state = 0;
                    }
                    break;
                case 4: // within single quoted attribute value
                    if (0 == ch)
                    {
                        single_quote (attributes, bookmarks);
                        done = true; // complain?
                    }
                    else if ('\'' == ch)
                    {
                        single_quote (attributes, bookmarks);
                        bookmarks[0] = bookmarks[5] + 1;
                        state = 0;
                    }
                    break;
                case 5: // within double quoted attribute value
                    if (0 == ch)
                    {
                        double_quote (attributes, bookmarks);
                        done = true; // complain?
                    }
                    else if ('"' == ch)
                    {
                        double_quote (attributes, bookmarks);
                        bookmarks[0] = bookmarks[6] + 1;
                        state = 0;
                    }
                    break;
                default:
                    throw new IllegalStateException ("how the fuck did we get in state " + state);
            }
        }
        length = cursor.getPosition () - mCursor.getPosition ();
        if (0 != length)
        {   // return tag based on second character, '/', '%', Letter (ch), '!'
            if (2 > length)
                // this is an error
                return (parseString ());
            ret = new TagNode (mPage, mCursor.getPosition (), cursor.getPosition (), attributes);
            mCursor = cursor;
        }
        else
            ret = null;

        return (ret);
    }

    /**
     * Parse a comment.
     * Parse a remark markup.<p>
     * From the <a href="http://www.w3.org/TR/html4/intro/sgmltut.html#h-3.2.4">
     * HTML 4.01 Specification, W3C Recommendation 24 December 1999</a>
     * http://www.w3.org/TR/html4/intro/sgmltut.html#h-3.2.4<p>
     * <cite>
     * 3.2.4 Comments<p>
     * HTML comments have the following syntax:<p>
     * <code>
     * &lt;!-- this is a comment --&gt;<p>
     * &lt;!-- and so is this one,<p>
     *     which occupies more than one line --&gt;<p>
     * </code>
     * White space is not permitted between the markup declaration
     * open delimiter("&lt;!") and the comment open delimiter ("--"),
     * but is permitted between the comment close delimiter ("--") and
     * the markup declaration close delimiter ("&gt;").
     * A common error is to include a string of hyphens ("---") within a comment.
     * Authors should avoid putting two or more adjacent hyphens inside comments.
     * Information that appears between comments has no special meaning
     * (e.g., character references are not interpreted as such).
     * Note that comments are markup.<p>
     * </cite>
     * <p>
     * This method uses a state machine with the following states:
     * <ol>
     * <li>state 0 - prior to the first open delimiter</li>
     * <li>state 1 - prior to the second open delimiter</li>
     * <li>state 2 - prior to the first closing delimiter</li>
     * <li>state 3 - prior to the second closing delimiter</li>
     * <li>state 4 - prior to the terminating &gt;</li>
     * </ol>
     * <p>
     * All comment text (everything excluding the &lt; and &gt;), is included
     * in the remark text.
     * We allow terminators like --!&gt; even though this isn't part of the spec.
     */
    protected Node parseRemark ()
        throws
            ParserException
    {
        Cursor cursor;
        boolean done;
        char ch;
        int state;
        int length;
        RemarkNode ret;

        cursor = mCursor.dup ();
        // sanity check
        ch = mPage.getCharacter (cursor);
        if ('<' != ch)
            return (parseString ());
        ch = mPage.getCharacter (cursor);
        if ('!' != ch)
            return (parseString ());
        done = false;
        state = 0;
        while (!done)
        {
            ch = mPage.getCharacter (cursor);
            switch (state)
            {
                case 0: // prior to the first open delimiter
                    if ('-' == ch)
                        state = 1;
                    else
                        return (parseString ());
                    break;
                case 1: // prior to the second open delimiter
                    if ('-' == ch)
                        state = 2;
                    else
                        return (parseString ());
                    break;
                case 2: // prior to the first closing delimiter
                    if ('-' == ch)
                        state = 3;
                    break;
                case 3: // prior to the second closing delimiter
                    if ('-' == ch)
                        state = 4;
                    else
                        state = 2;
                    break;
                case 4: // prior to the terminating >
                    if ('>' == ch)
                        done = true;
                    else if (('!' == ch) || ('-' == ch) || Character.isWhitespace (ch))
                    {
                        // stay in state 4
                    }
                    else
                        state = 2;
                    break;
                default:
                    throw new IllegalStateException ("how the fuck did we get in state " + state);
            }
        }
        length = cursor.getPosition () - mCursor.getPosition ();
        if (0 != length)
        {   // return tag based on second character, '/', '%', Letter (ch), '!'
            if (2 > length)
                // this is an error
                return (parseString ());
            ret = new RemarkNode (mPage, mCursor.getPosition (), cursor.getPosition ());
            mCursor = cursor;
        }
        else
            ret = null;

        return (ret);
    }

    /**
     * Mainline for command line operation
     */
    public static void main (String[] args) throws IOException, ParserException
    {
        URL url;
        Lexer lexer;
        Node node;

        if (0 >= args.length)
            System.out.println ("usage: java -jar htmllexer.jar <url>");
        else
        {
            url = new URL (args[0]);
            try
            {
                lexer = new Lexer (url.openConnection ());
                while (null != (node = lexer.nextNode ()))
                    System.out.println (node.toString ());
            }
            catch (ParserException pe)
            {
                System.out.println (pe.getMessage ());
                if (null != pe.getThrowable ())
                    System.out.println (pe.getThrowable ().getMessage ());
            }
        }
    }
}
