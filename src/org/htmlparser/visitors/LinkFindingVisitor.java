package org.htmlparser.visitors;

import org.htmlparser.tags.HTMLLinkTag;

public class LinkFindingVisitor extends HTMLVisitor {
	private String linkTextToFind;
	private boolean linkTagFound = false;
	private int count = 0;
	
	public LinkFindingVisitor(String linkTextToFind) {
		this.linkTextToFind = linkTextToFind.toUpperCase();
	}

	public void visitLinkTag(HTMLLinkTag linkTag) {
		System.out.println("Matching with "+linkTag.getLinkText());
		if (linkTag.getLinkText().toUpperCase().indexOf(linkTextToFind)!=-1) {
			linkTagFound = true;
			count++;
		}
	}
	
	public boolean linkTextFound() {
		return linkTagFound;
	}
	
	public int getCount() {
		return count;
	}

}
