// HTMLParser Library v1_2 - A java-based parser for HTML
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

package org.htmlparser.tests.tagTests;

import java.util.Hashtable;

import org.htmlparser.tags.HTMLAppletTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLAppletTagTest extends HTMLParserTestCase {

	public HTMLAppletTagTest(String name) {
		super(name);
	}

	public void testToHTML() throws HTMLParserException {
		String [][]paramsData = {{"Param1","Value1"},{"Name","Somik"},{"Age","23"}};
		Hashtable paramsMap = new Hashtable();
		String testHTML = new String("<APPLET CODE=Myclass.class ARCHIVE=test.jar CODEBASE=www.kizna.com>\n");
		for (int i = 0;i<paramsData.length;i++)
		{
			testHTML+="<PARAM NAME=\""+paramsData[i][0]+"\" VALUE=\""+paramsData[i][1]+"\">\n";
			paramsMap.put(paramsData[i][0],paramsData[i][1]);
		}
		testHTML+=
			"</APPLET>\n"+
			"</HTML>";
		createParser(testHTML);
		// Register the image scanner
		parser.registerScanners();
		parseAndAssertNodeCount(2);
		assertTrue("Node should be an applet tag",node[0] instanceof HTMLAppletTag);
		// Check the data in the applet tag
		HTMLAppletTag appletTag = (HTMLAppletTag)node[0];
		String expectedRawString = 
		"<APPLET CODE=Myclass.class ARCHIVE=test.jar CODEBASE=www.kizna.com>\r\n"+
		"<PARAM NAME=\"Name\" VALUE=\"Somik\">\r\n"+
		"<PARAM NAME=\"Param1\" VALUE=\"Value1\">\r\n"+
		"<PARAM NAME=\"Age\" VALUE=\"23\">\r\n"+
		"</APPLET>";
		assertEquals("Raw String",expectedRawString,appletTag.toHTML());				
	}
}
