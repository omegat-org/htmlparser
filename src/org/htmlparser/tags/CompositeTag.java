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

import org.htmlparser.HTMLNode;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleEnumeration;
import org.htmlparser.visitors.HTMLVisitor;

public abstract class CompositeTag extends HTMLTag {
	protected HTMLTag startTag, endTag;
	protected NodeList childTags; 

	public CompositeTag(TagData tagData, CompositeTagData compositeTagData) {
		super(tagData);
		this.childTags = compositeTagData.getChildren();
		this.startTag  = compositeTagData.getStartTag();
		this.endTag    = compositeTagData.getEndTag();
	}
	
	public SimpleEnumeration children() {
		return childTags.elements();
	}

	public HTMLNode getChild(int index) {
		return childTags.elementAt(index);	
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

	/**
	 * Searches all children who for a name attribute. Returns first match.
	 * @param name Attribute to match in tag
	 * @return HTMLTag Tag matching the name attribute
	 */
	public HTMLTag searchByName(String name) {
		HTMLNode node;
		HTMLTag tag=null;
		boolean found = false;
		for (SimpleEnumeration e = children();e.hasMoreNodes() && !found;) {
			node = (HTMLNode)e.nextNode();
			if (node instanceof HTMLTag) {
				tag = (HTMLTag)node;
				String nameAttribute = tag.getAttribute("NAME");
				if (nameAttribute!=null && nameAttribute.equals(name)) found=true;
			}
		}
		if (found) 
			return tag;
		else
			return null;
	}

	/** 
	 * Searches for any node whose text representation contains the search
	 * string. Collects all such nodes in a NodeList.
	 * e.g. if you wish to find any textareas in a form tag containing "hello
	 * world", the code would be :
	 * <code>
	 *  NodeList nodeList = formTag.searchFor("Hello World");
	 * </code>
	 * @param searchString search criterion
	 * @param caseSensitivie specify whether this search should be case
	 * sensitive
	 * @return NodeList Collection of nodes whose string contents or
	 * representation have the searchString in them
	 */

	public NodeList searchFor(String searchString, boolean caseSensitive) {
		NodeList foundVector = new NodeList();
		HTMLNode node;
		if (!caseSensitive) searchString = searchString.toUpperCase();
		for (SimpleEnumeration e = children();e.hasMoreNodes();) {
			node = e.nextNode();
			String nodeTextString = node.toPlainTextString(); 
			if (!caseSensitive) nodeTextString=nodeTextString.toUpperCase();
			if (nodeTextString.indexOf(searchString)!=-1) {
				foundVector.add(node);
			}	
		}
		return foundVector;
	}

	/**
	 * Collect all objects that are of a certain type
	 * Note that this will not check for parent types, and will not 
	 * recurse through child tags
	 * @param classType
	 * @return NodeList
	 */
	public NodeList searchFor(Class classType) {
		NodeList foundVector = new NodeList();
		HTMLNode node;
		for (SimpleEnumeration e = children();e.hasMoreNodes();) {
			node = e.nextNode();
			if (node.getClass().getName().equals(classType.getName())) 
				foundVector.add(node);
		}
		return foundVector;
	}
	/** 
	 * Searches for any node whose text representation contains the search
	 * string. Collects all such nodes in a NodeList.
	 * e.g. if you wish to find any textareas in a form tag containing "hello
	 * world", the code would be :
	 * <code>
	 *  NodeList nodeList = formTag.searchFor("Hello World");
	 * </code>
	 * This search is <b>case-insensitive</b>.
	 * @param searchString search criterion
	 * @return NodeList Collection of nodes whose string contents or
	 * representation have the searchString in them
	 */
	public NodeList searchFor(String searchString) {
		return searchFor(searchString, false);
	}

	public void collectInto(NodeList collectionList, String filter) {
		super.collectInto(collectionList, filter);
		HTMLNode node;
		for (SimpleEnumeration e = children();e.hasMoreNodes();) {
			node = (HTMLNode)e.nextNode();
			node.collectInto(collectionList,filter);
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
		if (visitor.shouldRecurseChildren()) { 
			startTag.accept(visitor);
			SimpleEnumeration children = children();
			while (children.hasMoreNodes()) {
				HTMLNode child = (HTMLNode)children.nextNode();
				child.accept(visitor);
			}
			endTag.accept(visitor);
		}
			else
				visitor.visitTag(this);
	}

}
