// HTMLParser Library v1_3_20030202 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//  
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
// 
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
//  
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.tags;

import java.util.List;

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class TableRow extends CompositeTag {
	private List columnsList;
	public TableRow(
		TagData tagData,
		CompositeTagData compositeTagData,
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