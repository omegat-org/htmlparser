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


package org.htmlparser.tags;

import java.util.Vector;
import org.htmlparser.util.LinkProcessor;

/**
 * BaseHrefTag represents an &lt;Base&gt; tag.
 * It extends a basic tag by providing an accessor to the HREF attribute.
 */
public class BaseHrefTag extends Tag
{
    public BaseHrefTag ()
    {
        setTagName ("BASE");
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
            base = LinkProcessor.removeLastSlash (base.trim());
        base = (null == base) ? "" : base;
        
        return (base);
    }

    public void setBaseUrl (String base)
    {
        setAttribute ("HREF", base);
    }

    public String toString()
    {
        return "BASE TAG\n"+
                "--------\n"+
                "Name : "+getBaseUrl();
    }

    /**
     * Override this because we need a trigger to set the base HREF on the page.
     * NOTE: setting of the attributes is the last thing done on the tag
     * after creation.
     * @param attribs The new BASE tag attributes.
     */
    public void setAttributesEx (Vector attribs)
    {
        super.setAttributesEx (attribs);
        getPage ().getLinkProcessor ().setBaseUrl (getBaseUrl ());
    }
}
