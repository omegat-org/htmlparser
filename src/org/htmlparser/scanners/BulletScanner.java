package org.htmlparser.scanners;

import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;


public class BulletScanner extends CompositeTagScanner {
	private static final String [] MATCH_STRING = {"LI"};
	private final static String ENDERS [] = { "BODY", "HTML" };
	private final static String END_TAG_ENDERS [] = { "UL" };
	
	public BulletScanner() {
		this("");
	}

	public BulletScanner(String filter) {
		super(filter, MATCH_STRING, ENDERS, END_TAG_ENDERS, false);
	}

	public Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		return new Bullet(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}
}
