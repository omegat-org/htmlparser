// HTMLParser Library v1_4_20030622 - A java-based parser for HTML
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

import org.htmlparser.RemarkNode;
import org.htmlparser.StringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TitleTag;

public abstract class NodeVisitor {
	private boolean recurseChildren;
	private boolean recurseSelf;
		
	public NodeVisitor() {
		this(true);	
	}
	
	public NodeVisitor(boolean recurseChildren) {
		this.recurseChildren = recurseChildren;
		this.recurseSelf = true;	
	}
	
	public NodeVisitor(boolean recurseChildren,boolean recurseSelf) {
		this.recurseChildren = recurseChildren;
		this.recurseSelf = recurseSelf;	
	}

	public void visitTag(Tag tag) {
		
	}

	public void visitStringNode(StringNode stringNode) {
	}
	
	public void visitLinkTag(LinkTag linkTag) {
	}
	
	public void visitImageTag(ImageTag imageTag) {
	}
	
	public void visitEndTag(EndTag endTag) {
		
	}
	
	public void visitTitleTag(TitleTag titleTag) {
		
	}
	public void visitRemarkNode(RemarkNode remarkNode) {
		
	}
	
	public boolean shouldRecurseChildren() {
		return recurseChildren;
	}
	
	public boolean shouldRecurseSelf() {
		return recurseSelf;
	}

	/**
	 * Override this method if you wish to do special
	 * processing upon completion of parsing 
	 */
	public void finishedParsing() {
	}
}
