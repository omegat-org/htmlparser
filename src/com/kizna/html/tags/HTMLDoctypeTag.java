// HTMLParser Library v1.1 - A java-based parser for HTML
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
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com
//
// This was originally - Class HTMLDecTag written by :
// Allen L. Fogleson
// Senior Project Manager Crunchy Technologies
// 2111 Wilson Boulevard Suite 350
// Arlington, Va 22201
// Email : afogleson@crunchytech.com
// Voice : (703) 469-2032
// Pager : (800) 826-3181

package com.kizna.html.tags; 
import com.kizna.html.HTMLNode; 
/**
 * The HTML Document Declaration Tag can identify &lt;!DOCTYPE&gt; tags
 */
public class HTMLDoctypeTag extends HTMLTag
{
	/**
	 * The HTMLDecTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagBegin beginning position of the tag
	 * @param tagEnd ending position of the tag
	 * @param tagContents contents of the remark tag
	 */
	public HTMLDoctypeTag(int tagBegin, int tagEnd, String tagContents,String tagLine)
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
		/**
	 * Print the contents of the remark tag.
	 */
	public void print()
	{
		System.out.println("Doctype Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd());
	}
}
