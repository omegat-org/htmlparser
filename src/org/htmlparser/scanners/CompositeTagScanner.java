package org.htmlparser.scanners;

import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
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
		HTMLTag endTag=null; 
		try {
			
			beforeScanningStarts();
			HTMLTag startTag = tag;
			endTag = null;
			boolean endTagFound = false;
			HTMLNode node=tag;
			
			Vector childVector = new Vector();
			
			Hashtable tempScanners=null;
			if (removeScanners) {
				tempScanners = reader.getParser().getScanners();
				reader.getParser().flushScanners();
			}
			if (isXmlEndTag(tag)) {
				endTag = tag; 
				endTagFound=true;
			}
			while (!endTagFound && node!=null){
				if (stringNodeIgnoreMode)
					reader.getStringParser().setIgnoreStateMode(true);
				node = reader.readElement();
				if (stringNodeIgnoreMode)
					reader.getStringParser().setIgnoreStateMode(false);
				if (node instanceof HTMLEndTag) {
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
			
			if (removeScanners)
				reader.getParser().setScanners(tempScanners);
			return createTag(new TagData(
					startTag.elementBegin(),
					endTag.elementEnd(),
					startTag.getText(),
					currLine				
				), new CompositeTagData(
					startTag,endTag,childVector
				)
			);
		}
		catch (Exception e) {
			StringBuffer msg = new StringBuffer();
			msg.append("Exception occurred in CompositeTagScanner.scan(),\n");
			msg.append("current tag being processed is : ");
			msg.append(tag.toHTML());
			if (endTag==null) 
				msg.append("\n no </"+tag.getTagName()+"> end tag was found!");
			throw new HTMLParserException(msg.toString(),e);
		}
	}

	public boolean isXmlEndTag(HTMLTag tag) {
		String tagText = tag.getText();
		return tagText.charAt(tagText.length()-1)=='/';
	}
	
	protected void beforeScanningStarts() {
	}
	
	protected void childNodeEncountered(HTMLNode node) {
	}

	protected abstract HTMLTag createTag(TagData tagData, CompositeTagData compositeTagData);


}
