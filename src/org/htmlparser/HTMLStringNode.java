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
 * Normal text in the html document is identified and represented by this class.
 */
public class HTMLStringNode extends HTMLNode
{
  	private final static int BEFORE_PARSE_BEGINS_STATE=0;	
  	private final static int PARSE_HAS_BEGUN_STATE=1;
  	private final static int PARSE_COMPLETED_STATE=2;	
  	private final static int PARSE_IGNORE_STATE=3;
  	private static boolean ignoreStateMode=false;
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
	public HTMLStringNode(StringBuffer textBuffer,int textBegin,int textEnd)
	{
		super(textBegin,textEnd);
		this.textBuffer = textBuffer;
		
	}
	
	public static HTMLNode find(HTMLReader reader,String input,int position) {
		return find(reader, input, position, ignoreStateMode);	
	}
	
	/**
	 * Locate the StringNode within the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 * @param ignoreStateMode enter ignoring state - if set, will enter ignoring
	 * state on encountering apostrophes
	 */		
	public static HTMLNode find(HTMLReader reader,String input,int position, boolean ignoreStateMode)
	{
		StringBuffer textBuffer = new StringBuffer();
		int state = BEFORE_PARSE_BEGINS_STATE;
		int textBegin=position;
		int textEnd=position;
		int inputLen = input.length();
		char ch;
		for (int i=position;(i<inputLen && state!=PARSE_COMPLETED_STATE);i++)
		{
			ch  = input.charAt(i);
			// When the input has ended but no text is found, we end up returning null
			if (ch=='<' && state==BEFORE_PARSE_BEGINS_STATE)
			{
				return null;
			}
			// The following conditionals are a bug fix
			// done by Roger Sollberger. They correspond to a
			// testcase in HTMLStringNodeTest (testTagCharsInStringNode)
			if (ch=='<' && state!=PARSE_IGNORE_STATE) {
				if ((i+1)<input.length()) {
					char nextChar = input.charAt(i+1);
					if  (((nextChar>='A') && (nextChar<='Z')) || // next char must be A-Z 
				     ((nextChar>='a') && (nextChar<='z')) || // next char must be a-z
				     (nextChar=='/' || nextChar=='!' || nextChar=='>' || nextChar=='%'))   // or next char is a '/' 
					{
						state = PARSE_COMPLETED_STATE;
						textEnd=i-1;
					}
				}
			}
			if (ignoreStateMode && ch=='\'') {
				if (state==PARSE_IGNORE_STATE) state=PARSE_HAS_BEGUN_STATE;
				else {
					if (input.charAt(i+1)=='<')
						state = PARSE_IGNORE_STATE;
				}
				
			}					
			if (state==BEFORE_PARSE_BEGINS_STATE)
			{
				state=PARSE_HAS_BEGUN_STATE;
			}
			if (state==PARSE_HAS_BEGUN_STATE || state==PARSE_IGNORE_STATE)
			{
				textBuffer.append(input.charAt(i));
			}				
			// Patch by Cedric Rosa
			if (state==BEFORE_PARSE_BEGINS_STATE && i==inputLen-1)
			   state=PARSE_HAS_BEGUN_STATE;
			if (state==PARSE_HAS_BEGUN_STATE && i==inputLen-1)
			{
				input = reader.getNextLine();

				if (input==null) {
					textEnd=i;
					state =PARSE_COMPLETED_STATE;
					
				} else {
					textBuffer.append(lineSeparator);
					inputLen = input.length();
					i=-1;
				}

			}
		}
		return new HTMLStringNode(textBuffer,textBegin,textEnd);
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
	public void collectInto(Vector collectionVector, String filter) {
		if (filter==STRING_FILTER) collectionVector.add(this);
	}

	public void accept(HTMLVisitor visitor) {
		visitor.visitStringNode(this);
	}

	public static boolean isIgnoreStateMode() {
		return ignoreStateMode;
	}

	public static void setIgnoreStateMode(boolean ignoreStateMode) {
		HTMLStringNode.ignoreStateMode = ignoreStateMode;
	}

}
