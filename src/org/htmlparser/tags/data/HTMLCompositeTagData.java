package org.htmlparser.tags.data;

import java.util.Vector;

import org.htmlparser.tags.HTMLTag;

public class HTMLCompositeTagData {
	private HTMLTag startTag;
	private HTMLTag endTag;
	private Vector children;
	
	public HTMLCompositeTagData(
		HTMLTag startTag, HTMLTag endTag, Vector children) {
		this.startTag = startTag;
		this.endTag   = endTag;
		this.children = children;
	}

	public Vector getChildren() {
		return children;
	}

	public HTMLTag getEndTag() {
		return endTag;
	}

	public HTMLTag getStartTag() {
		return startTag;
	}

}
