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

package org.htmlparser.visitors;

import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;

public abstract class NodeVisitor {
	private boolean recurseChildren;
	
	public NodeVisitor() {
		this(true);	
	}
	
	public NodeVisitor(boolean recurseChildren) {
		this.recurseChildren = recurseChildren;	
	}
	
	public void visitTag(Tag tag) {
		
	}

	public void visitStringNode(HTMLStringNode stringNode) {
	}
	
	public void visitLinkTag(LinkTag linkTag) {
	}
	
	public void visitImageTag(ImageTag imageTag) {
	}
	
	public void visitEndTag(EndTag endTag) {
		
	}
	
	public void visitRemarkNode(HTMLRemarkNode remarkNode) {
		
	}
	
	public boolean shouldRecurseChildren() {
		return recurseChildren;
	}

}
