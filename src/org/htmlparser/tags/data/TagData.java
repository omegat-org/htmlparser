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

package org.htmlparser.tags.data;

import java.util.Vector;
import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.util.ParserException;

public class TagData {
    private Page mPage;
    private int tagBegin;
    private int tagEnd;
    private Vector mAttributes;
    private String urlBeingParsed;
    private boolean isXmlEndTag;

    public TagData(Page page, int tagBegin, int tagEnd, Vector attributes) {
        this(page, tagBegin, tagEnd, attributes, "", false);
    }

    public TagData(Page page, int tagBegin, int tagEnd, Vector attributes, String urlBeingParsed) {
        this(page, tagBegin, tagEnd, attributes, urlBeingParsed, false);
    }

    public TagData(Page page, int tagBegin, int tagEnd, Vector attributes, String urlBeingParsed, boolean isXmlEndTag) {
        mPage = page;
        this.tagBegin = tagBegin;
        this.tagEnd   = tagEnd;
        mAttributes = attributes;
        this.urlBeingParsed = urlBeingParsed;
        this.isXmlEndTag = isXmlEndTag;
    }

    /**
     * Create a virtual tag.
     * Not on the page but virtually injected at the given position.
     */
    public TagData(String name, int tagBegin, Vector attributes, String urlBeingParsed, boolean isXmlEndTag)
    {
        this (
            null,
            tagBegin,
            tagBegin, // a virtual node has no length, + name.length () + 2 + (isXmlEndTag ? 1 : 0),
                      // was a todo: add attribute sizes to length
            attributes,
            urlBeingParsed,
            isXmlEndTag);
        if (null != name && (0 == attributes.size ()))
            attributes.insertElementAt (new Attribute (name), 0);
            
    }
    
    public int getTagBegin() {
        return tagBegin;
    }

    public void setTagBegin(int begin) {
        tagBegin = begin;
    }

    public int getTagEnd() {
        return tagEnd;
    }

    public void setTagEnd(int end) {
        tagEnd = end;
    }

    public String getTagContents()
    {
        String ret;

        if (null != mPage)
            ret = mPage.getText (getTagBegin(), getTagEnd());
        else
            ret = "";
            
        return (ret);
    }

    /**
     * @deprecated A tag may span more than one line. 
     */
    public String getTagLine()
    {
        String ret;

        if (null != mPage)
            ret = mPage.getLine (getTagBegin ());
        else
            ret = "";
            
        return (ret);
    }

    public void setTagContents (String tagContents, Vector attributes, String url, boolean xml_end_tag)
    {
        mPage = new Page (tagContents);
        tagBegin = 0;
        tagEnd = tagContents.length ();
        // TODO: this really needs work
        try
        {
            Cursor cursor = new Cursor (mPage, tagBegin);
            for (int i = tagBegin; i < tagEnd; i++)
                mPage.getCharacter (cursor);
        }
        catch (ParserException pe)
        {
        }
        mAttributes = attributes;
        urlBeingParsed = url;
        isXmlEndTag = xml_end_tag;
    }

    public String getUrlBeingParsed() {
        return urlBeingParsed;
    }

    public void setUrlBeingParsed(String baseUrl) {
        this.urlBeingParsed = baseUrl;
    }

    public boolean isEmptyXmlTag() {
        return isXmlEndTag;
    }

    /**
     * Returns the line number where the tag starts in the HTML. At the moment this
     * will only be valid for tags created with the
     * <code>CompositeTagScanner</code> or a subclass of it.
     */
    public int getStartLine ()
    {
        int ret;

        if (null != mPage)
            ret = mPage.row (getTagBegin ());
        else
            ret = -1;
            
        return (ret);
    }

    /**
     * Returns the line number where the tag ends in the HTML. At the moment this
     * will only be valid for tags created with the
     * <code>CompositeTagScanner</code> or a subclass of it.
     */
    public int getEndLine()
    {
        int ret;

        if (null != mPage)
            ret = mPage.row (getTagEnd ());
        else
            ret = -1;
            
        return (ret);
    }

    public Page getPage ()
    {
        return (mPage);
    }

    public Vector getAttributes ()
    {
        return (mAttributes);
    }
}
