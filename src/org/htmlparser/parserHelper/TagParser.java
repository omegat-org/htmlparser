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

import java.util.StringTokenizer;

import org.htmlparser.NodeReader;
import org.htmlparser.Parser;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserFeedback;

public class TagParser {
	public final static int TAG_BEFORE_PARSING_STATE=1;
	public final static int TAG_BEGIN_PARSING_STATE=1<<2;
	public final static int TAG_FINISHED_PARSING_STATE=1<<3;
	public final static int TAG_ILLEGAL_STATE=1<<4;
	public final static int TAG_IGNORE_DATA_STATE=1<<5;	    
	public final static int TAG_IGNORE_BEGIN_TAG_STATE=1<<6;
	public final static int TAG_IGNORE_CHAR_SINGLE_QUOTE=1<<7;
	
	public final static String ENCOUNTERED_QUERY_MESSAGE = "TagParser : Encountered > after a query. Accepting without correction and continuing parsing";
	
	private ParserFeedback feedback;

	public TagParser(ParserFeedback feedback) {
		this.feedback = feedback;
	}

	public Tag find(NodeReader reader,String input,int position) {
		int state = TAG_BEFORE_PARSING_STATE;
		int i=position;
		char ch;
		char[] ignorechar = new char[1]; // holds the character we're looking for when in TAG_IGNORE_DATA_STATE
		Tag tag = new Tag(new TagData(position, 0, reader.getLastLineNumber(), 0, "", input, "", false));

		Bool encounteredQuery = new Bool(false);
		while (i<tag.getTagLine().length() && 
				state!=TAG_FINISHED_PARSING_STATE && 
				state!=TAG_ILLEGAL_STATE
			)
		{
			ch = tag.getTagLine().charAt(i);
			state = automataInput(encounteredQuery, i, state, ch, tag, i, ignorechar);
			i = incrementCounter(i, reader, state, tag);
		}
		if (state==TAG_FINISHED_PARSING_STATE) {
			String tagLine = tag.getTagLine();
			if (i>1 && tagLine.charAt(i-2)=='/') {
				tag.setEmptyXmlTag(true);
				String tagContents = tag.getText();
				tag.setText(tagContents.substring(0,tagContents.length()-1));
			}
			return tag;
		} else
			return null;
	}

	private int automataInput(Bool encounteredQuery, int i, int state,char ch, Tag tag, int pos, char[] ignorechar) {
		state = checkIllegalState(i, state, ch, tag);
		state = checkFinishedState(encounteredQuery, i, state, ch, tag, pos);
		state = toggleIgnoringState(state, ch, ignorechar);
		if (state==TAG_BEFORE_PARSING_STATE && ch!='<') {
			state= TAG_ILLEGAL_STATE;
		}
		if (state==TAG_IGNORE_DATA_STATE && ch=='<') {
			// If the next tag char is is close tag, then
			// this is legal, we should continue
			if (!isWellFormedTag(tag,pos))
				state = TAG_IGNORE_BEGIN_TAG_STATE;
		}
		if (state==TAG_IGNORE_BEGIN_TAG_STATE && ch=='>') {
			state = TAG_IGNORE_DATA_STATE;
		}
		checkIfAppendable(encounteredQuery, state, ch, tag);
		state = checkBeginParsingState(i, state, ch, tag);

		return state;
	}

	private int checkBeginParsingState(int i, int state, char ch, Tag tag) {
		if (ch=='<' && 
			(state==TAG_BEFORE_PARSING_STATE || 
			  state==TAG_ILLEGAL_STATE))
		{
			// Transition from State 0 to State 1 - Record data till > is encountered
			tag.setTagBegin(i);
			state = TAG_BEGIN_PARSING_STATE;
		}
		return state;
	}

	private boolean isWellFormedTag(Tag tag, int pos) {
		String inputLine = tag.getTagLine();
		int closeTagPos = inputLine.indexOf('>',pos+1);
		int openTagPos = inputLine.indexOf('<',pos+1);
		return openTagPos > closeTagPos || (openTagPos ==-1 && closeTagPos!=-1);
 	}
 	
	private int checkFinishedState(Bool encounteredQuery, int i, int state,  char ch, Tag tag, int pos) {
		if (ch=='>')
		{
			if (state==TAG_BEGIN_PARSING_STATE)
			{
				state = TAG_FINISHED_PARSING_STATE;
				tag.setTagEnd(i);
			}
			else
			if (state==TAG_IGNORE_DATA_STATE) {
				if (encounteredQuery.getBoolean()) {
					encounteredQuery.setBoolean(false);
					feedback.info(ENCOUNTERED_QUERY_MESSAGE);
					return state;
				}
				// Now, either this is a valid > input, and should be ignored,
				// or it is a mistake in the html, in which case we need to correct it *sigh*
				if (isWellFormedTag(tag,pos)) return state;
				
				state = TAG_FINISHED_PARSING_STATE;
				tag.setTagEnd(i);
				// Do Correction
				// Correct the tag - assuming its grouped into name value pairs
				// Remove all inverted commas.
				correctTag(tag);
			
				StringBuffer msg = new StringBuffer();
				msg.append("HTMLTagParser : Encountered > inside inverted commas in line \n");
				msg.append(tag.getTagLine());
				msg.append(", location ");
				msg.append(i);
				msg.append("\n");
				for (int j=0;j<i;j++) msg.append(' ');
				msg.append('^');
				msg.append("\nAutomatically corrected.");
				feedback.warning(msg.toString());
			}
		} else
		if (ch=='<' && 
			state==TAG_BEGIN_PARSING_STATE && 
			tag.getText().charAt(0)!='%'
			) {
			state = TAG_FINISHED_PARSING_STATE;
			tag.setTagEnd(i-1);i--;
		}
		return state;
	}

