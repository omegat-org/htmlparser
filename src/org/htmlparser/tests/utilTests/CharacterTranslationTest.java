// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Derick Oswald
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

package org.htmlparser.tests.utilTests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.Translate;

public class CharacterTranslationTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.utilTests.CharacterTranslationTest", "CharacterTranslationTest");
    }

    public CharacterTranslationTest (String name)
    {
        super (name);
    }

    public void testInitialCharacterEntityReference ()
    {
        assertEquals (
            "character entity reference at start of string doesn't work",
            "\u00f7 is the division sign.",
            Translate.decode ("&divide; is the division sign."));
    }

    public void testInitialNumericCharacterReference ()
    {
        assertEquals (
            "numeric character reference at start of string doesn't work",
            "\u00f7 is the division sign.",
            Translate.decode ("&#247; is the division sign."));
    }

    public void testInitialCharacterEntityReferenceWithoutSemi ()
    {
        assertEquals (
            "character entity reference without a semicolon at start of string doesn't work",
            "\u00f7 is the division sign.",
            Translate.decode ("&divide is the division sign."));
    }

    public void testInitialNumericCharacterReferenceWithoutSemi ()
    {
        assertEquals (
            "numeric character reference without a semicolon at start of string doesn't work",
            "\u00f7 is the division sign.",
            Translate.decode ("&#247 is the division sign."));
    }

    public void testFinalCharacterEntityReference ()
    {
        assertEquals (
            "character entity reference at end of string doesn't work",
            "The division sign (\u00f7) is \u00f7",
            Translate.decode ("The division sign (\u00f7) is &divide;"));
    }

    public void testFinalNumericCharacterReference ()
    {
        assertEquals (
            "numeric character reference at end of string doesn't work",
            "The division sign (\u00f7) is \u00f7",
            Translate.decode ("The division sign (\u00f7) is &#247;"));
    }

    public void testFinalCharacterEntityReferenceWithoutSemi ()
    {
        assertEquals (
            "character entity reference without a semicolon at end of string doesn't work",
            "The division sign (\u00f7) is \u00f7",
            Translate.decode ("The division sign (\u00f7) is &divide"));
    }

    public void testFinalNumericCharacterReferenceWithoutSemi ()
    {
        assertEquals (
            "numeric character reference without a semicolon at end of string doesn't work",
            "The division sign (\u00f7) is \u00f7",
            Translate.decode ("The division sign (\u00f7) is &#247"));
    }

    public void testReferencesInString ()
    {
        assertEquals (
            "character references within a string don't work",
            "Thus, the character entity reference \u00f7 is a more convenient form than \u00f7 for obtaining the division sign (\u00f7)",
            Translate.decode ("Thus, the character entity reference &divide; is a more convenient form than &#247; for obtaining the division sign (\u00f7)"));
    }

    public void testBogusCharacterEntityReference ()
    {
        assertEquals (
            "bogus character entity reference doesn't work",
            "The character entity reference &divode; is bogus",
            Translate.decode ("The character entity reference &divode; is bogus"));
    }

    public void testBogusNumericCharacterReference ()
    {
        assertEquals (
            "bogus numeric character reference doesn't work",
            "The numeric character reference &#2F7; is bogus",
            Translate.decode ("The numeric character reference &#2F7; is bogus"));
    }

    public void testEncode ()
    {
        assertEquals (
            "encode doesn't work",
            "Character entity reference: &divide;, another: &nbsp;, numeric character reference: &#9831;.",
            Translate.encode ("Character entity reference: \u00f7, another: \u00a0, numeric character reference: \u2667."));
    }

    public void testEncodeLink ()
    {
        assertEquals (
            "encode link doesn't work",
            "&lt;a href=&quot;http://www.w3.org/TR/REC-html40/sgml/entities.html&quot;&gt;http://www.w3.org/TR/REC-html40/sgml/entities.html&lt;/a&gt;",
            Translate.encode ("<a href=\"http://www.w3.org/TR/REC-html40/sgml/entities.html\">http://www.w3.org/TR/REC-html40/sgml/entities.html</a>"));
    }

//    public byte[] encodedecode (byte[] bytes)
//        throws
//            IOException
//    {
//        InputStream in;
//        ByteArrayOutputStream out;
//
//        // encode
//        in = new ByteArrayInputStream (bytes);
//        out = new ByteArrayOutputStream ();
//        Translate.encode (in, new PrintStream (out));
//        in.close ();
//        out.close ();
//        
//        // decode
//        in = new ByteArrayInputStream (out.toByteArray ());
//        out = new ByteArrayOutputStream ();
//        Translate.decode (in, new PrintStream (out));
//        in.close ();
//        out.close ();
//
//        return (out.toByteArray ());
//    }
//
//    public void check (byte[] reference, byte[] result)
//        throws
//            IOException
//    {
//        InputStream ref;
//        InputStream in;
//        int i;
//        int i1;
//        int i2;
//
//        ref = new ByteArrayInputStream (reference);
//        in = new ByteArrayInputStream (result);
//        i = 0;
//        do
//        {
//            i1 = ref.read ();
//            i2 = in.read ();
//            if (i1 != i2)
//                fail ("byte difference detected at offset " + i);
//            i++;
//        }
//        while (-1 != i1);
//        ref.close ();
//        in.close ();
//    }
//
////    public void testInitialCharacterEntityReferenceCodec ()
////        throws
////            IOException
////    {
////        byte[] data = "\u00f7 is the division sign.".getBytes ();
////        check (data, encodedecode (data));
////    }
//
//    public void testEncodeDecodePage () throws IOException
//    {
//        URL url;
//        URLConnection connection;
//        InputStream in;
//        ByteArrayOutputStream out;
//        byte[] bytes;
//        byte[] result;
//        int c;
//
//        // get some bytes
//        url = new URL ("http://sourceforge.net/projects/htmlparser");
//        connection = url.openConnection ();
//        in = connection.getInputStream ();
//        out = new ByteArrayOutputStream ();
//        while (-1 != (c = in.read ()))
//            out.write (c);
//        in.close ();
//        out.close ();
//        bytes = out.toByteArray ();
//
//        // run it through
//        result = encodedecode (bytes);
//        
//        // check
//        check (bytes, result);
//    }
}



