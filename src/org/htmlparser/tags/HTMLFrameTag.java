// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

/**
 * Identifies an frame tag
 */
public class HTMLFrameTag extends HTMLTag
{
	/**
	 * The URL where the image is stored.
	 */
	protected String frameURL;
    protected String frameName;
	public HTMLFrameTag(String frameURL, String frameName, String frameContents,int frameBegin, int frameEnd,String tagLine) {
		super(frameBegin,frameEnd,frameContents,tagLine);
		this.frameURL = frameURL;
      this.frameName = frameName;
	}
	/**
	 * Returns the location of the image
	 */
	public String getFrameLocation() {
		return frameURL;
	}
	public String getFrameName() {
		return frameName;
	}
	/**
	 * Print the contents of the HTMLFrameTag
	 */
	public String toString() {
		return "FRAME TAG : Image at "+frameURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
	public String toHTML() {
		return super.toHTML();
	}	
}