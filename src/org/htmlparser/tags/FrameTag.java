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

import org.htmlparser.nodes.TagNode;

/**
 * Identifies a frame tag
 */
public class FrameTag
    extends
        TagNode
{
    /**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"FRAME"};

    /**
     * Create a new frame tag.
     */
    public FrameTag ()
    {
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
     * Returns the location of the frame.
     * @return The contents of the SRC attribute converted to an absolute URL.
     */
    public String getFrameLocation ()
    {
        String ret;
        
        ret = getAttribute ("SRC");
        if (null == ret)
            ret = "";
        else if (null != getPage ())
            ret = getPage ().getAbsoluteURL (ret);
        
        return (ret);
    }

    /**
     * Sets the location of the frame.
     * @param url The new frame location.
     */
    public void setFrameLocation (String url)
    {
        setAttribute ("SRC", url);
    }

    public String getFrameName()
    {
        return (getAttribute ("NAME"));
    }

    /**
     * Print the contents of the FrameTag.
     */
    public String toString()
    {
        return "FRAME TAG : Frame " +getFrameName() + " at "+getFrameLocation()+"; begins at : "+getStartPosition ()+"; ends at : "+getEndPosition ();
    }
}
