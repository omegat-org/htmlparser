// HTMLParser Library v1_4_20030622 - A java-based parser for HTML
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

import java.util.Stack;

import org.htmlparser.Parser;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;


public class BulletListScanner extends CompositeTagScanner {
	private static final String [] MATCH_STRING = { "UL", "OL" };
	private final static String ENDERS [] = { "BODY", "HTML" };
	private Stack ulli = new Stack();
	
	public BulletListScanner(Parser parser) {
		this("",parser);
	}

	public BulletListScanner(String filter, Parser parser) {
		super(filter, MATCH_STRING, ENDERS);
		parser.addScanner(new BulletScanner("-bullet",ulli));
	}

	public Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		return new BulletList(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}
	
	public void beforeScanningStarts() {
		ulli.push(this);
	}

}
