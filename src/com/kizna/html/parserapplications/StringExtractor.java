package com.kizna.html.parserapplications;

import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;

public class StringExtractor {
	private String resource;
	public StringExtractor(String resource) {
		this.resource = resource;
	}
	public String extractStrings() throws HTMLParserException {
		HTMLParser parser = new HTMLParser(resource);
		HTMLNode node;
		StringBuffer results= new StringBuffer();
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node = e.nextHTMLNode();
			//System.out.print(node.toPlainTextString());
			results.append(node.toPlainTextString());
		}
		return results.toString();
	}
	public static void main(String[] args) {
		System.out.println("Running..");
		StringExtractor se = new StringExtractor(args[0]);	
		try {
			System.out.println("Strings=  "+se.extractStrings());
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
			
	}
}
