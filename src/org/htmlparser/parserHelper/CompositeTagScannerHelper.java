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
	private Node currentNode;
	
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
		this.currentNode = null;	
	}
		
	public Tag scan() throws ParserException {
		do {
			currentNode = reader.readElement();
			if (currentNode==null) continue;
			if (currentNode instanceof Tag) {
				Tag possibleEndTag = (Tag)currentNode;
				if (tag.isEmptyXmlTag()) {
					endTag = possibleEndTag;
					currentNode = endTag;					
				}
			}
			if (currentNode instanceof EndTag)
				endTag = (Tag)currentNode;
			else 
				nodeList.add(currentNode);						
		}
		while (currentNode!=null);
		
		return scanner.createTag(
			new TagData(
				0,endTag.elementEnd(),0,0,"","","",tag.isEmptyXmlTag()
			),
			new CompositeTagData(
				tag,endTag,nodeList
			)
		);
	}
}
