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


//////////////////
// Java Imports //
//////////////////
import org.htmlparser.tags.FrameSetTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

/**
 * Scans for the Frame Tag. This is a subclass of TagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class FrameSetScanner extends CompositeTagScanner
{
	private static final String MATCH_NAME [] = {"FRAMESET"};
	
	public FrameSetScanner()
	{
		super(MATCH_NAME);
	}

	public FrameSetScanner(String filter)
	{
		super(filter,MATCH_NAME);
	}

	public String [] getID() {
		return MATCH_NAME;
	}

	public Tag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new FrameSetTag(tagData,compositeTagData);
	}

}
