// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2003 Derrick Oswald
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

package org.htmlparser.parserapplications;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;

/**
 * Save a wikiwikiweb locally.
 * Illustrative program to save a wiki locally.
 */
public class WikiCapturer
    extends
        SiteCapturer
{
    /**
     * Create a wikicapturer.
     */
    public WikiCapturer ()
    {
    }

    /**
     * Mainline to capture a web site locally.
     * @param args The command line arguments.
     * There are three arguments the web site to capture, the local directory
     * to save it to, and a flag (true or false) to indicate whether resources
     * such as images and video are to be captured as well.
     * These are requested via dialog boxes if not supplied.
     */
    public static void main (String[] args)
        throws
            MalformedURLException,
            IOException
    {
        WikiCapturer worker;
        String url;
        JFileChooser chooser;
        URL source;
        String path;
        File target;
        Boolean capture;
        int ret;
        
        worker = new WikiCapturer ();
        if (0 >= args.length)
        {
            url = (String)JOptionPane.showInputDialog (
                null,
                "Enter the URL to capture:",
                "Web Site",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "http://htmlparser.sourceforge.net/wiki");
            if (null != url)
                worker.setSource (url);
            else
                System.exit (1);
        }
        else
            worker.setSource (args[0]);
        if (1 >= args.length)
        {
            url = worker.getSource ();
            source = new URL (url);
            path = new File (new File ("." + File.separator), source.getHost () + File.separator).getCanonicalPath ();
            target = new File (path);
            chooser = new JFileChooser (target);
            chooser.setDialogType (JFileChooser.SAVE_DIALOG);
            chooser.setFileSelectionMode (JFileChooser.DIRECTORIES_ONLY);
            chooser.setSelectedFile (target); // this doesn't frickin' work
            chooser.setMultiSelectionEnabled (false);
            chooser.setDialogTitle ("Target Directory");
            ret = chooser.showSaveDialog (null);
            if (ret == JFileChooser.APPROVE_OPTION)
                worker.setTarget (chooser.getSelectedFile ().getAbsolutePath ());
            else
                System.exit (1);
        }
        else
            worker.setTarget (args[1]);
        if (2 >= args.length)
        {
            capture = (Boolean)JOptionPane.showInputDialog (
                null,
                "Should resources be captured:",
                "Capture Resources",
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[] { Boolean.TRUE, Boolean.FALSE},
                Boolean.TRUE);
            if (null != capture)
                worker.setCaptureResources (capture.booleanValue ());
            else
                System.exit (1);
        }
        else
            worker.setCaptureResources ((Boolean.valueOf (args[2]).booleanValue ()));
        worker.setFilter (
            new NotFilter (
                new OrFilter (
                    new AndFilter (
                        new TagNameFilter ("DIV"),
                        new HasAttributeFilter ("id", "navbar")), 
                    new OrFilter (
                        new AndFilter (
                            new TagNameFilter ("DIV"),
                            new HasAttributeFilter ("id", "actionbar")),
                        new AndFilter (
                            new TagNameFilter ("DIV"),
                            new HasAttributeFilter ("id", "xhtml-validator"))))));
        worker.capture ();
        
        System.exit (0);
    }
}
