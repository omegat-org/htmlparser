// HTMLParser Library v1_3_20030427 - A java-based parser for HTML
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
import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.StringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
/**
 * The HTMLScriptScanner identifies javascript code
 */

public class ScriptScanner extends CompositeTagScanner {
	private static final String SCRIPT_END_TAG = "</SCRIPT>";
	private static final String MATCH_NAME [] = {"SCRIPT"};
	private static final String ENDERS [] = {"BODY", "HTML"};
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

	public Tag scan(Tag tag, String url, NodeReader reader, String currLine)
		throws ParserException {
		try {
			int startLine = reader.getLastLineNumber();
			String line = null;
			StringBuffer scriptContents = 
				new StringBuffer();
			boolean endTagFound = false;
			Tag startTag = tag;
			Tag endTag = null;
			line = currLine;
			boolean sameLine = true;
			int startingPos = startTag.elementEnd();
			do {
				int endTagLoc = line.toUpperCase().indexOf(getEndTag(),startingPos);
				while (endTagLoc>0 && isScriptEmbeddedInDocumentWrite(line, endTagLoc)) {
					startingPos = endTagLoc+getEndTag().length();
					endTagLoc = line.toUpperCase().indexOf(getEndTag(), startingPos); 	
				}
				 
				if (endTagLoc!=-1) {
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
						scriptContents.append(Node.getLineSeparator());
						scriptContents.append(line.substring(0,endTagLoc));
					}
					
					reader.setPosInLine(endTag.elementEnd());
				} else {
					if (sameLine) 
						scriptContents.append(
							line.substring(
								startTag.elementEnd()+1
							)
						);
					else {
						scriptContents.append(Node.getLineSeparator());
						scriptContents.append(line);
					}
				}
				if (!endTagFound) {
					line = reader.getNextLine();
					startingPos = 0;
				}
				if (sameLine) 
					sameLine = false;
			}
			while (line!=null && !endTagFound);
			NodeList childrenNodeList = new NodeList();
			childrenNodeList.add(
				new StringNode(
					scriptContents,
					startTag.elementEnd(),
					endTag.elementBegin()-1
				)
			);
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
					startTag,endTag,childrenNodeList
				)
			);
			
		}
		catch (Exception e) {
			throw new ParserException("Error in ScriptScanner: ",e);
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
	
	private boolean isScriptEmbeddedInDocumentWrite(String line, int endTagLoc) {
		if (endTagLoc+getEndTag().length() > line.length()-1) return false;
		return line.charAt(endTagLoc+getEndTag().length())=='"';
	}

}
