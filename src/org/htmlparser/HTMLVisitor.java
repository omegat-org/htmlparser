package org.htmlparser;

import org.htmlparser.tags.HTMLTag;

public abstract class HTMLVisitor {
	public void visitTag(HTMLTag tag) {
	}
	public void visitStringNode(HTMLStringNode stringNode) {
	}
}
