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


package org.htmlparser;

import org.htmlparser.nodeDecorators.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

/**
 * Normal text in the html document is identified and represented by this class.
 */
public class StringNode extends AbstractNode
{
	public static final String STRING_FILTER="-string";
	
	/**
	 * The text of the string.
	 */	
	protected StringBuffer textBuffer;

	/** 
	 * Constructor takes in the text string, beginning and ending posns.
	 * @param text The contents of the string line
	 * @param textBegin The beginning position of the string
	 * @param textEnd The ending positiong of the string
	 */
	public StringNode(StringBuffer textBuffer,int textBegin,int textEnd)
	{
		super(textBegin,textEnd);
		this.textBuffer = textBuffer;
	}

	public static Node createStringNode(
		StringBuffer textBuffer, int textBegin, int textEnd, 
		boolean shouldDecode, boolean shouldRemoveEscapeCharacters,
		boolean shouldConvertNonBlankingSpace) {
		Node newNode = new StringNode(textBuffer, textBegin, textEnd);
		if (shouldDecode)
			newNode = new DecodingNode(newNode);
		if (shouldRemoveEscapeCharacters)
			newNode = new EscapeCharacterRemovingNode(newNode);
		if (shouldConvertNonBlankingSpace)
			newNode = new NonBreakingSpaceConvertingNode(newNode);
		return newNode;
	}
	

	/**
	 * Returns the text of the string line
	 */
	public String getText() {
		return textBuffer.toString();
	}
    /**
     * Sets the string contents of the node.
     * @param The new text for the node.
     */
	public void setText(String text)
	{
		textBuffer = new StringBuffer (text);
	}
	
	public String toPlainTextString() {
		return textBuffer.toString();
	}
	
	public String toHtml() {
		return textBuffer.toString();
	}
	
	public String toString() {
		return "Text = "+getText()+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
	
	public void collectInto(NodeList collectionList, String filter) {
		if (filter==STRING_FILTER) collectionList.add(this);
	}

	public void accept(NodeVisitor visitor) {
		visitor.visitStringNode(this);
	}
}
