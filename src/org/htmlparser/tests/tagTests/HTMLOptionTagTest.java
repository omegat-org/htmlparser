// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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

import org.htmlparser.scanners.HTMLOptionTagScanner;
import org.htmlparser.tags.HTMLOptionTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLOptionTagTest extends HTMLParserTestCase 
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
	
	public HTMLOptionTagTest(String name) 
	{
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		createParser(testHTML);
		parser.addScanner(new HTMLOptionTagScanner("-i"));
		parseAndAssertNodeCount(11);
	}	
	
	public void testToHTML() throws HTMLParserException 
	{
		for(int j=0;j<nodeCount;j++)
		{
			assertTrue("Node " + j + " should be Option Tag",node[j] instanceof HTMLOptionTag);
		}
		HTMLOptionTag OptionTag;
		OptionTag = (HTMLOptionTag) node[0];
		assertStringEquals("HTML String","<OPTION VALUE=\"Google Search\">Google</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[1];
		assertStringEquals("HTML String","<OPTION VALUE=\"AltaVista Search\">AltaVista</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[2];
		assertStringEquals("HTML String","<OPTION VALUE=\"Lycos Search\"></OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[3];
		assertStringEquals("HTML String","<OPTION>Yahoo!</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[4];
		assertStringEquals("HTML String","<OPTION>Hotmail</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[5];
		assertStringEquals("HTML String","<OPTION VALUE=\"ICQ Messenger\"></OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[6];
		assertStringEquals("HTML String","<OPTION>Mailcity\r\n</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[7];
		assertStringEquals("HTML String","<OPTION>Indiatimes\r\n</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[8];
		assertStringEquals("HTML String","<OPTION>Rediff\r\n</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[9];
		assertStringEquals("HTML String","<OPTION>Cricinfo</OPTION>",OptionTag.toHTML());
		OptionTag = (HTMLOptionTag) node[10];
		assertStringEquals("HTML String","<OPTION VALUE=\"Microsoft Passport\"></OPTION>",OptionTag.toHTML());
	}	
	
	public void testToString() throws HTMLParserException 
	{
		for(int j=0;j<nodeCount;j++)
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
