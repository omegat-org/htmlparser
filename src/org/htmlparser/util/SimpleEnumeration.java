// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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

package org.htmlparser.util;

import org.htmlparser.HTMLNode;

/**
 * The HTMLSimpleEnumeration interface is similar to HTMLEnumeration,
 * except that it does not throw exceptions. This interface is useful
 * when using HTMLVector, to enumerate through its elements in a simple
 * manner, without needing to do class casts for HTMLNode.
 * @author Somik Raha
 */
public interface SimpleEnumeration {
	/**
	 * Check if more nodes are available.
	 * @return <code>true</code> if a call to <code>nextHTMLNode()</code> will
	 * succeed.
	 */
   	public boolean hasMoreNodes();

	/**
	 * Get the next node.
	 * @return The next node in the HTML stream, or null if there are no more
	 * nodes. 
	 */
	public HTMLNode nextNode();
}
