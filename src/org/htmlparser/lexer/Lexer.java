// HTMLParser Library v1_4_20031207 - A java-based parser for HTML
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

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.AbstractNode;
import org.htmlparser.lexer.nodes.PageAttribute;
import org.htmlparser.lexer.nodes.NodeFactory;
import org.htmlparser.lexer.nodes.RemarkNode;
import org.htmlparser.lexer.nodes.StringNode;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.util.ParserException;

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
    implements
        Serializable,
        NodeFactory
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
     * The factory for new nodes.
     */
    protected NodeFactory mFactory;

    /**
     * Line number to trigger on.
     * This is tested on each <code>nextNode()</code> call, as an aid to debugging.
     * Alter this value and set a breakpoint on the line after the test.
     * Remember, these line numbers are zero based, while most editors are one based.
     * @see #nextNode
     */ 
    static protected int mDebugLineTrigger = -1;

    /**
     * Creates a new instance of a Lexer.
     */
    public Lexer ()
    {
        this (new Page (""));
    }

    /**
     * Creates a new instance of a Lexer.
     * @param page The page with HTML text.
     */
    public Lexer (Page page)
    {
        setPage (page);
        setCursor (new Cursor (page, 0));
        setNodeFactory (this);
    }

    /**
     * Creates a new instance of a Lexer.
     * @param text The text to parse.
     */
    public Lexer (String text)
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
     * Reset the lexer to start parsing from the beginning again.
     * The underlying components are reset such that the next call to
     * <code>nextNode()</code> will return the first lexeme on the page.
     */
    public void reset ()
    {
        getPage ().reset ();
        setCursor (new Cursor (getPage (), 0));
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
     * Set the page this lexer is working on.
     * @return The page that nodes will be read from.
     */
    public void setPage (Page page)
    {
        if (null == page)
            throw new IllegalArgumentException ("page cannot be null");
        // todo: sanity checks
        mPage = page;
    }

    /**
     * Get the current scanning position.
     * @return The lexer's cursor position.
     */
    public Cursor getCursor ()
    {
        return (mCursor);
    }

    /**
     * Set the current scanning position.
     * @param cursor The lexer's new cursor position.
     */
    public void setCursor (Cursor cursor)
    {
        if (null == cursor)
            throw new IllegalArgumentException ("cursor cannot be null");
        // todo: sanity checks
        mCursor = cursor;
    }

    /**
     * Get the current node factory.
     * @return The lexer's node factory.
     */
    public NodeFactory getNodeFactory ()
    {
        return (mFactory);
    }

    /**
     * Set the current node factory.
     * @param factory The node factory to be used by the lexer.
     */
    public void setNodeFactory (NodeFactory factory)
    {
        if (null == factory)
            throw new IllegalArgumentException ("node factory cannot be null");
        mFactory = factory;
    }

    public int getPosition ()
    {
        return (getCursor ().getPosition ());
    }

    public void setPosition (int position)
    {
        // todo: sanity checks
        getCursor ().setPosition (position);
    }

    /**
     * Get the current line number.
     * @return The line number the lexer's working on.
     */
    public int getCurrentLineNumber ()
    {
        return (getPage ().row (getCursor ()));
    }

    /**
     * Get the current line.
     * @return The string the lexer's working on.
     */
    public String getCurrentLine ()
    {
        return (getPage ().getLine (getCursor ()));
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
        return nextNode (false);
    }

    /**
     * Get the next node from the source.
     * @param quotesmart If <code>true</code>, strings ignore quoted contents.
     * @return A RemarkNode, StringNode or TagNode, or <code>null</code> if no
     * more lexemes are present.
     * @exception ParserException If there is a problem with the underlying page.
     */
    public Node nextNode (boolean quotesmart)
        throws
            ParserException
    {
        Cursor probe;
        char ch;
        Node ret;

        // debugging suppport
        if (-1 != mDebugLineTrigger)
        {
            Page page = getPage ();
            int lineno = page.row (mCursor);
            if (mDebugLineTrigger < lineno)
                mDebugLineTrigger = lineno + 1; // trigger on subsequent lines too
        }
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
                    ret = makeString (probe);
                else if ('/' == ch || '%' == ch || Character.isLetter (ch))
                {
                    probe.retreat ();
                    ret = parseTag (probe);
                }
                else if ('!' == ch)
                {
                    ch = mPage.getCharacter (probe);
                    if (0 == ch)
                        ret = makeString (probe);
                    else
                    {
                        if ('>' == ch) // handle <!>
                            ret = makeRemark (probe);
                        else
                        {
                            probe.retreat (); // remark and tag need this character
                            if ('-' == ch)
                                ret = parseRemark (probe, quotesmart);
                            else
                            {
                                probe.retreat (); // tag needs the previous one too
                                ret = parseTag (probe);
                            }
                        }
                    }
                }
                else
                    ret = parseString (probe, quotesmart);
                break;
            default:
                ret = parseString (probe, quotesmart);
                break;
        }

        return (ret);
    }

    /**
     * Advance the cursor through a JIS escape sequence.
     * @param cursor A cursor positioned within the escape sequence.
     */
    protected void scanJIS (Cursor cursor)
        throws
            ParserException
    {
        boolean done;
        char ch;
        int state;

        done = false;
        state = 0;
        while (!done)
        {
            ch = mPage.getCharacter (cursor);
            if (0 == ch)
                done = true;
            else
                switch (state)
                {
                    case 0:
                        if (0x1b == ch) // escape
                            state = 1;
                        break;
                    case 1:
                        if ('(' == ch)
                            state = 2;
                        else
                            state = 0;
                        break;
                    case 2:
                        if ('J' == ch)
                            done = true;
                        else
                            state = 0;
                        break;
                    default:
                        throw new IllegalStateException ("how the fuck did we get in state " + state);
                }
        }
    }

    /**
     * Parse a string node.
     * Scan characters until "&lt;/", "&lt;%", "&lt;!" or &lt; followed by a
     * letter is encountered, or the input stream is exhausted, in which
     * case <code>null</code> is returned.
     * @param cursor The position at which to start scanning.
     * @param quotesmart If <code>true</code>, strings ignore quoted contents.
     */
    protected Node parseString (Cursor cursor, boolean quotesmart)
        throws
            ParserException
    {
        boolean done;
        char ch;
        char quote;
        Node ret;

        done = false;
        quote = 0;
        while (!done)
        {
            ch = mPage.getCharacter (cursor);
            if (0 == ch)
                done = true;
            else if (0x1b == ch) // escape
            {
                ch = mPage.getCharacter (cursor);
                if (0 == ch)
                    done = true;
                else if ('$' == ch)
                {
                    ch = mPage.getCharacter (cursor);
                    if (0 == ch)
                        done = true;
                    else if ('B' == ch)
                        scanJIS (cursor);
                    else
                    {
                        cursor.retreat ();
                        cursor.retreat ();
                    }
                }
                else
                    cursor.retreat ();
            }
            else if (quotesmart && (0 == quote) && (('\'' == ch) || ('"' == ch)))
                quote = ch; // enter quoted state
            else if (quotesmart && (ch == quote))
                quote = 0; // exit quoted state
            else if ((0 == quote) && ('<' == ch))
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
                    // it's not a tag, so keep going, but check for quotes
                    cursor.retreat ();
                }
            }
        }

        return (makeString (cursor));
    }

    /**
     * Create a string node based on the current cursor and the one provided.
     */
    protected Node makeString (Cursor cursor)
        throws
            ParserException
    {
        int length;
        int begin;
        int end;
        Node ret;

        begin = mCursor.getPosition ();
        end = cursor.getPosition ();
        length = end - begin;
        if (0 != length)
        {   // got some characters
            mCursor = cursor;
            ret = getNodeFactory ().createStringNode (this.getPage (), begin, end);
        }
        else
            ret = null;
        
        return (ret);
    }

    private void whitespace (Vector attributes, int[] bookmarks)
    {
        if (bookmarks[1] > bookmarks[0])
            attributes.addElement (new PageAttribute (mPage, -1, -1, bookmarks[0], bookmarks[1], (char)0));
    }

    private void standalone (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new PageAttribute (mPage, bookmarks[1], bookmarks[2], -1, -1, (char)0));
    }

    private void empty (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new PageAttribute (mPage, bookmarks[1], bookmarks[2], bookmarks[2] + 1, -1, (char)0));
    }

    private void naked (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new PageAttribute (mPage, bookmarks[1], bookmarks[2], bookmarks[3], bookmarks[4], (char)0));
    }

    private void single_quote (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new PageAttribute (mPage, bookmarks[1], bookmarks[2], bookmarks[4] + 1, bookmarks[5], '\''));
    }

    private void double_quote (Vector attributes, int[] bookmarks)
    {
        attributes.addElement (new PageAttribute (mPage, bookmarks[1], bookmarks[2], bookmarks[5] + 1, bookmarks[6], '"'));
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
     * @param cursor The position at which to start scanning.
     */
    protected Node parseTag (Cursor cursor)
        throws
            ParserException
    {
        boolean done;
        char ch;
        int state;
        int[] bookmarks;
        Vector attributes;

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
                    if ((0 == ch) || ('>' == ch) || ('<' == ch))
                    {
                        if ('<' == ch)
                        {
                            // don't consume the opening angle
                            cursor.retreat ();
                            bookmarks[state + 1] = cursor.getPosition ();
                        }
                        whitespace (attributes, bookmarks);
                        done = true;
                    }
                    else if (!Character.isWhitespace (ch))
                    {
                        whitespace (attributes, bookmarks);
                        state = 1;
                    }
                    break;
                case 1: // within attribute name
                    if ((0 == ch) || ('>' == ch) || ('<' == ch))
                    {
                        if ('<' == ch)
                        {
                            // don't consume the opening angle
                            cursor.retreat ();
                            bookmarks[state + 1] = cursor.getPosition ();
                        }
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
                    else if (Character.isWhitespace (ch))
                    {
                        empty (attributes, bookmarks);
                        bookmarks[0] = bookmarks[3];
                        state = 0;
                    }
                    else
                        state = 3;
                    break;
                case 3: // within naked attribute value
                    if ((0 == ch) || ('>' == ch))
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

        // OK, before constructing the node, fix up erroneous attributes
        fixAttributes (attributes);

        return (makeTag (cursor, attributes));
    }

    /**
     * Try to resolve bad attributes.
     * Look for the following patterns and assume what they meant was the
     * construct on the right:
     * <p>Rule 1.
     * <pre>
     * att = -> att=
     * </pre>
     * An attribute named "=", converts a previous standalone attribute into
     * an empty attribute.
     * <p>Rule 2.
     * <pre>
     * att =value -> att=value
     * </pre>
     * An attribute name beginning with an equals sign, is the value of
     * a previous standalone attribute.
     * <p>Rule 3.
     * <pre>
     * att= "value" -> att="value"
     * </pre>
     * A quoted attribute name, is the value of a previous empty
     * attribute.
     * <p>Rule 4 and Rule 5.
     * <pre>
     * att="va"lue" -> att='va"lue'
     * att='val'ue' -> att="val'ue"
     * </pre>
     * An attribute name ending in a quote is a second part of a
     * similarly quoted value of a previous attribute. Note, this doesn't
     * change the quote value but it should, or the contained quote should be
     * removed.
     * <p>Note:
     * <pre>
     * att = "value" -> att="value"
     * </pre>
     * A quoted attribute name, is the value of a previous standalone
     * attribute separated by an attribute named "=" will be handled by
     * sequential application of rule 1 and 3.
     */
    protected void fixAttributes (Vector attributes) throws ParserException
    {
        PageAttribute attribute;
        Cursor cursor;
        char ch1; // name starting character
        char ch2; // name ending character
        PageAttribute prev1; // attribute prior to the current
        PageAttribute prev2; // attribute prior but one to the current
        char quote;

        cursor = new Cursor (getPage (), 0);
        prev1 = null;
        prev2 = null;
        // leave the name alone & start with second attribute
        for (int i = 2; i < attributes.size (); )
        {
            attribute = (PageAttribute)attributes.elementAt (i);
            if (!attribute.isWhitespace ())
            {
                cursor.setPosition (attribute.getNameStartPosition ());
                ch1 = attribute.getPage ().getCharacter (cursor);
                cursor.setPosition (attribute.getNameEndPosition () - 1);
                ch2 = attribute.getPage ().getCharacter (cursor);
                if ('=' == ch1)
                {   // possible rule 1 or 2
                    // check for a previous standalone, both rules need it, also check prev1 as a sanity check
                    if (null != prev2 && prev2.isStandAlone () && prev1.isWhitespace ())
                    {
                        if (1 == attribute.getNameEndPosition () - attribute.getNameStartPosition ())
                        {   // rule 1, an isolated equals sign
                            prev2.setValueStartPosition (attribute.getNameEndPosition ());
                            attributes.removeElementAt (i); // current
                            attributes.removeElementAt (i - 1); // whitespace
                            prev1 = prev2;
                            prev2 = null;
                            i--;
                            continue;
                        }
                        else
                        {
                            // rule 2, name starts with equals
                            prev2.setValueStartPosition (attribute.getNameStartPosition () + 1); // past the equals sign
                            prev2.setValueEndPosition (attribute.getNameEndPosition ());
                            attributes.removeElementAt (i); // current
                            attributes.removeElementAt (i - 1); // whitespace
                            prev1 = prev2;
                            prev2 = null;
                            i--;
                            continue;
                        }
                    }
                }
                else if ((('\'' == ch1) && ('\'' == ch2)) || (('"' == ch1) && ('"' == ch2)))
                {   // possible rule 3
                    // check for a previous empty, also check prev1 as a sanity check
                    if (null != prev2 && prev2.isEmpty () && prev1.isWhitespace ())
                    {   // TODO check that name has more than one character
                        prev2.setValueStartPosition (attribute.getNameStartPosition () + 1);
                        prev2.setValueEndPosition (attribute.getNameEndPosition () - 1);
                        prev2.setQuote (ch1);
                        attributes.removeElementAt (i); // current
                        attributes.removeElementAt (i - 1); // whitespace
                        prev1 = prev2;
                        prev2 = null;
                        i--;
                        continue;
                    }
                }
                else if (('\'' == ch2) || ('"' == ch2))
                {   // possible rule 4 or 5
                    // check for a previous valued attribute
                    if (null != prev1 && prev1.isValued ())
                    {   // check for a terminating quote of the same type
                        cursor.setPosition (prev1.getValueEndPosition ());
                        ch1 = prev1.getPage ().getCharacter (cursor); // crossing pages with cursor?
                        if (ch1 == ch2)
                        {
                            prev1.setValueEndPosition (attribute.getNameEndPosition () - 1);
                            attributes.removeElementAt (i); // current
                            continue;
                        }
                    }
                }
            }
            // shift and go on to next attribute
            prev2 = prev1;
            prev1 = attribute;
            i++;
        }
    }

    /**
     * Create a tag node based on the current cursor and the one provided.
     */
    protected Node makeTag (Cursor cursor, Vector attributes)
        throws
            ParserException
    {
        int length;
        int begin;
        int end;
        Node ret;

        begin = mCursor.getPosition ();
        end = cursor.getPosition ();
        length = end - begin;
        if (0 != length)
        {   // return tag based on second character, '/', '%', Letter (ch), '!'
            if (2 > length)
                // this is an error
                return (makeString (cursor));
            mCursor = cursor;
            ret = getNodeFactory ().createTagNode (this.getPage (), begin, end, attributes);
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
     * @param cursor The position at which to start scanning.
     * @param quotesmart If <code>true</code>, strings ignore quoted contents.
     */
    protected Node parseRemark (Cursor cursor, boolean quotesmart)
        throws
            ParserException
    {
        boolean done;
        char ch;
        int state;

        done = false;
        state = 0;
        while (!done)
        {
            ch = mPage.getCharacter (cursor);
            if (0 == ch)
                done = true;
            else
                switch (state)
                {
                    case 0: // prior to the first open delimiter
                        if ('>' == ch)
                            done = true;
                        if ('-' == ch)
                            state = 1;
                        else
                            return (parseString (cursor, quotesmart));
                        break;
                    case 1: // prior to the second open delimiter
                        if ('-' == ch)
                        {
                            // handle <!--> because netscape does
                            ch = mPage.getCharacter (cursor);
                            if (0 == ch)
                                done = true;
                            else if ('>' == ch)
                                done = true;
                            else
                            {
                                cursor.retreat ();
                                state = 2;
                            }                        
                        }
                        else
                            return (parseString (cursor, quotesmart));
                        break;
                    case 2: // prior to the first closing delimiter
                        if ('-' == ch)
                            state = 3;
                        else if (0 == ch)
                            return (parseString (cursor, quotesmart)); // no terminator
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

        return (makeRemark (cursor));
    }

    /**
     * Create a remark node based on the current cursor and the one provided.
     */
    protected Node makeRemark (Cursor cursor)
        throws
            ParserException
    {
        int length;
        int begin;
        int end;
        Node ret;

        begin = mCursor.getPosition ();
        end = cursor.getPosition ();
        length = end - begin;
        if (0 != length)
        {   // return tag based on second character, '/', '%', Letter (ch), '!'
            if (2 > length)
                // this is an error
                return (makeString (cursor));
            mCursor = cursor;
            ret = getNodeFactory ().createRemarkNode (this.getPage (), begin, end);
        }
        else
            ret = null;
        
        return (ret);
    }

    //
    // NodeFactory interface
    //

    /**
     * Create a new string node.
     * @param page The page the node is on.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public Node createStringNode (Page page,  int start, int end)
    {
        return (new StringNode (page, start, end));
    }

    /**
     * Create a new remark node.
     * @param page The page the node is on.
     * @param start The beginning position of the remark.
     * @param end The ending positiong of the remark.
     */
    public Node createRemarkNode (Page page,  int start, int end)
    {
        return (new RemarkNode (page, start, end));
    }

    /**
     * Create a new tag node.
     * Note that the attributes vector contains at least one element,
     * which is the tag name (standalone attribute) at position zero.
     * This can be used to decide which type of node to create, or
     * gate other processing that may be appropriate.
     * @param page The page the node is on.
     * @param start The beginning position of the tag.
     * @param end The ending positiong of the tag.
     * @param attributes The attributes contained in this tag.
     */
    public Node createTagNode (Page page, int start, int end, Vector attributes)
    {
        return (new TagNode (page, start, end, attributes));
    }

    /**
     * Mainline for command line operation
     */
    public static void main (String[] args)
        throws
            MalformedURLException,
            IOException,
            ParserException
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
