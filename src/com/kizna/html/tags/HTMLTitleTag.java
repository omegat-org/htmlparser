/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.kizna.html.tags;

/**
 * A Title Tag
 */
public class HTMLTitleTag extends HTMLTag {
	private String title;
	/**
	 * Constructor for HTMLTitleTag.
	 * @param tagBegin
	 * @param tagEnd
	 * @param tagContents
	 * @param tagLine
	 */
	public HTMLTitleTag(int tagBegin,int tagEnd,String title,String tagContents,String tagLine) {
		super(tagBegin, tagEnd, tagContents, tagLine);
		this.title = title;
	}

	/**
	 * Gets the title.
	 * @return Returns a String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	public String toString() {
		return "TITLE : "+title;
	}
}
