// HTMLParser Library v1_3_20021228 - A java-based parser for HTML
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


package org.htmlparser;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;


/**
 * A HTMLNode interface is implemented by all types of nodes (tags, string elements, etc)
 */
public abstract class HTMLNode
    implements
        Serializable
{
	/** 
	 * The beginning position of the tag in the line
	 */
	protected int nodeBegin;

	/**
	 * The ending position of the tag in the line
	 */
	protected int nodeEnd;

	/**
	 * Variable to store lineSeparator.
	 * This is setup to read <code>line.separator</code> from the System property.
	 * However it can also be changed using the mutator methods.
	 * This will be used in the toHTML() methods in all the sub-classes of HTMLNode.
	 */
    protected static String lineSeparator = System.getProperty ("line.separator", "\n");

	public HTMLNode(int nodeBegin, int nodeEnd) {
		this.nodeBegin = nodeBegin;
		this.nodeEnd = nodeEnd;
	}

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
	
	/**
	 * Return the string representation of the node as per the renderer.
	 * This allows plugging in of user-defined renderers, which can render
	 * the nodes without modifying the base parser design.
	 * <br><br>
	 * Code would now look like :
	 * <pre>
	 * HTMLNode node;
	 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
	 * 		node = (HTMLNode)e.nextElement();
	 * 		System.out.println(node.toHTML(renderer));
	 * }
	 * </pre>
	 * <br>
	 * The renderer could do just about anything, starting from taking care of
	 * uniformly saving the page to a disk, to doing special processing
	 * for nodes, such as links and images (if you are writing a crawler)
	 * <br>
	 * <br>
	 * For eg. <br>
	 * <pre>
	 * public class MyRenderer implements HTMLRenderer {
	 * 		public String render(HTMLNode node) {
	 * 			if (node instanceof HTMLLinkTag) {
	 * 				HTMLLinkTag linkTag = (HTMLLinkTag)node;
	 * 				// process linkTag contents 
	 * 				// to return modified link pointing to local disk
	 * 			} else
	 * 			if (node instanceof HTMLImageTag) {
	 * 				HTMLImageTag imageTag = (HTMLImageTag)node;
	 * 				// process imageTag contents
	 * 				// to return modified image pointing to local disk
	 * 				// Crawlers could even start threads to get the relevant
	 * 				// resource from the internet
	 * 			} else return node.toHTML();
	 * 		}
	 * }
	 * </pre>
	 */
	public String toHTML(HTMLRenderer renderer) {
		return toHTML();
	}
	
	/**
	 * Collect this node and its child nodes (if-applicable) into the collection parameter, provided the node
	 * satisfies the filtering criteria. <P/>
	 * 
	 * This mechanism allows powerful filtering code to be written very easily, without bothering about collection
	 * of embedded tags separately. e.g. when we try to get all the links on a page, it is not possible to get it
	 * at the top-level, as many tags (like form tags), can contain links embedded in them. We could get the links
	 * out by checking if the current node is a form tag, and going through its contents. However, this ties us down
	 * to specific tags, and is not a very clean approach. <P/>
	 * 
	 * Using collectInto(), programs get a lot shorter. Now, the code to extract all links from a page would look 
	 * like :
	 * <pre>
	 * Vector collectionVector = new Vector();
	 * HTMLNode node;
	 * String filter = HTMLLinkTag.LINK_TAG_FILTER;
	 * for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
	 * 		node = e.nextHTMLNode();
	 * 		node.collectInto(collectionVector,filter);
	 * }
	 * </pre>
	 * Thus, collectionVector will hold all the link nodes, irrespective of how deep the links are embedded.
	 * This of course implies that tags must fulfill their responsibilities toward honouring certain filters.
	 * 
	 * <B>Important:</B> In order to keep performance optimal, <B>do not create</B> you own filter strings, as 
	 * the internal matching occurs with the pre-existing filter string object (in the relevant class). i.e. do not
	 * make calls like : <I>collectInto(collectionVector,"-l")</I>, instead, make calls only like :
	 * <I>collectInto(collectionVector,HTMLLinkTag.LINK_TAG_FILTER)</I>.<P/>
	 * 
	 * To find out if your desired tag has filtering support, check the API of the tag.
	 */
	public abstract void collectInto(Vector collectionVector,String filter);
	
	/**
	 * Returns the beginning position of the tag.
	 */
	public int elementBegin()
	{
		return nodeBegin;
	}

	/**
	 * Returns the ending position fo the tag
	 */
	public int elementEnd()
	{
		return nodeEnd;
	}

	public abstract void accept(HTMLVisitor visitor);
	
}
