// HTMLParser Library v1_3_20021228 - A java-based parser for HTML
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

package org.htmlparser;
//////////////////
// Java Imports //
//////////////////
import java.io.*;
import java.net.*;
import java.util.*;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.scanners.*;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLMetaTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLEnumerationImpl;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.util.HTMLParserFeedback;
import org.htmlparser.util.HTMLTagParser;
import org.htmlparser.visitors.HTMLVisitor;

/**
 * This is the class that the user will use, either to get an iterator into 
 * the html page or to directly parse the page and print the results
 * <BR>
 * Typical usage of the parser is as follows : <BR>
 * [1] Create a parser object - passing the URL and a feedback object to the parser<BR>
 * [2] Register the common scanners. See {@link #registerScanners()} <BR>
 * You wouldnt do this if you want to configure a custom lightweight parser. In that case, 
 * you would add the scanners of your choice using {@link #addScanner(HTMLTagScanner)}<BR>
 * [3] Enumerate through the elements from the parser object <BR>
 * It is important to note that the parsing occurs when you enumerate, ON DEMAND. This is a thread-safe way, 
 * and you only get the control back after a particular element is parsed and returned.
 * 
 * <BR>
 * Below is some sample code to parse Yahoo.com and print all the tags.
 * <pre>
 * HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * // In this example, we are registering all the common scanners
 * parser.registerScanners(); 
 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
 *    HTMLNode node = (HTMLNode)e.nextElement();
 * 	  node.print();
 * }
 * </pre>
 * Below is some sample code to parse Yahoo.com and print only the text information. This scanning
 * will run faster, as there are no scanners registered here.
 * <pre>
 * HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * // In this example, none of the scanners need to be registered
 * // as a string node is not a tag to be scanned for.
 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
 *    HTMLNode node = (HTMLNode)e.nextElement();
 *    if (node instanceof HTMLStringNode) {
 *        HTMLStringNode stringNode = (HTMLStringNode)node;
 *        System.out.println(stringNode.getText());
 *    }
 * }
 * </pre>
 * The above snippet will print out only the text contents in the html document.<br>
 * Here's another snippet that will only print out the link urls in a document. 
 * This is an example of adding a link scanner.
 * <pre>
 * HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * parser.addScanner(new HTMLLinkScanner("-l"));
 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
 *    HTMLNode node = (HTMLNode)e.nextElement();
 *    if (node instanceof HTMLLinkTag) {
 *        HTMLLinkTag linkTag = (HTMLLinkTag)node;
 *        System.out.println(linkTag.getLink());
 *    }
 * }
 * </pre>
 *  @see HTMLParser#elements() 
 */
