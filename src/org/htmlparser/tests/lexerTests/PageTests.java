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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;

public class PageTests extends TestCase
{
    /**
     * The default charset.
     * This should be <code>ISO-8859-1</code>,
     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
     * Another alias is "8859_1".
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * Test the third level page class.
     */
	public PageTests (String name)
    {
		super (name);
	}

    /**
     * Test initialization with a null value.
     */
	public void testNull () throws ParserException
    {
        Page page;

        try
        {
            page = new Page ((URLConnection)null);
            assertTrue ("null value in constructor", false);
        }
        catch (IllegalArgumentException iae)
        {
            // expected outcome
        }

        try
        {
            page = new Page ((String)null);
            assertTrue ("null value in constructor", false);
        }
        catch (IllegalArgumentException iae)
        {
            // expected outcome
        }
    }

    /**
     * Test initialization with a real value.
     */
	public void testURLConnection () throws ParserException, IOException
    {
        String link;
        URL url;
        Page page;

        link = "http://www.ibm.com/jp/";
        url = new URL (link);
        page = new Page (url.openConnection ());
    }

    /**
     * Test initialization with non-existant URL.
     */
	public void testBadURLConnection () throws IOException
    {
        String link;
        URL url;
        Page page;

        link = "http://www.bigbogosity.org/";
        url = new URL (link);
        try
        {
            page = new Page (url.openConnection ());
        }
        catch (ParserException pe)
        {
            // expected response
        }
    }
}