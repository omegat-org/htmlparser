// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
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

package org.htmlparser;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.lexer.nodes.NodeFactory;
import org.htmlparser.nodeDecorators.DecodingNode;
import org.htmlparser.nodeDecorators.EscapeCharacterRemovingNode;
import org.htmlparser.nodeDecorators.NonBreakingSpaceConvertingNode;
import org.htmlparser.tags.Tag; // temporarily
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.IteratorImpl;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;
import org.htmlparser.visitors.NodeVisitor;

/**
 * This is the class that the user will use, either to get an iterator into
 * the html page or to directly parse the page and print the results
 * <BR>
 * Typical usage of the parser is as follows : <BR>
 * [1] Create a parser object - passing the URL and a feedback object to the parser<BR>
 * [2] Enumerate through the elements from the parser object <BR>
 * It is important to note that the parsing occurs when you enumerate, ON DEMAND.
 * This is a thread-safe way, and you only get the control back after a
 * particular element is parsed and returned, which could be the entire body.
 *  @see Parser#elements()
 */
public class Parser
    implements
        Serializable
{
    // Please don't change the formatting of the version variables below.
    // This is done so as to facilitate ant script processing.

    /**
     * The floating point version number.
     */
    public final static double
    VERSION_NUMBER = 1.4
    ;

    /**
     * The type of version.
     */
    public final static String
    VERSION_TYPE = "Integration Build"
    ;

    /**
     * The date of the version.
     */
    public final static String
    VERSION_DATE = "Jan 02, 2004"
    ;

    /**
     * The display version.
     */
    public final static String
    VERSION_STRING = "" + VERSION_NUMBER + " (" + VERSION_TYPE + " " + VERSION_DATE + ")"
    ;

    // End of formatting

    /**
     * Feedback object.
     */
    protected ParserFeedback mFeedback;

    /**
     * The html lexer associated with this parser.
     */
    protected Lexer mLexer;

    /**
     * Variable to store lineSeparator.
     * This is setup to read <code>line.separator</code> from the System property.
     * However it can also be changed using the mutator methods.
     * This will be used in the toHTML() methods in all the sub-classes of Node.
     */
    protected static String lineSeparator = System.getProperty("line.separator", "\n");

    /**
     * A quiet message sink.
     * Use this for no feedback.
     */
    public static ParserFeedback noFeedback = new DefaultParserFeedback (DefaultParserFeedback.QUIET);

    /**
     * A verbose message sink.
     * Use this for output on <code>System.out</code>.
     */
    public static ParserFeedback stdout = new DefaultParserFeedback ();

    //
    // Static methods
    //

    /**
     * @param lineSeparatorString New Line separator to be used
     */
    public static void setLineSeparator(String lineSeparatorString)
    {
        lineSeparator = lineSeparatorString;
    }

    /**
     * Return the version string of this parser.
     * @return A string of the form:
     * <pre>
     * "[floating point number] ([build-type] [build-date])"
     * </pre>
     */
    public static String getVersion ()
    {
        return (VERSION_STRING);
    }

    /**
     * Return the version number of this parser.
     * @return A floating point number, the whole number part is the major
     * version, and the fractional part is the minor version.
     */
    public static double getVersionNumber ()
    {
        return (VERSION_NUMBER);
    }

    //
    // Constructors
    //

    /**
     * Zero argument constructor.
     * The parser is in a safe but useless state.
     * Set the lexer or connection using setLexer() or setConnection().
     * @see #setLexer(Lexer)
     * @see #setConnection(URLConnection)
     */
    public Parser ()
    {
        this (new Lexer (new Page ("")), noFeedback);
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
     * @param lexer The lexer to draw characters from.
     * @param fb The object to use when information,
     * warning and error messages are produced. If <em>null</em> no feedback
     * is provided.
     */
    public Parser(Lexer lexer, ParserFeedback fb)
    {
        setFeedback (fb);
        if (null == lexer)
            throw new IllegalArgumentException ("lexer cannot be null");
        setLexer (lexer);
        setNodeFactory (new PrototypicalNodeFactory ());
    }

    /**
     * Constructor for custom HTTP access.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @param fb The object to use for message communication.
     */
    public Parser (URLConnection connection, ParserFeedback fb)
        throws
            ParserException
    {
        this (new Lexer (connection), fb);
    }

    /**
     * Creates a Parser object with the location of the resource (URL or file)
     * You would typically create a DefaultHTMLParserFeedback object and pass it in.
     * @param resourceLocn Either the URL or the filename (autodetects).
     * A standard HTTP GET is performed to read the content of the URL.
     * @param feedback The HTMLParserFeedback object to use when information,
     * warning and error messages are produced. If <em>null</em> no feedback
     * is provided.
     * @see #Parser(URLConnection,ParserFeedback)
     */
    public Parser(String resourceLocn, ParserFeedback feedback) throws ParserException
    {
        this (openConnection (resourceLocn, feedback), feedback);
    }

    /**
     * Creates a Parser object with the location of the resource (URL or file).
     * A DefaultHTMLParserFeedback object is used for feedback.
     * @param resourceLocn Either the URL or the filename (autodetects).
     */
    public Parser(String resourceLocn) throws ParserException
    {
        this (resourceLocn, stdout);
    }

    /**
     * This constructor is present to enable users to plugin their own lexers.
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
     * @param lexer The source for HTML to be parsed.
     */
    public Parser (Lexer lexer)
    {
        this (lexer, stdout);
    }

    /**
     * Constructor for non-standard access.
     * A DefaultHTMLParserFeedback object is used for feedback.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @see #Parser(URLConnection,ParserFeedback)
     */
    public Parser (URLConnection connection) throws ParserException
    {
        this (connection, stdout);
    }

    //
    // Bean patterns
    //

    /**
     * Set the connection for this parser.
     * This method creates a new <code>Lexer</code> reading from the connection.
     * Trying to set the connection to null is a noop.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @exception ParserException if the character set specified in the
     * HTTP header is not supported, or an i/o exception occurs creating the
     * lexer.
     * @see #setLexer
     */
    public void setConnection (URLConnection connection)
        throws
            ParserException
    {
        if (null != connection)
            setLexer (new Lexer (connection));
    }

    /**
     * Return the current connection.
     * @return The connection either created by the parser or passed into this
     * parser via <code>setConnection</code>.
     * @see #setConnection(URLConnection)
     */
    public URLConnection getConnection ()
    {
        return (getLexer ().getPage ().getConnection ());
    }

    /**
     * Set the URL for this parser.
     * This method creates a new Lexer reading from the given URL.
     * Trying to set the url to null or an empty string is a noop.
     * @see #setConnection(URLConnection)
     */
    public void setURL (String url)
        throws
            ParserException
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
        return (getLexer ().getPage ().getUrl ());
    }

    /**
     * Set the encoding for the page this parser is reading from.
     * @param encoding The new character set to use.
     */
    public void setEncoding (String encoding)
        throws
            ParserException
    {
        getLexer ().getPage ().setEncoding (encoding);
    }
        
    /**
     * Get the encoding for the page this parser is reading from.
     * This item is set from the HTTP header but may be overridden by meta
     * tags in the head, so this may change after the head has been parsed.
     */
    public String getEncoding ()
    {
        return (getLexer ().getPage ().getEncoding ());
    }

    /**
     * Set the lexer for this parser.
     * The current NodeFactory is set on the given lexer, since the lexer
     * contains the node factory object.
     * It does not adjust the <code>feedback</code> object.
     * Trying to set the lexer to <code>null</code> is a noop.
     * @param lexer The lexer object to use.
     */
    public void setLexer (Lexer lexer)
    {
        NodeFactory factory;

        if (null != lexer)
        {   // move a node factory that's been set to the new lexer
            factory = null;
            if (null != getLexer ())
                factory = getLexer ().getNodeFactory ();
            if (null != factory)
                lexer.setNodeFactory (factory);
            mLexer = lexer;
        }
    }

    /**
     * Returns the reader associated with the parser
     * @return The current lexer.
     */
    public Lexer getLexer ()
    {
        return (mLexer);
    }

    /**
     * Get the current node factory.
     * @return The parser's node factory.
     */
    public NodeFactory getNodeFactory ()
    {
        return (getLexer ().getNodeFactory ());
    }

    /**
     * Get the current node factory.
     * @return The parser's node factory.
     */
    public void setNodeFactory (NodeFactory factory)
    {
        if (null == factory)
            throw new IllegalArgumentException ("node factory cannot be null");
        getLexer ().setNodeFactory (factory);
    }

    /**
     * Sets the feedback object used in scanning.
     * @param fb The new feedback object to use.
     */
    public void setFeedback (ParserFeedback fb)
    {
        mFeedback = (null == fb) ? noFeedback : fb;
    }

    /**
     * Returns the feedback.
     * @return HTMLParserFeedback
     */
    public ParserFeedback getFeedback()
    {
        return (mFeedback);
    }

    //
    // Public methods
    //

    /**
     * Reset the parser to start from the beginning again.
     */
    public void reset ()
    {
        getLexer ().reset ();
    }

    /**
     * Returns an iterator (enumeration) to the html nodes. Each node can be a tag/endtag/
     * string/link/image<br>
     * This is perhaps the most important method of this class. In typical situations, you will need to use
     * the parser like this :
     * <pre>
     * Parser parser = new Parser("http://www.yahoo.com");
     * for (NodeIterator i = parser.elements();i.hasMoreElements();) {
     *    Node node = i.nextHTMLNode();
     *    if (node instanceof StringNode) {
     *      // Downcasting to StringNode
     *      StringNode stringNode = (StringNode)node;
     *      // Do whatever processing you want with the string node
     *      System.out.println(stringNode.getText());
     *    }
     *    // Check for the node or tag that you want
     *    if (node instanceof ...) {
     *      // Downcast, and process
     *      // recursively (nodes within nodes)
     *    }
     * }
     * </pre>
     */
    public NodeIterator elements () throws ParserException
    {
        return (new IteratorImpl (getLexer (), getFeedback ()));
    }

    /**
     * Parse the given resource, using the filter provided.
     * @param filter The filter to apply to the parsed nodes.
     */
    public void parse (NodeFilter filter) throws ParserException
    {
        NodeIterator e;
        Node node;
        NodeList list;

        list = new NodeList ();
        for (e = elements (); e.hasMoreNodes (); )
        {
            node = e.nextNode ();
            if (null != filter)
            {
                node.collectInto (list, filter);
                for (int i = 0; i < list.size (); i++)
                    System.out.println (list.elementAt (i));
                list.removeAll ();
            }
            else
                System.out.println (node);
        }
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
            ret =  openConnection (url, feedback);
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
                ret = openConnection (url, feedback);
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
     * The main program, which can be executed from the command line
     */
    public static void main(String [] args)
    {
        System.out.println("HTMLParser v"+VERSION_STRING);
        if (args.length<1 || args[0].equals("-help"))
        {
            System.out.println();
            System.out.println("Syntax : java -jar htmlparser.jar <resourceLocn/website> [node_type]");
            System.out.println("   <resourceLocn/website> the URL or file to be parsed");
            System.out.println("   node_type an optional node name, for example:");
            System.out.println("     A - Show only the link tags extracted from the document");
            System.out.println("     IMG - Show only the image tags extracted from the document");
            System.out.println("     TITLE - Extract the title from the document");
            System.out.println();
            System.out.println("Example : java -jar htmlparser.jar http://www.yahoo.com");
            System.out.println();
            System.out.println("For support, please join the HTMLParser mailing list (user/developer) from the HTML Parser home page...");
            System.out.println("HTML Parser home page : http://htmlparser.sourceforge.net");
            System.out.println();
            System.exit(-1);
        }
        try
        {
            Parser parser = new Parser (args[0]);
            System.out.println ("Parsing " + parser.getURL ());
            NodeFilter filter;
            if (1 < args.length)
                filter = new TagNameFilter (args[1]);
            else
                filter = null;
            parser.parse (filter);
        }
        catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public void visitAllNodesWith(NodeVisitor visitor) throws ParserException {
        Node node;
        visitor.beginParsing();
        for (NodeIterator e = elements();e.hasMoreNodes();) {
            node = e.nextNode();
            node.accept(visitor);
        }
        visitor.finishedParsing();
    }

    /**
     * Initializes the parser with the given input HTML String.
     * @param inputHTML the input HTML that is to be parsed.
     */
    public void setInputHTML (String inputHTML)
        throws
            ParserException
    {
        if (null == inputHTML)
            throw new IllegalArgumentException ("html cannot be null");
        if (!"".equals (inputHTML))
            setLexer (new Lexer (new Page (inputHTML)));
    }

    /**
     * Extract all nodes matching the given filter.
     * @see Node#collectInto(NodeList, NodeFilter)
     */
    public NodeList extractAllNodesThatMatch (NodeFilter filter) throws ParserException
    {
        NodeIterator e;
        NodeList ret;
        
        ret = new NodeList ();
        for (e = elements (); e.hasMoreNodes (); )
            e.nextNode ().collectInto (ret, filter);

        return (ret);
    }

    /**
     * Convenience method to extract all nodes of a given class type.
     * @see Node#collectInto(NodeList, NodeFilter)
     */
    public Node [] extractAllNodesThatAre (Class nodeType) throws ParserException
    {
        NodeList ret;

        ret = extractAllNodesThatMatch (new NodeClassFilter (nodeType)); 

        return (ret.toNodeArray ());
    }

    /**
     * Creates the parser on an input string.
     * @param inputHTML
     * @return Parser
     */
    public static Parser createParser(String inputHTML)
    {
        Lexer lexer;
        Parser ret;

        if (null == inputHTML)
            throw new IllegalArgumentException ("html cannot be null");
        lexer = new Lexer (new Page (inputHTML));
        ret = new Parser (lexer);

        return (ret);
    }

    /**
     * @return String lineSeparator that will be used in toHTML()
     */
    public static String getLineSeparator() {
        return lineSeparator;
    }
}
