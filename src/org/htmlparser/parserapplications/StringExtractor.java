// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

package org.htmlparser.parserapplications;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.Translate;

public class StringExtractor
{
    
    private static final String newline = System.getProperty ("line.separator");
    private static final int newline_size = newline.length ();
    
    private String resource;

    /**
     * Construct a StringExtractor to read from the given resource.
     * @param resource Either a URL or a file name.
     */ 
    public StringExtractor (String resource)
    {
        this.resource = resource;
    }
    
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
        HTMLParser parser;
        HTMLNode node;
        Tag tag;
        boolean preformatted;
        StringBuffer results;
        
        parser = new HTMLParser (resource);
        parser.registerScanners ();
        results = new StringBuffer (4096);
        preformatted = false;
        for (NodeIterator e = parser.elements (); e.hasMoreNodes ();)
        {
            node = e.nextNode ();
            if (node instanceof HTMLStringNode)
            {
                // node is a plain string
                // cast it to an HTMLStringNode
                HTMLStringNode string = (HTMLStringNode)node;
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
            else if (node instanceof HTMLRemarkNode)
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

    /**
     * Mainline.
     * @param args The command line arguments.
     */
    public static void main (String[] args)
    {
        boolean links;
        String url;
        StringExtractor se;
        
        links = false;
        url = null;
        for (int i = 0; i < args.length; i++)
            if (args[i].equalsIgnoreCase ("-links"))
                links = true;
            else
                url = args[i];
        if (null != url)
        {
            se = new StringExtractor (url);
            try
            {
                System.out.println (se.extractStrings (links));
            }
            catch (ParserException e)
            {
                e.printStackTrace ();
            }
        }
        else
            System.out.println ("Usage: java -classpath htmlparser.jar org.htmlparser.parserapplications.StringExtractor [-links] url");
    }
}
