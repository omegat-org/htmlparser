package org.htmlparser.scanners;

import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.LabelTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class LabelScanner extends CompositeTagScanner {
	private static final String MATCH_NAME [] = {"LABEL"};

	public LabelScanner(String filter) {
		super(filter,MATCH_NAME);
	}

	public String [] getID() {
		return MATCH_NAME;
	}
	
	protected HTMLTag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new LabelTag(tagData,compositeTagData);
	}
}
