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

package org.htmlparser.tags.data;

public class TagData {
	private int tagBegin;
	private int tagEnd;
	private int startLine;
	private int endLine;
	private String tagContents;
	private String tagLine;
	private String urlBeingParsed;
	private boolean isXmlEndTag;
	
	public TagData(int tagBegin, int tagEnd, String tagContents,String tagLine) {
		this(tagBegin, tagEnd, 0, 0, tagContents, tagLine, "", false);
	}
	
	public TagData(int tagBegin, int tagEnd, String tagContents,String tagLine, String urlBeingParsed) {
		this(tagBegin, tagEnd, 0, 0, tagContents, tagLine, urlBeingParsed, false);
	}
	
	public TagData(int tagBegin, int tagEnd, int startLine, int endLine, String tagContents,String tagLine, String urlBeingParsed, boolean isXmlEndTag) {
		this.tagBegin = tagBegin;
		this.tagEnd   = tagEnd;
		this.startLine = startLine;
		this.endLine = endLine;
		this.tagContents = tagContents;
		this.tagLine = tagLine;	
		this.urlBeingParsed = urlBeingParsed;
		this.isXmlEndTag = isXmlEndTag;
	}
	
	public int getTagBegin() {
		return tagBegin;
	}

	public String getTagContents() {
		return tagContents;
	}

	public int getTagEnd() {
		return tagEnd;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagContents(String tagContents) {
		this.tagContents = tagContents;
	}

	public String getUrlBeingParsed() {
		return urlBeingParsed;
	}

	public void setUrlBeingParsed(String baseUrl) {
		this.urlBeingParsed = baseUrl;
	}
	
	public boolean isEmptyXmlTag() {
		return isXmlEndTag;
	}

	/**
	 * Returns the line number where the tag starts in the HTML. At the moment this
	 * will only be valid for tags created with the
	 * <code>CompositeTagScanner</code> or a subclass of it.
	 */ 
	public int getStartLine() {
		return startLine;
	}

	/**
	 * Returns the line number where the tag ends in the HTML. At the moment this
	 * will only be valid for tags created with the
	 * <code>CompositeTagScanner</code> or a subclass of it.
	 */ 
	public int getEndLine() {
		return endLine;
	}
	
}
