// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

import junit.framework.TestCase;

import org.htmlparser.lexer.Stream;
import org.htmlparser.lexer.Source;

public class SourceTests extends TestCase
{
    /**
     * The default charset.
     * This should be <code>ISO-8859-1</code>,
     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
     * Another alias is "8859_1".
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * Test the first level stream class.
     */
    public SourceTests (String name)
    {
        super (name);
    }

    /**
     * Test initialization with a null value.
     */
    public void testNull () throws IOException
    {
        Source source;
        
        source = new Source (null);
        assertTrue ("erroneous character", -1 == source.read ());
    }

    /**
     * Test initialization with a null charset name.
     */
    public void testEmpty () throws IOException
    {
        Source source;
        
        source = new Source (new Stream (new ByteArrayInputStream (new byte[0])), null);
        assertTrue ("erroneous character", -1 == source.read ());
    }

    /**
     * Test initialization with an input stream having only one byte.
     */
    public void testOneByte () throws IOException
    {
        Source source;
        
        source = new Source (new Stream (new ByteArrayInputStream (new byte[] { (byte)0x42 })), null);
        assertTrue ("erroneous character", 'B' == source.read ());
        assertTrue ("extra character", -1 == source.read ());
    }

    /**
     * Test close.
     */
    public void testClose () throws IOException
    {
        Source source;

        source = new Source (new Stream (new ByteArrayInputStream ("hello word".getBytes ())), null);
        assertTrue ("no character", -1 != source.read ());
        source.destroy ();
        try
        {
            source.read ();
            fail ("not closed");
        }
        catch (IOException ioe)
        {
            // expected outcome
        }
   }

    /**
     * Test reset.
     */
    public void testReset () throws IOException
    {
        String reference;
        Source source;
        StringBuffer buffer;
        int c;
        
        reference = "Now is the time for all good men to come to the aid of the party";
        source = new Source (new Stream (new ByteArrayInputStream (reference.getBytes (DEFAULT_CHARSET))), null);
        buffer = new StringBuffer (reference.length ());
        while (-1 != (c = source.read ()))
            buffer.append ((char)c);
        assertTrue ("string incorrect", reference.equals (buffer.toString ()));
        source.reset ();
        buffer.setLength (0);
        while (-1 != (c = source.read ()))
            buffer.append ((char)c);
        assertTrue ("string incorrect", reference.equals (buffer.toString ()));
        source.close ();
    }

    /**
     * Test reset in the middle of reading.
     */
    public void testMidReset () throws IOException
    {
        String reference;
        Source source;
        StringBuffer buffer;
        int c;
        
        reference = "Now is the time for all good men to come to the aid of the party";
        source = new Source (new Stream (new ByteArrayInputStream (reference.getBytes (DEFAULT_CHARSET))), null);
        buffer = new StringBuffer (reference.length ());
        for (int i = 0; i < 25; i++)
            buffer.append ((char)source.read ());
        source.reset ();
        for (int i = 0; i < 25; i++)
            source.read ();
        while (-1 != (c = source.read ()))
            buffer.append ((char)c);
        assertTrue ("string incorrect", reference.equals (buffer.toString ()));
        source.close ();
    }

    /**
     * Test mark/reset in the middle of reading.
     */
    public void testMarkReset () throws IOException
    {
        String reference;
        Source source;
        StringBuffer buffer;
        int c;
        
        reference = "Now is the time for all good men to come to the aid of the party";
        source = new Source (new Stream (new ByteArrayInputStream (reference.getBytes (DEFAULT_CHARSET))), null);
        assertTrue ("not markable", source.markSupported ());
        buffer = new StringBuffer (reference.length ());
        for (int i = 0; i < 25; i++)
            buffer.append ((char)source.read ());
        source.mark (88);
        for (int i = 0; i < 25; i++)
            source.read ();
        source.reset ();
        while (-1 != (c = source.read ()))
            buffer.append ((char)c);
        assertTrue ("string incorrect", reference.equals (buffer.toString ()));
        source.close ();
    }

