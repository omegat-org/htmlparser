// HTMLParser Library v1_3_20021228 - A java-based parser for HTML
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

package org.htmlparser.util;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Processor class for links, is present basically as a utility class.
 */
public class HTMLLinkProcessor
    implements
        Serializable
{
    /**
     * Overriding base URL.
     * If set, this is used instead of a provided base URL in extract().
     */
    private String baseUrl;

    /**
     * Create an HTMLLinkProcessor.
     */
    public HTMLLinkProcessor ()
    {
        baseUrl = null;
    }

    /**
     * Create an absolute URL from a possibly relative link and a base URL.
     * @param link The reslative portion of a URL.
     * @param base The base URL unless overridden by the current baseURL property.
     * @return The fully qualified URL or the original link if a failure occured.
     */
    public String extract (String link, String base)
        throws
            HTMLParserException
    {
        URL url; // constructed URL combining relative link and base
        String path; // path portion of constructed URL
        boolean modified; // true if path is modified by us
        boolean absolute; // true if link starts with "/"
        int index;
        String ret;
        
        try
        {
            if (null == link)
                link = "";
            if (null != getBaseUrl ())
                base = getBaseUrl ();
            if ((null == base) || ("".equals (link)))
                ret = link;
            else
            {
                url = new URL (new URL (base), link);
                path = url.getFile ();
                modified = false;
                absolute = link.startsWith ("/");
                if (!absolute)
                {   // we prefer to fix incorrect relative links
                    // this doesn't fix them all, just the ones at the start
                    while (path.startsWith ("/."))
                    {
                        if (path.startsWith ("/../"))
                        {
                            path = path.substring (3);
                            modified = true;
                        }
                        else if (path.startsWith ("/./"))
                        {
                            path = path.substring (2);
                            modified = true;
                        }
                    }
                }
                // fix backslashes
                while (-1 != (index = path.indexOf ("/\\")))
                {
                    path = path.substring (0, index + 1) + path.substring (index + 2);
                    modified = true;
                }
                if (modified)
                    url = new URL (url, path);
                ret = url.toExternalForm ();
            }
        }
        catch (MalformedURLException murle)
        {
            ret = link;
        }
        
        return (Translate.decode (ret));
    }

    /**
     * Turn spaces into %20.
     * @param url The url containing spaces.
     * @return The URL with spaces as %20 sequences.
     */
    public static String fixSpaces (String url)
    {
        int index;
        int length;
        char ch;
        StringBuffer returnURL;
        
        index = url.indexOf (' ');
        if (-1 != index)
        {
            length = url.length ();
            returnURL = new StringBuffer (length * 3);
            returnURL.append (url.substring (0, index));
            for (int i = index; i < length; i++)
            {
                ch = url.charAt (i);
                if (ch==' ')
                    returnURL.append ("%20");
                else
                    returnURL.append (ch);
            }
            url = returnURL.toString ();
        }
        
        return (url);
    }
    
    /**
     * Check if a resource is a valid URL.
     * @param resourceLocn The resource to test.
     * @return <code>true</code> if the resource is a valid URL.
     */
    public static boolean isURL (String resourceLocn)
    {
        URL url;
        boolean ret;
        
        try
        {
            url = new URL (resourceLocn);
            ret = true;
        }
        catch (MalformedURLException murle)
        {
            ret = false;
        }
        
        return (ret);
    }
    
    /**
     * Returns the baseUrl.
     * @return String
     */
    public String getBaseUrl ()
    {
        return baseUrl;
    }
    
    /**
     * Sets the baseUrl.
     * @param baseUrl The baseUrl to set
     */
    public void setBaseUrl (String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

	public static String removeLastSlash(String baseUrl) {
	  if(baseUrl.charAt(baseUrl.length()-1)=='/')
	  {
	     return baseUrl.substring(0,baseUrl.length()-1);
	  }
	  else
	  {
	     return baseUrl;
	  }
	}
    
}
