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
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.*;
import java.net.*;

import org.htmlparser.util.*;

/**
 * Represents the contents of an HTML page.
 * Contains the source of characters and an index of positions of line
 * separators (actually the first character position on the next line).
 */
public class Page
    implements
        Serializable
{
    /**
     * The default charset.
     * This should be <code>ISO-8859-1</code>,
     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
     * Another alias is "8859_1".
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * The URL this page is coming from.
     * Cached value of <code>getConnection().toExternalForm()</code> or
     * <code>setUrl()</code>.
     */
    protected String mUrl;

    /**
     * The source of characters.
     */
    protected Source mSource;

    /**
     * Character positions of the first character in each line.
     */
    protected PageIndex mIndex;
    
    /**
     * The connection this page is coming from or <code>null</code>.
     */
    protected transient URLConnection mConnection;

    /**
     * Messages for page not there (404).
     */
    static private final String[] mFourOhFour =
    {
        "The web site you seek cannot be located, but countless more exist",
        "You step in the stream, but the water has moved on. This page is not here.",
        "Yesterday the page existed. Today it does not. The internet is like that.",
        "That page was so big. It might have been very useful. But now it is gone.",
        "Three things are certain: death, taxes and broken links. Guess which has occured.",
        "Chaos reigns within. Reflect, repent and enter the correct URL. Order shall return.",
        "Stay the patient course. Of little worth is your ire. The page is not found.",
        "A non-existant URL reduces your expensive computer to a simple stone.",
        "Many people have visited that page. Today, you are not one of the lucky ones.",
        "Cutting the wind with a knife. Bookmarking a URL. Both are ephemeral.",
    };

    /**
     * Construct an empty page.
     */
    public Page ()
    {
        this ("");
    }

    /**
     * Construct a page reading from a URL connection.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @exception IOException If an i/o exception occurs creating the
     * source.
     * @exception ParserException An exception object wrapping a number of
     * possible error conditions, some of which are outlined below.
     * UnsupportedEncodingException if the character set specified in the
     * HTTP header is not supported.
     */
    public Page (URLConnection connection) throws ParserException
    {
        if (null == connection)
            throw new IllegalArgumentException ("connection cannot be null");
        setConnection (connection);
    }

    /**
     * Construct a page from a stream encoded with the given charset.
     * @param stream The source of bytes.
     * @param charset The encoding used.
     * If null, defaults to the <code>DEFAULT_CHARSET</code>.
     * @exception UnsupportedEncodingException If the given charset is not supported.
     */
    public Page (InputStream stream, String charset)
        throws
            UnsupportedEncodingException
    {
        if (null == stream)
            throw new IllegalArgumentException ("stream cannot be null");
        if (null == charset)
            charset = DEFAULT_CHARSET;
        mSource = new Source (stream, charset);
        mIndex = new PageIndex (this);
        mConnection = null;
        mUrl = null;
    }

    public Page (String text)
    {
        InputStream stream;

        if (null == text)
            throw new IllegalArgumentException ("text cannot be null");
        try
        {
            stream = new ByteArrayInputStream (text.getBytes (Page.DEFAULT_CHARSET));
            mSource = new Source (stream, Page.DEFAULT_CHARSET, text.length () + 1);
            mIndex = new PageIndex (this);
        }
        catch (UnsupportedEncodingException uee)
        {
            // this is unlikely, so we cover it up with a runtime exception
            throw new IllegalStateException (uee.getMessage ());
        }
        mConnection = null;
        mUrl = null;
    }

    //
    // Serialization support
    //

    /**
     * Serialize the page.
     * There are two modes to serializing a page based on the connected state.
     * If connected, the URL and the current offset is saved, while if
     * disconnected, the underling source is saved.
     * @param out The object stream to store this object in.
     */
    private void writeObject (ObjectOutputStream out)
        throws
            IOException
    {
        String href;
        Source source;
        PageIndex index;

        // two cases, reading from a URL and not
        if (null != getConnection ())
        {
            out.writeBoolean (true);
            out.writeInt (mSource.offset ()); // need to preread this much
            href = getUrl ();
            out.writeObject (href);
            setUrl (getConnection ().getURL ().toExternalForm ());
            source = getSource ();
            mSource = null; // don't serialize the source if we can avoid it
            index = mIndex;
            mIndex = null; // will get recreated; valid for the new page anyway?
            out.defaultWriteObject ();
            mSource = source;
            mIndex = index;
        }
        else
        {
            out.writeBoolean (false);
            href = getUrl ();
            out.writeObject (href);
            setUrl (null); // don't try and read a bogus URL
            out.defaultWriteObject ();
            setUrl (href);
        }
    }

    /**
     * Deserialize the page.
     * @see #writeObject
     * @param in The object stream to decode.
     */
    private void readObject (ObjectInputStream in)
        throws
            IOException,
            ClassNotFoundException
    {
        boolean fromurl;
        int offset;
        String href;
        URL url;
        Cursor cursor;

        fromurl = in.readBoolean ();
        if (fromurl)
        {
            offset = in.readInt ();
            href = (String)in.readObject ();
            in.defaultReadObject ();
            // open the URL
            if (null != getUrl ())
            {
                url = new URL (getUrl ());
                try
                {
                    setConnection (url.openConnection ());
                }
                catch (ParserException pe)
                {
                    throw new IOException (pe.getMessage ());
                }
            }
            cursor = new Cursor (this, 0);
            for (int i = 0; i < offset; i++)
                try
                {
                    getCharacter (cursor);
                }
                catch (ParserException pe)
                {
                    throw new IOException (pe.getMessage ());
                }
            setUrl (href);
        }
        else
        {
            href = (String)in.readObject ();
            in.defaultReadObject ();
            setUrl (href);
        }
    }

    /**
     * Reset the page by resetting the source of characters.
     */
    public void reset ()
    {
        getSource ().reset ();
        mIndex = new PageIndex (this); // todo: is this really necessary?
    }

    /**
     * Get the connection, if any.
     * @return The connection object for this page, or null if this page
     * is built from a stream or a string.
     */
    public URLConnection getConnection ()
    {
        return (mConnection);
    }

    /**
     * Set the URLConnection to be used by this page.
     * Starts reading from the given connection.
     * This also resets the current url.
     * @param connection The connection to use.
     * It will be connected by this method.
     * @exception ParserException If the <code>connect()</code> method fails,
     * or an I/O error occurs opening the input stream or the character set
     * designated in the HTTP header is unsupported.
     */
    public void setConnection (URLConnection connection)
        throws
            ParserException
    {
        Stream stream;
        String charset;
        

        mConnection = connection;
        try
        {
            getConnection ().connect ();
        }
        catch (UnknownHostException uhe)
        {
            int message = (int)(Math.random () * mFourOhFour.length);
            throw new ParserException (mFourOhFour[message], uhe);
        }
        catch (IOException ioe)
        {
            throw new ParserException (ioe.getMessage (), ioe);
        }
        charset = getCharacterSet ();
        try
        {
            stream = new Stream (getConnection ().getInputStream ());
            try
            {
                mSource = new Source (stream, charset);
            }
            catch (UnsupportedEncodingException uee)
            {
                StringBuffer msg;
                String message;

                msg = new StringBuffer (1024);
                msg.append (getConnection ().getURL ().toExternalForm ());
                msg.append (" has an encoding (");
                msg.append (charset);
                msg.append (") which is not supported, using ");
                msg.append (DEFAULT_CHARSET);
                System.out.println (msg.toString ());
                charset = DEFAULT_CHARSET;
                mSource = new Source (stream, charset);
            }
        }
        catch (IOException ioe)
        {
            throw new ParserException (ioe.getMessage (), ioe);
        }
        mUrl = connection.getURL ().toExternalForm ();
        mIndex = new PageIndex (this);
    }

    /**
     * Get the URL for this page.
     * This is only available if the page has a connection
     * (<code>getConnection()</code> returns non-null), or the document base has
     * been set via a call to <code>setUrl()</code>.
     * @return The url for the connection, or <code>null</code> if there is
     * no conenction or the document base has not been set.
     */
    public String getUrl ()
    {
        return (mUrl);
    }

    /**
     * Set the URL for this page.
     * This doesn't affect the contents of the page, just the interpretation
     * of relative links from this point forward.
     * @param url The new URL.
     */
    public void setUrl (String url)
    {
        mUrl = url;
    }

    /**
     * Get the source this page is reading from.
     */
    public Source getSource ()
    {
        return (mSource);
    }

    /**
     * Read the character at the cursor position.
     * The cursor position can be behind or equal to the current source position.
     * Returns end of lines (EOL) as \n, by converting \r and \r\n to \n,
     * and updates the end-of-line index accordingly
     * Advances the cursor position by one (or two in the \r\n case).
     * @param cursor The position to read at.
     * @return The character at that position, and modifies the cursor to
     * prepare for the next read. If the source is exhausted a zero is returned.
     * @exception ParserException If an IOException on the underlying source
     * occurs, or an attemp is made to read characters in the future (the
     * cursor position is ahead of the underlying stream)
     */
    public char getCharacter (Cursor cursor)
        throws
            ParserException
    {
        int i;
        char ret;

        i = cursor.getPosition ();
        if (mSource.mOffset < i)
            // hmmm, we could skip ahead, but then what about the EOL index
            throw new ParserException ("attempt to read future characters from source");
        else if (mSource.mOffset == i)
            try
            {
                i = mSource.read ();
                if (0 > i)
                    ret = 0;
                else
                {
                    ret = (char)i;
                    cursor.advance ();
                }
            }
            catch (IOException ioe)
            {
                throw new ParserException (
                    "problem reading a character at position "
                    + cursor.getPosition (), ioe);
            }
        else
        {
            // historic read
            ret = mSource.mBuffer[i];
            cursor.advance ();
        }

        // handle \r
        if ('\r' == ret)
        {   // switch to single character EOL
            ret = '\n';

            // check for a \n in the next position
            if (mSource.mOffset == cursor.getPosition ())
                try
                {
                    i = mSource.read ();
                    if (-1 == i)
                    {
                        // do nothing
                    }
                    else if ('\n' == (char)i)
                        cursor.advance ();
                    else
                        try
                        {
                            mSource.unread ();
                        }
                        catch (IOException ioe)
                        {
                            throw new ParserException (
                                "can't unread a character at position "
                                + cursor.getPosition (), ioe);
                        }
                }
                catch (IOException ioe)
                {
                    throw new ParserException (
                        "problem reading a character at position "
                        + cursor.getPosition (), ioe);
                }
            else if ('\n' == mSource.mBuffer[cursor.getPosition ()])
                cursor.advance ();
        }
        if ('\n' == ret)
            // update the EOL index in any case
            mIndex.add (cursor);

        return (ret);
    }

    /**
     * Try and extract the character set from the HTTP header.
     * @return The character set name to use for this HTML page.
     */
    public String getCharacterSet ()
    {
        final String CONTENT_TYPE_STRING = "Content-Type";
        URLConnection connection;
        String string;
        String ret;

        ret = DEFAULT_CHARSET;
        connection = getConnection ();
        if (null != connection)
        {
            string = connection.getHeaderField (CONTENT_TYPE_STRING);
            if (null != string)
                ret = getCharset (string);
        }

        return (ret);
    }

    /**
     * Get a CharacterSet name corresponding to a charset parameter.
     * @param content A text line of the form:
     * <pre>
     * text/html; charset=Shift_JIS
     * </pre>
     * which is applicable both to the HTTP header field Content-Type and
     * the meta tag http-equiv="Content-Type".
     * Note this method also handles non-compliant quoted charset directives such as:
     * <pre>
     * text/html; charset="UTF-8"
     * </pre>
     * and
     * <pre>
     * text/html; charset='UTF-8'
     * </pre>
     * @return The character set name to use when reading the input stream.
     * For JDKs that have the Charset class this is qualified by passing
     * the name to findCharset() to render it into canonical form.
     * If the charset parameter is not found in the given string, the default
     * character set is returned.
     * @see #findCharset
     * @see #DEFAULT_CHARSET
     */
    public String getCharset (String content)
    {
        final String CHARSET_STRING = "charset";
        int index;
        String ret;

        ret = DEFAULT_CHARSET;
        if (null != content)
        {
            index = content.indexOf (CHARSET_STRING);

            if (index != -1)
            {
                content = content.substring (index + CHARSET_STRING.length ()).trim ();
                if (content.startsWith ("="))
                {
                    content = content.substring (1).trim ();
                    index = content.indexOf (";");
                    if (index != -1)
                        content = content.substring (0, index);

                    //remove any double quotes from around charset string
                    if (content.startsWith ("\"") && content.endsWith ("\"") && (1 < content.length ()))
                        content = content.substring (1, content.length () - 1);

                    //remove any single quote from around charset string
                    if (content.startsWith ("'") && content.endsWith ("'") && (1 < content.length ()))
                        content = content.substring (1, content.length () - 1);

                    ret = findCharset (content, ret);

                    // Charset names are not case-sensitive;
                    // that is, case is always ignored when comparing charset names.
                    if (!ret.equalsIgnoreCase (content))
                    {
                        System.out.println (
                            "detected charset \""
                            + content
                            + "\", using \""
                            + ret
                            + "\"");
                    }
                }
            }
        }

        return (ret);
    }

    /**
     * Lookup a character set name.
     * <em>Vacuous for JVM's without <code>java.nio.charset</code>.</em>
     * This uses reflection so the code will still run under prior JDK's but
     * in that case the default is always returned.
     * @param name The name to look up. One of the aliases for a character set.
     * @param _default The name to return if the lookup fails.
     */
    public String findCharset (String name, String _default)
    {
        String ret;

        try
        {
            Class cls;
            Method method;
            Object object;

            cls = Class.forName ("java.nio.charset.Charset");
            method = cls.getMethod ("forName", new Class[] { String.class });
            object = method.invoke (null, new Object[] { name });
            method = cls.getMethod ("name", new Class[] { });
            object = method.invoke (object, new Object[] { });
            ret = (String)object;
        }
        catch (ClassNotFoundException cnfe)
        {
            // for reflection exceptions, assume the name is correct
            ret = name;
        }
        catch (NoSuchMethodException nsme)
        {
            // for reflection exceptions, assume the name is correct
            ret = name;
        }
        catch (IllegalAccessException ia)
        {
            // for reflection exceptions, assume the name is correct
            ret = name;
        }
        catch (InvocationTargetException ita)
        {
            // java.nio.charset.IllegalCharsetNameException
            // and java.nio.charset.UnsupportedCharsetException
            // return the default
            ret = _default;
            System.out.println (
                "unable to determine cannonical charset name for "
                + name
                + " - using "
                + _default);
        }

        return (ret);
    }

    /**
     * Get the current encoding being used.
     * @return The encoding used to convert characters.
     */
    public String getEncoding ()
    {
        return (mSource.getEncoding ());
    }

    /**
     * Resets this page and begins reading from the source with the
     * given character set.
     * @param character_set The character set to use to convert bytes into
     * characters.
     */
    public void setEncoding (String character_set)
        throws
            ParserException
    {
        InputStream stream;
        
        stream = getSource ().getStream ();
        try
        {
            stream.reset ();
            if (!getEncoding ().equals (character_set))
            {
                mSource = new Source (stream, character_set);
                mIndex = new PageIndex (this);
            }
        }
        catch (IOException ioe)
        {
            throw new ParserException (ioe.getMessage (), ioe);
        }
    }

    /**
     * Get the line number for a cursor.
     * @param cursor The character offset into the page.
     * @return The line number the character is in.
     */
    public int row (Cursor cursor)
    {
        return (mIndex.row (cursor));
    }

    /**
     * Get the line number for a cursor.
     * @param position The character offset into the page.
     * @return The line number the character is in.
     */
    public int row (int position)
    {
        return (mIndex.row (position));
    }

    /**
     * Get the column number for a cursor.
     * @param cursor The character offset into the page.
     * @return The character offset into the line this cursor is on.
     */
    public int column (Cursor cursor)
    {
        return (mIndex.column (cursor));
    }

    /**
     * Get the column number for a cursor.
     * @param position The character offset into the page.
     * @return The character offset into the line this cursor is on.
     */
    public int column (int position)
    {
        return (mIndex.column (position));
    }

    /**
     * Get the text identified by the given limits.
     * @param start The starting position, zero based.
     * @param end The ending position
     * (exclusive, i.e. the character at the ending position is not included),
     * zero based.
     * @return The text from <code>start</code> to <code>end</code>.
     * @see #getText(StringBuffer, int, int)
     * @exception IllegalArgumentException If an attempt is made to get
     * characters ahead of the current source offset (character position).
     */
    public String getText (int start, int end)
    {
        StringBuffer ret;

        ret = new StringBuffer (Math.abs (end - start));
        getText (ret, start, end);

        return (ret.toString ());
    }

    /**
     * Put the text identified by the given limits into the given buffer.
     * @param buffer The accumulator for the characters.
     * @param start The starting position, zero based.
     * @param end The ending position
     * (exclusive, i.e. the character at the ending position is not included),
     * zero based.
     * @exception IllegalArgumentException If an attempt is made to get
     * characters ahead of the current source offset (character position).
     */
    public void getText (StringBuffer buffer, int start, int end)
    {
        int length;

        if ((mSource.mOffset < start) || (mSource.mOffset < end))
            throw new IllegalArgumentException ("attempt to extract future characters from source");
        if (end < start)
        {
            length = end;
            end = start;
            start = length;
        }
        length = end - start;
        buffer.append (mSource.mBuffer, start, length);
    }

    /**
     * Get all text read so far from the source.
     * @return The text from the source.
     * @see #getText(StringBuffer)
     */
    public String getText ()
    {
        StringBuffer ret;

        ret = new StringBuffer (mSource.mOffset);
        getText (ret);

        return (ret.toString ());
    }

    /**
     * Put all text read so far from the source into the given buffer.
     * @param buffer The accumulator for the characters.
     * @see #getText(StringBuffer,int,int)
     */
    public void getText (StringBuffer buffer)
    {
        getText (buffer, 0, mSource.mOffset);
    }

    /**
     * Get the text line the position of the cursor lies on.
     * @param cursor The position to calculate for.
     * @return The contents of the URL or file corresponding to the line number
     * containg the cursor position.
     */
    public String getLine (Cursor cursor)
    {
        int line;
        int start;
        int end;

        line = row (cursor);
        start = mIndex.elementAt (line);
        line++;
        end = mIndex.last ();
        if (end <= line)
            end = mIndex.elementAt (end);
        else
            end = mSource.mOffset;
        return (getText (start,  end));
    }

// todo refactor into common code method:

    /**
     * Get the text line the position of the cursor lies on.
     * @param cursor The position to calculate for.
     * @return The contents of the URL or file corresponding to the line number
     * containg the cursor position.
     */
    public String getLine (int position)
    {
        int line;
        int start;
        int end;

        line = row (position);
        start = mIndex.elementAt (line);
        line++;
        end = mIndex.last ();
        if (end <= line)
            end = mIndex.elementAt (end);
        else
            end = mSource.mOffset;
        return (getText (start,  end));
    }
}

