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
 * A Title Tag
 */
public class HTMLTitleTag extends HTMLTag {
	private String title;
	/**
	 * Constructor for HTMLTitleTag.
	 * @param tagBegin
	 * @param tagEnd
	 * @param tagContents
	 * @param tagLine
	 */
	public HTMLTitleTag(int tagBegin,int tagEnd,String title,String tagContents,String tagLine) {
		super(tagBegin, tagEnd, tagContents, tagLine);
		this.title = title;
	}
	/**
	 * Gets the title.
	 * @return Returns a String
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Sets the title.
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	public String toPlainTextString() {
		return title;
	}
	public String toHTML() {
		return "<TITLE>"+title+"</TITLE>";
	}
	public String toString() {
		return "TITLE: "+title;
	}
}