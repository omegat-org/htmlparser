// HTMLParser Library v1_2_20021120 - A java-based parser for HTML
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

/**
 * Normal text in the html document is identified and represented by this class.
 */
public class HTMLStringNode extends HTMLNode
{
	/**
	 * The text of the string.
   */	
	protected StringBuffer textBuffer;
  /** 
	 * The beginning position of the tag in the line
	 */	
	protected int textBegin;
	/**
	 * The ending position of the tag in the line
	 */	
	protected int textEnd;
	/** 
	 * Constructor takes in the text string, beginning and ending posns.
	 * @param text The contents of the string line
	 * @param textBegin The beginning position of the string
	 * @param textEnd The ending positiong of the string
	 */
	public HTMLStringNode(StringBuffer textBuffer,int textBegin,int textEnd)
	{
		this.textBuffer = textBuffer;
		this.textBegin = textBegin;
		this.textEnd = textEnd;
	}
	/**
	 * Returns the beginning position of the string.
	 */	
	public int elementBegin()
	{
		return textBegin;
	}
	/**
	 * Returns the ending position fo the tag
	 */	
	public int elementEnd()
	{
		return textEnd;
	}
	/**
	 * Locate the StringNode within the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 */		
	public static HTMLNode find(HTMLReader reader,String input,int position)
	{
		StringBuffer textBuffer = new StringBuffer();
		int state = 0;
		int textBegin=position;
		int textEnd=position;
		int inputLen = input.length();
		char ch;
		for (int i=position;(i<inputLen && state!=2);i++)
		{
			ch  = input.charAt(i);
			// When the input has ended but no text is found, we end up returning null
			if (ch=='<' && state==0)
			{
				return null;
			}
			// The following conditionals are a bug fix
			// done by Roger Sollberger. They correspond to a
			// testcase in HTMLStringNodeTest (testTagCharsInStringNode)
			if (ch=='<') {
				if ((i+1)<input.length()) {
					char nextChar = input.charAt(i+1);
					if  (((nextChar>='A') && (nextChar<='Z')) || // next char must be A-Z 
				     ((nextChar>='a') && (nextChar<='z')) || // next char must be a-z
				     (nextChar=='/' || nextChar=='!' || nextChar=='>' || nextChar=='%'))   // or next char is a '/' 
					{
						state = 2;
						textEnd=i-1;
					}
				}
			}
			if (state==0)
			{
				if (ch!=' ') state=1;
				else textBuffer.append(input.charAt(i));
			}
			if (state==1)
			{
				textBuffer.append(input.charAt(i));
			}				
			// Patch by Cedric Rosa
			if (state==0 && i==inputLen-1)
			   state=1;
			if (state==1 && i==inputLen-1)
			{
				input = reader.getNextLine();

				if (input==null) {
					textEnd=i;
					state =2;
					
				} else {
					textBuffer.append(lineSeparator);
					inputLen = input.length();
					i=-1;
					textBegin=-1;
				}

			}
		}
		if (textBegin<=textEnd) return new HTMLStringNode(textBuffer,textBegin,textEnd);
		else return null;
	}
	/**
	 * Returns the text of the string line
	 */
	public String getText()
	{
		return textBuffer.toString();
	}
	public String toPlainTextString() {
		return textBuffer.toString();
	}
	public String toHTML() {
		return textBuffer.toString();
	}
	public String toString() {
		return "Text = "+getText()+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
