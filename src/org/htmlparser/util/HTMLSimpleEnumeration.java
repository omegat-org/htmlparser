package org.htmlparser.util;

import org.htmlparser.HTMLNode;

public interface HTMLSimpleEnumeration {
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
	public HTMLNode nextHTMLNode();
}