    /**
     * Test skip.
     */
    public void testSkip () throws IOException
    {
        String part1;
        String part2;
        String part3;
        String reference;
        Source source;
        StringBuffer buffer;
        int c;
       
        part1 = "Now is the time ";
        part2 = "for all good men ";
        part3 = "to come to the aid of the party";
        reference = part1 + part2 + part3;
        source = new Source (new Stream (new ByteArrayInputStream (reference.getBytes (DEFAULT_CHARSET))), null);
        buffer = new StringBuffer (reference.length ());
        for (int i = 0; i < part1.length (); i++)
            buffer.append ((char)source.read ());
        source.skip (part2.length ());
        while (-1 != (c = source.read ()))
            buffer.append ((char)c);
        assertTrue ("string incorrect", (part1 + part3).equals (buffer.toString ()));
        source.close ();
    }

    /**
     * Test multi-byte read.
     */
    public void testMultByte () throws IOException
    {
        String reference;
        Source source;
        char[] buffer;
        
        reference = "Now is the time for all good men to come to the aid of the party";
        source = new Source (new Stream (new ByteArrayInputStream (reference.getBytes (DEFAULT_CHARSET))), null);
        buffer = new char[reference.length ()];
        source.read (buffer, 0, buffer.length);
        assertTrue ("string incorrect", reference.equals (new String (buffer)));
        assertTrue ("extra character", -1 == source.read ());
        source.close ();
    }

    /**
     * Test positioned multi-byte read.
     */
    public void testPositionedMultByte () throws IOException
    {
        String part1;
        String part2;
        String part3;
        String reference;
        Source source;
        char[] buffer;
        int c;
        int length;
       
        part1 = "Now is the time ";
        part2 = "for all good men ";
        part3 = "to come to the aid of the party";
        reference = part1 + part2 + part3;
        source = new Source (new Stream (new ByteArrayInputStream (reference.getBytes (DEFAULT_CHARSET))), null);
        buffer = new char[reference.length ()];
        for (int i = 0; i < part1.length (); i++)
            buffer[i] = (char)source.read ();
        length = source.read (buffer, part1.length (), part2.length ());
        assertTrue ("incorrect length", part2.length () == length);
        length += part1.length ();
        for (int i = 0; i < part3.length (); i++)
            buffer[i + length] = (char)source.read ();
        assertTrue ("string incorrect", reference.equals (new String (buffer)));
        assertTrue ("extra character", -1 == source.read ());
        source.close ();
    }

    /**
     * Test ready.
     */
    public void testReady () throws IOException
    {
        Source source;
        
        source = new Source (new Stream (new ByteArrayInputStream (new byte[] { (byte)0x42, (byte)0x62 })), null);
        assertTrue ("ready?", !source.ready ());
        assertTrue ("erroneous character", 'B' == source.read ());
        assertTrue ("not ready", source.ready ());
        assertTrue ("erroneous character", 'b' == source.read ());
        assertTrue ("ready?", !source.ready ());
        assertTrue ("extra character", -1 == source.read ());
    }

    /**
     * Test that the same characters are returned as with another reader.
     */
    public void testSameChars () throws IOException
    {
        String link;
        ArrayList chars1;
        ArrayList chars2;
        URL url;
        URLConnection connection;
        InputStreamReader in;
        int c;
        Source source;
        int index;

        // pick a big file
        link = "http://sourceforge.net/projects/htmlparser/HTMLParser_Coverage.html";
        chars1 = new ArrayList ();
        chars2 = new ArrayList ();
        try
        {
            url = new URL (link);
            connection = url.openConnection ();
            connection.connect ();
            in = new InputStreamReader (new BufferedInputStream (connection.getInputStream ()), DEFAULT_CHARSET);
            while (-1 != (c = in.read ()))
                chars1.add (new Character ((char)c));
            in.close ();

            connection = url.openConnection ();
            connection.connect ();
            source = new Source (new Stream (connection.getInputStream ()));
            while (-1 != (c = source.read ()))
                chars2.add (new Character ((char)c));
            source.close ();

            index = 0;
            while (index < chars1.size ())
            {
                assertEquals ("characters differ at position " + index, chars1.get (index), chars2.get (index));
                index++;
            }
            assertTrue ("extra characters", index == chars2.size ());
        }
        catch (MalformedURLException murle)
        {
            fail ("bad url " + link);
        }
    }
}
