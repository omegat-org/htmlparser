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

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.RegexFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;

/**
 * Wrapper for RegexFilters.
 */
public class RegexFilterWrapper
	extends
		Filter
	implements
        ActionListener,
        DocumentListener
{
    /**
     * Mapping for RegexFilter constants to strings.
     */
    public static Object[][] mMap;
    static
    {
        mMap = new Object[3][];
        mMap[0] = new Object[2];
        mMap[0][0] = "MATCH";
        mMap[0][1] = new Integer (RegexFilter.MATCH);
        mMap[1] = new Object[2];
        mMap[1][0] = "LOOKINGAT";
        mMap[1][1] = new Integer (RegexFilter.LOOKINGAT);
        mMap[2] = new Object[2];
        mMap[2][0] = "FIND";
        mMap[2][1] = new Integer (RegexFilter.FIND);
    }

    /**
     * The underlying filter.
     */
    protected RegexFilter mFilter;

	/**
	 * Text to check for
	 */
    protected JTextArea mPattern;

    /**
     * Combo box for strategy.
     */
    protected JComboBox mStrategy;

    /**
     * Create a wrapper over a new RegexFilter.
     */ 
    public RegexFilterWrapper ()
    {
        mFilter = new RegexFilter ();

        // add the text pattern
        mPattern = new JTextArea (2, 20);
        mPattern.setBorder (new BevelBorder (BevelBorder.LOWERED));
        add (mPattern);
        mPattern.getDocument ().addDocumentListener (this);
        mPattern.setText (mFilter.getPattern ());

        // add the strategy choice
        mStrategy = new JComboBox ();
        mStrategy.addItem ("MATCH");
        mStrategy.addItem ("LOOKINGAT");
        mStrategy.addItem ("FIND");
        add (mStrategy);
        mStrategy.addActionListener (this);
        mStrategy.setSelectedIndex (strategyToIndex (mFilter.getStrategy ()));
    }

    //
    // Filter overrides and concrete implementations
    //

    public String getDescription ()
    {
        return ("Nodes containing regex");
    }

    public String getIconSpec ()
    {
        return ("images/RegexFilter.gif");
    }

    public NodeFilter getNodeFilter ()
    {
        RegexFilter ret;
        
        ret = new RegexFilter ();
        ret.setStrategy (mFilter.getStrategy ());
        ret.setPattern (mFilter.getPattern ());
            
        return (ret);
    }

    public void setNodeFilter (NodeFilter filter, Parser context)
    {
        mFilter = (RegexFilter)filter;
        mPattern.setText (mFilter.getPattern ());
        mStrategy.setSelectedIndex (strategyToIndex (mFilter.getStrategy ()));
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
        out.append ("RegexFilter ");
        out.append (ret);
        out.append (" = new RegexFilter ();");
        newline (out);
        spaces (out, context[0]);
        out.append (ret);
        out.append (".setStrategy (RegexFilter.");
        out.append (strategyToString (mFilter.getStrategy ()));
        out.append (");");
        newline (out);
        spaces (out, context[0]);
        out.append (ret);
        out.append (".setPattern (\"");
        out.append (mFilter.getPattern ());
        out.append ("\");");
        newline (out);
        
        return (ret);
    }

    public String strategyToString (int strategy)
    {
        for (int i =0; i < mMap.length; i++)
            if (strategy == ((Integer)mMap[i][1]).intValue ())
                return ((String)mMap[i][0]);
        throw new IllegalArgumentException ("unknown strategy constant - " + strategy);
    }

    public int stringToStrategy (String strategy)
    {
        for (int i =0; i < mMap.length; i++)
            if (strategy.equalsIgnoreCase ((String)mMap[i][0]))
                return (((Integer)mMap[i][1]).intValue ());
        throw new IllegalArgumentException ("unknown strategy constant - " + strategy);
    }

    public int strategyToIndex (int strategy)
    {
        for (int i =0; i < mMap.length; i++)
            if (strategy == ((Integer)mMap[i][1]).intValue ())
                return (i);
        throw new IllegalArgumentException ("unknown strategy constant - " + strategy);
    }

    public int indexToStrategy (int index)
    {
        return (((Integer)mMap[index][1]).intValue ());
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

        source = event.getSource ();
        if (source == mStrategy)
            mFilter.setStrategy (indexToStrategy (mStrategy.getSelectedIndex ()));
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
            mFilter.setPattern (doc.getText (0, doc.getLength ()));
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
            mFilter.setPattern (doc.getText (0, doc.getLength ()));
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