//    /**
//     * The default charset.
//     * This should be <code>ISO-8859-1</code>,
//     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
//     * Another alias is "8859_1".
//     */
//    protected static final String DEFAULT_CHARSET = "ISO-8859-1";
//
//    /**
//     *  Trigger for charset detection.
//     */
//    protected static final String CHARSET_STRING = "charset";
//
//
//    /**
//     * Try and extract the character set from the HTTP header.
//     * @param connection The connection with the charset info.
//     * @return The character set name to use for this HTML page.
//     */
//    protected String getCharacterSet (URLConnection connection)
//    {
//        final String field = "Content-Type";
//
//        String string;
//        String ret;
//
//        ret = DEFAULT_CHARSET;
//        string = connection.getHeaderField (field);
//        if (null != string)
//            ret = getCharset (string);
//
//        return (ret);
//    }
//
//    /**
//     * Get a CharacterSet name corresponding to a charset parameter.
//     * @param content A text line of the form:
//     * <pre>
//     * text/html; charset=Shift_JIS
//     * </pre>
//     * which is applicable both to the HTTP header field Content-Type and
//     * the meta tag http-equiv="Content-Type".
//     * Note this method also handles non-compliant quoted charset directives such as:
//     * <pre>
//     * text/html; charset="UTF-8"
//     * </pre>
//     * and
//     * <pre>
//     * text/html; charset='UTF-8'
//     * </pre>
//     * @return The character set name to use when reading the input stream.
//     * For JDKs that have the Charset class this is qualified by passing
//     * the name to findCharset() to render it into canonical form.
//     * If the charset parameter is not found in the given string, the default
//     * character set is returned.
//     * @see ParserHelper#findCharset
//     * @see #DEFAULT_CHARSET
//     */
//    protected String getCharset(String content)
//    {
//        int index;
//        String ret;
//
//        ret = DEFAULT_CHARSET;
//        if (null != content)
//        {
//            index = content.indexOf(CHARSET_STRING);
//
//            if (index != -1)
//            {
//                content = content.substring(index + CHARSET_STRING.length()).trim();
//                if (content.startsWith("="))
//                {
//                    content = content.substring(1).trim();
//                    index = content.indexOf(";");
//                    if (index != -1)
//                        content = content.substring(0, index);
//
//                    //remove any double quotes from around charset string
//                    if (content.startsWith ("\"") && content.endsWith ("\"") && (1 < content.length ()))
//                        content = content.substring (1, content.length () - 1);
//
//                    //remove any single quote from around charset string
//                    if (content.startsWith ("'") && content.endsWith ("'") && (1 < content.length ()))
//                        content = content.substring (1, content.length () - 1);
//
//                    ret = ParserHelper.findCharset(content, ret);
//                    // Charset names are not case-sensitive;
//                    // that is, case is always ignored when comparing charset names.
//                    if (!ret.equalsIgnoreCase(content))
//                    {
//                        feedback.info (
//                            "detected charset \""
//                            + content
//                            + "\", using \""
//                            + ret
//                            + "\"");
//                    }
//                }
//            }
//        }
//
//        return (ret);
//    }
//

