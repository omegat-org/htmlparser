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

import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;

/**
 * BaseHrefTag represents an &lt;Base&gt; tag.
 * It extends a basic tag by providing an accessor to the HREF attribute.
 */
public class BaseHrefTag extends Tag
{
    /**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"BASE"};

    /**
     * Create a new base tag.
     */
    public BaseHrefTag ()
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

    /**
     * Get the value of the HREF attribute, if any.
     * @return The HREF value, with the last slash removed, if any.
     */
    public String getBaseUrl()
    {
        String base;

        base = getAttribute ("HREF");
        if (base != null && base.length() > 0)
            base = base.trim ();
        base = (null == base) ? "" : base;
        
        return (base);
    }

    public void setBaseUrl (String base)
    {
        setAttribute ("HREF", base);
    }

    /**
     * Perform the meaning of this tag.
     * This sets the base URL to use for the rest of the page.
     */
    public void doSemanticAction () throws ParserException
    {
        Page page;
        
        page = getPage ();
        if (null != page)
        {
            page.getLinkProcessor ().setBaseUrl (getBaseUrl ());
        }
    }
}
