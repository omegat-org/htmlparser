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

package org.htmlparser.lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.htmlparser.util.ParserException;

/**
 * Represents the contents of an HTML page.
 * Contains a character array of the page downloaded so far,
 * a String with those characters in it,
 * and an index of positions of line separators (actually the first
 * character position on the next line).
 */
public class Page
{
    /**
     * The default charset.
     * This should be <code>ISO-8859-1</code>,
     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
     * Another alias is "8859_1".
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * The logging object.
     */
    protected static Log mLog = null;

    /**
     * The source of characters.
     */
    protected Source mSource;

    /**
     * The characters read so far from the source.
     */
    protected char[] mCharacters;
    
    /**
     * The string representation of the source.
     */
    protected String mString;

    /**
     * Character positions of the first character in each line.
     */
    protected PageIndex mIndex;

    /**
     * Messages for page not there (404).
     */
    private String[] mFourOhFour =
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
     * Construct a page reading from a URL.
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
//        throws
//            IOException,
//            UnsupportedEncodingException
    {
        if (null == connection)
            throw new IllegalArgumentException ("connection cannot be null");
        try
        {
            connection.connect ();
        }
        catch (UnknownHostException uhe)
        {
            throw new ParserException ("the host (" + connection.getURL ().getHost () + ") was not found", uhe);
        }
        catch (IOException ioe)
        {
            throw new ParserException ("oops", ioe);
        }
        try
        {
            mSource = new Source (new Stream (connection.getInputStream ()), getCharacterSet (connection));
        }
        catch (IOException ioe)
        {
            throw new ParserException ("oops2", ioe);
        }
        mCharacters = null;
        mString = null;
        mIndex = new PageIndex (this);
    }

    /**
     * Try and extract the character set from the HTTP header.
     * @param connection The connection with the charset info.
     * @return The character set name to use for this HTML page.
     */
    protected String getCharacterSet (URLConnection connection)
    {
        final String CONTENT_TYPE_STRING = "Content-Type";

        String string;
        String ret;
        
        ret = DEFAULT_CHARSET;
        string = connection.getHeaderField (CONTENT_TYPE_STRING);
        if (null != string)
            ret = getCharset (string);

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
    protected String getCharset (String content)
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
                        getLog ().info (
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
            getLog ().debug (
                "unable to determine cannonical charset name for "
                + name
                + " - using "
                + _default,
                ita);
		}
        
		return (ret);
	}

    //
    // Bean patterns
    //

    public Log getLog ()
    {
        if (null == mLog)
            mLog = LogFactory.getLog (this.getClass ());
//        String name = this.getClass ().getName ();
//        java.util.logging.Logger logger = java.util.logging.Logger.getLogger (name);
//        logger.setLevel (java.util.logging.Level.FINEST);
        return (mLog);
    }
}
