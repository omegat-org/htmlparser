package org.htmlparser.tags.data;

public class TagData {
	private int tagBegin;
	private int tagEnd;
	private String tagContents;
	private String tagLine;
	
	public TagData(int tagBegin, int tagEnd, String tagContents,String tagLine) {
		this.tagBegin = tagBegin;
		this.tagEnd   = tagEnd;
		this.tagContents = tagContents;
		this.tagLine = tagLine;	
	}
	public int getTagBegin() {
		return tagBegin;
	}

	public String getTagContents() {
		return tagContents;
	}

	public int getTagEnd() {
		return tagEnd;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagContents(String tagContents) {
		this.tagContents = tagContents;
	}

}
