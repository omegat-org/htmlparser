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

import org.htmlparser.scanners.ScriptScanner;

/**
 * A script tag.
 */
public class ScriptTag extends CompositeTag
{
    /**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"SCRIPT"};

    /**
     * The set of end tag names that indicate the end of this tag.
     */
    private static final String[] mEndTagEnders = new String[] {"BODY", "HTML"};

    /**
     * Create a new script tag.
     */
    public ScriptTag ()
    {
        setThisScanner (new ScriptScanner ());
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
     * Return the set of end tag names that cause this tag to finish.
     * @return The names of following end tags that stop further scanning.
     */
    public String[] getEndTagEnders ()
    {
        return (mEndTagEnders);
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
