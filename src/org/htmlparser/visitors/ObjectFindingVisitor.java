// HTMLParser Library v1_4_20030713 - A java-based parser for HTML
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

// contributed by Joshua Kerievsky

package org.htmlparser.visitors;

import org.htmlparser.Node;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;

public class ObjectFindingVisitor extends NodeVisitor {
	private Class classTypeToFind;
	private int count = 0;
	private NodeList tags;
	
	public ObjectFindingVisitor(Class classTypeToFind) {
		this(classTypeToFind,false);
	}
	
	public ObjectFindingVisitor(Class classTypeToFind,boolean recurse) {
		super(recurse);
		this.classTypeToFind = classTypeToFind;
		this.tags = new NodeList();
	}
	
	public int getCount() {
		return count;
	}

	public void visitTag(Tag tag) {
		if (tag.getClass().getName().equals(classTypeToFind.getName())) {
			count++;
			tags.add(tag);
		}
	}

	public Node[] getTags() {
		return tags.toNodeArray();
	}
}
