// HTMLParser Library v1_2_20021215 - A java-based parser for HTML
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

// Author of this class : Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package org.htmlparser.tests.tagTests;

import java.io.*;
import junit.framework.*;
import org.htmlparser.*;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.*;

public class HTMLInputTagTest extends HTMLParserTestCase {
	private String testHTML = new String("<INPUT type=\"text\" name=\"Google\">");

	public HTMLInputTagTest(String name) 
	{
		super(name);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		createParser(testHTML,"http://www.google.com/test/index.html");
		parser.addScanner(new HTMLInputTagScanner("-i"));
	}
	
	public void testToHTML() throws HTMLParserException 
	{
		parseAndAssertNodeCount(1);
		assertTrue("Node 1 should be INPUT Tag",node[0] instanceof HTMLInputTag);
		HTMLInputTag InputTag;
		InputTag = (HTMLInputTag) node[0];
		assertEquals("HTML String","<INPUT NAME=\"Google\" TYPE=\"text\">",InputTag.toHTML());
	}
	
	public void testToString() throws HTMLParserException 
	{
		parseAndAssertNodeCount(1);
		assertTrue("Node 1 should be INPUT Tag",node[0] instanceof HTMLInputTag);
		HTMLInputTag InputTag;
		InputTag = (HTMLInputTag) node[0];
		assertEquals("HTML Raw String","INPUT TAG\n--------\nNAME : Google\nTYPE : text\n",InputTag.toString());
	}
	
}