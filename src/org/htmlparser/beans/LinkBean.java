// HTMLParser Library v1_2 - A java-based parser for HTML
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

package org.htmlparser.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.ObjectFindingVisitor;

/**
 * Extract strings from a URL.
 * @author Derrick Oswald
 * Created on December 23, 2002, 5:01 PM
 */
public class LinkBean extends Object implements Serializable
{
    /**
     * Property name in event where the URL contents changes.
     */
    public static final String PROP_LINKS_PROPERTY = "Links";

    /**
     * Property name in event where the URL changes.
     */
    public static final String PROP_URL_PROPERTY = "URL";

    /**
     * Bound property support.
     */
    protected PropertyChangeSupport mPropertySupport;

    /**
     * The strings extracted from the URL.
     */
    protected URL[] mLinks;
    
    /**
     * The parser used to extract strings.
     */
    protected HTMLParser mParser;

    /** Creates new StringBean */
    public LinkBean ()
    {
        mPropertySupport = new PropertyChangeSupport (this);
        mLinks = null;
        mParser = new HTMLParser ();
    }

    //
    // internals
    //

    protected URL[] extractLinks (String url) throws ParserException
    {
        HTMLParser parser;
        Vector vector;
        HTMLNode node;
        LinkTag link;
        URL[] ret;
        
        parser = new HTMLParser (url);
        parser.registerScanners ();
        ObjectFindingVisitor visitor = new ObjectFindingVisitor(LinkTag.class);
        parser.visitAllNodesWith(visitor);
        HTMLNode [] nodes = visitor.getTags();
        vector = new Vector();
        for (int i = 0; i < nodes.length; i++)
            try
            {
                link = (LinkTag)nodes[i];
                vector.add(new URL (link.getLink ()));
            }
            catch (MalformedURLException murle)
            {
                //vector.remove (i);
                //i--;
            }
        ret = new URL[vector.size ()];
        vector.copyInto (ret);

        return (ret);
    }

    /**
     * Determine if two arrays of URL's are the same.
     * @param array1 One array of URL's
     * @param array2 Another array of URL's
     * @return <code>true</code> if the URL's match in number and value,
     * <code>false</code> otherwise.
     */
    protected boolean equivalent (URL[] array1, URL[] array2)
    {
        boolean ret;
        
        ret = false;
        if ((null == array1) && (null == array2))
            ret = true;
        else if ((null != array1) && (null != array2))
            if (array1.length == array2.length)
            {
                ret = true;
                for (int i = 0; i < array1.length && ret; i++)
                    if (!(array1[i] == array2[i]))
                        ret = false;
            }
        
        return (ret);
    }

    //
    // Property change support.
    //

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * @param listener The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener (PropertyChangeListener listener)
    {
        mPropertySupport.addPropertyChangeListener (listener);
    }

    /**
     * Remove a PropertyChangeListener from the listener list.
     * This removes a PropertyChangeListener that was registered for all properties.
     * @param the PropertyChangeListener to be removed.
     */
    public void removePropertyChangeListener (PropertyChangeListener listener)
    {
        mPropertySupport.removePropertyChangeListener (listener);
    }
    
    //
    // Properties
    //

    /**
     * Refetch the URL contents.
     */
    private void setLinks ()
    {
        String url;
        URL[] urls;
        URL[] oldValue;

        url = getURL ();
        if (null != url)
            try
            {
                urls = extractLinks (getURL ());
                if (!equivalent (mLinks, urls))
                {
                    oldValue = mLinks;
                    mLinks = urls;
                    mPropertySupport.firePropertyChange (PROP_LINKS_PROPERTY, oldValue, mLinks);
                }
            }
            catch (ParserException hpe)
            {
                mLinks = null;
            }
    }

    /**
     * Getter for property links.
     * @return Value of property links.
     */
    public URL[] getLinks ()
    {
        if (null == mLinks)
            try
            {
                mLinks = extractLinks (getURL ());
                mPropertySupport.firePropertyChange (PROP_LINKS_PROPERTY, null, mLinks);
            }
            catch (ParserException hpe)
            {
                mLinks = null;
            }

        return (mLinks);
    }

    
    /**
     * Getter for property URL.
     * @return Value of property URL.
     */
    public String getURL ()
    {
        return (mParser.getURL ());
    }
    
    /**
     * Setter for property URL.
     * @param url New value of property URL.
     */
    public void setURL (String url)
    {
        String old;
        
        old = getURL ();
        if (((null == old) && (null != url)) || ((null != old) && !old.equals (url)))
        {
            try
            {
                mParser.setURL (url);
                mPropertySupport.firePropertyChange (PROP_URL_PROPERTY, old, getURL ());
                setLinks ();
            }
            catch (ParserException hpe)
            {
                // failed... now what
            }
        }
    }

    /**
     * Getter for property Connection.
     * @return Value of property Connection.
     */
    public URLConnection getConnection ()
    {
        return (mParser.getConnection ());
    }
    
    /**
     * Setter for property Connection.
     * @param url New value of property Connection.
     */
    public void setConnection (URLConnection connection)
    {
        try
        {
            mParser.setConnection (connection);
            setLinks ();
        }
        catch (ParserException hpe)
        {
            // failed... now what
        }
    }

//    /**
//     * Unit test.
//     */
//    public static void main (String[] args)
//    {
//        LinkBean lb = new LinkBean ();
//        lb.setURL ("http://cbc.ca");
//        URL[] urls = lb.getLinks ();
//        for (int i = 0; i < urls.length; i++)
//            System.out.println (urls[i]);
//    }
}


