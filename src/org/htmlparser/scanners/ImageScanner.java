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


package org.htmlparser.scanners;
//////////////////
// Java Imports //
//////////////////
import java.util.Hashtable;
import java.util.Vector;
import org.htmlparser.lexer.nodes.Attribute;

import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
/**
 * Scans for the Image Tag. This is a subclass of TagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class ImageScanner extends TagScanner
{
    public static final String IMAGE_SCANNER_ID = "IMG";
    private LinkProcessor processor;
    /**
     * Overriding the default constructor
     */
    public ImageScanner()
    {
        super();
        processor = new LinkProcessor();
    }
    /**
     * Overriding the constructor to accept the filter
     */
    public ImageScanner(String filter,LinkProcessor processor)
    {
        super(filter);
        this.processor = processor;
    }

   /**
    * Extract the location of the image
    * Given the tag (with attributes), and the url of the html page in which
    * this tag exists, perform best effort to extract the 'intended' URL.
    * Attempts to handle such attributes as:
    * <pre>
    * &lt;IMG SRC=http://www.redgreen.com&gt; - normal
    * &lt;IMG SRC =http://www.redgreen.com&gt; - space between attribute name and equals sign
    * &lt;IMG SRC= http://www.redgreen.com&gt; - space between equals sign and attribute value
    * &lt;IMG SRC = http://www.redgreen.com&gt; - space both sides of equals sign
    * </pre>
    * @param tag The tag with the 'SRC' attribute.
    * @param url URL of web page being parsed.
    */
    public String extractImageLocn (Tag tag, String url) throws ParserException
    {
        Vector attributes;
        int size;
        Attribute attribute;
        String string;
        String data;
        int state;
        String name;
        String ret;

        ret = "";
        state = 0;
        attributes = tag.getAttributesEx ();
        size = attributes.size ();
        for (int i = 0; (i < size) && (state < 3); i++)
        {
            attribute = (Attribute)attributes.elementAt (i);
            string = attribute.getName ();
            data = attribute.getValue ();
            switch (state)
            {
                case 0: // looking for 'src'
                    if (null != string)
                    {
                        name = string.toUpperCase ();
                        if (name.equals ("SRC"))
                        {
                            state = 1;
                            if (null != data)
                            {
                                if ("".equals (data))
                                    state = 2; // empty attribute, SRC= 
                                else
                                {
                                    ret = data;
                                    i = size; // exit fast
                                }
                            }

                        }
                        else if (name.startsWith ("SRC"))
                        {
                            // missing equals sign
                            ret = string.substring (3);
                            state = 0; // go back to searching for SRC
                            // because, maybe we found SRCXXX
                            // where XXX isn't a URL
                        }
                    }
                    break;
                case 1: // looking for equals sign
                    if (null != string)
                    {
                        if (string.startsWith ("="))
                        {
                            state = 2;
                            if (1 < string.length ())
                            {
                                ret = string.substring (1);
                                state = 0; // keep looking ?
                            }
                            else if (null != data)
                            {
                                ret = string.substring (1);
                                state = 0; // keep looking ?
                            }
                        }
                    }
                    break;
                case 2: // looking for a valueless attribute that could be a relative or absolute URL
                    if (null != string)
                    {
                        if (null == data)
                            ret = string;
                        state = 0; // only check first non-whitespace item
                        // not every valid attribute after an equals
                    }
                    break;
                default:
                    throw new IllegalStateException ("we're not supposed to in state " + state);
            }
        }
        ret = ParserUtils.removeChars (ret, '\n');
        ret = ParserUtils.removeChars (ret, '\r');
        ret = processor.extract (ret, url);
        
        return (ret);
    }

    public String [] getID() {
        String [] ids = new String[1];
        ids[0] = IMAGE_SCANNER_ID;
        return ids;
    }

    protected Tag createTag(TagData tagData, Tag tag, String url)
        throws ParserException {
        String link = extractImageLocn(tag,url);
        return new ImageTag(tagData, link);
    }

}
