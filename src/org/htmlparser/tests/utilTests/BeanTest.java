// HTMLParser Library v1_3_20030511 - A java-based parser for HTML
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

package org.htmlparser.tests.utilTests;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Vector;

import junit.framework.TestCase;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.beans.LinkBean;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class BeanTest extends TestCase
{
    public BeanTest (String name)
    {
        super (name);
    }

    protected byte[] pickle (Object object)
        throws
            IOException
    {
        ByteArrayOutputStream bos;
        ObjectOutputStream oos;
        byte[] ret;
        
        bos = new ByteArrayOutputStream ();
        oos = new ObjectOutputStream (bos);
        oos.writeObject (object);
        oos.close ();
        ret = bos.toByteArray ();

        return (ret);
    }
    
    protected Object unpickle (byte[] data)
        throws
            IOException,
            ClassNotFoundException
    {
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        Object ret;

        bis = new ByteArrayInputStream (data);
        ois = new ObjectInputStream (bis);
        ret = ois.readObject ();
        ois.close ();
        
        return (ret);
    }

    public void testZeroArgConstructor ()
        throws
            IOException,
            ClassNotFoundException,
            ParserException
    {
        Parser parser;
        byte[] data;
        
        parser = new Parser ();
        data = pickle (parser);
        parser = (Parser)unpickle (data);
    }
        
    public void testSerializable ()
        throws
            IOException,
            ClassNotFoundException,
            ParserException
    {
        Parser parser;
        Vector vector;
        NodeIterator enumeration;
        byte[] data;
        
        parser = new Parser ("http://htmlparser.sourceforge.net/test/example.html");
        enumeration = parser.elements ();
        vector = new Vector (50);
        while (enumeration.hasMoreNodes ())
            vector.addElement (enumeration.nextNode ());

        data = pickle (parser);
        parser = (Parser)unpickle (data);

        enumeration = parser.elements ();
        while (enumeration.hasMoreNodes ())
            assertEquals (
                "Nodes before and after serialization differ",
                ((Node)vector.remove (0)).toHtml (),
                ((Node)enumeration.nextNode ()).toHtml ());
    }
    
    public void testSerializableScanners ()
        throws
            IOException,
            ClassNotFoundException,
            ParserException
    {
        Parser parser;
        Vector vector;
        NodeIterator enumeration;
        byte[] data;
        
        parser = new Parser ("http://htmlparser.sourceforge.net/test/example.html");
        parser.registerScanners ();
        enumeration = parser.elements ();
        vector = new Vector (50);
        while (enumeration.hasMoreNodes ())
            vector.addElement (enumeration.nextNode ());

        data = pickle (parser);
        parser = (Parser)unpickle (data);

        enumeration = parser.elements ();
        while (enumeration.hasMoreNodes ())
            assertEquals (
                "Nodes before and after serialization differ",
                ((Node)vector.remove (0)).toHtml (),
                ((Node)enumeration.nextNode ()).toHtml ());
    }

    public void testSerializableStringBean ()
        throws
            IOException,
            ClassNotFoundException,
            ParserException
    {
        StringBean sb;
        String text;
        byte[] data;
        
        sb = new StringBean ();
        sb.setURL ("http://htmlparser.sourceforge.net/test/example.html");
        text = sb.getStrings ();

        data = pickle (sb);
        sb = (StringBean)unpickle (data);

        assertEquals (
            "Strings before and after serialization differ",
            text,
            sb.getStrings ());
    }

    public void testSerializableLinkBean ()
        throws
            IOException,
            ClassNotFoundException,
            ParserException
    {
        LinkBean lb;
        URL[] links;
        byte[] data;
        URL[] links2;
        
        lb = new LinkBean ();
        lb.setURL ("http://htmlparser.sourceforge.net/test/example.html");
        links = lb.getLinks ();

        data = pickle (lb);
        lb = (LinkBean)unpickle (data);

        links2 = lb.getLinks ();
        assertEquals ("Number of links after serialization differs", links.length, links2.length);
        for (int i = 0; i < links.length; i++)
        {
            assertEquals (
                "Links before and after serialization differ",
                links[i],
                links2[i]);
        }
    }
    
    public void testStringBeanListener ()
    {
        final StringBean sb;
        final Boolean hit[] = new Boolean[1];
        
        sb = new StringBean ();
        hit[0] = Boolean.FALSE;
        sb.addPropertyChangeListener (
            new PropertyChangeListener ()
            {
                public void propertyChange (PropertyChangeEvent event)
                {
                    if (event.getSource ().equals (sb))
                        if (event.getPropertyName ().equals (StringBean.PROP_STRINGS_PROPERTY))
                            hit[0] = Boolean.TRUE;
                }
            });

        hit[0] = Boolean.FALSE;
        sb.setURL ("http://htmlparser.sourceforge.net/test/example.html");
        assertTrue (
            "Strings property change not fired for URL change",
            hit[0].booleanValue ());

        hit[0] = Boolean.FALSE;
        sb.setLinks (true);
        assertTrue (
            "Strings property change not fired for links change",
            hit[0].booleanValue ());
    }

    public void testLinkBeanListener ()
    {
        final LinkBean lb;
        final Boolean hit[] = new Boolean[1];
        
        lb = new LinkBean ();
        hit[0] = Boolean.FALSE;
        lb.addPropertyChangeListener (
            new PropertyChangeListener ()
            {
                public void propertyChange (PropertyChangeEvent event)
                {
                    if (event.getSource ().equals (lb))
                        if (event.getPropertyName ().equals (LinkBean.PROP_LINKS_PROPERTY))
                            hit[0] = Boolean.TRUE;
                }
            });

        hit[0] = Boolean.FALSE;
        lb.setURL ("http://htmlparser.sourceforge.net/test/example.html");
        assertTrue (
            "Links property change not fired for URL change",
            hit[0].booleanValue ());
    }
}

