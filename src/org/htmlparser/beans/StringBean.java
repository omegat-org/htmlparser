// HTMLParser Library v1_2 - A java-based parser for HTML
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

package org.htmlparser.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.net.URLConnection;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.RemarkNode;
import org.htmlparser.StringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.Translate;

/**
 * Extract strings from a URL.
 * @author Derrick Oswald
 * Created on December 23, 2002, 5:01 PM
 */
public class StringBean implements Serializable
{
    /**
     * Property name in event where the URL contents changes.
     */
    public static final String PROP_STRINGS_PROPERTY = "Strings";

    /**
     * Property name in event where the link request property changes.
     */
    public static final String PROP_LINKS_PROPERTY = "Links";

    /**
     * Property name in event where the URL changes.
     */
    public static final String PROP_URL_PROPERTY = "URL";

    /**
     * A newline.
     */
    private static final String newline = System.getProperty ("line.separator");

    /**
     * The length of the newline.
     */
    private static final int newline_size = newline.length ();

    /**
     * Bound property support.
     */
    protected PropertyChangeSupport mPropertySupport;

    /**
     * The strings extracted from the URL.
     */
    protected String mStrings;
    
    /**
     * If <code>true</code> the link URL is embedded in the text output.
     */
    protected boolean mLinks;
    
    /**
     * The parser used to extract strings.
     */
    protected Parser mParser;

    /** Creates new StringBean */
    public StringBean ()
    {
        mPropertySupport = new PropertyChangeSupport (this);
        mStrings = null;
        mLinks = false;
        mParser = new Parser ();
    }

    //
    // internals
    //

    /**
     * Add the given text collapsing whitespace.
     * Use a little finite state machine:
     * <pre>
     * state 0: whitepace was last emitted character
     * state 1: in whitespace
     * state 2: in word
     * A whitespace character moves us to state 1 and any other character
     * moves us to state 2, except that state 0 stays in state 0 until
     * a non-whitespace and going from whitespace to word we emit a space
     * before the character:
     *    input:     whitespace   other-character
     * state\next
     *    0               0             2
     *    1               1        space then 2
     *    2               1             2
     * </pre>
     * @param buffer The buffer to append to.
     * @param string The string to append.
     */
    protected void collapse (StringBuffer buffer, String string)
    {
        int chars;
        int length;
        int state;
        char character;
        
        chars = string.length ();
        if (0 != chars)
        {
            length = buffer.length ();
            state = (   (0 == length)
            || (buffer.charAt (length - 1) == ' ')
            || ((newline_size <= length) && buffer.substring (length - newline_size, length).equals (newline))) ? 0 : 1;
            for (int i = 0; i < chars; i++)
            {
                character = string.charAt (i);
                switch (character)
                {
                    // see HTML specification section 9.1 White space
                    // http://www.w3.org/TR/html4/struct/text.html#h-9.1
                    case '\u0020':
                    case '\u0009':
                    case '\u000C':
                    case '\u200B':
                    case '\r':
                    case '\n':
                        if (0 != state)
                            state = 1;
                        break;
                    default:
                        if (1 == state)
                            buffer.append (' ');
                        state = 2;
                        buffer.append (character);
                }
            }
        }
        
    }
    
    /**
     * Appends a newline to the buffer if there isn't one there already.
     * Except if the buffer is empty.
     * @param buffer The buffer to append to.
     */
    protected void carriage_return (StringBuffer buffer)
    {
        int length;
        
        length = buffer.length ();
        if (   (0 != length) // why bother appending newlines to the beginning of a buffer
        && (   (newline_size <= length) // not enough chars to hold a newline
        && (!buffer.substring (length - newline_size, length).equals (newline))))
            buffer.append (newline);
    }
    
