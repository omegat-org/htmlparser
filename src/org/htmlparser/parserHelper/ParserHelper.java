// HTMLParser Library v1_4_20030713 - A java-based parser for HTML
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

package org.htmlparser.parserHelper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;

public class ParserHelper implements Serializable {

	public ParserHelper() {
		super();
	}

	/**
	 * Opens a connection using the given url.
	 * @param url The url to open.
	 * @param feedback The ibject to use for messages or <code>null</code>.
	 * @exception ParserException if an i/o exception occurs accessing the url.
	 */
	public static URLConnection openConnection (URL url, ParserFeedback feedback)
	    throws
	        ParserException
	{
	    URLConnection ret;
	    
	    try
	    {
	        ret = url.openConnection ();
	    }
	    catch (IOException ioe)
	    {
	        String msg = "HTMLParser.openConnection() : Error in opening a connection to " + url.toExternalForm ();
	        ParserException ex = new ParserException (msg, ioe);
	        if (null != feedback)
	            feedback.error (msg, ex);
	        throw ex;
	    }
	    
	    return (ret);
	}

	/**
	 * Opens a connection based on a given string.
	 * The string is either a file, in which case <code>file://localhost</code>
	 * is prepended to a canonical path derived from the string, or a url that
	 * begins with one of the known protocol strings, i.e. <code>http://</code>.
     * Embedded spaces are silently converted to %20 sequences.
	 * @param string The name of a file or a url.
	 * @param feedback The object to use for messages or <code>null</code> for no feedback.
	 * @exception ParserException if the string is not a valid url or file.
	 */
	public static URLConnection openConnection (String string, ParserFeedback feedback)
	    throws
	        ParserException
	{
	    final String prefix = "file://localhost";
	    String resource;
	    URL url;
	    StringBuffer buffer;
	    URLConnection ret;
	
	    try
	    {
	        url = new URL (LinkProcessor.fixSpaces (string));
	        ret =  ParserHelper.openConnection (url, feedback);
	    }
	    catch (MalformedURLException murle)
	    {   // try it as a file
	        try
	        {
                File file = new File (string);
                resource = file.getCanonicalPath ();
                buffer = new StringBuffer (prefix.length () + resource.length ());
                buffer.append (prefix);
                if (!resource.startsWith ("/"))
                    buffer.append ("/");
                buffer.append (resource);
	            url = new URL (LinkProcessor.fixSpaces (buffer.toString ()));
	            ret = ParserHelper.openConnection (url, feedback);
	            if (null != feedback)
	                feedback.info (url.toExternalForm ());
	        }
	        catch (MalformedURLException murle2)
	        {
	            String msg = "HTMLParser.openConnection() : Error in opening a connection to " + string;
	            ParserException ex = new ParserException (msg, murle2);
	            if (null != feedback)
	                feedback.error (msg, ex);
	            throw ex;
	        }
	        catch (IOException ioe)
            {
	            String msg = "HTMLParser.openConnection() : Error in opening a connection to " + string;
	            ParserException ex = new ParserException (msg, ioe);
	            if (null != feedback)
	                feedback.error (msg, ex);
	            throw ex;
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
	public static String findCharset (String name, String _default)
	{
		String ret;
        
		try
		{
			Class cls;
			java.lang.reflect.Method method;
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
		catch (java.lang.reflect.InvocationTargetException ita)
		{
			// java.nio.charset.IllegalCharsetNameException
			// and java.nio.charset.UnsupportedCharsetException
			// return the default
			ret = _default;
		}
        
		return (ret);
	}

}
