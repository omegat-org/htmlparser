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

import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
/**
 * Scans for the Link Tag. This is a subclass of TagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class LinkScanner extends CompositeTagScanner
{
    private static final String MATCH_NAME [] = {"A"};
    public static final String LINK_SCANNER_ID = "A";
    public LinkProcessor processor;
    private final static String ENDERS [] = { "TD","TR","FORM","LI","BODY", "HTML" };
    private final static String ENDTAG_ENDERS [] = { "TD","TR","FORM","LI","BODY", "HTML" };

    /**
     * Overriding the default constructor
     */
    public LinkScanner() {
        this("");
    }

    /**
     * Overriding the constructor to accept the filter
     */
    public LinkScanner(String filter) {
        super(filter,MATCH_NAME,ENDERS,ENDTAG_ENDERS, false);
        processor = new LinkProcessor();
    }

    public Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
    {
        LinkTag ret;

        ret = new LinkTag ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);
        ret.setStartTag (startTag);
        ret.setEndTag (endTag);
        ret.setChildren (children);

        return (ret);        
    }

    /**
     * Check if we can handle this tag.
     * @param tag The generic tag with the name A.
     * @param previousOpenScanner Indicates any previous scanner which hasn't
     * completed, before the current scan has begun.
     */
    public boolean evaluate (Tag tag, TagScanner previousOpenScanner)
    {
        return (null != tag.getAttributeEx ("HREF"));
    }

    public BaseHrefScanner createBaseHREFScanner(String filter) {
        return new BaseHrefScanner(filter,processor);
    }

    public ImageScanner createImageScanner(String filter) {
        return new ImageScanner(filter,processor);
    }

    /**
     * @see org.htmlparser.scanners.TagScanner#getID()
     */
    public String [] getID() {
        return MATCH_NAME;
    }

}
