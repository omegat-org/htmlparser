package org.htmlparser.nodeDecorators;

import org.htmlparser.Node;

public class NonBreakingSpaceConvertingNode extends AbstractNodeDecorator {
	public NonBreakingSpaceConvertingNode(Node newDelegate) {
		super(newDelegate);
	}

	public String toPlainTextString() {
		String result = delegate.toPlainTextString();
		return result.replace ('\u00a0',' ');
	}
}
