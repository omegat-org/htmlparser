// HTMLParser Library v1_3_20030419 - A java-based parser for HTML
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

import org.htmlparser.*;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.tags.data.*;
import org.htmlparser.util.*;

public class CompositeTagScannerHelper {
	private CompositeTagScanner scanner;
	private Tag tag;
	private String url;
	private NodeReader reader;
	private String currLine;
	private Tag endTag;
	private NodeList nodeList;
	private boolean endTagFound;
	private int startingLineNumber;
	private int endingLineNumber;
	
	public CompositeTagScannerHelper(
		CompositeTagScanner scanner,
		Tag tag, 
		String url, 
		NodeReader reader,
		String currLine) {
		
		this.scanner = scanner;
		this.tag = tag;
		this.url = url;
		this.reader = reader;
		this.currLine = currLine;	
		this.endTag = null;
		this.nodeList = new NodeList();
		this.endTagFound = false;
	}
		
	public Tag scan() throws ParserException {
		this.startingLineNumber = reader.getLastLineNumber();
		scanner.beforeScanningStarts();
		Node currentNode = tag;
		doEmptyXmlTagCheckOn(currentNode);
		if (!endTagFound) { 
			do {
				currentNode = reader.readElement();
				if (currentNode==null) continue; 
				currLine = reader.getCurrentLine();
				if (currentNode instanceof Tag) 
					doForceCorrectionCheckOn((Tag)currentNode);
					
				doEmptyXmlTagCheckOn(currentNode);
				if (!endTagFound)
					doChildAndEndTagCheckOn(currentNode);					
			}
			while (currentNode!=null && !endTagFound);
		}
		if (endTag==null) {
			createCorrectionEndTagBefore(reader.getLastReadPosition()+1);
		}
		this.endingLineNumber = reader.getLastLineNumber();
		return createTag();
	}

	private void createCorrectionEndTagBefore(int pos) {
		String endTagName = tag.getTagName();
		int endTagBegin = pos ;
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
	
	private void createCorrectionEndTagBefore(Tag possibleEndTagCauser) {
		String endTagName = tag.getTagName();
		int endTagBegin = possibleEndTagCauser.elementBegin();
		int endTagEnd = endTagBegin + endTagName.length() + 2; 
		possibleEndTagCauser.setTagBegin(endTagEnd+1);
		reader.setNextParsedNode(possibleEndTagCauser);
		endTag = new EndTag(
			new TagData(
				endTagBegin,
				endTagEnd,
				endTagName,
				currLine
			)
		);
	}

	private StringBuffer createModifiedLine(String endTagName, int endTagBegin) {
		StringBuffer newLine = new StringBuffer();
		newLine.append(currLine.substring(0,endTagBegin));
		newLine.append("</");
		newLine.append(endTagName);
		newLine.append(">");
		newLine.append(currLine.substring(endTagBegin,currLine.length()));
		return newLine;
	}

	private Tag createTag() throws ParserException {
		CompositeTag newTag = 
		 	(CompositeTag)
		 	scanner.createTag(
			new TagData(
				tag.elementBegin(),
				endTag.elementEnd(),
				startingLineNumber,
				endingLineNumber,
				tag.getText(),
				currLine,
				url,
				tag.isEmptyXmlTag()
			),
			new CompositeTagData(
				tag,endTag,nodeList
			)
		);
		for (int i=0;i<newTag.getChildCount();i++) {
			Node child = newTag.childAt(i);
			child.setParent(newTag);
		}
		return newTag;
	}

	private void doChildAndEndTagCheckOn(Node currentNode) {
		if (currentNode instanceof EndTag) {
			EndTag possibleEndTag = (EndTag)currentNode;
			if (isExpectedEndTag(possibleEndTag)) {
				endTagFound = true;
				endTag = possibleEndTag;
				return;
			}
		}
		nodeList.add(currentNode);
		scanner.childNodeEncountered(currentNode);
	}

	private boolean isExpectedEndTag(EndTag possibleEndTag) {
		return possibleEndTag.getTagName().equals(tag.getTagName());
	}

	private void doEmptyXmlTagCheckOn(Node currentNode) {
		if (currentNode instanceof Tag) {
			Tag possibleEndTag = (Tag)currentNode;
			if (isXmlEndTag(tag)) {
				endTag = possibleEndTag;
				endTagFound = true;			
			} 
		}
	}

	private void doForceCorrectionCheckOn(Tag possibleEndTagCauser) {
		if (isEndTagMissing(possibleEndTagCauser)) {
			createCorrectionEndTagBefore(possibleEndTagCauser);

			endTagFound = true;			
		}
	}

	private boolean isEndTagMissing(Tag possibleEndTag) {
		return 
			scanner.isTagToBeEndedFor(possibleEndTag) || 
			isSelfChildTagRecievedIncorrectly(possibleEndTag);
	}

	private boolean isSelfChildTagRecievedIncorrectly(Tag possibleEndTag) {
		return (
			!(possibleEndTag instanceof EndTag) &&
			!scanner.isAllowSelfChildren() && 
			possibleEndTag.getTagName().equals(tag.getTagName())
		);
	}
	
	public boolean isXmlEndTag(Tag tag) {
		String tagText = tag.getText();
		int lastSlash = tagText.lastIndexOf("/");
		return (lastSlash == tagText.length()-1 || tag.isEmptyXmlTag()) && tag.getText().indexOf("://")==-1;
	}
}
