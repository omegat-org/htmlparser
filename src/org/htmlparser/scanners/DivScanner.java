package org.htmlparser.scanners;

import org.htmlparser.tags.Div;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class DivScanner extends CompositeTagScanner {
	private static String MATCH_STRING [] = {"DIV"};
	
	public DivScanner() {
		this("");
	}

	public DivScanner(String filter) {
		super(filter, MATCH_STRING);
	}

	protected HTMLTag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new Div(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}

}