	private void checkIfAppendable(Bool encounteredQuery,int state, char ch, Tag tag) {
		if (state==TAG_IGNORE_DATA_STATE || 
			state==TAG_BEGIN_PARSING_STATE || 
			state==TAG_IGNORE_BEGIN_TAG_STATE) {
			if (ch=='?') 
				encounteredQuery.setBoolean(true);
			tag.append(ch);
		}
	}

	private int checkIllegalState(int i, int state, char ch, Tag tag) {
		if (ch=='/' && i>0 && tag.getTagLine().charAt(i-1)=='<' && 
			state!=TAG_IGNORE_DATA_STATE && 
			state!=TAG_IGNORE_BEGIN_TAG_STATE)
		{
			state = TAG_ILLEGAL_STATE;
		}

		return state;
	}
	
	public void correctTag(Tag tag) {
		String tempText = tag.getText();
		StringBuffer absorbedText = new StringBuffer();
		char c;
		for (int j=0;j<tempText.length();j++) {
			c = tempText.charAt(j);
			if (c!='"')
			absorbedText.append(c);
		}
		// Go into the next stage.
		StringBuffer result = insertInvertedCommasCorrectly(absorbedText);
		tag.setText(result.toString());
	}	
	public StringBuffer insertInvertedCommasCorrectly(StringBuffer absorbedText) {
		StringBuffer result = new StringBuffer();
		StringTokenizer tok = new StringTokenizer(absorbedText.toString(),"=",false);
		String token;
		token=  (String)tok.nextToken();
		result.append(token+"=");
		for (;tok.hasMoreTokens();) {
			token=  (String)tok.nextToken();
			token = pruneSpaces(token);
			result.append('"');
			int lastIndex = token.lastIndexOf(' ');
			if (lastIndex!=-1 && tok.hasMoreTokens()) {
				result.append(token.substring(0,lastIndex));
				result.append('"');
				result.append(token.substring(lastIndex,token.length()));
			} else result.append(token+'"');
			if (tok.hasMoreTokens()) result.append("=");
		}
		return result;
	}	
	public static String pruneSpaces(String token) {
		int firstSpace;
		int lastSpace;
		firstSpace = token.indexOf(' ');
		while (firstSpace==0) {
			token = token.substring(1,token.length());
			firstSpace = token.indexOf(' ');
		}
		lastSpace  = token.lastIndexOf(' ');
		while (lastSpace==token.length()-1) {
			token = token.substring(0,token.length()-1);
			lastSpace  = token.lastIndexOf(' ');
		}			
		return token;
	}	

    /**
     * Check for quote character (" or ') and switch to TAG_IGNORE_DATA_STATE
     * or back out to TAG_BEGIN_PARSING_STATE.
     * @param state The current state.
     * @param ch The character to test.
     * @param ignorechar The character that caused entry to TAG_IGNORE_DATA_STATE.
     */
    private int toggleIgnoringState(int state, char ch, char[] ignorechar)
    {
        if (state==TAG_IGNORE_DATA_STATE)
        {
            if (ch == ignorechar[0])
                state = TAG_BEGIN_PARSING_STATE;
        }
        else if (state == TAG_BEGIN_PARSING_STATE)
            if (ch == '"' || ch == '\'')
            {
                state = TAG_IGNORE_DATA_STATE;
                ignorechar[0] = ch;
            }

        return (state);
	}	
	
	public int incrementCounter(int i, NodeReader reader, int state, Tag tag) {
		String nextLine = null;
		if (
			(state==TAG_BEGIN_PARSING_STATE || 
			 state==TAG_IGNORE_DATA_STATE ||
             state==TAG_IGNORE_BEGIN_TAG_STATE
			) && 
			i==tag.getTagLine().length()-1)
		{
			// The while loop below is a bug fix contributed by
			// Annette Doyle - see testcase HTMLImageScannerTest.testImageTagOnMultipleLines()
			// Further modified by Somik Raha, to remove bug - HTMLTagTest.testBrokenTag
			int numLinesAdvanced = 0;
			do {
				nextLine = reader.getNextLine();
				numLinesAdvanced++;
			}
			while (nextLine!=null && nextLine.length()==0);
			if (nextLine==null) {
				// This means we have a broken tag. Fill in an end tag symbol here.
				nextLine = ">";
			} else {
				// This means this is just a new line, hence add the new line character
				tag.append(Parser.getLineSeparator());
			}
			
			// Ensure blank lines are included in tag's 'tagLines'
			while (--numLinesAdvanced > 0)
				tag.setTagLine("");

			// We need to continue parsing to the next line
			tag.setTagLine(nextLine);
			i=-1;
		}		
		return ++i;
	}	
	// Class provided for thread safety in TagParser
	class Bool {
		private boolean boolValue;
		
		Bool(boolean boolValue) {
			this.boolValue = boolValue;	
		}
		
		public void setBoolean(boolean boolValue) {
			this.boolValue = boolValue;
		}
		
		public boolean getBoolean() {
			return boolValue;
		}
	}
}
