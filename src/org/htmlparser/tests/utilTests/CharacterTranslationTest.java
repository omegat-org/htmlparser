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

package org.htmlparser.tests.utilTests;

import junit.framework.TestCase;

import org.htmlparser.util.Translate;

public class CharacterTranslationTest extends TestCase
{
    public CharacterTranslationTest (String name)
    {
        super (name);
    }

    public void testInitialCharacterEntityReference ()
    {
        assertEquals (
            "character entity reference at start of string doesn't work",
            "÷ is the division sign.",
            Translate.decode ("&divide; is the division sign."));
    }

    public void testInitialNumericCharacterReference ()
    {
        assertEquals (
            "numeric character reference at start of string doesn't work",
            "÷ is the division sign.",
            Translate.decode ("&#247; is the division sign."));
    }

    public void testInitialCharacterEntityReferenceWithoutSemi ()
    {
        assertEquals (
            "character entity reference without a semicolon at start of string doesn't work",
            "÷ is the division sign.",
            Translate.decode ("&divide; is the division sign."));
    }

    public void testInitialNumericCharacterReferenceWithoutSemi ()
    {
        assertEquals (
            "numeric character reference without a semicolon at start of string doesn't work",
            "÷ is the division sign.",
            Translate.decode ("&#247; is the division sign."));
    }

    public void testFinalCharacterEntityReference ()
    {
        assertEquals (
            "character entity reference at end of string doesn't work",
            "The division sign (÷) is ÷",
            Translate.decode ("The division sign (÷) is &divide;"));
    }

    public void testFinalNumericCharacterReference ()
    {
        assertEquals (
            "numeric character reference at end of string doesn't work",
            "The division sign (÷) is ÷",
            Translate.decode ("The division sign (÷) is &#247;"));
    }

    public void testFinalCharacterEntityReferenceWithoutSemi ()
    {
        assertEquals (
            "character entity reference without a semicolon at end of string doesn't work",
            "The division sign (÷) is ÷",
            Translate.decode ("The division sign (÷) is &divide"));
    }

    public void testFinalNumericCharacterReferenceWithoutSemi ()
    {
        assertEquals (
            "numeric character reference without a semicolon at end of string doesn't work",
            "The division sign (÷) is ÷",
            Translate.decode ("The division sign (÷) is &#247"));
    }

    public void testReferencesInString ()
    {
        assertEquals (
            "character references within a string don't work",
            "Thus, the character entity reference ÷ is a more convenient form than ÷ for obtaining the division sign (÷)",
            Translate.decode ("Thus, the character entity reference &divide; is a more convenient form than &#247; for obtaining the division sign (÷)"));
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
            Translate.encode ("Character entity reference: ÷, another: \u00a0, numeric character reference: \u2667."));
    }

    public void testEncodeLink ()
    {
        assertEquals (
            "encode link doesn't work",
            "&lt;a href=&quot;http://www.w3.org/TR/REC-html40/sgml/entities.html&quot;&gt;http://www.w3.org/TR/REC-html40/sgml/entities.html&lt;/a&gt;",
            Translate.encode ("<a href=\"http://www.w3.org/TR/REC-html40/sgml/entities.html\">http://www.w3.org/TR/REC-html40/sgml/entities.html</a>"));
    }
}



