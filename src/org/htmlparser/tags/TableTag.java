package org.htmlparser.tags;

import java.util.List;

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class TableTag extends CompositeTag {
	private List rows;
	
	public TableTag(
		TagData tagData,
		CompositeTagData compositeTagData,
		List rows) {
		super(tagData, compositeTagData);
		this.rows = rows;
	}
	
	public int getRowCount() {
		return rows.size();
	}
	
	public TableRow getRow(int i) {
		return (TableRow)rows.get(i);
	}

}
