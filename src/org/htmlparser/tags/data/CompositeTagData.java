// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

package org.htmlparser.tags.data;

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;

public class CompositeTagData {
	private Tag startTag;
	private Tag endTag;
	private NodeList children;
	
	public CompositeTagData(
		Tag startTag, Tag endTag, Vector children) {
		this.startTag = startTag;
		this.endTag   = endTag;
		this.children = new NodeList();
		if (children!=null)
		for (Enumeration e = children.elements();e.hasMoreElements();) {
			this.children.add((Node)e.nextElement());
		}
	}

	public NodeList getChildren() {
		return children;
	}

	public Tag getEndTag() {
		return endTag;
	}

	public Tag getStartTag() {
		return startTag;
	}

}
