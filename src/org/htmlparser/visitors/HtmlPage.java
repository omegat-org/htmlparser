package org.htmlparser.visitors;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.scanners.TableScanner;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.HTMLTitleTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

public class HtmlPage extends HTMLVisitor {
	private String title;
	private NodeList nodesInBody;
	private NodeList tables;
	private boolean bodyTagBegin;
	
	public HtmlPage(HTMLParser parser) {
		parser.registerScanners();
		parser.addScanner(new TableScanner(parser));
		nodesInBody = new NodeList();
		tables = new NodeList();
		bodyTagBegin = false;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void visitTag(HTMLTag tag) {
		addTagToBodyIfApplicable(tag);
			
		if (isTitle(tag)) {
			HTMLTitleTag titleTag = (HTMLTitleTag)tag;
			title = titleTag.getTitle();
		} 
		else if (isTable(tag)) {
			tables.add(tag);
		}
		else {
			if (isBodyTag(tag))
				bodyTagBegin = true;
		}
	}

	private boolean isTitle(HTMLTag tag) {
		return tag instanceof HTMLTitleTag;
	}

	private boolean isTable(HTMLTag tag) {
		return tag instanceof TableTag;
	}

	private void addTagToBodyIfApplicable(HTMLNode node) {
		if (bodyTagBegin)
			nodesInBody.add(node);
	}

	public void visitEndTag(HTMLEndTag endTag) {
		if (isBodyTag(endTag)) 
			bodyTagBegin = false;
		addTagToBodyIfApplicable(endTag);	
	}

	public void visitRemarkNode(HTMLRemarkNode remarkNode) {
		addTagToBodyIfApplicable(remarkNode);
	}

	public void visitStringNode(HTMLStringNode stringNode) {
		addTagToBodyIfApplicable(stringNode);
	}
	
	private boolean isBodyTag(HTMLTag tag) {
		return tag.getTagName().equals("BODY");
	}
	
	public NodeList getBody() {
		return nodesInBody;
	}
	
	public TableTag [] getTables() {
		TableTag [] tableArr = new TableTag[tables.size()];
		for (int i=0;i<tables.size();i++)
			tableArr[i] = (TableTag)tables.elementAt(i);
		return tableArr;
	}



}
