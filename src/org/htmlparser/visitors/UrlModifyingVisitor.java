package org.htmlparser.visitors;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.visitors.HTMLVisitor;

public class UrlModifyingVisitor extends HTMLVisitor {
	private String linkPrefix;
	private StringBuffer modifiedResult;
	private HTMLParser parser;
	
	public UrlModifyingVisitor(HTMLParser parser, String linkPrefix) {
		this.parser = parser;
		HTMLLinkScanner linkScanner = new HTMLLinkScanner();  
		parser.addScanner(linkScanner);
		parser.addScanner(
			linkScanner.createImageScanner(
				HTMLImageTag.IMAGE_TAG_FILTER
			)
		);
		this.linkPrefix =linkPrefix; 
		modifiedResult = new StringBuffer();
	}
	
	public void visitLinkTag(HTMLLinkTag linkTag) {
		linkTag.setLink(linkPrefix + linkTag.getLink());
	}

	public void visitImageTag(HTMLImageTag imageTag) {
		imageTag.setImageURL(linkPrefix + imageTag.getImageURL());
		modifiedResult.append(imageTag.toHTML());
	}
	
	public void visitEndTag(HTMLEndTag endTag) {
		modifiedResult.append(endTag.toHTML());
	}

	public void visitStringNode(HTMLStringNode stringNode) {
		modifiedResult.append(stringNode.toHTML());
	}

	public void visitTag(HTMLTag tag) {
		modifiedResult.append(tag.toHTML());
	}
	
	public String getModifiedResult() {
		return modifiedResult.toString();		
	}
}