public class HTMLParser
    implements
        Serializable
{
	// Please don't change the formatting of the VERSION_STRING
	// below. This is done so as to facilitate the ant script
	public final static java.lang.String 
	VERSION_STRING="1.3 (Integration Build Dec 28, 2002)"
	;
	// End of formatting

    /**
     * The default charset.
     * This should be <code>ISO-8859-1</code>,
     * see RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt?number=2616) section 3.7.1
     * Another alias is "8859_1".
     */
    protected static final String DEFAULT_CHARSET = "ISO-8859-1";

	/**
	 * Feedback object.
	 */
	protected HTMLParserFeedback feedback;
	
	/**
	 * The URL or filename to be parsed.
	 */
	protected String resourceLocn;
	
	/** 
	 * The html reader associated with this parser.
	 */
	protected transient HTMLReader reader;

    /**
     * The list of scanners to apply at the top level.
     */
	private Hashtable scanners;

    /**
     * The encoding being used to decode the connection input stream.
     */
    protected String character_set;

    /**
     * The source for HTML.
     */
    protected transient URLConnection url_conn;

    /**
     * A quiet message sink.
     * Use this for no feedback.
     */
    public static HTMLParserFeedback noFeedback = new DefaultHTMLParserFeedback (DefaultHTMLParserFeedback.QUIET);
    
    /**
     * A verbose message sink.
     * Use this for output on <code>System.out</code>.
     */
    public static HTMLParserFeedback stdout = new DefaultHTMLParserFeedback ();

    //
    // Static methods
    //

	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static void setLineSeparator(String lineSeparator)
	{
		HTMLNode.setLineSeparator(lineSeparator);	
	}
	
    /**
     * Opens a connection using the given url.
     * @param url The url to open.
     * @param feedback The ibject to use for messages or <code>null</code>.
     * @exception HTMLParserException if an i/o exception occurs accessing the url.
     */
    private static URLConnection openConnection (URL url, HTMLParserFeedback feedback)
        throws
            HTMLParserException
    {
        URLConnection ret;
        
        try
        {
            ret = url.openConnection ();
        }
        catch (IOException ioe)
        {
            String msg = "HTMLParser.openConnection() : Error in opening a connection to " + url.toExternalForm ();
            HTMLParserException ex = new HTMLParserException (msg, ioe);
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
     * @exception HTMLParserException if the string is not a valid url or file.
     */
    private static URLConnection openConnection (String string, HTMLParserFeedback feedback)
        throws
            HTMLParserException
    {
        final String prefix = "file://localhost";
        String resource;
        URL url;
        StringBuffer buffer;
        URLConnection ret;

        // for a while we warn people about spaces in their URL
        resource = HTMLLinkProcessor.fixSpaces (string);
        if (!resource.equals (string) && (null != feedback))
            feedback.warning ("URL containing spaces was adjusted to \""
                + resource
                + "\", use HTMLLinkProcessor.fixSpaces()");

        try
        {
            url = new URL (resource);
            ret =  openConnection (url, feedback);
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
                ret = openConnection (url, feedback);
                if (null != feedback)
                    feedback.info (url.toExternalForm ());
            }
            catch (MalformedURLException murle2)
            {
                String msg = "HTMLParser.openConnection() : Error in opening a connection to " + string;
                HTMLParserException ex = new HTMLParserException (msg, murle2);
                if (null != feedback)
                    feedback.error (msg, ex);
                throw ex;
            }
        }
        
        return (ret);
    }

    //
    // Constructors
    //

    /**
     * Zero argument constructor.
     * The parser is in a safe but useless state.
     * Set the reader or connection using setReader() or setConnection().
     * @see #setReader(HTMLReader)
     * @see #setConnection(URLConnection)
     */
    public HTMLParser ()
    {
        setFeedback (null);
        setScanners (null);
        resourceLocn = null;
        reader = null;
        character_set = DEFAULT_CHARSET;
        url_conn = null;
		HTMLTag.setTagParser (new HTMLTagParser (getFeedback ()));
    }

    /**
	 * This constructor enables the construction of test cases, with readers
	 * associated with test string buffers. It can also be used with readers of the user's choice
	 * streaming data into the parser.<p/>
	 * <B>Important:</B> If you are using this constructor, and you would like to use the parser
	 * to parse multiple times (multiple calls to parser.elements()), you must ensure the following:<br>
	 * <ul>
	 * <li>Before the first parse, you must mark the reader for a length that you anticipate (the size of the stream).</li>
	 * <li>After the first parse, calls to elements() must be preceded by calls to :
	 * <pre>
	 * parser.getReader().reset();
	 * </pre>
	 * </li>
	 * </ul>
	 * @param rd The reader to draw characters from.
	 * @param fb The object to use when information,
     * warning and error messages are produced. If <em>null</em> no feedback
     * is provided.
	 */
	public HTMLParser(HTMLReader rd, HTMLParserFeedback fb) 
	{
        setFeedback (fb);
        setScanners (null);
        setReader (rd);
		HTMLTag.setTagParser(new HTMLTagParser(feedback));
	}
	
    /**
     * Constructor for custom HTTP access.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @param fb The object to use for message communication.
     */
    public HTMLParser (URLConnection connection, HTMLParserFeedback fb)
        throws
            HTMLParserException
    {
        setFeedback (fb);
        setScanners (null);
        HTMLTag.setTagParser (new HTMLTagParser (feedback));
        setConnection (connection);
    }

	/**
	 * Creates a HTMLParser object with the location of the resource (URL or file)
	 * You would typically create a DefaultHTMLParserFeedback object and pass it in.
	 * @param resourceLocn Either the URL or the filename (autodetects).
     * A standard HTTP GET is performed to read the content of the URL.
	 * @param feedback The HTMLParserFeedback object to use when information,
     * warning and error messages are produced. If <em>null</em> no feedback
     * is provided.
     * @see #HTMLParser(URLConnection,HTMLParserFeedback)
	 */
	public HTMLParser(String resourceLocn, HTMLParserFeedback feedback) throws HTMLParserException
	{
        this (openConnection (resourceLocn, feedback), feedback);
    }

	/**
	 * Creates a HTMLParser object with the location of the resource (URL or file).
	 * A DefaultHTMLParserFeedback object is used for feedback.
	 * @param resourceLocn Either the URL or the filename (autodetects).
	 */
	public HTMLParser(String resourceLocn) throws HTMLParserException
	{
		this (resourceLocn, stdout);
	}
	
	/**
	 * This constructor is present to enable users to plugin their own readers. 
	 * A DefaultHTMLParserFeedback object is used for feedback. It can also be used with readers of the user's choice
	 * streaming data into the parser.<p/>
	 * <B>Important:</B> If you are using this constructor, and you would like to use the parser
	 * to parse multiple times (multiple calls to parser.elements()), you must ensure the following:<br>
	 * <ul>
	 * <li>Before the first parse, you must mark the reader for a length that you anticipate (the size of the stream).</li>
	 * <li>After the first parse, calls to elements() must be preceded by calls to :
	 * <pre>
	 * parser.getReader().reset();
	 * </pre>
	 * </li>
     * @param reader The source for HTML to be parsed.
	 */
	public HTMLParser(HTMLReader reader) 
	{
		this (reader, stdout);	
	}	

    /**
     * Constructor for non-standard access.
     * A DefaultHTMLParserFeedback object is used for feedback.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @see #HTMLParser(URLConnection,HTMLParserFeedback)
     */
    public HTMLParser (URLConnection connection) throws HTMLParserException
    {
        this (connection, stdout);
    }

    //
    // Serialization support
    //

    private void writeObject (ObjectOutputStream out)
        throws
            IOException
    {
        if ((null == getConnection ()) || /*redundant*/(null == getURL ()))
            if (null != getReader ())
                throw new IOException ("can only serialize parsers with a URL");
        out.defaultWriteObject ();
    }

    private void readObject (ObjectInputStream in)
        throws
            IOException,
            ClassNotFoundException
    {
        in.defaultReadObject ();
        try
        {
            // reopen the connection and create a reader which are transient fields
            setURL (getURL ());
        }
        catch (HTMLParserException hpe)
        {
            throw new IOException (hpe.toString ());
        }
    }

    //
    // Bean patterns
    //

    /**
     * Set the connection for this parser.
     * This method sets four of the fields in the parser object;
     * <code>resourceLocn</code>, <code>url_conn</code>, <code>character_set</code>
     * and <code>reader</code>. It does not adjust the <code>scanners</code> list
     * or <code>feedback</code> object. The four fields are set atomicly by
     * this method, either they are all set or none of them is set. Trying to
     * set the connection to null is a noop.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @exception HTMLParserException if the character set specified in the
     * HTTP header is not supported, or an i/o exception occurs creating the
     * reader.
     */
    public void setConnection (URLConnection connection)
        throws
            HTMLParserException
    {
        String res;
        HTMLReader rd;
        String chs;
        URLConnection con;

        if (null != connection)
        {
            res = getURL ();
            rd = getReader ();
            chs = getEncoding ();
            con = getConnection ();
            try
            {
                resourceLocn = connection.getURL ().toExternalForm ();
                url_conn = connection;
                url_conn.connect ();
                character_set = getCharacterSet (url_conn);
                createReader ();
            }
            catch (UnsupportedEncodingException uee)
            {
                String msg = "setConnection() : The content of " + connection.getURL ().toExternalForm () + " has an encoding which is not supported";
                HTMLParserException ex = new HTMLParserException (msg, uee);
                feedback.error (msg, ex);
                resourceLocn = res;
                url_conn = con;
                character_set = chs;
                reader = rd;
                throw ex;
            }
            catch (IOException ioe)
            {
                String msg = "setConnection() : Error in opening a connection to " + connection.getURL ().toExternalForm ();
                HTMLParserException ex = new HTMLParserException (msg, ioe);
                feedback.error (msg, ex);
                resourceLocn = res;
                url_conn = con;
                character_set = chs;
                reader = rd;
                throw ex;
            }
        }
    }

    /**
     * Return the current connection.
     * @return The connection either created by the parser or passed into this
     * parser via <code>setConnection</code>.
     * @see #setConnection(URLConnection)
     */
    public URLConnection getConnection ()
    {
        return (url_conn);
    }

    /**
     * Set the URL for this parser.
     * This method sets four of the fields in the parser object;
     * <code>resourceLocn</code>, <code>url_conn</code>, <code>character_set</code>
     * and <code>reader</code>. It does not adjust the <code>scanners</code> list
     * or <code>feedback</code> object.Trying to set the url to null or an
     * empty string is a noop.
     * @see #setConnection(URLConnection)
     */
    public void setURL (String url)
        throws
            HTMLParserException
    {
        if ((null != url) && !"".equals (url))
            setConnection (openConnection (url, getFeedback ()));
    }

    /**
     * Return the current URL being parsed.
     * @return The url passed into the constructor or the file name
     * passed to the constructor modified to be a URL.
     */
    public String getURL ()
    {
        return (resourceLocn);
    }

    /**
     * Set the encoding for this parser.
     * If there is no connection (getConnection() returns null) it simply sets
     * the character set name stored in the parser (Note: the reader object
     * which must have been set in the constructor or by <code>setReader()</code>,
     * may or may not be using this character set).
     * Otherwise (getConnection() doesn't return null) it does this by reopening the
     * input stream of the connection and creating a reader that uses this
     * character set. In this case, this method sets two of the fields in the
     * parser object; <code>character_set</code> and <code>reader</code>.
     * It does not adjust <code>resourceLocn</code>, <code>url_conn</code>,
     * <code>scanners</code> or <code>feedback</code>. The two fields are set
     * atomicly by this method, either they are both set or none of them is set.
     * Trying to set the encoding to null or an empty string is a noop.
     * @exception HTMLParserException If the opening of the reader
     */
    public void setEncoding (String encoding)
        throws
            HTMLParserException
    {
        String chs;
        HTMLReader rd;

        if ((null != encoding) && !"".equals (encoding))
            if (null == getConnection ())
                character_set = encoding;
            else
            {
                rd = getReader ();
                chs = getEncoding ();
                try
                {
                    character_set = encoding;
                    createReader ();
                }
                catch (UnsupportedEncodingException uee)
                {
                    String msg = "setEncoding() : The specified encoding is not supported";
                    HTMLParserException ex = new HTMLParserException (msg, uee);
                    feedback.error (msg, ex);
                    character_set = chs;
                    reader = rd;
                    throw ex;
                }
                catch (IOException ioe)
                {
                    String msg = "setEncoding() : Error in opening a connection to " + getConnection ().getURL ().toExternalForm ();
                    HTMLParserException ex = new HTMLParserException (msg, ioe);
                    feedback.error (msg, ex);
                    character_set = chs;
                    reader = rd;
                    throw ex;
                }
            }
    }

    /**
     * The current encoding.
     * This item is et from the HTTP header but may be overridden by meta
     * tags in the head, so this may change after the head has been parsed.
     */
    public String getEncoding ()
    {
        return (character_set);
    }

    /**
     * Set the reader for this parser.
     * This method sets four of the fields in the parser object;
     * <code>resourceLocn</code>, <code>url_conn</code>, <code>character_set</code>
     * and <code>reader</code>. It does not adjust the <code>scanners</code> list
     * or <code>feedback</code> object. The <code>url_conn</code> is set to
     * null since this cannot be determined from the reader. The 
     * <code>character_set</code> is set to the default character set since
     * this cannot be determined from the reader.
     * Trying to set the reader to <code>null</code> is a noop.
     * @param rd The reader object to use. This reader will be bound to this
     * parser after this call.
     */
    public void setReader (HTMLReader rd)
    {
        if (null != rd)
        {
            resourceLocn = rd.getURL ();
            reader = rd;
            character_set = DEFAULT_CHARSET;
            url_conn = null;
            reader.setParser(this);
        }
    }

	/**
	 * Returns the reader associated with the parser
	 * @return HTMLReader
	 */
	public HTMLReader getReader() {
		return reader;
	}

	/**
	 * Get the number of scanners registered currently in the scanner.
	 * @return int number of scanners registered
	 */
	public int getNumScanners() {
		return scanners.size();	
	}
	
	/**
	 * This method is to be used to change the set of scanners in the current parser.
	 * @param newScanners Vector holding scanner objects to be used during the parsing process.
	 */
	public void setScanners(Hashtable newScanners)
    {
		scanners = (null == newScanners) ? new Hashtable() : newScanners;
	}
	
	/**
	 * Get an enumeration of scanners registered currently in the parser
	 * @return Enumeration of scanners currently registered in the parser
	 */
	public Hashtable getScanners() {
		return scanners;
	}

	/**
	 * Sets the feedback object used in scanning.
	 * @param fb The new feedback object to use.
	 */
	public void setFeedback(HTMLParserFeedback fb)
    {
        feedback = (null == fb) ? noFeedback : fb;
	}

	/**
	 * Returns the feedback.
	 * @return HTMLParserFeedback
	 */
	public HTMLParserFeedback getFeedback() {
		return feedback;
	}

    //
    // Internal methods
    //

    /**
     * Create a new reader for the URLConnection object.
     * The current character set is used to transform the input stream
     * into a character reader.
     * @exception UnsupportedEncodingException if the current character set 
     * is not supported on this platform.
     * @exception IOException if there is a problem constructing the reader.
     * @see #getEncoding()
     */
    protected void createReader ()
        throws
            UnsupportedEncodingException,
            IOException
    {
        InputStream stream;
        InputStreamReader in;
        
        stream = url_conn.getInputStream ();
        in = new InputStreamReader (stream, character_set);
        reader = new HTMLReader (new BufferedReader (in), resourceLocn);
        reader.setParser (this);
    }

//    /**
//     * Dump the HTTP header contents.
//     */
//    protected void dumpHeader ()
//    {
//        java.util.Map map = url_conn.getHeaderFields ();
//        for (Iterator iterator = map.keySet ().iterator (); iterator.hasNext (); )
//        {
//            String key = (String)iterator.next ();
//            feedback.info (key + "=" + map.get (key));
//        }
//    }

    /**
     * Lookup a character set name.
     * <em>Vacuous for JVM's without <code>java.nio.charset</code>.</em>
     * This uses reflection so the code will still run under prior JDK's but
     * in that case the default is always returned.
     * @param name The name to look up. One of the aliases for a character set.
     * @param _default The name to return if the lookup fails.
     */
    protected String findCharset (String name, String _default)
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
    
    /**
     * Try and extract the character set from the HTTP header.
     * @param connection The connection with the charset info.
     * @return The character set name to use for this HTML page.
     */
    protected String getCharacterSet (URLConnection connection)
    {
        final String field = "Content-Type";

        String string;
        String ret;
        
        ret = DEFAULT_CHARSET;
        string = connection.getHeaderField (field);
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
     * @return The character set name to use when reading the input stream.
     * For JDKs that have the Charset class this is qualified by passing
     * the name to findCharset() to render it into canonical form.
     * If the charset parameter is not found in the given string, the default
     * character set is returned.
     * @see #findCharset(String,String)
     * @see #DEFAULT_CHARSET
     */
    protected String getCharset (String content)
    {
        final String parameter = "charset";

        int index;
        String string;
        String ret;
        
        ret = DEFAULT_CHARSET;
        if (null != content)
            if (-1 != (index = content.indexOf (parameter)))
            {
                content = content.substring (index + parameter.length ()).trim ();
                if (content.startsWith ("="))
                {
                    content = content.substring (1).trim ();
                    if (-1 != (index = content.indexOf (";")))
                        content = content.substring (0, index);
                    ret = findCharset (content, ret);
                    // Charset names are not case-sensitive;
                    // that is, case is always ignored when comparing charset names.
                    if (!ret.equalsIgnoreCase (content))
                        feedback.info (
                            "detected charset \""
                            + content
                            + "\", using \""
                            + ret
                            + "\"");
                }
            }
        
        return (ret);
    }

    //
    // Public methods
    //

	/**
	 * Add a new Tag Scanner.
	 * In typical situations where you require a no-frills parser, use the registerScanners() method to add the most
	 * common parsers. But when you wish to either compose a parser with only certain scanners registered, use this method.
	 * It is advantageous to register only the scanners you want, in order to achieve faster parsing speed. This method 
	 * would also be of use when you have developed custom scanners, and need to register them into the parser.
	 * @param scanner HTMLTagScanner object (or derivative) to be added to the list of registered scanners
	 */
	public void addScanner(HTMLTagScanner scanner) {
		String ids[] = scanner.getID();
		for (int i=0;i<ids.length;i++) {
			scanners.put(ids[i],scanner);
		}
		scanner.setFeedback(feedback);
	}
	
	/**
	 * Returns an iterator (enumeration) to the html nodes. Each node can be a tag/endtag/
	 * string/link/image<br>
	 * This is perhaps the most important method of this class. In typical situations, you will need to use
	 * the parser like this :
	 * <pre>
	 * HTMLParser parser = new HTMLParser("http://www.yahoo.com");
	 * parser.registerScanners();
	 * for (HTMLEnumeration e = parser.elements();e.hasMoreElements();) {
	 *    HTMLNode node = e.nextHTMLNode();
	 *    if (node instanceof HTMLStringNode) {
	 *      // Downcasting to HTMLStringNode
	 *      HTMLStringNode stringNode = (HTMLStringNode)node;
	 *      // Do whatever processing you want with the string node
	 *      System.out.println(stringNode.getText());
	 *    }
	 *    // Check for the node or tag that you want
	 *    if (node instanceof ...) {
	 *      // Downcast, and process
	 *    }
	 * }
	 * </pre>
	 */
	public HTMLEnumeration elements() throws HTMLParserException
    {
        boolean remove_scanner;
        HTMLNode node;
        HTMLMetaTag meta;
        String httpEquiv;
        String charset;
        boolean restart;
        HTMLEndTag end;
        HTMLEnumerationImpl ret;

        remove_scanner = false;
        restart = false;
        ret = new HTMLEnumerationImpl (reader, resourceLocn, feedback);
        if (null != url_conn)
            try
            {
                if (null == scanners.get ("-m"))
                {
                    addScanner (new HTMLMetaTagScanner ("-m"));
                    remove_scanner = true;
                }

                /* pre-read up to </HEAD> looking for charset directive */
                while (null != (node = ret.peek ()))
                {
                    if (node instanceof HTMLMetaTag)
                    {   // check for charset on Content-Type
                        meta = (HTMLMetaTag)node;
                        httpEquiv = meta.getParameter ("HTTP-EQUIV");
                        if ("Content-Type".equalsIgnoreCase (httpEquiv))
                        {
                            charset = getCharset (meta.getParameter ("CONTENT"));
                            if (!charset.equalsIgnoreCase (character_set))
                            {   // oops, different character set, restart
                                character_set = charset;
                                createReader ();
                                ret = new HTMLEnumerationImpl (reader, resourceLocn, feedback);
                            }
                            // once we see the Content-Type meta tag we're finished the pre-read
                            break;
                        }
                    }
                    else if (node instanceof HTMLEndTag)
                    {
                        end = (HTMLEndTag)node;
                        if (end.getTagName ().equalsIgnoreCase ("HEAD"))
                            // or, once we see the </HEAD> tag we're finished the pre-read
                            break;
                    }
                }
            }
            catch (UnsupportedEncodingException uee)
            {
                String msg = "elements() : The content of " + url_conn.getURL ().toExternalForm () + " has an encoding which is not supported";
                HTMLParserException ex = new HTMLParserException (msg, uee);
                feedback.error (msg, ex);
                throw ex;
            }
            catch (IOException ioe)
            {
                String msg = "elements() : Error in opening a connection to " + url_conn.getURL ().toExternalForm ();
                HTMLParserException ex = new HTMLParserException (msg, ioe);
                feedback.error (msg, ex);
                throw ex;
            }
            finally
            {
                if (remove_scanner)
                    scanners.remove ("-m");
            }

        return (ret);
	}
	
	/**
	 * Flush the current scanners registered. The registered scanners list becomes empty with this call.
	 */
	public void flushScanners() {
		scanners = new Hashtable();	
	}
	
	/**
	 * Return the scanner registered in the parser having the
	 * given id
	 * @param id The id of the requested scanner
	 * @return HTMLTagScanner The Tag Scanner
	 */
	public HTMLTagScanner getScanner(String id) {
		return (HTMLTagScanner)scanners.get(id);
	}

	/**
	 * Parse the given resource, using the filter provided
	 */
	public void parse(String filter) throws Exception
	{
		HTMLNode node;
		for (HTMLEnumeration e=elements();e.hasMoreNodes();)
		{
			node = e.nextNode();
	  	  	if (node!=null)
			{
			 	if (filter==null)
				node.print(); 
				else
				{
					// There is a filter. Find if the associated filter of this node
					// matches the specified filter
					if (!(node instanceof HTMLTag)) continue;
					HTMLTag tag=(HTMLTag)node;
					HTMLTagScanner scanner = tag.getThisScanner();
					if (scanner==null) continue;
					String tagFilter = scanner.getFilter();
					if (tagFilter==null) continue;
					if (tagFilter.equals(filter))
							node.print();
				}		
			}
			else System.out.println("Node is null");
		}

	}
	
	/**
	 * This method should be invoked in order to register some common scanners. The scanners that get added are : <br>
	 * HTMLLinkScanner    (filter key "-l")<br>
	 * HTMLImageScanner   (filter key "-i")<br>
	 * HTMLScriptScanner  (filter key "-s") <br>
	 * HTMLStyleScanner   (filter key "-t") <br>
	 * HTMLJspScanner     (filter key "-j") <br>
	 * HTMLAppletScanner  (filter key "-a") <br>
	 * HTMLMetaTagScanner (filter key "-m") <br>
	 * HTMLTitleScanner   (filter key "-t") <br>
	 * HTMLDoctypeScanner (filter key "-d") <br>
	 * HTMLFormScanner    (filter key "-f") <br>
	 * HTMLFrameSetScanner(filter key "-r") <br>
	 * HTMLBaseHREFScanner(filter key "-b") <br>
	 * <br>
	 * Call this method after creating the HTMLParser object. e.g. <BR>
	 * <pre>
	 * HTMLParser parser = new HTMLParser("http://www.yahoo.com");
	 * parser.registerScanners();
	 * </pre>
	 */ 
	public void registerScanners() {
		if (scanners.size()>0) {
			System.err.println("registerScanners() should be called first, when no other scanner has been registered.");
			System.err.println("Other scanners already exist, hence this method call wont have any effect");
			return;
		}
		HTMLLinkScanner linkScanner = new HTMLLinkScanner(HTMLLinkTag.LINK_TAG_FILTER);
		// Note - The BaseHREF and Image scanners share the same
		// link processor - internally linked up with the factory
		// method in the link scanner class
		addScanner(linkScanner);
		addScanner(linkScanner.createImageScanner(HTMLImageTag.IMAGE_TAG_FILTER));
		addScanner(new HTMLScriptScanner("-s"));
		addScanner(new HTMLStyleScanner("-t"));
		addScanner(new HTMLJspScanner("-j"));
		addScanner(new HTMLAppletScanner("-a"));
		addScanner(new HTMLMetaTagScanner("-m"));
		addScanner(new HTMLTitleScanner("-T"));
		addScanner(new HTMLDoctypeScanner("-d"));
		addScanner(new HTMLFormScanner("-f"));
		addScanner(new HTMLFrameSetScanner("-r"));	
		addScanner(linkScanner.createBaseHREFScanner("-b"));
	}
	
	/**
	 * Removes a specified scanner object.
	 * @param scanner HTMLTagScanner object to be removed from the list of registered scanners
	 */
	public void removeScanner(HTMLTagScanner scanner) {
		scanners.remove(scanner);
	}

	/**
	 * The main program, which can be executed from the command line
	 */
	public static void main(String [] args)
	{
		System.out.println("HTMLParser v"+VERSION_STRING);
		if (args.length<1 || args[0].equals("-help"))
		{
			System.out.println();
			System.out.println("Syntax : java -jar htmlparser.jar <resourceLocn/website> -l");
			System.out.println("   <resourceLocn> the name of the file to be parsed (with complete path if not in current directory)");
			System.out.println("   -l Show only the link tags extracted from the document");
			System.out.println("   -i Show only the image tags extracted from the document");
			System.out.println("   -s Show only the Javascript code extracted from the document");
			System.out.println("   -t Show only the Style code extracted from the document");
			System.out.println("   -a Show only the Applet tag extracted from the document");
			System.out.println("   -j Parse JSP tags");	
			System.out.println("   -m Parse Meta tags");		
			System.out.println("   -T Extract the Title");
			System.out.println("   -f Extract forms");
			System.out.println("   -r Extract frameset");
			System.out.println("   -help This screen");
			System.out.println();
			System.out.println("HTML Parser home page : http://htmlparser.sourceforge.net");
			System.out.println();
			System.out.println("Example : java -jar htmlparser.jar http://www.yahoo.com");
			System.out.println();
			System.out.println("If you have any doubts, please join the HTMLParser mailing list (user/developer) from the HTML Parser home page instead of mailing any of the contributors directly. You will be surprised with the quality of open source support. ");
			System.exit(-1);
		}
		try {
			HTMLParser parser = new HTMLParser(args[0]);
            System.out.println("Parsing " + parser.getURL ());
			parser.registerScanners();
			try {
				if (args.length==2)
				{
					parser.parse(args[1]);
				} else
				parser.parse(null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
	}
	
	public void visitAllNodesWith(HTMLVisitor visitor) throws HTMLParserException {
		HTMLNode node;
		for (HTMLEnumeration e = elements();e.hasMoreNodes();) {
			node = e.nextNode();
			node.accept(visitor);
		}
	}
	
	/** Initializes the parser with the given input HTML String.
	 * @param inputHTML the input HTML that is to be parsed.
	 */
	public void setInputHTML(String inputHTML) {
	  if ("".equals(inputHTML)) {
		reader = new HTMLReader(new StringReader(inputHTML),"");      
	  }
	}	
	
	/**
	 * Creates the parser on an input string.
	 * @param inputHTML
	 * @return HTMLParser
	 */
	public static HTMLParser createParser(String inputHTML) {
		HTMLReader reader =	
			new HTMLReader(new StringReader(inputHTML),"");
		return new HTMLParser(reader);
	}
	
	public static HTMLParser createLinkRecognizingParser(String inputHTML) {
		HTMLParser parser = createParser(inputHTML);
		parser.addScanner(new HTMLLinkScanner(HTMLLinkTag.LINK_TAG_FILTER));
		return parser;
	}
}
