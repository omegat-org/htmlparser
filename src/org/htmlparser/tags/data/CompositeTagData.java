package org.htmlparser.tags.data;

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.NodeList;

public class CompositeTagData {
	private HTMLTag startTag;
	private HTMLTag endTag;
	private NodeList children;
	
	public CompositeTagData(
		HTMLTag startTag, HTMLTag endTag, Vector children) {
		this.startTag = startTag;
		this.endTag   = endTag;
		this.children = new NodeList();
		if (children!=null)
		for (Enumeration e = children.elements();e.hasMoreElements();) {
			this.children.add((HTMLNode)e.nextElement());
		}
	}

	public NodeList getChildren() {
		return children;
	}

	public HTMLTag getEndTag() {
		return endTag;
	}

	public HTMLTag getStartTag() {
		return startTag;
	}

}
