// HTMLParser Library v1_4_20030727 - A java-based parser for HTML
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

package org.htmlparser.lexer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * A buffered source of characters.
 * A Source is very similar to a the following construct:
 * <pre>
 * new InputStreamReader (new BufferedInputStream (connection.getInputStream ()), charset)
 * </pre>
 * It differs from the above, in two ways:
 * <li>the fetching of bytes from the connection's input stream may be asynchronous</li>
 * <li>the character set may be changed, which resets the input stream</li>
 * 
 */
public class Source extends Reader
{
    /**
     * An initial buffer size.
     */
    protected static final int BUFFER_SIZE = 4096;

    /**
     * Return value when no more characters are left.
     */
    public static final int EOF = -1;

    /**
     * The stream of bytes.
     */
    protected Stream mStream;

    /**
     * The converter from bytes to characters.
     */
    protected InputStreamReader mReader;

    /**
     * The characters read so far.
     */
    public volatile char[] mBuffer;

    /**
     * The number of valid bytes in the buffer.
     */
    public volatile int mLevel;

    /**
     * The offset of the next byte returned by read().
     */
    protected int mOffset;

    /**
     * The bookmark.
     */
    protected int mMark;

    /**
     * Create a source of characters using the default character set.
     * @param stream The stream of bytes to use.
     */
    public Source (Stream stream)
        throws
            UnsupportedEncodingException
    {
        this (stream, null);
    }

    /**
     * Create a source of characters.
     * @param stream The stream of bytes to use.
     * @param charset The character set used in encoding the stream.
     */
    public Source (Stream stream, String charset)
        throws
            UnsupportedEncodingException
    {
        if (null == stream)
            stream = new Stream (null);
        mStream = stream;
        if (null == charset)
            mReader = new InputStreamReader (stream);
        else
            mReader = new InputStreamReader (stream, charset);
        mBuffer = null;
        mLevel = 0;
        mOffset = 0;
        mMark = -1;
    }

    /**
     * Fetch more characters from the underlying reader.
     * Has no effect if the underlying reader has been drained.
     * @param min The minimum to read.
     * @exception IOException If the underlying reader read() throws one.
     */
    protected void fill (int min)
        throws
            IOException
    {
        char[] buffer;
        int read;

        if (null != mReader) // mReader goes null when it's been sucked dry
        {
            // get some buffer space
            // unknown length... keep doubling
            if (null == mBuffer)
            {
                mBuffer = new char[Math.max (BUFFER_SIZE, min)];
                buffer = mBuffer;
            }
            else
            {
                read = Math.max (BUFFER_SIZE / 2, min);
                if (mBuffer.length - mLevel < read)
                    buffer = new char[Math.max (mBuffer.length * 2, mBuffer.length + min)];
                else
                    buffer = mBuffer;
            }

            // read into the end of the 'new' buffer
            read = mReader.read (buffer, mLevel, buffer.length - mLevel);
            if (-1 == read)
            {
                mReader.close ();
                mReader = null;
            }
            else
            {
                if (mBuffer != buffer)
                {   // copy the bytes previously read
                    System.arraycopy (mBuffer, 0, buffer, 0, mLevel);
                    mBuffer = buffer;
                }
                mLevel += read;
            }
        }
    }

    //
    // Reader overrides
    //

    /**
     * Close the stream.  Once a stream has been closed, further read(),
     * ready(), mark(), or reset() invocations will throw an IOException.
     * Closing a previously-closed stream, however, has no effect.
     * @exception IOException  If an I/O error occurs
     */
    public void close () throws IOException
    {
        mStream = null;
        if (null != mReader)
            mReader.close ();
        mReader = null;
        mBuffer = null;
        mLevel = 0;
        mOffset = 0;
        mMark = -1;
    }
    
    /**
     * Read a single character.
     * This method will block until a character is available,
     * an I/O error occurs, or the end of the stream is reached.
     * @return The character read, as an integer in the range 0 to 65535
     * (<tt>0x00-0xffff</tt>), or -1 if the end of the stream has
     * been reached
     * @exception IOException If an I/O error occurs.
     */
    public int read () throws IOException
    {
        int ret;
        
        if (null == mStream) // mStream goes null on close()
            throw new IOException ("reader is closed");
        if (mLevel - mOffset < 1)
            fill (1);
        if (mOffset >= mLevel)
            ret = EOF;
        else
        {
            ret = mBuffer[mOffset];
            mOffset++;
        }
            
        return (ret);
    }
    
