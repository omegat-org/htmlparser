// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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

package org.htmlparser.tags;

import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleEnumeration;
import org.htmlparser.visitors.HTMLVisitor;

public abstract class CompositeTag extends HTMLTag {
	protected HTMLTag startTag, endTag;
	protected NodeList childTags; 

	public CompositeTag(HTMLTagData tagData, HTMLCompositeTagData compositeTagData) {
		super(tagData);
		this.childTags = compositeTagData.getChildren();
		this.startTag  = compositeTagData.getStartTag();
		this.endTag    = compositeTagData.getEndTag();
	}
	
	public SimpleEnumeration children() {
		return childTags.elements();
	}

	public String toPlainTextString() {
		StringBuffer stringRepresentation = new StringBuffer();
		for (SimpleEnumeration e=children();e.hasMoreNodes();) {
			stringRepresentation.append(e.nextNode().toPlainTextString());
		}
		return stringRepresentation.toString();
	}

	public void putStartTagInto(StringBuffer sb) {
		sb.append(startTag.toHTML());
	}

	protected void putChildrenInto(StringBuffer sb) {
		HTMLNode node,prevNode=startTag;
		for (SimpleEnumeration e=children();e.hasMoreNodes();) {
			node = e.nextNode();
			if (prevNode!=null) {
				if (prevNode.elementEnd()>node.elementBegin()) {
					// Its a new line
					sb.append(lineSeparator);					
				}
			}
			sb.append(node.toHTML());
			prevNode=node;
		}
		if (prevNode.elementEnd()>endTag.elementBegin()) {
			sb.append(lineSeparator);
		}
	}

	protected void putEndTagInto(StringBuffer sb) {
		sb.append(endTag.toHTML());
	}

	public String toHTML() {
		StringBuffer sb = new StringBuffer();
		putStartTagInto(sb);
		putChildrenInto(sb);
		putEndTagInto(sb);
		return sb.toString();
	}

	public HTMLTag searchByName(String name) {
		HTMLNode node;
		HTMLTag tag=null;
		boolean found = false;
		for (SimpleEnumeration e = children();e.hasMoreNodes() && !found;) {
			node = (HTMLNode)e.nextNode();
			if (node instanceof HTMLTag) {
				tag = (HTMLTag)node;
				String nameAttribute = tag.getParameter("NAME");
				if (nameAttribute!=null && nameAttribute.equals(name)) found=true;
			}
		}
		if (found) 
			return tag;
		else
			return null;
	}

	public Vector searchFor(String searchString, boolean caseSensitive) {
		Vector foundVector = new Vector();
		HTMLNode node;
		if (!caseSensitive) searchString = searchString.toUpperCase();
		for (SimpleEnumeration e = children();e.hasMoreNodes();) {
			node = (HTMLNode)e.nextNode();
			String nodeTextString = node.toPlainTextString(); 
			if (!caseSensitive) nodeTextString=nodeTextString.toUpperCase();
			if (nodeTextString.indexOf(searchString)!=-1) {
				foundVector.addElement(node);
			}	
		}
		return foundVector;
	}

	/** 
	 * Case insensitive search
	 * @param searchString
	 * @return Vector
	 */
	public Vector searchFor(String searchString) {
		return searchFor(searchString, false);
	}

	public void collectInto(Vector collectionVector, String filter) {
		super.collectInto(collectionVector, filter);
		HTMLNode node;
		for (SimpleEnumeration e = children();e.hasMoreNodes();) {
			node = (HTMLNode)e.nextNode();
			node.collectInto(collectionVector,filter);
		}
	}

	public String getChildrenHTML() {
		StringBuffer buff = new StringBuffer();
		for (SimpleEnumeration e = children();e.hasMoreNodes();) {
			HTMLNode node = (HTMLNode)e.nextNode();
			buff.append(node.toHTML());
		}
		return buff.toString();
	}
	
	public void accept(HTMLVisitor visitor) {
		startTag.accept(visitor);
		SimpleEnumeration children = children();
		while (children.hasMoreNodes()) {
			HTMLNode child = (HTMLNode)children.nextNode();
			child.accept(visitor);
		}
		endTag.accept(visitor);
	}
	
}
