package com.kizna.html.parserapplications;

import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;

public class StringExtractor {
	private String resource;
	public StringExtractor(String resource) {
		this.resource = resource;
	}
	public String extractStrings() {
		HTMLParser parser = new HTMLParser(resource);
		HTMLNode node;
		StringBuffer results= new StringBuffer();
		for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			//System.out.print(node.toPlainTextString());
			results.append(node.toPlainTextString());
		}
		return results.toString();
	}
	public static void main(String[] args) {
		System.out.println("Running..");
		StringExtractor se = new StringExtractor(args[0]);	
		System.out.println("Strings=  "+se.extractStrings());
	}
}
