package com.kizna.html;

import java.io.PrintWriter;
/**
 * A HTMLNode interface is implemented by all types of nodes (tags, string elements, etc)
 */
public abstract class HTMLNode
{
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
 * Return the string representation of the node.
 * Subclasses must define this method, and this is typically to be used in the manner<br>
 * <pre>System.out.println(node)</pre>
 * @return java.lang.String
 */
public abstract String toString();
}
