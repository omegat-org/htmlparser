// HTMLParser Library v1_2_20021208 - A java-based parser for HTML
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

import org.htmlparser.*;
import java.util.Enumeration;
import java.util.Vector;
/**
 * Identifies a link tag 
 */
public class HTMLLinkTag extends HTMLTag
{
	public static final String LINK_TAG_FILTER="-l";
	/**
	 * The URL where the link points to
	 */	
	protected String link;
	/**
	 * The text of of the link element
	 */
	protected String linkText;
	/**
	* The contents of link element after the tag
	* added by Kaarle Kaila 23.10.2001
	*/
	protected String linkContents;
	/**
	 * The accesskey existing inside this link.
	 */
	protected String accessKey;
	private java.util.Vector nodeVector;
	private boolean mailLink;
	private boolean javascriptLink;

	
	/**
	 * Constructor creates an HTMLLinkNode object, which basically stores the location
	 * where the link points to, and the text it contains.
	 * <p>
	 * In order to get the contents of the link tag, use the method linkData(), 
	 * which returns an enumeration of nodes encapsulated within the link.
	 * <p>
	 * The following code will get all the images inside a link tag.
	 * <pre>
	 * HTMLNode node ;
	 * HTMLImageTag imageTag;
	 * for (Enumeration e=linkTag.linkData();e.hasMoreElements();) {
	 * 		node = (HTMLNode)e.nextElement();
	 * 		if (node instanceof HTMLImageTag) {
	 * 			imageTag = (HTMLImageTag)node;
	 * 			// Process imageTag
	 * 		}
	 * }
	 * </pre>
	 * There is another mechanism available that allows for uniform extraction of images. You could do this to
	 * get all images from a web page : 
	 * <pre>
	 * HTMLNode node;
	 * Vector imageCollectionVector = new Vector();
	 * for (HTMLEnumeration e = parser.elements();e.hasMoreNode();) {
	 * 		node = e.nextHTMLNode();
	 * 		node.collectInto(imageCollectionVector,HTMLImageTag.IMAGE_FILTER);
	 * }
	 * </pre>
	 * The link tag processes all its contents in collectInto().
	 * @link The URL to which the link points to
	 * @linkText The text which is stored inside this link tag
	 * @linkBegin The beginning position of the link tag
	 * @linkEnd The ending position of the link tag
	 * @param linkContents contains the data from the link element
	 * @accessKey The accessKey element of the link tag (valid for Compact HTML - IMODE devices)
	 * @see linkData()
	 */
	public HTMLLinkTag(String link,String linkText,int linkBegin, int linkEnd, String accessKey,String currentLine,Vector nodeVector,boolean mailLink,boolean javascriptLink,String tagContents,String linkContents)
	{
		super(linkBegin,linkEnd,tagContents,currentLine);  // Kaarle Kaila 23.10.2001
		this.link = link;
		this.linkText = linkText;
		this.accessKey = accessKey;
		this.nodeVector = nodeVector;
		this.mailLink = mailLink;
		this.linkContents = linkContents;  // Kaarle Kaila 23.10.2001

		this.javascriptLink = javascriptLink;

	}
	/**
	 * Returns the accesskey element if any inside this link tag
   */
	public String getAccessKey()
	{
		return accessKey;
	}
	/**
	 * Returns the url as a string, to which this link points
	 */
	public String getLink()
	{
		return link;
	}
	/**
	 * Returns the text contained inside this link tag
	 */
	public String getLinkText()
	{
		return linkText;
	}
	/**
	 * Return the text contained in this linkinode
	 *  Kaarle Kaila 23.10.2001
	 */
	public String getText()
	{
		return tagContents + ">" + linkContents;
	}
	/**
	 * Is this a mail address
	 * @return boolean true/false
	 */
	public boolean isMailLink() {
		return mailLink;
	}

    /**
	 * Tests if the link is javascript
	 * @return flag indicating if the link is a javascript code
	 */
        public boolean isJavascriptLink() {
		return javascriptLink;
	}

    /**
	 * Tests if the link is an FTP link.
	 *
	 * @return flag indicating if this link is an FTP link
	 */
    public boolean isFTPLink() {
		return link.indexOf("ftp://")==0;
	}

    /**
	 * Tests if the link is an HTTP link.
	 *
	 * @return flag indicating if this link is an HTTP link
	 */
    public boolean isHTTPLink() {
    	if (!isFTPLink() && !isHTTPSLink() && !isJavascriptLink()) return true;
    	else return false;
	}

	/**
	 * Tests if the link is an HTTPS link.
	 *
	 * @return flag indicating if this link is an HTTPS link
	 */
        public boolean isHTTPSLink() {
                return link.indexOf("https://")==0;
        }

        /**
	 * Tests if the link is an HTTP link or one of its variations (HTTPS, etc.).
	 * 
	 * @return flag indicating if this link is an HTTP link or one of its variations (HTTPS, etc.)
	 */
        public boolean isHTTPLikeLink() {
                return isHTTPLink() || isHTTPSLink();
        }

	/**
	 * Returns an enumeration of nodes contained within the link tag.
	 */
	public Enumeration linkData() 
	{
		return nodeVector.elements();	
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (8/3/2001 1:49:31 AM)
	 * @param newMailLink boolean
	 */
	public void setMailLink(boolean newMailLink) {
		mailLink = newMailLink;
	}

	/**
	 * Set the link as a javascript link.
	 * 
	 * @param newJavascriptLink flag indicating if the link is a javascript code
	 */
	public void setJavascriptLink(boolean newJavascriptLink) {
		javascriptLink = newJavascriptLink;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (7/1/2001 4:39:41 PM)
	 * @param newNodeVector java.util.Vector
	 */
	public void setNodeVector(java.util.Vector newNodeVector) {
		nodeVector = newNodeVector;
	}
	public String toPlainTextString() {
		StringBuffer sb = new StringBuffer();
		HTMLNode node;
		for (Enumeration e=linkData();e.hasMoreElements();)
		{
			node = (HTMLNode)e.nextElement();
			sb.append(node.toPlainTextString());
		}
		return sb.toString();
	}
	public String toHTML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(tagContents.toString());
		sb.append(">");
		HTMLNode node;
		for (Enumeration e = linkData();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			sb.append(node.toHTML());
		}
		sb.append("</A>");
		return sb.toString();
	}
	/**
	 * Print the contents of this Link Node
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Link to : "+link + "; titled : "+linkText+"; begins at : "+elementBegin()+"; ends at : "+elementEnd()+ ", AccessKey=");
		if (accessKey==null) sb.append("null\n");
		else sb.append(accessKey+"\n");
		if (linkData()!=null) 
		{
			sb.append("  "+"LinkData\n");
			sb.append("  "+"--------\n");
			
			HTMLNode node;
			int i = 0;
			for (Enumeration e=linkData();e.hasMoreElements();)
			{
				node = (HTMLNode)e.nextElement();
				sb.append("   "+(i++)+ " ");
				sb.append(node.toString()+"\n");
			}
		}
		sb.append("  "+"*** END of LinkData ***\n");
		return sb.toString();
	}
	public String toHTML(HTMLRenderer renderer) {
		return renderer.renderLinksToHTML(this);
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void collectInto(Vector collectionVector, String filter) {
		if (filter==LINK_TAG_FILTER) collectionVector.add(this); else {
			HTMLNode node;
			for (Enumeration e=nodeVector.elements();e.hasMoreElements();) {
				node = (HTMLNode)e.nextElement();
				node.collectInto(collectionVector,filter);
			}
		}
		
	}

}
