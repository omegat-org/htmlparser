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
//
// Author of this class : Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package com.kizna.htmlTests.tagTests;

import java.io.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

import junit.framework.*;

public class HTMLOptionTagTest extends TestCase 
{
	private String testHTML = new String(
									"<OPTION value=\"Google Search\">Google</OPTION>" +
									"<OPTION value=\"AltaVista Search\">AltaVista" +
									"<OPTION value=\"Lycos Search\"></OPTION>" +
									"<OPTION>Yahoo!</OPTION>" + 
									"<OPTION>\nHotmail</OPTION>" +
									"<OPTION value=\"ICQ Messenger\">" +
									"<OPTION>Mailcity\n</OPTION>"+
									"<OPTION>\nIndiatimes\n</OPTION>"+
									"<OPTION>\nRediff\n</OPTION>\n"+
									"<OPTION>Cricinfo" +
									"<OPTION value=\"Microsoft Passport\">"
									);
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLOptionTagTest.
	 * @param arg0
	 */
	public HTMLOptionTagTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLOptionTagTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLOptionTagTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];

		reader.getParser().addScanner(new HTMLOptionTagScanner("-i"));
		
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
	}
	
	public void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testToHTML() throws HTMLParserException 
	{
		
		assertEquals("There should be 11 nodes identified",11,i);	
		for(int j=0;j<i;j++)
		{
			assertTrue("Node " + j + " should be Option Tag",node[j] instanceof HTMLOptionTag);
		}
		HTMLOptionTag OptionTag;
		OptionTag = (HTMLOptionTag) node[0];
		assertEquals("HTML String","<OPTION VALUE=\"Google Search\">Google</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[1];
		assertEquals("HTML String","<OPTION VALUE=\"AltaVista Search\">AltaVista</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[2];
		assertEquals("HTML String","<OPTION VALUE=\"Lycos Search\"></OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[3];
		assertEquals("HTML String","<OPTION>Yahoo!</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[4];
		assertEquals("HTML String","<OPTION>Hotmail</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[5];
		assertEquals("HTML String","<OPTION VALUE=\"ICQ Messenger\"></OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[6];
		assertEquals("HTML String","<OPTION>Mailcity\r\n</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[7];
		assertEquals("HTML String","<OPTION>Indiatimes\r\n</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[8];
		assertEquals("HTML String","<OPTION>Rediff\r\n</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[9];
		assertEquals("HTML String","<OPTION>Cricinfo</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[10];
		assertEquals("HTML String","<OPTION VALUE=\"Microsoft Passport\"></OPTION>",OptionTag.toHTML());
	}	
	
	public void testToString() throws HTMLParserException 
	{
		
		assertEquals("There should be 11 nodes identified",11,i);	
		for(int j=0;j<i;j++)
		{
			assertTrue("Node " + j + " should be Option Tag",node[j] instanceof HTMLOptionTag);
		}
		HTMLOptionTag OptionTag;
		OptionTag = (HTMLOptionTag) node[0];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nVALUE : Google Search\nTEXT : Google\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[1];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nVALUE : AltaVista Search\nTEXT : AltaVista\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[2];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nVALUE : Lycos Search\nTEXT : \n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[3];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nTEXT : Yahoo!\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[4];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nTEXT : Hotmail\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[5];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nVALUE : ICQ Messenger\nTEXT : \n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[6];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nTEXT : Mailcity\r\n\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[7];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nTEXT : Indiatimes\r\n\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[8];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nTEXT : Rediff\r\n\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[9];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nTEXT : Cricinfo\n",OptionTag.toString());
		OptionTag = (HTMLOptionTag) node[10];
		assertEquals("HTML Raw String","OPTION TAG\n--------\nVALUE : Microsoft Passport\nTEXT : \n",OptionTag.toString());
	}
	
}
