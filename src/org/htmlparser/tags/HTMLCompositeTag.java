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

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public abstract class HTMLCompositeTag extends HTMLTag {
	protected HTMLTag startTag, endTag;
	protected Vector childTags; 

	public HTMLCompositeTag(HTMLTagData tagData, HTMLCompositeTagData compositeTagData) {
		super(tagData);
		this.childTags = compositeTagData.getChildren();
		this.startTag  = compositeTagData.getStartTag();
		this.endTag    = compositeTagData.getEndTag();
	}
	
	public Enumeration children() {
		return childTags.elements();
	}

	public String toPlainTextString() {
		StringBuffer stringRepresentation = new StringBuffer();
		HTMLNode node;
		for (Enumeration e=children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();		
			stringRepresentation.append(node.toPlainTextString());
		}
		return stringRepresentation.toString();
	}

	public void putStartTagInto(StringBuffer sb) {
		sb.append(startTag.toHTML());
	}

	protected void putChildrenInto(StringBuffer sb) {
		HTMLNode node,prevNode=startTag;
		for (Enumeration e=children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
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
		for (Enumeration e = children();e.hasMoreElements() && !found;) {
			node = (HTMLNode)e.nextElement();
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
		for (Enumeration e = children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
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
		for (Enumeration e = children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			node.collectInto(collectionVector,filter);
		}
	}

	public String getChildrenHTML() {
		StringBuffer buff = new StringBuffer();
		for (Enumeration e = children();e.hasMoreElements();) {
			HTMLNode node = (HTMLNode)e.nextElement();
			buff.append(node.toHTML());
		}
		return buff.toString();
	}
}
