package org.htmlparser.scanners;

import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;


public class BulletScanner extends CompositeTagScanner {
	private static final String [] MATCH_STRING = {"LI"};
	
	public BulletScanner() {
		super(MATCH_STRING);
	}

	public BulletScanner(String filter) {
		super(filter, MATCH_STRING);
	}

	protected Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		return new Bullet(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}

}