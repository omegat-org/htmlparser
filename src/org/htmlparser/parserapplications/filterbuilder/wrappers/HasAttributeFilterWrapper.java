// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2005 Derrick Oswald
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

package org.htmlparser.parserapplications.filterbuilder.wrappers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Wrapper for HasAttributeFilters.
 */
public class HasAttributeFilterWrapper
	extends
		Filter
	implements
        ActionListener,
        DocumentListener
{
    /**
     * The underlying filter.
     */
    protected HasAttributeFilter mFilter;

    /**
     * Combo box for attribute name.
     */
    protected JComboBox mAttributeName;

    /**
     * The check box for has value.
     */
    protected JCheckBox mValued;

	/**
	 * Value to check for.
	 */
    protected JTextArea mAttributeValue;

    /**
     * Create a wrapper over a new HasAttributeFilter.
     */ 
    public HasAttributeFilterWrapper ()
    {
        String value;

        mFilter = new HasAttributeFilter ();

        // add the attribute name choice
        mAttributeName = new JComboBox ();
        mAttributeName.setEditable (true);
        add (mAttributeName);
        mAttributeName.addItem (mFilter.getAttributeName ());
        mAttributeName.addActionListener (this);

        // add the valued flag
        value = mFilter.getAttributeValue ();
        mValued = new JCheckBox ("Has Value");
        add (mValued);
        mValued.setSelected (null != value);
        mValued.addActionListener (this);

        // add the value pattern
        mAttributeValue = new JTextArea (2, 20);
        mAttributeValue.setBorder (new BevelBorder (BevelBorder.LOWERED));
        add (mAttributeValue);
        if (null != value)
            mAttributeValue.setText (value);
        else
            mAttributeValue.setVisible (false);
        mAttributeValue.getDocument ().addDocumentListener (this);
    }

    //
    // local methods
    //

    protected void addAttributes (Set set, Node node)
    {
        Vector attributes;
        Attribute attribute;
        String name;
        NodeList children;

        if (node instanceof Tag)
        {
            attributes = ((Tag)node).getAttributesEx ();
            for (int i = 1; i < attributes.size (); i++)
            {
                attribute = (Attribute)attributes.elementAt (i);
                name = attribute.getName ();
                if (null != name)
                    set.add (name);
            }
            if (node instanceof CompositeTag)
            {
                children = ((CompositeTag)node).getChildren ();
                if (null != children)
                    for (int i = 0; i < children.size (); i++)
                        addAttributes (set, children.elementAt (i));
            }
        }
    }

    protected void addAttributeValues (Set set, Node node)
    {
        Vector attributes;
        Attribute attribute;
        String value;
        NodeList children;

        if (node instanceof Tag)
        {
            attributes = ((Tag)node).getAttributesEx ();
            for (int i = 1; i < attributes.size (); i++)
            {
                attribute = (Attribute)attributes.elementAt (i);
                if (null != attribute.getName ())
                {
                    value = attribute.getValue ();
                    if (null != value)
                        set.add (value);
                }
            }
            if (node instanceof CompositeTag)
            {
                children = ((CompositeTag)node).getChildren ();
                if (null != children)
                    for (int i = 0; i < children.size (); i++)
                        addAttributeValues (set, children.elementAt (i));
            }
        }
    }

    //
    // Filter overrides and concrete implementations
    //

    public String getDescription ()
    {
        return ("Has attribute");
    }

    public String getIconSpec ()
    {
        return ("images/HasAttributeFilter.gif");
    }

    public NodeFilter getNodeFilter ()
    {
        HasAttributeFilter ret;
        
        ret = new HasAttributeFilter ();
        ret.setAttributeName (mFilter.getAttributeName ());
        ret.setAttributeValue (mFilter.getAttributeValue ());
            
        return (ret);
    }

    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        Set set;
        String name;
        String value;

        mFilter = (HasAttributeFilter)filter;
        set = new HashSet ();
        context.reset ();
        try
        {
	        for (NodeIterator iterator = context.elements (); iterator.hasMoreNodes (); )
	            addAttributes (set, iterator.nextNode ());
        }
        catch (ParserException pe)
        {
            // oh well, we tried
        }
        for (Iterator iterator = set.iterator (); iterator.hasNext (); )
            mAttributeName.addItem (iterator.next ());
        name = mFilter.getAttributeName ();
        if (!name.equals (""))
            mAttributeName.setSelectedItem (name);
        value = mFilter.getAttributeValue ();
        if (null != value)
        {
            mValued.setSelected (true);
            mAttributeValue.setVisible (true);
            mAttributeValue.setText (value);
        }
        else
        {
            mValued.setSelected (false);
            mAttributeValue.setVisible (false);
        }
    }

    public NodeFilter[] getSubNodeFilters ()
    {
        return (new NodeFilter[0]);
    }

    public void setSubNodeFilters (NodeFilter[] filters)
    {
        // should we complain?
    }

    public String toJavaCode (StringBuffer out, int[] context)
    {
        String ret;
        
        ret = "filter" + context[1]++;
        spaces (out, context[0]);
        out.append ("HasAttributeFilter ");
        out.append (ret);
        out.append (" = new HasAttributeFilter ();");
        newline (out);
        spaces (out, context[0]);
        out.append (ret);
        out.append (".setAttributeName (\"");
        out.append (mFilter.getAttributeName ());
        out.append ("\");");
        newline (out);
        if (null != mFilter.getAttributeValue ())
        {
	        spaces (out, context[0]);
	        out.append (ret);
	        out.append (".setAttributeValue (\"");
	        out.append (mFilter.getAttributeValue ());
	        out.append ("\");");
	        newline (out);
        }
        
        return (ret);
    }

    //
    // NodeFilter interface
    //

    public boolean accept (Node node)
    {
        return (mFilter.accept (node));
    }

    //
    // ActionListener interface
    //

    /**
     * Invoked when an action occurs on the combo box.
     */
    public void actionPerformed (ActionEvent event)
    {
        Object source;
        Object[] selection;
        boolean valued;

        source = event.getSource ();
        if (source == mAttributeName)
        {
            selection = mAttributeName.getSelectedObjects ();
            if ((null != selection) && (0 != selection.length))
                mFilter.setAttributeName ((String)selection[0]);
        }
        else if (source == mValued)
        {
	        valued = mValued.isSelected ();
	        if (valued)
	        {
	            mFilter.setAttributeValue (mAttributeValue.getText ());
	            mAttributeValue.setVisible (true);
	        }
	        else
	        {
	            mAttributeValue.setVisible (false);
	            mAttributeValue.setText ("");
	            mFilter.setAttributeValue (null);
	        }
        }
    }

    //
    // DocumentListener interface
    //

    public void insertUpdate (DocumentEvent e)
    {
        Document doc;
        
        doc = e.getDocument ();
        try
        {
            mFilter.setAttributeValue (doc.getText (0, doc.getLength ()));
        }
        catch (BadLocationException ble)
        {
            ble.printStackTrace ();
        }
    }

    public void removeUpdate (DocumentEvent e)
    {
        Document doc;
        
        doc = e.getDocument ();
        try
        {
            mFilter.setAttributeValue (doc.getText (0, doc.getLength ()));
        }
        catch (BadLocationException ble)
        {
            ble.printStackTrace ();
        }
    }

    public void changedUpdate (DocumentEvent e)
    {
        // plain text components don't fire these events
    }
}
