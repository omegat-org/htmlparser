// HTMLParser Library v1_2_20021016 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.html;

import java.io.PrintWriter;
/**
 * A HTMLNode interface is implemented by all types of nodes (tags, string elements, etc)
 */
public abstract class HTMLNode
{
	/**
	 * Variable to store lineSeparator.<br>
	 * This is setup to read line.separator from the System property.<br>
	 * However it can also be changed using the mutator methods.
	 * THis will be used in the toHTML() methods in all the sub-classes of HTMLNode.
	 */
	protected static String lineSeparator = 
			(String)java.security.AccessController.doPrivileged(
						new sun.security.action.GetPropertyAction("line.separator"));
	
	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static void setLineSeparator(String lineSeparator)
	{
		HTMLNode.lineSeparator = lineSeparator;	
	}
	
	/**
	 * @return String lineSeparator that will be used in toHTML()
	 */
	public static String getLineSeparator()
	{
		return HTMLNode.lineSeparator;
	}

	/**
	 * Return the beginning position of the element
	 */
	public abstract int elementBegin();
	/**
	 * Return the ending position of the element
	 */
	public abstract int elementEnd();
	/**
	 * Print the contents of the html node. This method cannot be overridden. It makes a call to the node's
	 * toString() method.
	 */
	public final void print() {
		System.out.println(toString());
	}
	/**
	 * Prints the contents of the node (by a call to toString()) to the print writer provided.
	 * This method cannot be overridden.
	 */
	public final void print(PrintWriter pw) {
		pw.println(toString());
	} 
	/**
	 * Returns a string representation of the node. This is an important method, it allows a simple string transformation
	 * of a web page, regardless of a node.<br>
	 * Typical application code (for extracting only the text from a web page) would then be simplified to  :<br>
	 * <pre>
	 * HTMLNode node;
	 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
	 *    node = (HTMLNode)e.nextElement();
	 *    System.out.println(node.toPlainTextString()); // Or do whatever processing you wish with the plain text string
	 * }
	 * </pre>
	 */
	public abstract String toPlainTextString();
	/**
	 * This method will make it easier when using html parser to reproduce html pages (with or without modifications)
	 * Applications reproducing html can use this method on nodes which are to be used or transferred as they were 
	 * recieved, with the original html
	 */
	public abstract String toHTML();
/**
 * Return the string representation of the node.
 * Subclasses must define this method, and this is typically to be used in the manner<br>
 * <pre>System.out.println(node)</pre>
 * @return java.lang.String
 */
public abstract String toString();
}
