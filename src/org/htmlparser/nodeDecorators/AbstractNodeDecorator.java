// HTMLParser Library v1_4_20030727 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com
//
// This class was contributed by Joshua Kerievsky

package org.htmlparser.nodeDecorators;

import org.htmlparser.Node;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;


public abstract class AbstractNodeDecorator implements Node {
	protected Node delegate;
	
	protected AbstractNodeDecorator(Node delegate) {
		this.delegate = delegate;
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
		return delegate.toPlainTextString();
	}

	public String toString() {
		return delegate.toString();
	}

}
