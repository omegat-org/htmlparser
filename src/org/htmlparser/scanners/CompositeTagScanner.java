// HTMLParser Library v1_3_20030405 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.parserHelper.CompositeTagScannerHelper;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;

public abstract class CompositeTagScanner extends TagScanner {
	protected String [] nameOfTagToMatch;
	private boolean removeScanners;
	private boolean stringNodeIgnoreMode;
	private TagScanner previousOpenScanner = null;
	private boolean allowSelfChildren;
	
	public CompositeTagScanner(String [] nameOfTagToMatch) {
		this("",nameOfTagToMatch,false,false);
	}

	public CompositeTagScanner(String filter, String [] nameOfTagToMatch) {
		this(filter,nameOfTagToMatch,true);
	}

	public CompositeTagScanner(String filter, String [] nameOfTagToMatch, boolean allowSelfChildren) {
		this(filter,nameOfTagToMatch,false,false);
		this.allowSelfChildren = allowSelfChildren;	
	}

	public CompositeTagScanner(String filter, String [] nameOfTagToMatch, boolean removeScanners, boolean stringNodeIgnoreMode) {
		super(filter);
		this.nameOfTagToMatch = nameOfTagToMatch;
		this.removeScanners = removeScanners;
		this.stringNodeIgnoreMode = stringNodeIgnoreMode;
	}
	
	public Tag scan(Tag tag, String url, NodeReader reader,String currLine) throws ParserException {
		CompositeTagScannerHelper helper = 
			new CompositeTagScannerHelper(this,tag,url,reader,currLine);
		return helper.scan();
	}

	public void beforeScanningStarts() {
	}
	
	public void childNodeEncountered(Node node) {
	}

	public abstract Tag createTag(TagData tagData, CompositeTagData compositeTagData) throws ParserException;

	public boolean isTagToBeEndedFor(String tagName) {
		return false;
	}

	public boolean isAllowSelfChildren() {
		return allowSelfChildren;
	}

}
