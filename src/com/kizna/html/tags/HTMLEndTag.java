// HTMLParser Library v1_2_20020831 - A java-based parser for HTML
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

package com.kizna.html.tags;

import com.kizna.html.HTMLNode; 
/**
 * HTMLEndTag can identify closing tags, like &lt;/A&gt;, &lt;/FORM&gt;, etc.
 */
public class HTMLEndTag extends HTMLTag
{
	public final static int ENDTAG_BEFORE_PARSING_STATE=0;
    public final static int ENDTAG_WAIT_FOR_SLASH_STATE=1;
    public final static int ENDTAG_BEGIN_PARSING_STATE=2;
	public final static int ENDTAG_FINISHED_PARSING_STATE=3;
	
	/**
	 * Constructor takes 3 arguments to construct an HTMLEndTag object.
	 * @param tagBegin Beginning position of the end tag
	 * @param tagEnd Ending position of the end tag
	 * @param tagContents Text contents of the tag
	 */
	public HTMLEndTag(int tagBegin, int tagEnd, String tagContents,String tagLine)
	{
		super(tagBegin,tagEnd,tagContents,tagLine);
	}
	/**
	 * Locate the end tag withing the input string, by parsing from the given position
	 * @param input Input String
	 * @param position Position to start parsing from
	 */
	public static HTMLNode find(String input,int position)
	{
		int state = ENDTAG_BEFORE_PARSING_STATE;
		StringBuffer tagContents = new StringBuffer();
		int tagBegin=0;
		int tagEnd=0;
		int inputLen = input.length();
		char ch;
		int i ;
		for (i=position;(i<inputLen&& state!=ENDTAG_FINISHED_PARSING_STATE);i++)
		{
			ch = input.charAt(i);
			if (ch=='>' && state==ENDTAG_BEGIN_PARSING_STATE)
			{
				state = ENDTAG_FINISHED_PARSING_STATE;
				tagEnd = i;
			}				
			if (state==ENDTAG_BEGIN_PARSING_STATE)
			{
				tagContents.append(ch);
			}
			if (state==ENDTAG_WAIT_FOR_SLASH_STATE)
			{
				if (ch=='/')			
				{
					state = ENDTAG_BEGIN_PARSING_STATE;
				}
				else return null;
			}

			if (ch=='<')
			{
				if (state==ENDTAG_BEFORE_PARSING_STATE)
				{
					// Transition from State 0 to State 1 - Record data till > is encountered
					tagBegin = i;
					state = ENDTAG_WAIT_FOR_SLASH_STATE;
				}
				else if (state==ENDTAG_BEGIN_PARSING_STATE)
				{
					state=ENDTAG_FINISHED_PARSING_STATE;
					tagEnd=i;			
				}
			}
		}
		// If parsing did not complete, it might be possible to accept
		if (state==ENDTAG_BEGIN_PARSING_STATE) {
			tagEnd=i;
			state=ENDTAG_FINISHED_PARSING_STATE;
		}		
		if (state==ENDTAG_FINISHED_PARSING_STATE)
		return new HTMLEndTag(tagBegin,tagEnd,tagContents.toString(),input);
		else return null;	
	}
	public String toPlainTextString() {
		return "";
	}
	public String toHTML() {
		return "</"+tagContents+">";
	}
	public String toString() {
		return "EndTag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