    /**
     * Extract the text from a page.
     * @param links if <code>true</code> include hyperlinks in output.
     * @return The textual contents of the page.
     */
    public String extractStrings (boolean links)
        throws
            ParserException
    {
        Node node;
        Tag tag;
        boolean preformatted;
        StringBuffer results;

        mParser.flushScanners ();
        mParser.registerScanners ();
        results = new StringBuffer (4096);
        preformatted = false;
        for (NodeIterator e = mParser.elements (); e.hasMoreNodes ();)
        {
            node = e.nextNode ();
            if (node instanceof StringNode)
            {
                // node is a plain string
                // cast it to an HTMLStringNode
                StringNode string = (StringNode)node;
                // retrieve the data from the object
                if (preformatted)
                    results.append (string.getText ());
                else
                    collapse (results, Translate.decode (string.getText ()));
            }
            else if (node instanceof LinkTag)
            {
                // node is a link
                // cast it to an HTMLLinkTag
                LinkTag link = (LinkTag)node;
                // retrieve the data from the object
                if (preformatted)
                    results.append (link.getLinkText ());
                else
                    collapse (results, Translate.decode (link.getLinkText ()));
                if (links)
                {
                    results.append ("<");
                    results.append (link.getLink ());
                    results.append (">");
                }
            }
            else if (node instanceof FormTag)
            {
                FormTag form = (FormTag)node;
                if (form.breaksFlow ()) // it does
                    carriage_return (results);
                if (preformatted)
                    results.append (form.toPlainTextString ());
                else
                    collapse (results, Translate.decode (form.toPlainTextString ()));
            }
            else if (node instanceof RemarkNode)
            {
                // skip comments
            }
            else if (node instanceof Tag)
            {
                tag = (Tag)node;
                if (tag.breaksFlow ())
                    carriage_return (results);
                if (tag.getText ().toUpperCase ().equals ("PRE"))
                    preformatted = !(tag instanceof EndTag);
            }
        }
        
        return (results.toString ());
    }

    //
    // Property change support.
    //

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * @param listener The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener (PropertyChangeListener listener)
    {
        mPropertySupport.addPropertyChangeListener (listener);
    }

    /**
     * Remove a PropertyChangeListener from the listener list.
     * This removes a PropertyChangeListener that was registered for all properties.
     * @param the PropertyChangeListener to be removed.
     */
    public void removePropertyChangeListener (PropertyChangeListener listener)
    {
        mPropertySupport.removePropertyChangeListener (listener);
    }
    
    //
    // Properties
    //

    /**
     * Getter for property strings.
     * @return Value of property strings.
     */
    public String getStrings ()
    {
        if (null == mStrings)
            try
            {
                mStrings = extractStrings (getLinks ());
                mPropertySupport.firePropertyChange (PROP_STRINGS_PROPERTY, null, mStrings);
            }
            catch (ParserException hpe)
            {
                mStrings = hpe.toString ();
            }

        return (mStrings);
    }

    /**
     * Refetch the URL contents.
     */
    private void setStrings ()
    {
        String url;
        String strings;
        String oldValue;

        url = getURL ();
        if (null != url)
            try
            {
                mParser.setURL (getURL ()); // bad: reset the scanner
                strings = extractStrings (getLinks ());
                if ((null == mStrings) || !mStrings.equals (strings))
                {
                    oldValue = mStrings;
                    mStrings = strings;
                    mPropertySupport.firePropertyChange (PROP_STRINGS_PROPERTY, oldValue, mStrings);
                }
            }
            catch (ParserException hpe)
            {
                mStrings = hpe.toString ();
            }
    }

    /**
     * Getter for property links.
     * @return Value of property links.
     */
    public boolean getLinks ()
    {
        return (mLinks);
    }
    
    /**
     * Setter for property links.
     * @param links New value of property links.
     */
    public void setLinks (boolean links)
    {
        boolean oldValue = mLinks;
        if (oldValue != links)
        {
            mLinks = links;
            mPropertySupport.firePropertyChange (PROP_LINKS_PROPERTY, oldValue, mLinks);
            setStrings ();
        }
    }
    
    /**
     * Getter for property URL.
     * @return Value of property URL.
     */
    public String getURL ()
    {
        return (mParser.getURL ());
    }
    
    /**
     * Setter for property URL.
     * @param url New value of property URL.
     */
    public void setURL (String url)
    {
        String old;
        
        old = getURL ();
        if (((null == old) && (null != url)) || ((null != old) && !old.equals (url)))
        {
            try
            {
                mParser.setURL (url);
                mPropertySupport.firePropertyChange (PROP_URL_PROPERTY, old, getURL ());
                setStrings ();
            }
            catch (ParserException hpe)
            {
                // failed... now what
            }
        }
    }

    /**
     * Getter for property Connection.
     * @return Value of property Connection.
     */
    public URLConnection getConnection ()
    {
        return (mParser.getConnection ());
    }
    
    /**
     * Setter for property Connection.
     * @param url New value of property Connection.
     */
    public void setConnection (URLConnection connection)
    {
        try
        {
            mParser.setConnection (connection);
            setStrings ();
        }
        catch (ParserException hpe)
        {
            // failed... now what
        }
    }

//    /**
//     * Unit test.
//     */
//    public static void main (String[] args)
//    {
//        StringBean sb = new StringBean ();
//        sb.setURL ("http://cbc.ca");
//        sb.setLinks (true);
//        System.out.println (sb.getStrings ());
//    }
}
