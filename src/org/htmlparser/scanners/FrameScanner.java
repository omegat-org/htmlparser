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

import java.util.Hashtable;
import java.util.Vector;
import org.htmlparser.lexer.Page;

import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;

/**
 * Scanner for frame tags.
 */
public class FrameScanner extends TagScanner
{
    /**
     * Overriding the default constructor
     */
    public FrameScanner()
    {
        super();
    }

    /**
     * Overriding the constructor to accept the filter
     */
    public FrameScanner(String filter)
    {
        super(filter);
    }

    /**
     * @see org.htmlparser.scanners.TagScanner#getID()
     */
    public String [] getID() 
    {
        String [] ids = new String[1];
        ids[0] = "FRAME";
        return ids;
    }

    protected Tag createTag (Page page, int start, int end, Vector attributes, Tag tag, String url) throws ParserException
    {
        FrameTag ret;
        
        ret = new FrameTag ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);
        
        return (ret);
    }
}
