package com.kizna.html.tags;

/**
 * A Meta Tag
 */
public class HTMLMetaTag extends HTMLTag {
	private String metaTagName;
	private String metaTagContents;
	private String httpEquiv;
	public HTMLMetaTag(int tagBegin, int tagEnd, String tagContents, String httpEquiv, String metaTagName, String metaTagContents,String tagLine) {
		super(tagBegin,tagEnd,tagContents,tagLine);
		this.httpEquiv = httpEquiv;
		this.metaTagName = metaTagName;
		this.metaTagContents = metaTagContents;
	}
	public String getHttpEquiv() {
		return httpEquiv;
	}
	public String getMetaTagContents() {
		return metaTagContents;
	}
	public String getMetaTagName() {
		return metaTagName;
	}
	public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}
	public void setMetaTagContents(String metaTagContents) {
		this.metaTagContents = metaTagContents;
	}
	public void setMetaTagName(String metaTagName) {
		this.metaTagName = metaTagName;
	}
	public String toString() {
		return "META TAG\n"+
				"--------\n"+
				"Http-Equiv : "+getHttpEquiv()+"\n"+
				"Name : "+metaTagName+"\n"+
				"Contents : "+metaTagContents+"\n";	
	}
}
