// HTMLParser Library v1_3_20030427 - A java-based parser for HTML
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

public class LinkData {
	private String link;
	private String linkText;
	private String accessKey;
	private boolean mailLink;
	private boolean javascriptLink;
	
	public LinkData(String link,String linkText,String accessKey,boolean mailLink,
	boolean javascriptLink) {
		this.link = link;
		this.linkText = linkText;
		this.accessKey = accessKey;
		this.mailLink = mailLink;
		this.javascriptLink = javascriptLink;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public boolean isJavascriptLink() {
		return javascriptLink;
	}

	public String getLink() {
		return link;
	}

	public String getLinkText() {
		return linkText;
	}

	public boolean isMailLink() {
		return mailLink;
	}

	public String toString() {
		return "";
	}
}
