// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package org.htmlparser.tests.utilTests;

import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLTagParser;
import org.htmlparser.tests.tagTests.HTMLTagTest;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLTagParserTest extends TestCase {
	private HTMLTagParser tagParser;
	/**
	 * Constructor for HTMLTagParserTest.
	 * @param arg0
	 */
	public HTMLTagParserTest(String arg0) {
		super(arg0);
	}
    public void testCorrectTag() {
    	HTMLTag tag = new HTMLTag(0,20,"font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"","<font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\">");
		tagParser.correctTag(tag);
		HTMLTagTest.assertStringEquals("Corrected Tag","font face=\"Arial,helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"",tag.getText());
    }	
	public void testInsertInvertedCommasCorrectly() {
		StringBuffer test = new StringBuffer("a b=c d e = f"); 
		StringBuffer result = tagParser.insertInvertedCommasCorrectly(test);
		HTMLTagTest.assertStringEquals("Expected Correction","a b=\"c d\" e=\"f\"",result.toString());
		
	}
	public void testPruneSpaces() {
		String test = "  fdfdf dfdf   ";
		assertEquals("Expected Pruned string","fdfdf dfdf",tagParser.pruneSpaces(test));
	}   
	protected void setUp() {
		tagParser = new HTMLTagParser(new DefaultHTMLParserFeedback());	
	} 
	public static TestSuite suite() {
		return new TestSuite(HTMLTagParserTest.class);
	}
}
