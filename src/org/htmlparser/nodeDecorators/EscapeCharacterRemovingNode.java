package org.htmlparser.nodeDecorators;

import org.htmlparser.Node;
import org.htmlparser.util.ParserUtils;

public class EscapeCharacterRemovingNode extends AbstractNodeDecorator {
	public EscapeCharacterRemovingNode(Node newDelegate) {
		super(newDelegate);
	}

	public String toPlainTextString() {
		return ParserUtils.removeEscapeCharacters(delegate.toPlainTextString());
	}
}
