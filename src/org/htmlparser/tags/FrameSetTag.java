// HTMLParser Library v1_4_20030629 - A java-based parser for HTML
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

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * Identifies an frame tag
 */
public class FrameSetTag extends CompositeTag
{
	/**
	 * The URL where the image is stored.
	 */
	protected String frameURL;
   	protected String frameName;
   	protected NodeList frames;
	public FrameSetTag(TagData tagData,CompositeTagData compositeTagData) {
		super(tagData,compositeTagData);
      	this.frames = compositeTagData.getChildren();
	}
	
	/**
	 * Returns the location of the frame
	 */
	public String getFrameLocation() {
		return frameURL;
	}
	
	public String getFrameName() {
		return frameName;
	}
	
	/**
	 * Print the contents of the HTMLImageNode
	 */
	public String toString() {
		return "FRAME TAG : Image at "+frameURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
	
	/**
	 * Returns the frames.
	 * @return Vector
	 */
	public NodeList getFrames() {
		return frames;
	}

	public FrameTag getFrame(String frameName) {
		boolean found = false;
		FrameTag frameTag=null;
		for (SimpleNodeIterator e=frames.elements();e.hasMoreNodes() && !found;) {
			frameTag = (FrameTag)e.nextNode();
			if (frameTag.getFrameName().toUpperCase().equals(frameName.toUpperCase())) found = true;
		}
		if (found)
		return frameTag; else return null;
	}
	/**
	 * Sets the frames.
	 * @param frames The frames to set
	 */
	public void setFrames(NodeList frames) {
		this.frames = frames;
	}
}
