// HTMLParser Library v1_3_20030405 - A java-based parser for HTML
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

public class RemarkNodeParser {
	public final static int REMARK_NODE_BEFORE_PARSING_STATE=0;
	public final static int REMARK_NODE_OPENING_ANGLE_BRACKET_STATE=1;
	public final static int REMARK_NODE_EXCLAMATION_RECEIVED_STATE=2;
	public final static int REMARK_NODE_FIRST_DASH_RECEIVED_STATE=3;
	public final static int REMARK_NODE_ACCEPTING_STATE=4;		
	public final static int REMARK_NODE_CLOSING_FIRST_DASH_RECEIVED_STATE=5;	
	public final static int REMARK_NODE_CLOSING_SECOND_DASH_RECEIVED_STATE=6;		
	public final static int REMARK_NODE_ACCEPTED_STATE=7;	
	public final static int REMARK_NODE_ILLEGAL_STATE=8;
	public final static int REMARK_NODE_FINISHED_PARSING_STATE=2;	
	
	/**
	 * Locate the remark tag withing the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 */	
	public RemarkNode find(NodeReader reader,String input,int position)
	{
		int state = REMARK_NODE_BEFORE_PARSING_STATE;
		StringBuffer tagContents = new StringBuffer();
		int tagBegin=0;
		int tagEnd=0;
		int i=position;
		int inputLen = input.length();
		char ch,prevChar=' ';
		while (i < inputLen && state < REMARK_NODE_ACCEPTED_STATE)
		{
			ch = input.charAt(i);
			if (state == REMARK_NODE_CLOSING_SECOND_DASH_RECEIVED_STATE) {
				if (ch == '>')
				{
					state=REMARK_NODE_ACCEPTED_STATE;
					tagEnd=i;
				} else if (ch=='-') {
					tagContents.append(prevChar);
				} else
				{
					// Rollback last 2 characters (assumed same)
					state = REMARK_NODE_ACCEPTING_STATE;
					tagContents.append(prevChar);
					tagContents.append(prevChar);
				}

			}

			if (state==REMARK_NODE_CLOSING_FIRST_DASH_RECEIVED_STATE)
			{
				if (ch == '-')
				{
					state=REMARK_NODE_CLOSING_SECOND_DASH_RECEIVED_STATE;
				} else
				{
					// Rollback
					state = REMARK_NODE_ACCEPTING_STATE;
					tagContents.append(prevChar);
				}
			} 
			if (state==REMARK_NODE_ACCEPTING_STATE) {
				if (ch == '-') {
					state=REMARK_NODE_CLOSING_FIRST_DASH_RECEIVED_STATE;
				} /*else
				if (ch == '<')
				{
					state=REMARK_NODE_ILLEGAL_STATE;
				} */
			}
			if (state==REMARK_NODE_ACCEPTING_STATE)
			{
				// We can append contents now		
				tagContents.append(ch);
			} 

			
			if (state==REMARK_NODE_FIRST_DASH_RECEIVED_STATE)
			{
				if (ch == '-') {
					state=REMARK_NODE_ACCEPTING_STATE;
					// Do a lookahead and see if the next char is >
					if (input.length()>i+1 && input.charAt(i+1)=='>') {
						state=REMARK_NODE_ACCEPTED_STATE;tagEnd=i+1;
					}
				}
				else state=REMARK_NODE_ILLEGAL_STATE;
			} 
			if (state==REMARK_NODE_EXCLAMATION_RECEIVED_STATE)
			{
				if (ch == '-')
				state=REMARK_NODE_FIRST_DASH_RECEIVED_STATE;
				else if (ch == '>')
				{
					state=REMARK_NODE_ACCEPTED_STATE;
					tagEnd=i;
				}
				else state=REMARK_NODE_ILLEGAL_STATE;
			} 
			if (state==REMARK_NODE_OPENING_ANGLE_BRACKET_STATE)
			{
				if (ch == '!')
				state=REMARK_NODE_EXCLAMATION_RECEIVED_STATE;
				else state = REMARK_NODE_ILLEGAL_STATE; // This is not a remark tag
			} 
			if (state == REMARK_NODE_BEFORE_PARSING_STATE)
			{
				if (ch=='<') {
					// Transition from State 0 to State 1 - Record data till > is encountered
					tagBegin = i;
					state = REMARK_NODE_OPENING_ANGLE_BRACKET_STATE;
				}
				else if (ch!=' ') {
					// Its not a space, hence this is probably a string node, not a remark node
					state = REMARK_NODE_ILLEGAL_STATE;
				}
			} 
//			if (state > REMARK_NODE_OPENING_ANGLE_BRACKET_STATE && state < REMARK_NODE_ACCEPTED_STATE && i == input.length() - 1)
			if (state >=REMARK_NODE_ACCEPTING_STATE  && state < REMARK_NODE_ACCEPTED_STATE && i == input.length() - 1)			
			{
				// We need to continue parsing to the next line
				//input = reader.getNextLine();
				tagContents.append(Node.getLineSeparator());
				do {
					input = reader.getNextLine();		
				}
				while (input!=null && input.length()==0);
				if (input!=null)
				inputLen = input.length(); else inputLen=-1;
				i=-1;
			}
			if (state==REMARK_NODE_ILLEGAL_STATE)
			{
				return null;
			}				
			i++;
			prevChar = ch;
		}
		if (state==REMARK_NODE_ACCEPTED_STATE)
		return new RemarkNode(tagBegin,tagEnd,tagContents.toString());
		else
		return null;	
	}
}
