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
//
// Author of this class : Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package org.htmlparser.tests.tagTests;

import java.io.*;
import java.util.*;
import org.htmlparser.*;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.*;

import junit.framework.*;

public class HTMLSelectTagTest extends HTMLParserTestCase
{
	private String testHTML = new String(
									"<SELECT name=\"Nominees\">\n"+
									"<option value=\"Spouse\">Spouse"+
									"<option value=\"Father\"></option>\n"+
									"<option value=\"Mother\">Mother\n" +
									"<option value=\"Son\">\nSon\n</option>"+
									"<option value=\"Daughter\">\nDaughter\n"+
									"<option value=\"Nephew\">\nNephew</option>\n"+
									"<option value=\"Niece\">Niece\n" +
									"</select>"
									);
	
	public HTMLSelectTagTest(String name) 
	{
		super(name);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		createParser(testHTML);
		parser.addScanner(new HTMLSelectTagScanner("-s"));
		
		parseAndAssertNodeCount(1);
	}
	
	public void testToHTML() throws HTMLParserException 
	{
		assertTrue("Node 1 should be Select Tag",node[0] instanceof HTMLSelectTag);
		HTMLSelectTag SelectTag;
		SelectTag = (HTMLSelectTag) node[0];
		assertEquals("HTML String","<SELECT NAME=\"Nominees\">\r\n"+
									"<OPTION VALUE=\"Spouse\">Spouse</OPTION>\r\n"+
									"<OPTION VALUE=\"Father\"></OPTION>\r\n"+
									"<OPTION VALUE=\"Mother\">Mother\r\n</OPTION>\r\n" +
									"<OPTION VALUE=\"Son\">Son\r\n</OPTION>\r\n"+
									"<OPTION VALUE=\"Daughter\">Daughter\r\n</OPTION>\r\n"+
									"<OPTION VALUE=\"Nephew\">Nephew</OPTION>\r\n"+
									"<OPTION VALUE=\"Niece\">Niece\r\n</OPTION>\r\n"+
									"</SELECT>",
									SelectTag.toHTML());
	}	
	
	
	public void testToString() throws HTMLParserException 
	{
		assertTrue("Node 1 should be Select Tag",node[0] instanceof HTMLSelectTag);
		HTMLSelectTag SelectTag;
		SelectTag = (HTMLSelectTag) node[0];
		assertStringEquals("HTML Raw String","SELECT TAG\n--------\nNAME : Nominees\n" +
								"OPTION TAG\n--------\nVALUE : Spouse\nTEXT : Spouse\n\n" +
								"OPTION TAG\n--------\nVALUE : Father\nTEXT : \n\n" +
								"OPTION TAG\n--------\nVALUE : Mother\nTEXT : Mother\r\n\n\n" +
								"OPTION TAG\n--------\nVALUE : Son\nTEXT : Son\r\n\n\n" +
								"OPTION TAG\n--------\nVALUE : Daughter\nTEXT : Daughter\r\n\n\n" +
								"OPTION TAG\n--------\nVALUE : Nephew\nTEXT : Nephew\n\n" +
								"OPTION TAG\n--------\nVALUE : Niece\nTEXT : Niece\r\n\n\n",
							SelectTag.toString());
	}
	
}
