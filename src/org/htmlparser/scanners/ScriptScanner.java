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

package org.htmlparser.scanners;
/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tags.data.*;
import org.htmlparser.util.*;
/**
 * The HTMLScriptScanner identifies javascript code
 */

public class ScriptScanner extends CompositeTagScanner {
	private static final String SCRIPT_END_TAG = "</SCRIPT>";
	private static final String MATCH_NAME [] = {"SCRIPT"};
	private static final String ENDERS [] = {"BODY", "HTML"};
	private int endTagLoc;
	private Tag endTag;
	private Tag startTag;
	private int startingPos;
	private boolean sameLine;
	private boolean endTagFound;
	private NodeReader reader;
	
	private StringBuffer scriptContents;
	public ScriptScanner() {
		super("",MATCH_NAME,ENDERS);
	}

	public ScriptScanner(String filter) {
		super(filter,MATCH_NAME,ENDERS);
	}

	public ScriptScanner(String filter, String[] nameOfTagToMatch) {
		super(filter,nameOfTagToMatch,ENDERS);
	}
	
	public String [] getID() {
		return MATCH_NAME;
	}

	public Tag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new ScriptTag(tagData,compositeTagData);
	}

	public Tag scan(Tag tag, String url, NodeReader nodeReader, String currLine)
		throws ParserException {
		try {
			this.reader = nodeReader;
			int startLine = reader.getLastLineNumber();
			startTag = tag;
			extractScriptTagFrom(currLine);
			if (isScriptEndTagNotFound()) {
				createScriptEndTag(tag, currLine);
			}
			return createScriptTagUsing(url, currLine, startLine);
			
		}
		catch (Exception e) {
			throw new ParserException("Error in ScriptScanner: ",e);
		}
	}

	private Tag createScriptTagUsing(String url, String currLine, int startLine) {
		return createTag(
			new TagData(
				startTag.elementBegin(),
				endTag.elementEnd(),
				startLine,
				reader.getLastLineNumber(),
				startTag.getText(),
				currLine,
				url,
				false
			), new CompositeTagData(
				startTag,endTag,createChildrenNodeList()
			)
		);
	}

	private NodeList createChildrenNodeList() {
		NodeList childrenNodeList = new NodeList();
		childrenNodeList.add(
			new StringNode(
				scriptContents,
				startTag.elementEnd(),
				endTag.elementBegin()-1
			)
		);
		return childrenNodeList;
	}

	private void createScriptEndTag(Tag tag, String currLine) {
		// If end tag doesn't exist, create one
		String endTagName = tag.getTagName();
		int endTagBegin = reader.getLastReadPosition()+1 ;
		int endTagEnd = endTagBegin + endTagName.length() + 2; 
		endTag = new EndTag(
			new TagData(
				endTagBegin,
				endTagEnd,
				endTagName,
				currLine
			)
		);
	}

	private boolean isScriptEndTagNotFound() {
		return endTag == null;
	}

	private void extractScriptTagFrom(String currLine) throws ParserException {
		String line = null;
		scriptContents = new StringBuffer();
		endTagFound = false;
		
		endTag = null;
		line = currLine;
		sameLine = true;
		startingPos = startTag.elementEnd();
		do {
			doExtractionOfScriptContentsFrom(line);
			if (!endTagFound) {
				line = reader.getNextLine();
				startingPos = 0;
			}
			if (sameLine) 
				sameLine = false;
		}
		while (line!=null && !endTagFound);
	}

	private void doExtractionOfScriptContentsFrom(String line) throws ParserException {
		endTagLoc = line.toUpperCase().indexOf(getEndTag(),startingPos);
		findStartingAndEndingLocations(line);
		
		if (endTagLoc!=-1) {
			extractEndTagFrom(line);
		} else {
			continueParsing(line);
		}
	}

	private void continueParsing(String line) {
		if (sameLine) 
			scriptContents.append(
				line.substring(
					startTag.elementEnd()+1
				)
			);
		else {
			scriptContents.append(Parser.getLineSeparator());
			scriptContents.append(line);
		}
	}

	private void extractEndTagFrom(String line) throws ParserException {
		endTagFound = true;
		endTag = (EndTag)EndTag.find(line,endTagLoc);
		if (sameLine) 
			scriptContents.append(
				getCodeBetweenStartAndEndTags(
					line,
					startTag,
					endTagLoc)
			);
		else {
			scriptContents.append(Parser.getLineSeparator());
			scriptContents.append(line.substring(0,endTagLoc));
		}
		
		reader.setPosInLine(endTag.elementEnd());
	}

	private void findStartingAndEndingLocations(String line) {
		while (endTagLoc>0 && isThisEndTagLocationFalseMatch(line, endTagLoc)) {
			startingPos = endTagLoc+getEndTag().length();
			endTagLoc = line.toUpperCase().indexOf(getEndTag(), startingPos); 	
		}
	}

	public String getCodeBetweenStartAndEndTags(
		String line,
		Tag startTag,
		int endTagLoc) throws ParserException {
		try {
			
			return line.substring(
				startTag.elementEnd()+1,
				endTagLoc
			);
		}
		catch (Exception e) {
			StringBuffer msg = new StringBuffer("Error in getCodeBetweenStartAndEndTags():\n");
			msg.append("substring starts at: "+(startTag.elementEnd()+1)).append("\n");
			msg.append("substring ends at: "+(endTagLoc));
			throw new ParserException(msg.toString(),e);
		}
	}

	/**
	 * Gets the end tag that the scanner uses to stop scanning. Subclasses of
	 * <code>ScriptScanner</code> you should override this method.
	 * @return String containing the end tag to search for, i.e. &lt;/SCRIPT&gt;
	 */ 
	public String getEndTag() {
		return SCRIPT_END_TAG;
	}
	
	private boolean isThisEndTagLocationFalseMatch(String line, int endTagLoc) {
		if (endTagLoc+getEndTag().length() > line.length()-1) return false;
		char charAfterSuspectedEndTag = 
			line.charAt(endTagLoc+getEndTag().length()); 
		return charAfterSuspectedEndTag=='"' || charAfterSuspectedEndTag=='\'';
	}

}
