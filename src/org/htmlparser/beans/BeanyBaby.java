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

/**
 * Demo of beans.
 * Created on December 30, 2002, 7:54 PM
 * @author Derrick Oswald
 */
public class BeanyBaby
    extends
        javax.swing.JFrame
    implements
        java.beans.PropertyChangeListener,
        java.awt.event.ActionListener,
        java.awt.event.MouseListener
{
    
    /** Creates new form BeanyBaby */
    public BeanyBaby ()
    {
        initComponents ();
        // shenanigans to get the splitter bar at the midpoint
        show ();
        mSplitPane.setDividerLocation(0.5);
        hide ();
        // set up twinning
        mLinkBean.addPropertyChangeListener (this);
        mStringBean.addPropertyChangeListener (this);
        // set up user input
        mTextField.addActionListener (this);
        mLinkBean.addMouseListener (this);
    }

    //
    // PropertyChangeListener interface
    //

    /**
     * This method ties the two beans together on the same connection.
     * Whenever a property changes on one bean, make sure the URL properties
     * agree by setting the connection from one to the other.
     * @param event The event describing the event source
     * and the property that has changed.
     */
    public void propertyChange (java.beans.PropertyChangeEvent event)
    {
        Object source;
        
        source = event.getSource ();
        if (source == mLinkBean)
        {
            if (!mLinkBean.getURL ().equals (mStringBean.getURL ()))
                mStringBean.setConnection (mLinkBean.getConnection ());
        }
        else if (source == mStringBean)
        {
            if (!mStringBean.getURL ().equals (mLinkBean.getURL ()))
                mLinkBean.setConnection (mStringBean.getConnection ());
        }
    }

    //
    // ActionListener interface
    //

    public void actionPerformed (java.awt.event.ActionEvent event)
    {
        Object source;
        String url;

        source = event.getSource ();
        if (source == mTextField)
        {
            url = mTextField.getText ();
            mTextField.selectAll ();
            mLinkBean.setURL (url);
        }
    }
    
    //
    // MouseListener interface
    //
    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     */
    public void mouseClicked (java.awt.event.MouseEvent event)
    {
        int index;
        String url;

        if (2 == event.getClickCount())
        {
            index = mLinkBean.locationToIndex (event.getPoint ());
            url = mLinkBean.getModel ().getElementAt (index).toString ();
            setURL (url);
        }
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered (java.awt.event.MouseEvent event)
    {
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited (java.awt.event.MouseEvent event)
    {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed (java.awt.event.MouseEvent event)
    {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased (java.awt.event.MouseEvent event)
    {
    }

    //
    // API control
    //
    public void setURL (String url)
    {
        mTextField.setText (url);
        actionPerformed (new java.awt.event.ActionEvent (mTextField, 0, "hit it"));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        javax.swing.JScrollPane jScrollPane1;
        javax.swing.JScrollPane jScrollPane2;
        javax.swing.JPanel jPanel1;

        jPanel1 = new javax.swing.JPanel();
        mSplitPane = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        mLinkBean = new org.htmlparser.beans.HTMLLinkBean();
        jScrollPane2 = new javax.swing.JScrollPane();
        mStringBean = new org.htmlparser.beans.HTMLTextBean();
        mTextField = new javax.swing.JTextField();

        setTitle("BeanyBaby");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(mLinkBean);

        mSplitPane.setLeftComponent(jScrollPane1);

        jScrollPane2.setViewportView(mStringBean);

        mSplitPane.setRightComponent(jScrollPane2);

        jPanel1.add(mSplitPane, java.awt.BorderLayout.CENTER);

        mTextField.setToolTipText("Enter the URL to view");
        jPanel1.add(mTextField, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(640, 480));
        setLocation((screenSize.width-640)/2,(screenSize.height-480)/2);
    }//GEN-END:initComponents
    
    /** Exit the Application */
    private void exitForm (java.awt.event.WindowEvent evt)
    {//GEN-FIRST:event_exitForm
        System.exit (0);
    }//GEN-LAST:event_exitForm
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.htmlparser.beans.HTMLLinkBean mLinkBean;
    private javax.swing.JTextField mTextField;
    private javax.swing.JSplitPane mSplitPane;
    private org.htmlparser.beans.HTMLTextBean mStringBean;
    // End of variables declaration//GEN-END:variables

    /**
     * Unit test.
     */
    public static void main (String[] args)
    {
        BeanyBaby bb = new BeanyBaby ();
        bb.show ();
        bb.setURL ("http://cbc.ca");
    }
}
