package org.htmlparser.visitors;

import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLTag;

public abstract class HTMLVisitor {
	public void visitTag(HTMLTag tag) {
		
	}

	public void visitStringNode(HTMLStringNode stringNode) {
	}
}
