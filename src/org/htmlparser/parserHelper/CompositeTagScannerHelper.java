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
		this.endTag = tag;
		this.nodeList = new NodeList();
		this.endTagFound = false;
	}
		
	public Tag scan() throws ParserException {
		Node currentNode = tag;
		doEmptyXmlTagCheckOn(currentNode);
		if (!endTagFound) { 
			do {
				currentNode = reader.readElement();
				if (currentNode==null) continue;
				doEmptyXmlTagCheckOn(currentNode);
				doChildAndEndTagCheckOn(currentNode);					
			}
			while (currentNode!=null && !endTagFound);
		}
		return createTag();
	}

	private Tag createTag() throws ParserException {
		return scanner.createTag(
			new TagData(
				0,endTag.elementEnd(),0,0,"","","",tag.isEmptyXmlTag()
			),
			new CompositeTagData(
				tag,endTag,nodeList
			)
		);
	}

	private void doChildAndEndTagCheckOn(Node currentNode) {
		if (currentNode instanceof EndTag) {
			endTag = (Tag)currentNode;
			endTagFound = true;
		}
		else 
			nodeList.add(currentNode);	
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
}
