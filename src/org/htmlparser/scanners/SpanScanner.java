package org.htmlparser.scanners;

import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class SpanScanner extends CompositeTagScanner {
	private static final String [] MATCH_ID = {"SPAN"};
	
	public SpanScanner() {
		this("");
	}

	public SpanScanner(String filter) {
		super(filter, MATCH_ID);
	}

	protected HTMLTag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new Span(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_ID;
	}

}
