// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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
/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.tags.HTMLStyleTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLTagData;
/**
 * The HTMLStyleScanner scans identifies &lt;style&gt; code
 */

public class HTMLStyleScanner extends CompositeTagScanner {
	private static final String MATCH_NAME [] = {"STYLE"};

	public HTMLStyleScanner() {
		super(MATCH_NAME);
	}

	public HTMLStyleScanner(String filter) {
		super(filter,MATCH_NAME);
	}

	public String [] getID() {
		return MATCH_NAME;
	}
	
	protected HTMLTag createTag(
		HTMLTagData tagData,
		HTMLCompositeTagData compositeTagData) {
		return new HTMLStyleTag(tagData,compositeTagData);
	}

}
