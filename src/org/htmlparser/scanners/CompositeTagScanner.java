// HTMLParser Library v1_3_20030302 - A java-based parser for HTML
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

import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;

public abstract class CompositeTagScanner extends TagScanner {
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
	
	public Tag scan(Tag tag, String url, NodeReader reader,String currLine)
		throws ParserException {
		Tag endTag=null; 
		try {
			if (isBrokenTag()) {
				if (isTagFoundAtAll(tag)) 
					return getReplacedEndTag(tag, reader, currLine);
				else  
					return getInsertedEndTag(tag, reader, currLine);
			}
			beforeScanningStarts();
			Tag startTag = tag;
			endTag = null;
			boolean endTagFound = false;
			Node node=tag;
			
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
				if (node instanceof EndTag) {
					endTag = (Tag)node;
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
			msg.append(tag.toHtml());
			if (endTag==null) 
				msg.append("\n no </"+tag.getTagName()+"> end tag was found!");
			throw new ParserException(msg.toString(),e);
		}
	}

	public boolean isXmlEndTag(Tag tag) {
		String tagText = tag.getText();
		return tagText.charAt(tagText.length()-1)=='/';
	}
	
	protected void beforeScanningStarts() {
	}
	
	protected void childNodeEncountered(Node node) {
	}

	protected abstract Tag createTag(TagData tagData, CompositeTagData compositeTagData);


}
