package org.htmlparser.scanners;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class TableRowScanner extends CompositeTagScanner {
	private final static String MATCH_STRING [] = { "TR" };
	private List columns;
	
	public TableRowScanner(HTMLParser parser) {
		this("",parser);
	}

	public TableRowScanner(String filter,HTMLParser parser) {
		super(filter, MATCH_STRING);
		parser.addScanner(new TableColumnScanner());
	}

	public TableRowScanner(
		String filter,
		String[] nameOfTagToMatch,
		boolean removeScanners,
		boolean stringNodeIgnoreMode) {
		super(filter, nameOfTagToMatch, removeScanners, stringNodeIgnoreMode);
	}

	protected HTMLTag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new TableRow(tagData,compositeTagData,columns);
	}

	public String[] getID() {
		return MATCH_STRING;
	}

	protected void beforeScanningStarts() {
		columns = new ArrayList();
	}

	protected void childNodeEncountered(HTMLNode node) {
		if (node instanceof TableColumn)
			columns.add(node);
	}

}
