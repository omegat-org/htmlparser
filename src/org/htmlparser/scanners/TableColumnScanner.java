package org.htmlparser.scanners;

import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public class TableColumnScanner extends CompositeTagScanner {
	private final static String MATCH_STRING [] = { "TD" };
	
	public TableColumnScanner() {
		this("");
	}

	public TableColumnScanner(String filter) {
		super(filter,MATCH_STRING);
	}

	public TableColumnScanner(
		String filter,
		String[] nameOfTagToMatch,
		boolean removeScanners,
		boolean stringNodeIgnoreMode) {
		super(filter, nameOfTagToMatch, removeScanners, stringNodeIgnoreMode);
	}

	protected HTMLTag createTag(
		HTMLTagData tagData,
		HTMLCompositeTagData compositeTagData) {
		return new TableColumn(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}

}
