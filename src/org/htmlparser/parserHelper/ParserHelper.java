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
	 * is prepended (with an intervening slash if required), or a url that
	 * begins with one of the known protocol strings, i.e. <code>http://</code>.
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
	
	    // for a while we warn people about spaces in their URL
	    resource = LinkProcessor.fixSpaces (string);
	    if (!resource.equals (string) && (null != feedback))
	        feedback.warning ("URL containing spaces was adjusted to \""
	            + resource
	            + "\", use HTMLLinkProcessor.fixSpaces()");
	
	    try
	    {
	        url = new URL (resource);
	        ret =  ParserHelper.openConnection (url, feedback);
	    }
	    catch (MalformedURLException murle)
	    {   // try it as a file
	        buffer = new StringBuffer (prefix.length () + string.length () + 1);
	        buffer.append (prefix);
	        if (!string.startsWith (File.separator))
	            buffer.append ("/");
	        buffer.append (resource);
	        try
	        {
	            url = new URL (buffer.toString ());
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
