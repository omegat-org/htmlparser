package org.htmlparser.tags;

import java.util.List;

import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;

public class TableRow extends CompositeTag {
	private List columnsList;
	public TableRow(
		HTMLTagData tagData,
		HTMLCompositeTagData compositeTagData,
		List columnsList) {
		super(tagData, compositeTagData);
		this.columnsList = columnsList;
	}
	
	public int getColumnCount() {
		return columnsList.size();
	}

	public TableColumn [] getColumns() {
		TableColumn [] columns = new TableColumn[columnsList.size()];
		for (int i=0;i<columnsList.size();i++) {
			columns[i] = (TableColumn)columnsList.get(i);
		}
		return columns;
	}
}
