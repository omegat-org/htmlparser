// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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
import org.htmlparser.scanners.AppletScanner;
import org.htmlparser.scanners.BaseHrefScanner;
import org.htmlparser.scanners.BodyScanner;
import org.htmlparser.scanners.BulletListScanner;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.scanners.DivScanner;
import org.htmlparser.scanners.DoctypeScanner;
import org.htmlparser.scanners.FormScanner;
import org.htmlparser.scanners.FrameSetScanner;
import org.htmlparser.scanners.HeadScanner;
import org.htmlparser.scanners.HtmlScanner;
import org.htmlparser.scanners.ImageScanner;
import org.htmlparser.scanners.JspScanner;
import org.htmlparser.scanners.LinkScanner;
import org.htmlparser.scanners.MetaTagScanner;
import org.htmlparser.scanners.ScriptScanner;
import org.htmlparser.scanners.StyleScanner;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.scanners.TitleScanner;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
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
 * [2] Register the common scanners. See {@link #registerScanners()} <BR>
 * You wouldnt do this if you want to configure a custom lightweight parser. In that case,
 * you would add the scanners of your choice using {@link #addScanner(TagScanner)}<BR>
 * [3] Enumerate through the elements from the parser object <BR>
 * It is important to note that the parsing occurs when you enumerate, ON DEMAND. This is a thread-safe way,
 * and you only get the control back after a particular element is parsed and returned.
 *
 * <BR>
 * Below is some sample code to parse Yahoo.com and print all the tags.
 * <pre>
 * Parser parser = new Parser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * // In this example, we are registering all the common scanners
 * parser.registerScanners();
 * for (NodeIterator i = parser.elements();i.hasMoreNodes();) {
 *  Node node = i.nextNode();
 *  node.print();
 * }
 * </pre> Below is some sample code to parse Yahoo.com and print only the text
 * information. This scanning will run faster, as there are no scanners
 * registered here.
 * <pre>
 * Parser parser = new Parser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * // In this example, none of the scanners need to be registered
 * // as a string node is not a tag to be scanned for.
 * for (NodeIterator i = parser.elements();i.hasMoreNodes();) {
 *  Node node = i.nextNode();
 *  if (node instanceof StringNode) {
 *      StringNode stringNode =
 *      (StringNode)node;
 *      System.out.println(stringNode.getText());
 *  }
 * }
 * </pre>
 * The above snippet will print out only the text contents in the html document.<br>
 * Here's another snippet that will only print out the link urls in a document.
 * This is an example of adding a link scanner.
 * <pre>
 * Parser parser = new Parser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * parser.addScanner(new LinkScanner("-l"));
 * for (NodeIterator i = parser.elements();i.hasMoreNodes();) {
 *  Node node = i.nextNode();
 *  if (node instanceof LinkTag) {
 *      LinkTag linkTag = (LinkTag)node;
 *      System.out.println(linkTag.getLink());
 *  }
 * }
 * </pre>
 *  @see Parser#elements()
 */
public class Parser
    implements
        Serializable,
        NodeFactory
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
    VERSION_DATE = "Nov 09, 2003"
    ;

    /**
     * The display version.
     */
    public final static String
    VERSION_STRING = "" + VERSION_NUMBER + " (" + VERSION_TYPE + " " + VERSION_DATE + ")"
    ;

    // End of formatting

    /**
     * This object is used by the StringParser to create new StringNodes at runtime, based on
     * use configurations of the factory
     */
    private StringNodeFactory stringNodeFactory;

    /**
     * Feedback object.
     */
    protected ParserFeedback feedback;

    /**
     * The html lexer associated with this parser.
     */
    protected Lexer mLexer;

    /**
     * The list of scanners to apply at the top level.
     */
    protected Map mScanners;

    /**
     * The list of tags to return at the top level.
     * The list is keyed by tag name.
     */
    protected Map mBlastocyst;

    /**
     * The current scanner when recursing into a tag.
     */
    protected TagScanner mScanner;

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
        setFeedback (null);
        setScanners (null);
        setLexer (new Lexer (new Page ("")));
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
    public Parser(Lexer lexer, ParserFeedback fb)
    {
        setFeedback (fb);
        setScanners (null);
        setLexer (lexer);
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
        setFeedback (fb);
        setScanners (null);
        setConnection (connection);
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
     * @param reader The source for HTML to be parsed.
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
     * It does not adjust the <code>mScanners</code> list
     * or <code>feedback</code> object. Trying to
     * set the connection to null is a noop.
     * @param connection A fully conditioned connection. The connect()
     * method will be called so it need not be connected yet.
     * @exception ParserException if the character set specified in the
     * HTTP header is not supported, or an i/o exception occurs creating the
     * lexer.
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
     * It does not adjust the <code>mScanners</code> list
     * or <code>feedback</code> object. Trying to set the url to null or an
     * empty string is a noop.
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
     * @param The new character set to use.
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
     * TIt does not adjust the <code>mScanners</code> list
     * or <code>feedback</code> object.
     * Trying to set the lexer to <code>null</code> is a noop.
     * @param lexer The lexer object to use.
     */
    public void setLexer (Lexer lexer)
    {
        if (null != lexer)
        {
            mLexer = lexer;
            mLexer.setNodeFactory (this);
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
     * Get the number of scanners registered currently in the parser.
     * @return int number of scanners registered.
     */
    public int getNumScanners()
    {
        return mScanners.size();
    }

    /**
     * This method is to be used to change the set of scanners in the current parser.
     * @param newScanners List of scanner objects to be used during the parsing process.
     */
    public void setScanners (Map newScanners)
    {
        Iterator iterator;
        TagScanner scanner;

        flushScanners ();
        if (null != newScanners)
            for (iterator = newScanners.entrySet ().iterator (); iterator.hasNext (); )
            {
                scanner = (TagScanner)iterator.next ();
                addScanner (scanner);
            }
    }

    /**
     * Get the list of scanners registered currently in the parser
     * @return List of scanners currently registered in the parser
     */
    public Map getScanners()
    {
        return mScanners;
    }

    /**
     * Sets the feedback object used in scanning.
     * @param fb The new feedback object to use.
     */
    public void setFeedback(ParserFeedback fb)
    {
        feedback = (null == fb) ? noFeedback : fb;
    }

    /**
     * Returns the feedback.
     * @return HTMLParserFeedback
     */
    public ParserFeedback getFeedback() {
        return feedback;
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
     * Add a new Tag Scanner.
     * In typical situations where you require a no-frills parser, use the registerScanners() method to add the most
     * common parsers. But when you wish to either compose a parser with only certain scanners registered, use this method.
     * It is advantageous to register only the scanners you want, in order to achieve faster parsing speed. This method
     * would also be of use when you have developed custom scanners, and need to register them into the parser.
     * @param scanner TagScanner object (or derivative) to be added to the list of registered scanners.
     */
    public void addScanner(TagScanner scanner)
    {
        String ids[];
        Tag tag;
        
        ids = scanner.getID();
        for (int i = 0; i < ids.length; i++)
        {
            mScanners.put (ids[i], scanner);
            // for now, the only way to create a tag is to ask the scanner...
            try
            {
                if (scanner instanceof CompositeTagScanner)
                {
                    tag = ((CompositeTagScanner)scanner).createTag (null, 0, 0, null, null, null, null);
                    mBlastocyst.put (ids[i], tag);
                }
                else
                {
                    tag = scanner.createTag (null, 0, 0, null, null, null);
                    mBlastocyst.put (ids[i], tag);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace ();
            }
        }
    }

    /**
     * Returns an iterator (enumeration) to the html nodes. Each node can be a tag/endtag/
     * string/link/image<br>
     * This is perhaps the most important method of this class. In typical situations, you will need to use
     * the parser like this :
     * <pre>
     * Parser parser = new Parser("http://www.yahoo.com");
     * parser.registerScanners();
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
     *    }
     * }
     * </pre>
     * @param filter The filter to apply to the nodes.
     */
    public NodeIterator elements () throws ParserException
    {
        return (new IteratorImpl (getLexer (), feedback));
    }

    /**
     * Flush the current scanners registered.
     * The registered scanners list becomes empty with this call.
     */
    public void flushScanners()
    {
        mScanners = new Hashtable ();
        mBlastocyst = new Hashtable ();
    }

    /**
     * Return the scanner registered in the parser having the
     * given id
     * @param id The id of the requested scanner
     * @return TagScanner The Tag Scanner
     */
    public TagScanner getScanner (String id)
    {
        Tag tag;
        TagScanner ret;

        ret = null;

        tag = (Tag)mBlastocyst.get (id);
        if (null != tag)
            ret = (TagScanner)tag.getThisScanner ();
        
        return (ret);
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
     * This method should be invoked in order to register some common scanners.
     * The scanners that get added are : <br>
     * LinkScanner    (filter key "-l")<br>
     * ImageScanner   (filter key "-i")<br>
     * ScriptScanner  (filter key "-s") <br>
     * StyleScanner   (filter key "-t") <br>
     * JspScanner     (filter key "-j") <br>
     * AppletScanner  (filter key "-a") <br>
     * MetaTagScanner (filter key "-m") <br>
     * TitleScanner   (filter key "-t") <br>
     * DoctypeScanner (filter key "-d") <br>
     * FormScanner    (filter key "-f") <br>
     * FrameSetScanner(filter key "-r") <br>
     * BulletListScanner(filter key "-bulletList") <br>
     * DivScanner(filter key "-div") <br>
     * TableScanner(filter key "") <br>
     * <br>
     * Call this method after creating the Parser object. e.g. <BR>
     * <pre>
     * Parser parser = new Parser("http://www.yahoo.com");
     * parser.registerScanners();
     * </pre>
     */
    public void registerScanners() {
        if (mScanners.size()>0)
        {
            System.err.println("registerScanners() should be called first, when no other scanner has been registered.");
            System.err.println("Other scanners already exist, hence this method call won't have any effect");
            return;
        }
        addScanner(new LinkScanner(LinkTag.LINK_TAG_FILTER));
        addScanner(new ImageScanner(ImageTag.IMAGE_TAG_FILTER));
        addScanner(new ScriptScanner("-s"));
        addScanner(new StyleScanner("-t"));
        addScanner(new JspScanner("-j"));
        addScanner(new AppletScanner("-a"));
        addScanner(new MetaTagScanner("-m"));
        addScanner(new TitleScanner("-T"));
        addScanner(new DoctypeScanner("-d"));
        addScanner(new FormScanner("-f",this));
        addScanner(new FrameSetScanner("-r"));
        addScanner(new BaseHrefScanner("-b"));
        addScanner(new BulletListScanner("-bulletList",this));
    //  addScanner(new SpanScanner("-p"));
        addScanner(new DivScanner("-div"));
        addScanner(new TableScanner(this));
    }

    /**
     * Make a call to registerDomScanners(), instead of registerScanners(),
     * when you are interested in retrieving a Dom representation of the html
     * page. Upon parsing, you will receive an Html object - which will contain
     * children, one of which would be the body. This is still evolving, and in
     * future releases, you might see consolidation of Html - to provide you
     * with methods to access the body and the head.
     */
    public void registerDomScanners() {
        registerScanners();
        addScanner(new HtmlScanner());
        addScanner(new BodyScanner());
        addScanner(new HeadScanner());
    }

    /**
     * Removes a specified scanner object. You can create
     * an anonymous object as a parameter. This method
     * will use the scanner's key and remove it from the
     * registry of scanners.
     * e.g.
     * <pre>
     * removeScanner(new FormScanner(""));
     * </pre>
     * @param scanner TagScanner object to be removed from the list of registered scanners
     */
    public void removeScanner(TagScanner scanner)
    {
        String[] ids;

        ids = scanner.getID ();
        for (int i = 0; i < ids.length; i++)
        {
            mScanners.remove (ids[i]);
            mBlastocyst.remove (ids[i]);
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
            parser.registerScanners ();
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
     * @see Node#collectInto()
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
     * @see Node#collectInto()
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

    public static Parser createLinkRecognizingParser(String inputHTML) {
        Parser parser = createParser(inputHTML);
        parser.addScanner(new LinkScanner(LinkTag.LINK_TAG_FILTER));
        return parser;
    }

    /**
     * @return String lineSeparator that will be used in toHTML()
     */
    public static String getLineSeparator() {
        return lineSeparator;
    }

    public StringNodeFactory getStringNodeFactory() {
        if (stringNodeFactory == null)
            stringNodeFactory = new StringNodeFactory();
        return stringNodeFactory;
    }

    public void setStringNodeFactory(StringNodeFactory stringNodeFactory) {
        this.stringNodeFactory = stringNodeFactory;
    }
    
    //
    // NodeFactory interface
    //

    /**
     * Create a new string node.
     * @param page The page the node is on.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public Node createStringNode (Page page, int start, int end)
    {
        Node ret;
        
        ret = new StringNode (page, start, end);
        if (null != stringNodeFactory)
        {
            if (stringNodeFactory.shouldDecodeNodes ())
                ret = new DecodingNode (ret);
            if (stringNodeFactory.shouldRemoveEscapeCharacters ())
                ret = new EscapeCharacterRemovingNode (ret);
            if (stringNodeFactory.shouldConvertNonBreakingSpace ())
                ret = new NonBreakingSpaceConvertingNode (ret);
        }

        return (ret);
    }

    /**
     * Create a new remark node.
     * @param page The page the node is on.
     * @param start The beginning position of the remark.
     * @param end The ending positiong of the remark.
     */
    public Node createRemarkNode (Page page, int start, int end)
    {
        return (new RemarkNode (page, start, end));
    }

    /**
     * Create a new tag node.
     * Note that the attributes vector contains at least one element,
     * which is the tag name (standalone attribute) at position zero.
     * This can be used to decide which type of node to create, or
     * gate other processing that may be appropriate.
     * @param page The page the node is on.
     * @param start The beginning position of the tag.
     * @param end The ending positiong of the tag.
     * @param attributes The attributes contained in this tag.
     */
    public Node createTagNode (Page page, int start, int end, Vector attributes)
        throws
            ParserException
    {
        Attribute attribute;
        String id;
        Tag prototype;
        Tag ret;

        ret = null;

        if (0 != attributes.size ())
        {
            attribute = (Attribute)attributes.elementAt (0);
            id = attribute.getName ();
            if (null != id)
            {
                try
                {
                    id = id.toUpperCase ();
                    if (!id.startsWith ("/"))
                    {
                        if (id.endsWith ("/"))
                            id = id.substring (0, id.length () - 1);
                        prototype = (Tag)mBlastocyst.get (id);
                        if (null != prototype)
                        {
                            ret = (Tag)prototype.clone ();
                            ret.setPage (page);
                            ret.setStartPosition (start);
                            ret.setEndPosition (end);
                            ret.setAttributesEx (attributes);
                        }
                    }
                }
                catch (CloneNotSupportedException cnse)
                {
                    // default to creating a new one
                }
            }
        }
        if (null == ret)
            ret = new Tag (page, start, end, attributes);

        return (ret);
    }
}
