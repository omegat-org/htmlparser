package com.kizna.html;

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
	 * Print the contents of the html node
	 */
	public final void print() {
		System.out.println(toString());
	}
/**
 * Insert the method's description here.
 * Creation date: (5/6/2002 2:21:01 PM)
 * @return java.lang.String
 */
public abstract String toString();
}
