package org.htmlparser.util;

import org.htmlparser.HTMLNode;

public interface HTMLPeekingEnumeration extends HTMLEnumeration{
	/**
	 * Fetch a node without consuming it.
	 * Subsequent calls to <code>peek()</code> will return subsequent nodes.
	 * The node returned by <code>peek()</code> will never be a node already
	 * consumed by <code>nextHTMLNode()</code>.<p>
	 * For example, say there are nodes &lt;H1&gt;&lt;H2&gt;&lt;H3&gt;&lt;H4&gt;&lt;H5&gt;,
	 * this is the nodes that would be returned for the indicated calls:
	 * <pre>
	 * peek()         H1
	 * peek()         H2
	 * nextHTMLNode() H1
	 * peek()         H3
	 * nextHTMLNode() H2
	 * nextHTMLNode() H3
	 * nextHTMLNode() H4
	 * peek()         H5
	 * </pre>
	 * @return The next node that would be returned by <code>nextHTMLNode()</code>
	 * or the node after the last node returned by <code>peek()</code>, whichever
	 * is later in the stream. or null if there are no more nodes available via
	 * the above rules.
	 */
	public HTMLNode peek () throws HTMLParserException;
}