    /**
     * Read characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     * @param cbuf Destination buffer
     * @param off Offset at which to start storing characters
     * @param len Maximum number of characters to read
     * @return The number of characters read, or -1 if the end of the
     * stream has been reached
     * @exception IOException If an I/O error occurs.
     */
    public int read (char[] cbuf, int off, int len) throws IOException
    {
        int ret;

        if (null == mStream) // mStream goes null on close()
            throw new IOException ("reader is closed");
        if ((null == cbuf) || (0 > off) || (0 > len))
            throw new IOException ("illegal argument read ("
                + ((null == cbuf) ? "null" : "cbuf")
                + ", " + off + ", " + len + ")");
        if (mLevel - mOffset < len)
            fill (len - (mLevel - mOffset)); // minimum to satisfy this request
        if (mOffset >= mLevel)
            ret = EOF;
        else
        {
            ret = Math.min (mLevel - mOffset, len);
            System.arraycopy (mBuffer, mOffset, cbuf, off, ret);
            mOffset += ret;
        }
            
        return (ret);
    }
    
    /**
     * Reset the stream.  If the stream has been marked, then attempt to
     * reposition it at the mark.  If the stream has not been marked, then
     * attempt to reset it in some way appropriate to the particular stream,
     * for example by repositioning it to its starting point.  Not all
     * character-input streams support the reset() operation, and some support
     * reset() without supporting mark().
     * @exception  IOException  If the stream has not been marked,
     * or if the mark has been invalidated,
     * or if the stream does not support reset(),
     * or if some other I/O error occurs
     */
    public void reset () throws IOException
    {
        if (null == mStream) // mStream goes null on close()
            throw new IOException ("reader is closed");
        if (-1 != mMark)
            mOffset = mMark;
        else
            mOffset = 0;
    }
    
    /**
     * Tell whether this stream supports the mark() operation. 
     * @return <code>true</code> if and only if this stream supports the mark operation.
     */
    public boolean markSupported ()
    {
        return (true);
    }
    
    /**
     * Mark the present position in the stream.  Subsequent calls to reset()
     * will attempt to reposition the stream to this point.  Not all
     * character-input streams support the mark() operation.
     * @param  readAheadLimit <em>Not used.</em>
     * @exception IOException <em>Never thrown</em>.
     *
     */
    public void mark (int readAheadLimit) throws IOException
    {
        if (null == mStream) // mStream goes null on close()
            throw new IOException ("reader is closed");
        mMark = mOffset;
    }
    
    /**
     * Tell whether this stream is ready to be read.
     * @return <code>true</code> if the next read() is guaranteed not to block
     * for input, <code>false</code> otherwise.
     * Note that returning false does not guarantee that the next read will block.
     * @exception IOException <em>Never thrown</em>.
     */
    public boolean ready () throws IOException
    {
        if (null == mStream) // mStream goes null on close()
            throw new IOException ("reader is closed");
        return (mOffset < mLevel);
    }
    
    /**
     * Skip characters.
     * This method will block until some characters are available,
     * an I/O error occurs, or the end of the stream is reached.
     * <em>Note: n is treated as an int</em>
     * @param n The number of characters to skip.
     * @return The number of characters actually skipped
     * @exception IllegalArgumentException If <code>n</code> is negative.
     * @exception IOException If an I/O error occurs.
     */
    public long skip (long n) throws IOException
    {
        long ret;

        if (null == mStream) // mStream goes null on close()
            throw new IOException ("reader is closed");
        if (mLevel - mOffset < n)
            fill ((int)(n - (mLevel - mOffset))); // minimum to satisfy this request
        if (mOffset >= mLevel)
            ret = EOF;
        else
        {
            ret = Math.min (mLevel - mOffset, n);
            mOffset += ret;
        }
        
        return (ret);
    }
}
