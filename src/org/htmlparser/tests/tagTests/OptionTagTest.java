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
//
// Author of this class : Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package org.htmlparser.tests.tagTests;

import java.util.Stack;

import org.htmlparser.scanners.OptionTagScanner;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class OptionTagTest extends ParserTestCase 
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
//									"<OPTION value=\"AOL\"><SPAN>AOL</SPAN></OPTION>" +
//									"<OPTION value=\"Time Warner\">Time <LABEL>Warner <SPAN>AOL </SPAN>Inc.</LABEL>"
									);
	
	public OptionTagTest(String name) 
	{
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		createParser(testHTML);
		parser.addScanner(new OptionTagScanner("-option", new Stack ()));
		parseAndAssertNodeCount(11);
	}	
	
	public void testToHTML() throws ParserException 
	{
		for(int j=0;j<nodeCount;j++)
		{
			//assertTrue("Node " + j + " should be Option Tag",node[j] instanceof OptionTag);
			System.out.println(node[j].getClass().getName());
			System.out.println(node[j].toHtml());
		}
		OptionTag OptionTag;
		OptionTag = (OptionTag) node[0];
		assertStringEquals("HTML String","<OPTION VALUE=\"Google Search\">Google</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[1];
		assertStringEquals("HTML String","<OPTION VALUE=\"AltaVista Search\">AltaVista</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[2];
		assertStringEquals("HTML String","<OPTION VALUE=\"Lycos Search\"></OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[3];
		assertStringEquals("HTML String","<OPTION>Yahoo!</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[4];
		assertStringEquals("HTML String","<OPTION>\r\nHotmail</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[5];
		assertStringEquals("HTML String","<OPTION VALUE=\"ICQ Messenger\"></OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[6];
		assertStringEquals("HTML String","<OPTION>Mailcity\r\n</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[7];
		assertStringEquals("HTML String","<OPTION>\r\nIndiatimes\r\n</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[8];
		assertStringEquals("HTML String","<OPTION>\r\nRediff\r\n</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[9];
		assertStringEquals("HTML String","<OPTION>Cricinfo</OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[10];
		assertStringEquals("HTML String","<OPTION VALUE=\"Microsoft Passport\"></OPTION>",OptionTag.toHtml());
/*		OptionTag = (OptionTag) node[11];
		assertStringEquals("HTML String","<OPTION VALUE=\"AOL\"><SPAN>AOL</SPAN></OPTION>",OptionTag.toHtml());
		OptionTag = (OptionTag) node[12];
		assertStringEquals("HTML String","<OPTION value=\"Time Warner\">Time <LABEL>Warner <SPAN>AOL </SPAN>Inc.</LABEL></OPTION>",OptionTag.toHtml());
*/	}	
	
	public void testToString() throws ParserException 
	{
		for(int j=0;j<11;j++)
		{
			assertTrue("Node " + j + " should be Option Tag",node[j] instanceof OptionTag);
		}
		OptionTag OptionTag;
		OptionTag = (OptionTag) node[0];
		assertEquals("HTML Raw String","OPTION VALUE: Google Search TEXT: Google\n",OptionTag.toString());
		OptionTag = (OptionTag) node[1];
		assertEquals("HTML Raw String","OPTION VALUE: AltaVista Search TEXT: AltaVista\n",OptionTag.toString());
		OptionTag = (OptionTag) node[2];
		assertEquals("HTML Raw String","OPTION VALUE: Lycos Search TEXT: \n",OptionTag.toString());
		OptionTag = (OptionTag) node[3];
		assertEquals("HTML Raw String","OPTION VALUE: null TEXT: Yahoo!\n",OptionTag.toString());
		OptionTag = (OptionTag) node[4];
		assertEquals("HTML Raw String","OPTION VALUE: null TEXT: Hotmail\n",OptionTag.toString());
		OptionTag = (OptionTag) node[5];
		assertEquals("HTML Raw String","OPTION VALUE: ICQ Messenger TEXT: \n",OptionTag.toString());
		OptionTag = (OptionTag) node[6];
		assertEquals("HTML Raw String","OPTION VALUE: null TEXT: Mailcity\r\n\n",OptionTag.toString());
		OptionTag = (OptionTag) node[7];
		assertEquals("HTML Raw String","OPTION VALUE: null TEXT: Indiatimes\r\n\n",OptionTag.toString());
		OptionTag = (OptionTag) node[8];
		assertEquals("HTML Raw String","OPTION VALUE: null TEXT: Rediff\r\n\n",OptionTag.toString());
		OptionTag = (OptionTag) node[9];
		assertEquals("HTML Raw String","OPTION VALUE: null TEXT: Cricinfo\n",OptionTag.toString());
		OptionTag = (OptionTag) node[10];
		assertEquals("HTML Raw String","OPTION VALUE: Microsoft Passport TEXT: \n",OptionTag.toString());
/*		OptionTag = (OptionTag) node[11];
		assertEquals("HTML Raw String","OPTION VALUE: AOL TEXT: AOL\n",OptionTag.toString());
		OptionTag = (OptionTag) node[12];
		assertEquals("HTML Raw String","OPTION VALUE: Time Warner TEXT: Time Warner AOL Inc.\n",OptionTag.toString());
*/	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {OptionTagTest.class.getName()});
	}
	
}
