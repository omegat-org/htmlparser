package org.htmlparser.scanners;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public class TableScanner extends CompositeTagScanner {
	private final static String MATCH_STRING [] = { "TABLE" };
	private List rows;
	
	public TableScanner(HTMLParser parser) {
		this(parser,"");
	}

	public TableScanner(HTMLParser parser,String filter) {
		super(filter, MATCH_STRING);
		parser.addScanner(new TableRowScanner(parser));
	}

	protected HTMLTag createTag(
		HTMLTagData tagData,
		HTMLCompositeTagData compositeTagData) {
		return new TableTag(tagData,compositeTagData,rows);
	}

	public String[] getID() {
		return MATCH_STRING;
	}

	protected void beforeScanningStarts() {
		rows = new ArrayList();
	}

	protected void childNodeEncountered(HTMLNode node) {
		if (node instanceof TableRow) 
			rows.add(node);
	}
}
