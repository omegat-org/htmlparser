package org.htmlparser.tags.data;

public class HTMLLinkTagData {
	private String link;
	private String linkText;
	private String accessKey;
	private boolean mailLink;
	private boolean javascriptLink;
	private String linkContents;
	
	public HTMLLinkTagData(String link,String linkText,String accessKey,boolean mailLink,
	boolean javascriptLink, String linkContents) {
		this.link = link;
		this.linkText = linkText;
		this.accessKey = accessKey;
		this.mailLink = mailLink;
		this.javascriptLink = javascriptLink;
		this.linkContents = linkContents;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public boolean isJavascriptLink() {
		return javascriptLink;
	}

	public String getLink() {
		return link;
	}

	public String getLinkContents() {
		return linkContents;
	}

	public String getLinkText() {
		return linkText;
	}

	public boolean isMailLink() {
		return mailLink;
	}

}
