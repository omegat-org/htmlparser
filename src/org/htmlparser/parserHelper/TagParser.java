// HTMLParser Library v1_3_20030316 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.*;
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
	private boolean encounteredQuery;
	private int i;

	public TagParser(ParserFeedback feedback) {
		this.feedback = feedback;
	}

	public Tag find(NodeReader reader,String input,int position) {
		int state = TAG_BEFORE_PARSING_STATE;
		StringBuffer tagContents = new StringBuffer();
		i=position;
		char ch;
		Tag tag = new Tag(new TagData(0,0,"",input));
		encounteredQuery = false;
		while (i<tag.getTagLine().length()&& 
				((state&TAG_FINISHED_PARSING_STATE)==0) && 
				((state&TAG_ILLEGAL_STATE)==0)
			)
		{
			ch = tag.getTagLine().charAt(i);
			state = automataInput(state, ch, tag, i);
			i = incrementCounter(reader, state, tag);
		}
		if ((state&TAG_FINISHED_PARSING_STATE)!=0)
		return tag;
		else
		return null;	
	}

	private int automataInput(int state,char ch, Tag tag, int pos) {
		state = checkIllegalState(state, ch, tag);
		state = checkFinishedState(state, ch, tag, pos);
		state = toggleIgnoringState(state, ch);
		if ((state&TAG_BEFORE_PARSING_STATE)!=0 && ch!='<') {
            state= TAG_ILLEGAL_STATE;
        }
		if ((state&TAG_IGNORE_DATA_STATE)!=0 && ch=='<') {
			// If the next tag char is is close tag, then
			// this is legal, we should continue
			if (!isWellFormedTag(tag,pos))
				state = TAG_IGNORE_BEGIN_TAG_STATE;
		}
		if ((state&TAG_IGNORE_BEGIN_TAG_STATE)!=0 && ch=='>') {

				state = TAG_IGNORE_DATA_STATE;
		}
		checkIfAppendable(state, ch, tag);
		state = checkBeginParsingState(state, ch, tag);

		return state;
	}

	private int checkBeginParsingState(int state, char ch, Tag tag) {
		if (ch=='<' && 
			((state&TAG_BEFORE_PARSING_STATE)!=0 || 
			  (state&TAG_ILLEGAL_STATE)!=0))
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
 	
	private int checkFinishedState(int state,  char ch, Tag tag, int pos) {
		if (ch=='>')
		{
			if ((state&TAG_BEGIN_PARSING_STATE)!=0)
			{
				state = TAG_FINISHED_PARSING_STATE;
				tag.setTagEnd(i);
			}
			else
			if ((state&TAG_IGNORE_DATA_STATE)!=0) {
				if (encounteredQuery) {
					encounteredQuery=false;
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
			(state&TAG_BEGIN_PARSING_STATE)!=0 && 
			tag.getText().charAt(0)!='%') {
			state = TAG_FINISHED_PARSING_STATE;
			tag.setTagEnd(i-1);i--;
		}
		return state;
	}

	private void checkIfAppendable(int state, char ch, Tag tag) {
		if ((state&TAG_IGNORE_DATA_STATE)!=0 || 
			(state&TAG_BEGIN_PARSING_STATE)!=0 || 
			(state&TAG_IGNORE_BEGIN_TAG_STATE)!=0) {
			if (ch=='?') encounteredQuery=true;
			tag.append(ch);
		}
	}

	private int checkIllegalState(int state, char ch, Tag tag) {
		if (ch=='/' && i>0 && tag.getTagLine().charAt(i-1)=='<' && 
			(state&TAG_IGNORE_DATA_STATE)==0 && 
			(state&TAG_IGNORE_BEGIN_TAG_STATE)==0)
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
	private int toggleIgnoringState(int state, char ch) {
		if (ch=='"' ) {
			// State 4 is ignoring mode. In this mode, we cant exit upon recieving endtag character
			// This is to avoid problems with end tags within inverted commas (occuring with JSP tags).
			if ((state&TAG_IGNORE_DATA_STATE)!=0) {
				state = TAG_BEGIN_PARSING_STATE;
				
			} else
			if ((state&TAG_BEGIN_PARSING_STATE)!=0) 
				state = TAG_IGNORE_DATA_STATE;
		}
		else 
		if ((state&TAG_IGNORE_DATA_STATE)!=0 && ch=='\'') {
			if ((state&TAG_IGNORE_CHAR_SINGLE_QUOTE)!=0) {
				state = TAG_BEGIN_PARSING_STATE;
			} 
		} else
		if ((state&TAG_IGNORE_DATA_STATE)==0 && (ch=='\'' || ch=='"')) {
			state = TAG_IGNORE_DATA_STATE;
			if (ch=='\'')
				state |= TAG_IGNORE_CHAR_SINGLE_QUOTE;
		}
		
		return state;
		
	}	
	private int _toggleIgnoringState(int state, char ch) {
		if (ch=='"' ||ch=='\'' ) {
			// State 4 is ignoring mode. In this mode, we cant exit upon recieving endtag character
			// This is to avoid problems with end tags within inverted commas (occuring with JSP tags).
			if ((state&TAG_IGNORE_DATA_STATE)!=0) {
				state = TAG_BEGIN_PARSING_STATE;
			
			} else
			if ((state&TAG_BEGIN_PARSING_STATE)!=0) 
				state = TAG_IGNORE_DATA_STATE;
		}
		return state;
	
	}	

	
	public int incrementCounter(NodeReader reader, int state, Tag tag) {
		String nextLine = null;
		if (
			((state&TAG_BEGIN_PARSING_STATE)!=0 || 
			 (state&TAG_IGNORE_DATA_STATE)!=0
			) && 
			i==tag.getTagLine().length()-1)
		{
			// The while loop below is a bug fix contributed by
			// Annette Doyle - see testcase HTMLImageScannerTest.testImageTagOnMultipleLines()
			// Further modified by Somik Raha, to remove bug - HTMLTagTest.testBrokenTag
			do {
				nextLine = reader.getNextLine();		
			}
			while (nextLine!=null && nextLine.length()==0);
			if (nextLine==null) {
				// This means we have a broken tag. Fill in an end tag symbol here.
				nextLine = ">";
			} else {
				// This means this is just a new line, hence add the new line character
				tag.append(Node.getLineSeparator());
			}

			// We need to continue parsing to the next line
			tag.setTagLine(nextLine);
			i=-1;
		}		
		return ++i;
	}	
}
