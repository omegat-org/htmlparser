// HTMLParser Library v1_3_20030125 - A java-based parser for HTML
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

package org.htmlparser.parserHelper;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLStringNode;

public class StringParser {
	private final static int BEFORE_PARSE_BEGINS_STATE=0;	
	private final static int PARSE_HAS_BEGUN_STATE=1;
	private final static int PARSE_COMPLETED_STATE=2;	
	private final static int PARSE_IGNORE_STATE=3;
	private boolean ignoreStateMode=false;

	
	public HTMLNode find(HTMLReader reader,String input,int position) {
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
	public HTMLNode find(HTMLReader reader,String input,int position, boolean ignoreStateMode)
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
				do {
					input = reader.getNextLine();
					if (input!=null && input.length()==0)
						textBuffer.append(HTMLNode.getLineSeparator());
				}
				while (input!=null && input.length()==0);
				
				if (input==null) {
					textEnd=i;
					state =PARSE_COMPLETED_STATE;
					
				} else {
					textBuffer.append(HTMLNode.getLineSeparator());
					inputLen = input.length();
					i=-1;
				}

			}
		}
		return new HTMLStringNode(textBuffer,textBegin,textEnd);
	}
	
	public boolean isIgnoreStateMode() {
		return ignoreStateMode;
	}

	public void setIgnoreStateMode(boolean ignoreStateMode) {
		this.ignoreStateMode = ignoreStateMode;
	}	
}
