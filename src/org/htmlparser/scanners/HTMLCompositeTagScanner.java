package org.htmlparser.scanners;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.HTMLParserException;

public abstract class HTMLCompositeTagScanner extends HTMLTagScanner {
	protected String nameOfTagToMatch;

	public HTMLCompositeTagScanner(String nameOfTagToMatch) {
		super();
		this.nameOfTagToMatch = nameOfTagToMatch;
	}

	public HTMLCompositeTagScanner(String filter, String nameOfTagToMatch) {
		super(filter);
		this.nameOfTagToMatch = nameOfTagToMatch;
	}

	public HTMLTag scan(HTMLTag tag, String url, HTMLReader reader,String currLine)
		throws HTMLParserException {
		HTMLTag startTag = tag;
		HTMLTag endTag = null;
		boolean endTagFound = false;
		HTMLNode node;
		
		Vector childVector = new Vector();
		List flavorList = new ArrayList();
		
		do {
			node = reader.readElement();
			if (node instanceof HTMLEndTag) {
				endTag = (HTMLTag)node;
				if (endTag.getText().equalsIgnoreCase(nameOfTagToMatch)) 
					endTagFound = true;
			} 
			if (!endTagFound){
				childVector.addElement(node);
				childNodeEncountered(node);
			}
		}
		while (endTagFound==false && node!=null);
		
		return createTag(new HTMLTagData(
				startTag.elementBegin(),
				endTag.elementEnd(),
				startTag.getText(),
				currLine				
			), new HTMLCompositeTagData(
				startTag,endTag,childVector
			)
		);
	}
	
	protected void childNodeEncountered(HTMLNode node) {
	}

	protected abstract HTMLTag createTag(HTMLTagData tagData, HTMLCompositeTagData compositeTagData);


}
