// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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


package org.htmlparser;

import java.util.Vector;

import org.htmlparser.visitors.*;

/**
 * The remark tag is identified and represented by this class.
 */
public class HTMLRemarkNode extends HTMLNode
{
	public static final String TYPE="REMARK_NODE";
	public final static String REMARK_NODE_FILTER="-r";
	
	/**
	 * Tag contents will have the contents of the comment tag.
   	 */
	String tagContents;
	/**
	 * The HTMLRemarkTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param nodeBegin beginning position of the tag
	 * @param nodeEnd ending position of the tag
	 * @param tagContents contents of the remark tag
	 * @param tagLine The current line being parsed, where the tag was found	 
	 */
	public HTMLRemarkNode(int tagBegin, int tagEnd, String tagContents)
	{
		super(tagBegin,tagEnd);
		this.tagContents = tagContents;
	}

	/** 
	 * Returns the text contents of the comment tag.
	 */
	public String getText()
	{
		return tagContents;
	}
	public String toPlainTextString() {
		return tagContents;
	}
	public String toHTML() {
		return "<!--"+tagContents+"-->";
	}
	/**
	 * Print the contents of the remark tag.
	 */
	public String toString()
	{
		return "Comment Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd()+"\n";
	}

	public void collectInto(Vector collectionVector, String filter) {
		if (filter==REMARK_NODE_FILTER) collectionVector.add(this);
	}

	public void accept(HTMLVisitor visitor) {
		visitor.visitRemarkNode(this);
	}

	public String getType() {
		return TYPE;
	}


}
