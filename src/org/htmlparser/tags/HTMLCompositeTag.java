package org.htmlparser.tags;

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;


public abstract class HTMLCompositeTag extends HTMLTag {
	private HTMLTag startTag, endTag;
	private Vector childTags; 

	public HTMLCompositeTag(
		int tagBegin,
		int tagEnd,
		String tagContents,
		String tagLine,
		Vector childTags,HTMLTag startTag, HTMLTag endTag) {
		super(tagBegin, tagEnd, tagContents, tagLine);
		this.childTags = childTags;
		this.startTag  = startTag;
		this.endTag    = endTag;
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
		sb.append("<");
		sb.append(getParameter(TAGNAME));
		if (containsMoreThanOneKey()) sb.append(" ");
		String key,value;
		int i = 0;
		for (Enumeration e = parsed.keys();e.hasMoreElements();) {
			key = (String)e.nextElement();
			i++;
			if (key!=TAGNAME) {
				value = getParameter(key);
				sb.append(key+"=\""+value+"\"");
				if (i<parsed.size()) sb.append(" ");
			}
		}
		sb.append(">");
	}

	public boolean containsMoreThanOneKey() {
		return parsed.keySet().size()>1;
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
		sb.append("</").append(parsed.get(TAGNAME)).append(">");
	}

	public String toHTML() {
		StringBuffer sb = new StringBuffer();
		putStartTagInto(sb);
		//sb.append(tagContents.toString());
		putChildrenInto(sb);
		putEndTagInto(sb);
		return sb.toString();
	}

}
