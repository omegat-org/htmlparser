package org.htmlparser;

import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.Translate;
import org.htmlparser.visitors.NodeVisitor;


public class DecodingNode implements Node {
	private Node delegate; 

	protected DecodingNode(Node node) {
		delegate = node;
	}

	public String toPlainTextString() {
		return Translate.decode(delegate.toPlainTextString());
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

	public String toHTML() {
		return delegate.toHTML();
	}

}
