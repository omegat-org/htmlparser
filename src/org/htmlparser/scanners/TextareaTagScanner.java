// HTMLParser Library v1_4_20030629 - A java-based parser for HTML
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

import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class TextareaTagScanner extends CompositeTagScanner
{
	private static final String MATCH_NAME [] = {"TEXTAREA"};
	private static final String [] ENDERS = { "INPUT", "TEXTAREA", "SELECT", "OPTION" };
    private static final String [] END_TAG_ENDERS = {"FORM", "BODY", "HTML" };
	private Stack stack;

	public TextareaTagScanner(Stack stack)
	{
		this("", stack);
	}
	
	public TextareaTagScanner(String filter, Stack stack)
	{
        super(filter, MATCH_NAME, ENDERS, END_TAG_ENDERS, false);
        this.stack = stack;
	}

	public String [] getID() {
		return MATCH_NAME;
	}

	public Tag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
        if (!stack.empty () && (this == stack.peek ()))
            stack.pop ();
		return new TextareaTag(tagData,compositeTagData);
	}

	public void beforeScanningStarts ()
    {
        stack.push (this);
	}

	/**
	 * This is the logic that decides when a option tag can be allowed
	 */
	public boolean shouldCreateEndTagAndExit ()
    {
        boolean ret;
        
        ret = false;

		if (0 != stack.size ())
        {
            TagScanner parentScanner = (TagScanner)stack.peek ();
            if (parentScanner instanceof CompositeTagScanner)
            {
                CompositeTagScanner scanner = (CompositeTagScanner)parentScanner;
                if (scanner.tagEnderSet.contains (MATCH_NAME[0])) // should loop over names
                {
                    stack.pop ();
                    ret = true;
                }
            }
        }
        
        return (ret);
	}
}
