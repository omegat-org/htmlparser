package org.htmlparser.scanners;

import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.HTMLParserException;

public abstract class CompositeTagScanner extends HTMLTagScanner {
	protected String [] nameOfTagToMatch;
	private boolean removeScanners;
	private boolean stringNodeIgnoreMode;

	public CompositeTagScanner(String [] nameOfTagToMatch) {
		this("",nameOfTagToMatch,false,false);
	}

	public CompositeTagScanner(String filter, String [] nameOfTagToMatch) {
		this(filter,nameOfTagToMatch,false,false);
	}
	public CompositeTagScanner(String filter, String [] nameOfTagToMatch, boolean removeScanners, boolean stringNodeIgnoreMode) {
		this.nameOfTagToMatch = nameOfTagToMatch;
		this.removeScanners = removeScanners;
		this.stringNodeIgnoreMode = stringNodeIgnoreMode;
	}
	
	public HTMLTag scan(HTMLTag tag, String url, HTMLReader reader,String currLine)
		throws HTMLParserException {
		beforeScanningStarts();
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
				reader.getStringParser().setIgnoreStateMode(true);
			node = reader.readElement();
			if (stringNodeIgnoreMode)
				reader.getStringParser().setIgnoreStateMode(false);
			if (node!=null && node.getType()==HTMLEndTag.TYPE) {
				endTag = (HTMLTag)node;
				for (int i=0;i<nameOfTagToMatch.length && !endTagFound;i++) {
					if (endTag.getText().equalsIgnoreCase(nameOfTagToMatch[i])) 
						endTagFound = true;
				}
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
	protected void beforeScanningStarts() {
	}
	
	protected void childNodeEncountered(HTMLNode node) {
	}

	protected abstract HTMLTag createTag(HTMLTagData tagData, HTMLCompositeTagData compositeTagData);


}
