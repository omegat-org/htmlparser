// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Email : somik@industriallogic.com
// Website : http://www.industriallogic.com

package org.htmlparser.tags;

import org.htmlparser.tags.data.TagData;

/**
 * The HTML Document Declaration Tag can identify &lt;&#33;DOCTYPE&gt; tags.
 */
public class DoctypeTag extends Tag
{
    /**
	 * The HTMLDoctypeTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagData The data for this tag.
	 */
	public DoctypeTag(TagData tagData)
	{
		super(tagData);
	}
	/**
	 * Print the contents of the remark tag.
	 */
	public String toString()
	{
		return "Doctype Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
	public String toHtml() {
		return "<!DOCTYPE "+tagContents+">";
	}
}
