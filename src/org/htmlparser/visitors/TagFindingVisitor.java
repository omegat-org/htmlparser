// HTMLParser Library v1_3_20030504 - A java-based parser for HTML
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
// Website : http:// www.industriallogic.com

// contributed by Joshua Kerievsky

package org.htmlparser.visitors;

import org.htmlparser.Node;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;

public class TagFindingVisitor extends NodeVisitor {
	private String [] tagsToBeFound;
	private int count [];
	private int endTagCount [];
	private NodeList [] tags;
	private NodeList [] endTags;
	private boolean endTagCheck;
	
	public TagFindingVisitor(String [] tagsToBeFound) {
		this(tagsToBeFound,false);
	}

	public TagFindingVisitor(String [] tagsToBeFound, boolean endTagCheck) {
		this.tagsToBeFound = tagsToBeFound;
		this.tags = new NodeList[tagsToBeFound.length];
		if (endTagCheck) {
			endTags = new NodeList[tagsToBeFound.length];
			endTagCount = new int[tagsToBeFound.length];
		}
		for (int i=0;i<tagsToBeFound.length;i++) {
			tags[i] = new NodeList();
			if (endTagCheck)
				endTags[i] = new NodeList();
		}
		this.count = new int[tagsToBeFound.length];
		this.endTagCheck = endTagCheck;	
	}	
	
	public int getTagCount(int index) {
		return count[index];
	}

	public void visitTag(Tag tag) {
		for (int i=0;i<tagsToBeFound.length;i++)
			if (tag.getTagName().equalsIgnoreCase(tagsToBeFound[i])) {
				count[i]++;
				tags[i].add(tag);
			}
	}

	public Node [] getTags(int index) {
		return tags[index].toNodeArray();
	}

	public void visitEndTag(EndTag endTag) {
		if (!endTagCheck) return;
		for (int i=0;i<tagsToBeFound.length;i++)
			if (endTag.getTagName().equalsIgnoreCase(tagsToBeFound[i])) {
				endTagCount[i]++;
				endTags[i].add(endTag);
			}
	}
	
	public int getEndTagCount(int index) {
		return endTagCount[index];
	}
	
}
