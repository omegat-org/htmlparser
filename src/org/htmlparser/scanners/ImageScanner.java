// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
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
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.Attribute;

import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.Tag;
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

    public String [] getID() {
        String [] ids = new String[1];
        ids[0] = IMAGE_SCANNER_ID;
        return ids;
    }

    protected Tag createTag (Page page, int start, int end, Vector attributes, Tag tag, String url) throws ParserException
    {
        ImageTag ret;

        ret = new ImageTag ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);

        // special step here...
        // Need to update the imageURL string in the image tag,
        // but not the SRC attribute which it does when you set the ImageURL
        // property. Can't do it in the tag, because the tag doesn't have the
        // current link processor object which might have a BASE href different
        // than the page.
        String src = ret.getAttribute ("SRC");
        ret.setImageURL (processor.extract (ret.getImageURL (), page.getUrl ()));
        if (null == src)
            ret.removeAttribute ("SRC");
        else
            ret.setAttribute ("SRC", src);

        return (ret);
    }
}