package org.htmlparser.tags;

import java.util.List;

import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public class TableRow extends CompositeTag {
	private List columns;
	public TableRow(
		HTMLTagData tagData,
		HTMLCompositeTagData compositeTagData,
		List columns) {
		super(tagData, compositeTagData);
		this.columns = columns;
	}
	
	public int getColumnCount() {
		return columns.size();
	}

}
