
// HTMLParser Library v1_2_20021031 - A java-based parser for HTML
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

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Hashtable;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLAppletTag;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLAppletTagTest extends TestCase {

	/**
	 * Constructor for HTMLAppletTagTest.
	 * @param arg0
	 */
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
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.registerScanners();
			
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 2 node identified",new Integer(2),new Integer(i));	
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
	public static TestSuite suite() {
		return new TestSuite(HTMLAppletTagTest.class);
	}
}
