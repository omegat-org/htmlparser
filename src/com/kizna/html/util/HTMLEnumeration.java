package com.kizna.html.util;

import com.kizna.html.HTMLNode;

public interface HTMLEnumeration {
	public boolean hasMoreNodes() throws HTMLParserException;
	public HTMLNode nextHTMLNode() throws HTMLParserException;
}
