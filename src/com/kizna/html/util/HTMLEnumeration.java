package com.kizna.html.util;

import com.kizna.html.HTMLNode;

public interface HTMLEnumeration {
	public boolean hasMoreNodes();
	public HTMLNode nextHTMLNode() throws HTMLParserException;
}
