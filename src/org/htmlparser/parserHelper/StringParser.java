// HTMLParser Library v1_4_20030601 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.StringNode;

public class StringParser {
	private final static int BEFORE_PARSE_BEGINS_STATE=0;	
	private final static int PARSE_HAS_BEGUN_STATE=1;
	private final static int PARSE_COMPLETED_STATE=2;	
	private final static int PARSE_IGNORE_STATE=3;

    /**
     * Returns true if the text at <code>pos</code> in <code>line</code> should be scanned as a tag.
     * Basically an open angle followed by a known special character or a letter.
     * @param line The current line being parsed.
     * @param pos The position in the line to examine.
     * @return <code>true</code> if we think this is the start of a tag.
     */
    private boolean beginTag (String line, int pos)
    {
        char ch;
        boolean ret;
        
        ret = false;
        
        if (pos + 2 <= line.length ())
            if ('<' == line.charAt (pos))
            {
                ch = line.charAt (pos + 1);
                // the order of these tests might be optimized for speed
                if ('/' == ch || '%' == ch || Character.isLetter (ch) || '!' == ch)
                    ret = true;
            }

        return (ret);
    }

	/**
	 * Locate the StringNode within the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 * @param balance_quotes If <code>true</code> enter ignoring state on
     * encountering quotes.
	 */		
	public Node find(NodeReader reader,String input,int position, boolean balance_quotes)
	{
		StringBuffer textBuffer = new StringBuffer();
		int state = BEFORE_PARSE_BEGINS_STATE;
		int textBegin=position;
		int textEnd=position;
		int inputLen = input.length();
		char ch;
        char ignore_ender = '\"';
		for (int i=position;(i<inputLen && state!=PARSE_COMPLETED_STATE);i++)
		{
			ch  = input.charAt(i);
			if (ch=='<' && state!=PARSE_IGNORE_STATE)
            {
                if (beginTag (input, i))
                {
                    state = PARSE_COMPLETED_STATE;
                    textEnd=i-1;
                }
			}
			if (balance_quotes && (ch=='\'' || ch=='"'))
            {
				if (state==PARSE_IGNORE_STATE)
                {
                    if (ch == ignore_ender)
                        state=PARSE_HAS_BEGUN_STATE;
                }
				else
                {
                    ignore_ender = ch;
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
						textBuffer.append(Node.getLineSeparator());
				}
				while (input!=null && input.length()==0);
				
				if (input==null) {
					textEnd=i;
					state =PARSE_COMPLETED_STATE;
					
				} else {
					textBuffer.append(Node.getLineSeparator());
					inputLen = input.length();
					i=-1;
				}

			}
		}
		return new StringNode(textBuffer,textBegin,textEnd);
	}
}
