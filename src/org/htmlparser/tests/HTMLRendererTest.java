package org.htmlparser.tests;

import java.util.Enumeration;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLRenderer;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

import junit.framework.TestCase;

public class HTMLRendererTest extends HTMLParserTestCase {

	public HTMLRendererTest(String name) {
		super(name);
	}
	
	public void testRenderer() throws HTMLParserException {
		HTMLRenderer renderer = new HTMLRenderer() {
			public String renderImagesToHTML(HTMLImageTag imageTag) {
				imageTag.setImageURL("Modified://"+imageTag.getImageURL());
				return imageTag.toHTML();
			}
			public String renderLinksToHTML(HTMLLinkTag linkTag) {
				StringBuffer sb = new StringBuffer();
				linkTag.setLink("Modified://"+linkTag.getLink());
				linkTag.putLinkStartTagInto(sb);
				sb.append(linkTag.getLinkContentsAndEndTagWith(this));
				return sb.toString();
			}

		};
		createParser("<HTML><BODY><A HREF=\"mylink.html\"><IMG SRC=\"mypic.jpg\"></A><IMG SRC=\"mysecondimage.gif\"></BODY></HTML>");
		parser.registerScanners();

		// Verify the modification
		StringBuffer html = new StringBuffer();
		HTMLNode node;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node = e.nextHTMLNode();
			html.append(node.toHTML(renderer));
		}
		String expectedHTML = 
		"<HTML><BODY><A HREF=\"Modified://mylink.html\"><IMG SRC=\"Modified://mypic.jpg\"></A><IMG SRC=\"Modified://mysecondimage.gif\"></BODY></HTML>";
		assertStringEquals("Expected HTML",expectedHTML,html.toString());
	}
}
