package org.htmlparser.scanners;

import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.HTMLParserException;

public abstract class HTMLCompositeTagScanner extends HTMLTagScanner {
	protected String nameOfTagToMatch;
	private boolean removeScanners;
	private boolean stringNodeIgnoreMode;

	public HTMLCompositeTagScanner(String nameOfTagToMatch) {
		this("",nameOfTagToMatch,false,false);
	}

	public HTMLCompositeTagScanner(String filter, String nameOfTagToMatch) {
		this(filter,nameOfTagToMatch,false,false);
	}
	public HTMLCompositeTagScanner(String filter, String nameOfTagToMatch, boolean removeScanners, boolean stringNodeIgnoreMode) {
		this.nameOfTagToMatch = nameOfTagToMatch;
		this.removeScanners = removeScanners;
		this.stringNodeIgnoreMode = stringNodeIgnoreMode;
	}
	
	public HTMLTag scan(HTMLTag tag, String url, HTMLReader reader,String currLine)
		throws HTMLParserException {
		HTMLTag startTag = tag;
		HTMLTag endTag = null;
		boolean endTagFound = false;
		HTMLNode node;
		
		Vector childVector = new Vector();
		
		Hashtable tempScanners=null;
		if (removeScanners) {
			tempScanners = reader.getParser().getScanners();
			reader.getParser().flushScanners();
		}
		
		do {
			if (stringNodeIgnoreMode)
				HTMLStringNode.setIgnoreStateMode(true);
			node = reader.readElement();
			if (stringNodeIgnoreMode)
				HTMLStringNode.setIgnoreStateMode(false);
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
		if (removeScanners)
			reader.getParser().setScanners(tempScanners);
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
