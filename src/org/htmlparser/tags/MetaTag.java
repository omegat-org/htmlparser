// HTMLParser Library v1_3_20030511 - A java-based parser for HTML
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

import org.htmlparser.tags.data.TagData;

/**
 * A Meta Tag
 */
public class MetaTag extends Tag {
	private String metaTagName;
	private String metaTagContents;
	private String httpEquiv;
	public MetaTag(TagData tagData, String httpEquiv, String metaTagName,String metaTagContents) {
		super(tagData);
		this.httpEquiv = httpEquiv;
		this.metaTagName = metaTagName;
		this.metaTagContents = metaTagContents;
	}
	public String getHttpEquiv() {
		return httpEquiv;
	}
	public String getMetaContent() {
		return metaTagContents;
	}
	public String getMetaTagName() {
		return metaTagName;
	}
	public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}
	public void setMetaTagContents(String metaTagContents) {
		this.metaTagContents = metaTagContents;
	}
	public void setMetaTagName(String metaTagName) {
		this.metaTagName = metaTagName;
	}
	public String toString() {
		return "META TAG\n"+
				"--------\n"+
				"Http-Equiv : "+getHttpEquiv()+"\n"+
				"Name : "+metaTagName+"\n"+
				"Contents : "+metaTagContents+"\n";	
	}
}
