package org.htmlparser.nodeDecorators;

import org.htmlparser.Node;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserUtils;
import org.htmlparser.visitors.NodeVisitor;

public class EscapeCharacterRemovingNode implements Node {
	private Node delegate; 	

	public EscapeCharacterRemovingNode(Node newDelegate) {
		this.delegate = newDelegate;
	}

	public void accept(NodeVisitor visitor) {
		delegate.accept(visitor);
	}

	public void collectInto(NodeList collectionList, Class nodeType) {
		delegate.collectInto(collectionList, nodeType);
	}

	public void collectInto(NodeList collectionList, String filter) {
		delegate.collectInto(collectionList, filter);
	}

	public int elementBegin() {
		return delegate.elementBegin();
	}

	public int elementEnd() {
		return delegate.elementEnd();
	}

	public boolean equals(Object arg0) {
		return delegate.equals(arg0);
	}

	public CompositeTag getParent() {
		return delegate.getParent();
	}

	public String getText() {
		return delegate.getText();
	}

	public void setParent(CompositeTag tag) {
		delegate.setParent(tag);
	}

	public void setText(String text) {
		delegate.setText(text);
	}

	public String toHtml() {
		return delegate.toHtml();
	}

	public String toPlainTextString() {
		return ParserUtils.removeEscapeCharacters(delegate.toPlainTextString());
	}

	public String toString() {
		return delegate.toString();
	}

}
