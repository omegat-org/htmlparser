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

package org.htmlparser.tags;

import org.htmlparser.lexer.nodes.Attribute;

/**
 * A Meta Tag
 */
public class MetaTag extends Tag
{
    
    public MetaTag ()
    {
        setTagName ("META");
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

    public String toString()
    {
        return "META TAG\n"+
                "--------\n"+
                "Http-Equiv : "+getHttpEquiv()+"\n"+
                "Name : "+ getMetaTagName() +"\n"+
                "Contents : "+getMetaContent()+"\n";
    }
}
