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

import org.htmlparser.StringNode;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.Translate;


/**
 * Extracts text from a web page.
 * Usage:
 * <code>
 * HTMLParser parser = new HTMLParser(...);
 * TextExtractingVisitor visitor = new TextExtractingVisitor();
 * parser.visitAllNodesWith(visitor);
 * String textInPage = visitor.getExtractedText();
 * </code>
 */
public class TextExtractingVisitor extends NodeVisitor {
	private StringBuffer textAccumulator;
	private boolean preTagBeingProcessed;
	
	public TextExtractingVisitor() {
		textAccumulator = new StringBuffer();
		preTagBeingProcessed = false;
	}

	public String getExtractedText() {
		return textAccumulator.toString();
	}

	public void visitStringNode(StringNode stringNode) {
		String text = stringNode.getText();
		if (!preTagBeingProcessed) {
			text = Translate.decode(text); 
			text = replaceNonBreakingSpaceWithOrdinarySpace(text);
		}
		textAccumulator.append(text);
	}

	private String replaceNonBreakingSpaceWithOrdinarySpace(String text) {
		return text.replace('\u00a0',' ');
	}

	public void visitEndTag(EndTag endTag) {
		if (isPreTag(endTag)) 
			preTagBeingProcessed = false;
	}

	public void visitTag(Tag tag) {
		if (isPreTag(tag)) 
			preTagBeingProcessed = true;
	}

	private boolean isPreTag(Tag tag) {
		return tag.getTagName().equals("PRE");
	}

}
