package org.htmlparser.tags;

import java.util.List;

import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public class TableTag extends CompositeTag {
	private List rows;
	
	public TableTag(
		HTMLTagData tagData,
		HTMLCompositeTagData compositeTagData,
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
