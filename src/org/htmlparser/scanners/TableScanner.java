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

package org.htmlparser.scanners;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

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
		TagData tagData,
		CompositeTagData compositeTagData) {
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