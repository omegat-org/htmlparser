package org.htmlparser.scanners;

import org.htmlparser.Parser;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;


public class BulletListScanner extends CompositeTagScanner {
	private static final String [] MATCH_STRING = {"UL"};
	private final static String ENDERS [] = { "BODY", "HTML" };
	
	public BulletListScanner(Parser parser) {
		this("",parser);
	}

	public BulletListScanner(String filter, Parser parser) {
		super(filter, MATCH_STRING, ENDERS);
		parser.addScanner(new BulletScanner("-bullet"));
	}

	public Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		return new BulletList(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}
}
