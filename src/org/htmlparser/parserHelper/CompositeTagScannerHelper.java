/*
 * Created on Apr 12, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.htmlparser.parserHelper;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class CompositeTagScannerHelper {
	private CompositeTagScanner scanner;
	private Tag tag;
	private String url;
	private NodeReader reader;
	private String currLine;
	private Tag endTag;
	private NodeList nodeList;
	private boolean endTagFound;
	
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
		return scanner.createTag(
			new TagData(
				tag.elementBegin(),endTag.elementEnd(),0,0,"","",url,tag.isEmptyXmlTag()
			),
			new CompositeTagData(
				tag,endTag,nodeList
			)
		);
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
			if (tag.isEmptyXmlTag()) {
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
			scanner.isTagToBeEndedFor(possibleEndTag.getTagName()) || 
			isSelfChildTagRecievedIncorrectly(possibleEndTag);
	}

	private boolean isSelfChildTagRecievedIncorrectly(Tag possibleEndTag) {
		return (
			!(possibleEndTag instanceof EndTag) &&
			!scanner.isAllowSelfChildren() && 
			possibleEndTag.getTagName().equals(tag.getTagName())
		);
	}
}
