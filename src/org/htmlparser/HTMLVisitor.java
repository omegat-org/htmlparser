package org.htmlparser;

import org.htmlparser.tags.HTMLTag;

public abstract class HTMLVisitor {
	public abstract void visitTag(HTMLTag tag);
	public abstract void visitStringNode(HTMLStringNode stringNode);
}
