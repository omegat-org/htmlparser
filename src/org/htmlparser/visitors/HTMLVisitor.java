package org.htmlparser.visitors;

import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;

public abstract class HTMLVisitor {
	public void visitTag(HTMLTag tag) {
		
	}

	public void visitStringNode(HTMLStringNode stringNode) {
	}
	
	public void visitLinkTag(HTMLLinkTag linkTag) {
	}
	
	public void visitImageTag(HTMLImageTag imageTag) {
	}
	
	public void visitEndTag(HTMLEndTag endTag) {
		
	}
	
	public void visitRemarkNode(HTMLRemarkNode remarkNode) {
		
	}
	
}
