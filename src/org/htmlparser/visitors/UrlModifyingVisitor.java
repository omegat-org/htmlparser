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
//
// This class was contributed by Joshua Kerievsky

package org.htmlparser.visitors;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.visitors.HTMLVisitor;

public class UrlModifyingVisitor extends HTMLVisitor {
	private String linkPrefix;
	private StringBuffer modifiedResult;
	private HTMLParser parser;
	
	public UrlModifyingVisitor(HTMLParser parser, String linkPrefix) {
		this.parser = parser;
		HTMLLinkScanner linkScanner = new HTMLLinkScanner();  
		parser.addScanner(linkScanner);
		parser.addScanner(
			linkScanner.createImageScanner(
				HTMLImageTag.IMAGE_TAG_FILTER
			)
		);
		this.linkPrefix =linkPrefix; 
		modifiedResult = new StringBuffer();
	}
	
	public void visitLinkTag(HTMLLinkTag linkTag) {
		linkTag.setLink(linkPrefix + linkTag.getLink());
	}

	public void visitImageTag(HTMLImageTag imageTag) {
		imageTag.setImageURL(linkPrefix + imageTag.getImageURL());
		modifiedResult.append(imageTag.toHTML());
	}
	
	public void visitEndTag(HTMLEndTag endTag) {
		modifiedResult.append(endTag.toHTML());
	}

	public void visitStringNode(HTMLStringNode stringNode) {
		modifiedResult.append(stringNode.toHTML());
	}

	public void visitTag(HTMLTag tag) {
		modifiedResult.append(tag.toHTML());
	}
	
	public String getModifiedResult() {
		return modifiedResult.toString();		
	}
}
