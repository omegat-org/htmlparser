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

package org.htmlparser.tags;

import org.htmlparser.Attribute;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.ParserException;

/**
 * A Meta Tag
 */
public class MetaTag
    extends
        TagNode
{
    /**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"META"};

    /**
     * Create a new meta tag.
     */
    public MetaTag ()
    {
    }

    /**
     * Return the set of names handled by this tag.
     * @return The names to be matched that create tags of this type.
     */
    public String[] getIds ()
    {
        return (mIds);
    }

    public String getHttpEquiv ()
    {
        return (getAttribute ("HTTP-EQUIV"));
    }

    public String getMetaContent ()
    {
        return (getAttribute ("CONTENT"));
    }

    public String getMetaTagName ()
    {
        return (getAttribute ("NAME"));
    }

    public void setHttpEquiv(String httpEquiv)
    {
        Attribute equiv;
        equiv = getAttributeEx ("HTTP-EQUIV");
        if (null != equiv)
            equiv.setValue (httpEquiv);
        else
            getAttributesEx ().add (new Attribute ("HTTP-EQUIV", httpEquiv));
    }

    public void setMetaTagContents(String metaTagContents)
    {
        Attribute content;
        content = getAttributeEx ("CONTENT");
        if (null != content)
            content.setValue (metaTagContents);
        else
            getAttributesEx ().add (new Attribute ("CONTENT", metaTagContents));
    }

    public void setMetaTagName(String metaTagName)
    {
        Attribute name;
        name = getAttributeEx ("NAME");
        if (null != name)
            name.setValue (metaTagName);
        else
            getAttributesEx ().add (new Attribute ("NAME", metaTagName));
    }
    
    /**
     * Check for a charset directive, and if found, set the charset for the page.
     */
    public void doSemanticAction () throws ParserException
    {
        String httpEquiv;
        String charset;

        httpEquiv = getHttpEquiv ();
        if ("Content-Type".equalsIgnoreCase (httpEquiv))
        {
            charset = getPage ().getCharset (getAttribute ("CONTENT"));
            getPage ().setEncoding (charset);
        }
    }
}
