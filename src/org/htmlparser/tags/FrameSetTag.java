// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * Identifies an frame set tag.
 */
public class FrameSetTag extends CompositeTag
{
    public FrameSetTag ()
    {
        setTagName ("FRAMESET");
    }

    /**
     * Print the contents of the FrameSetTag
     */
    public String toString()
    {
        return "FRAMESET TAG : begins at : "+elementBegin()+"; ends at : "+elementEnd();
    }

    /**
     * Returns the frames.
     * @return The children of this tag.
     */
    public NodeList getFrames()
    {
        return (getChildren());
    }

    /**
     * Gets a frame by name.
     * @param 
     * @return The specified frame or <code>null</code> if it wasn't found.
     */
    public FrameTag getFrame(String name)
    {
        boolean found;
        Node node;
        FrameTag frameTag;
        
        found = false;
        name = name.toUpperCase ();
        frameTag = null;
        for (SimpleNodeIterator e=getFrames().elements();e.hasMoreNodes() && !found;)
        {
            node = e.nextNode();
            if (node instanceof FrameTag)
            {
                frameTag = (FrameTag)node;
                if (frameTag.getFrameName().toUpperCase().equals(name))
                    found = true;
            }
        }
        if (found)
            return (frameTag);
        else
            return (null);
    }

    /**
     * Sets the frames (children of this tag).
     * @param frames The frames to set
     */
    public void setFrames(NodeList frames)
    {
        setChildren (frames);
    }
}
