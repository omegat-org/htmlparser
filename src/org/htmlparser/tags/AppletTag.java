// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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

import java.util.Enumeration;
import java.util.Hashtable;

import org.htmlparser.Node;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * AppletTag represents an &lt;Applet&gt; tag.
 * It extends a basic tag by providing accessors to the class, codebase,
 * archive and parameters.
 */
public class AppletTag extends CompositeTag 
{
    /**
     * Create a new AppletTag with the dats given.
     * @param tagData The data for this tag.
     * @param compositeTagData The data for this composite tag.
     */
    public AppletTag (TagData tagData,CompositeTagData compositeTagData) 
    {
        super(tagData,compositeTagData);
    }

    /**
     * Extract the applet <code>PARAM</code> tags from the child list.
     * @return The list of applet parameters (keys and values are String objects).
     */
    public Hashtable createAppletParamsTable ()
    {
        NodeList kids;
        Node node;
        Tag tag;
        String paramName;
        String paramValue;
        Hashtable ret;

        ret = new Hashtable ();
        kids = getChildren ();
        for (int i = 0; i < kids.size (); i++)
        { 
            node = children.elementAt(i);
            if (node instanceof Tag)
            {
                tag = (Tag)node;
                if (tag.getTagName().equals ("PARAM"))
                {
                    paramName = tag.getAttribute ("NAME");
                    if (null != paramName && 0 != paramName.length ())
                    {
                        paramValue = tag.getAttribute ("VALUE");
                        ret.put (paramName,paramValue);
                    }
                }
            }
        }
        
        return (ret);
    }

    /**
     * Get the class name of the applet.
     * @return The value of the <code>CODE</code> attribute.
     */
    public String getAppletClass ()
    {
        return (getAttribute ("CODE"));
    }

    /**
     * Get the applet parameters.
     * @return The list of parameter values (keys and values are String objects).
     */
    public Hashtable getAppletParams ()
    {
        return (createAppletParamsTable ());
    }
    
    /**
     * Get the jar file of the applet.
     * @return The value of the <code>ARCHIVE</code> attribute, or <code>null</code> if it wasn't specified.
     */
    public String getArchive()
    {
        return (getAttribute ("ARCHIVE"));
    }
    
    /**
     * Get the code base of the applet.
     * @return The value of the <code>CODEBASE</code> attribute, or <code>null</code> if it wasn't specified.
     */
    public String getCodeBase()
    {
        return (getAttribute ("CODEBASE"));
    }

    /**
     * Get the <code>PARAM<code> tag with the given name.
     * <em>NOTE: This was called (erroneously) getAttribute() in previous versions.</em>
     * @param key The applet parameter name to get.
     * @return The value of the parameter or <code>null</code> if there is no parameter of that name.
     */
    public String getParameter (String key)
    {
        return ((String)(getAppletParams ().get (key)));
    }

    /**
     * Get an enumeration over the (String) parameter names.
     * @return An enumeration of the <code>PARAM<code> tag <code>NAME<code> attributes.
     */
    public Enumeration getParameterNames () 
    {
        return (getAppletParams ().keys ());
    }

    /**
     * Set the <code>CODE<code> attribute.
     * @param The new applet class.
     */
    public void setAppletClass (String newAppletClass)
    {
        setAttribute ("CODE", newAppletClass);
    }
    
    /**
     * Set the enclosed <code>PARM<code> children.
     * @param The new parameters.
     */
    public void setAppletParams (Hashtable newAppletParams)
    {
        NodeList kids;
        Node node;
        Tag tag;
        String paramName;
        String paramValue;
        String s;
        TagData tagData;
        
        kids = getChildren ();
        // erase appletParams from kids
        for (int i = 0; i < kids.size (); )
        {
            node = kids.elementAt (i);
            if (node instanceof Tag)
                if (((Tag)node).getTagName ().equals ("PARAM"))
                    kids.remove (i);
                else
                    i++;
            else
                i++;
        }
        
        // add newAppletParams to kids
        for (Enumeration e = newAppletParams.keys (); e.hasMoreElements (); )
        {
            paramName = (String)e.nextElement ();
            paramValue = (String)newAppletParams.get (paramName);
            s = "PARAM VALUE=\"" + paramValue + "\" NAME=\"" + paramName + "\"";
            tagData = new TagData (0, 0, 0, 0, s, s, "", false); // what, no URL?
            kids.add (new Tag (tagData));
        }
        
        //set kids as new children
        setChildren (kids);
    }
    
    /**
     * Set the <code>ARCHIVE<code> attribute.
     * @param The new archive file.
     */
    public void setArchive (String newArchive)
    {
        setAttribute ("ARCHIVE", newArchive);
    }
    
    /**
     * Set the <code>CODEBASE<code> attribute.
     * @param The new applet code base.
     */
    public void setCodeBase (String newCodeBase)
    {
        setAttribute ("CODEBASE", newCodeBase);
    }

    /**
     * Output a string representing this applet tag.
     * @return A string showing the contents of the applet tag.
     */
    public String toString ()
    {
        Hashtable parameters;
        Enumeration params;
        String paramName;
        String paramValue;
        boolean found;
        Node node;
        StringBuffer ret;
        
        ret = new StringBuffer(500);
        ret.append ("Applet Tag\n");
        ret.append ("**********\n");
        ret.append ("Class Name = ");
        ret.append (getAppletClass ());
        ret.append ("\n");
        ret.append ("Archive = ");
        ret.append (getArchive ());
        ret.append ("\n");
        ret.append ("Codebase = ");
        ret.append (getCodeBase ());
        ret.append ("\n");
        parameters = getAppletParams ();
        params = parameters.keys ();
        if (null == params)
            ret.append ("No Params found.\n");
        else
            for (int cnt = 0; params.hasMoreElements (); cnt++)
            {
                paramName = (String)params.nextElement ();
                paramValue = (String)parameters.get (paramName);
                ret.append (cnt);
                ret.append (": Parameter name = ");
                ret.append (paramName);
                ret.append (", Parameter value = ");
                ret.append (paramValue);
                ret.append ("\n");
            }
        found = false;
        for (SimpleNodeIterator e = children (); e.hasMoreNodes ();)
        {
            node = e.nextNode ();
            if (node instanceof Tag)
                if (((Tag)node).getTagName ().equals ("PARAM"))
                    continue;
            if (!found)
                ret.append ("Miscellaneous items :\n");
            else
                ret.append (" ");
            found = true;
            ret.append (node.toString ());
        }
        if (found)
            ret.append ("\n");
        ret.append ("End of Applet Tag\n");
        ret.append ("*****************\n");

        return (ret.toString ());
    }
}
