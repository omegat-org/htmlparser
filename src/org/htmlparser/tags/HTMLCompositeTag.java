package org.htmlparser.tags;

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public abstract class HTMLCompositeTag extends HTMLTag {
	private HTMLTag startTag, endTag;
	private Vector childTags; 

	public HTMLCompositeTag(HTMLTagData tagData, HTMLCompositeTagData compositeTagData) {
		super(tagData);
		this.childTags = compositeTagData.getChildren();
		this.startTag  = compositeTagData.getStartTag();
		this.endTag    = compositeTagData.getEndTag();
	}
	
	public Enumeration children() {
		return childTags.elements();
	}

	public String toPlainTextString() {
		StringBuffer stringRepresentation = new StringBuffer();
		HTMLNode node;
		for (Enumeration e=children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();		
			stringRepresentation.append(node.toPlainTextString());
		}
		return stringRepresentation.toString();
	}

	public void putStartTagInto(StringBuffer sb) {
		sb.append(startTag.toHTML());
	}

	protected void putChildrenInto(StringBuffer sb) {
		HTMLNode node,prevNode=startTag;
		for (Enumeration e=children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			if (prevNode!=null) {
				if (prevNode.elementEnd()>node.elementBegin()) {
					// Its a new line
					sb.append(lineSeparator);					
				}
			}
			sb.append(node.toHTML());
			prevNode=node;
		}
		if (prevNode.elementEnd()>endTag.elementBegin()) {
			sb.append(lineSeparator);
		}
	}

	protected void putEndTagInto(StringBuffer sb) {
		sb.append(endTag.toHTML());
	}

	public String toHTML() {
		StringBuffer sb = new StringBuffer();
		putStartTagInto(sb);
		putChildrenInto(sb);
		putEndTagInto(sb);
		return sb.toString();
	}

	public HTMLTag searchByName(String name) {
		HTMLNode node;
		HTMLTag tag=null;
		boolean found = false;
		for (Enumeration e = children();e.hasMoreElements() && !found;) {
			node = (HTMLNode)e.nextElement();
			if (node instanceof HTMLTag) {
				tag = (HTMLTag)node;
				String nameAttribute = tag.getParameter("NAME");
				if (nameAttribute!=null && nameAttribute.equals(name)) found=true;
			}
		}
		if (found) 
			return tag;
		else
			return null;
	}

	public Vector searchFor(String searchString, boolean caseSensitive) {
		Vector foundVector = new Vector();
		HTMLNode node;
		if (!caseSensitive) searchString = searchString.toUpperCase();
		for (Enumeration e = children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			String nodeTextString = node.toPlainTextString(); 
			if (!caseSensitive) nodeTextString=nodeTextString.toUpperCase();
			if (nodeTextString.indexOf(searchString)!=-1) {
				foundVector.addElement(node);
			}	
		}
		return foundVector;
	}

	/** 
	 * Case insensitive search
	 * @param searchString
	 * @return Vector
	 */
	public Vector searchFor(String searchString) {
		return searchFor(searchString, false);
	}

	public void collectInto(Vector collectionVector, String filter) {
		super.collectInto(collectionVector, filter);
		HTMLNode node;
		for (Enumeration e = children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			node.collectInto(collectionVector,filter);
		}
	}

}
