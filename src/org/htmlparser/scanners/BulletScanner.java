// HTMLParser Library v1_4_20030727 - A java-based parser for HTML
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

import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;

/**
 * This scanner is created by BulletListScanner. It shares a stack to maintain the parent-child relationship
 * with BulletListScanner. The rules implemented are :<br>
 * [1] A &lt;ul&gt; can have &lt;li&gt; under it<br>
 * [2] A &lt;li&gt; can have &lt;ul&gt; under it<br>
 * [3] A &lt;li&gt; cannot have &lt;li&gt; under it<br> 
 * <p>
 * These rules are implemented easily through the shared stack. 
 */
public class BulletScanner extends CompositeTagScanner {
	private static final String [] MATCH_STRING = {"LI"};
	private final static String ENDERS [] = { "BODY", "HTML" };
	private final static String END_TAG_ENDERS [] = { "UL" };
	private Stack ulli;
	
	public BulletScanner(Stack ulli) {
		this("",ulli);
	}

	public BulletScanner(String filter, Stack ulli) {
		super(filter, MATCH_STRING, ENDERS, END_TAG_ENDERS, false);
		this.ulli = ulli;
	}

	public Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		return new Bullet(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}
	
	/**
	 * This is the logic that decides when a bullet tag can be allowed
	 */
	public boolean shouldCreateEndTagAndExit() {
		if (ulli.size()==0) return false;
		CompositeTagScanner parentScanner = (CompositeTagScanner)ulli.peek();
		if (parentScanner == this) {
			ulli.pop();
			return true;
		} else 
			return false;
	}

	public void beforeScanningStarts() {
		ulli.push(this);
	}

}
