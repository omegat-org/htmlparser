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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.htmlTests.tagTests;

import java.io.StringReader;

import com.kizna.html.tags.HTMLBaseHREFTag;


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
public class HTMLBaseHREFTagTest extends TestCase {

	/**
	 * Constructor for HTMLBaseHREFTagTest.
	 * @param arg0
	 */
	public HTMLBaseHREFTagTest(String arg0) {
		super(arg0);
	}
	public void testToHTML() {
		HTMLBaseHREFTag baseRefTag = new HTMLBaseHREFTag(0,0,"","http://www.abc.com","");
		String expected = "<BASE HREF=\"http://www.abc.com\">";
		assertEquals("Expected HTML Reconstruction",expected,baseRefTag.toHTML());
	}
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLBaseHREFTagTest.class);
		return suite;
	}	
}
