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

/**
 * A script tag.
 */
public class ScriptTag extends CompositeTag
{
    public ScriptTag ()
    {
        setTagName ("SCRIPT");
    }

    /**
     * Get the language attribute value.
     */
    public String getLanguage()
    {
        return (getAttribute("LANGUAGE"));
    }

    /**
     * Get the contents of the tag's children.
     */
    public String getScriptCode()
    {
        return (getChildrenHTML ());
    }

    /**
     * Get the type attribute value.
     */
    public String getType()
    {
        return (getAttribute("TYPE"));
    }

    /**
     * Set the language of the script tag.
     * @param language The new language value.
     */
    public void setLanguage (String language)
    {
        setAttribute ("LANGUAGE", language);
    }

    /**
     * Set the type of the script tag.
     * @param type The new type value.
     */
    public void setType (String type)
    {
        setAttribute ("TYPE", type);
    }

    /**
     * Print the contents of the script tag.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Script Node : \n");
        if (getLanguage () != null || getType () != null)
        {
            sb.append("Properties -->\n");
            if (getLanguage () != null && getLanguage ().length () !=0)
                sb.append("[Language : "+ getLanguage ()+"]\n");
            if (getType () != null && getType ().length () != 0)
                sb.append("[Type : "+ getType ()+"]\n");
        }
        sb.append("\n");
        sb.append("Code\n");
        sb.append("****\n");
        sb.append(getScriptCode()+"\n");
        return sb.toString();
    }
}
