// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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

import org.htmlparser.HTMLNode; 
/**
 * The JSP/ASP tags like &lt;%...%&gt; can be identified by this class.
 */
public class HTMLJspTag extends HTMLTag
{
	/**
	 * The HTMLJspTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagBegin beginning position of the tag
	 * @param tagEnd ending position of the tag
	 * @param tagContents contents of the remark tag
	 */
	public HTMLJspTag(int tagBegin, int tagEnd, String tagContents, String tagLine)
	{
		super(tagBegin,tagEnd,tagContents,tagLine);
	}
	/**
	 * Returns the beginning position of the tag.
	 */
	public int elementBegin()
	{
		return tagBegin;
	}
	/**
	 * Returns the ending position fo the tag
	 */
	public int elementEnd()
	{
		return tagEnd;
	}
	public String toHTML() {
		return "<%"+tagContents+"%>";
	}
		/**
	 * Print the contents of the remark tag.
	 */
	public String toString()
	{
		return "JSP/ASP Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
